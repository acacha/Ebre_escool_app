package org.acacha.ebre_escool.ebre_escool_app.accounts;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.ResponseBody;

import org.acacha.ebre_escool.ebre_escool_app.initial_settings.EbreEscoolLoginResponse;
import org.acacha.ebre_escool.ebre_escool_app.initial_settings.InitialSettingsActivity;

import java.io.IOException;

import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static org.acacha.ebre_escool.ebre_escool_app.accounts.EbreEscoolAccount.*;

/**
 * Sergi Tur Badenas
 */
public class EbreEscoolAuthenticator extends AbstractAccountAuthenticator {

    private String TAG = "EbreEscoolAuthenticator";
    private final Context mContext;

    public EbreEscoolAuthenticator(Context context) {
        super(context);
        // I hate you! Google - set mContext as protected!
        this.mContext = context;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType,
                                String authTokenType, String[] requiredFeatures, Bundle options)
                                    throws NetworkErrorException {
        Log.d("EbreEscool", TAG + "> addAccount");

        final Intent intent = new Intent(mContext, InitialSettingsActivity.class);
        intent.putExtra(InitialSettingsActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(InitialSettingsActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(InitialSettingsActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {

        Log.d("Ebreescool", TAG + "> getAuthToken");

        // If the caller requested an authToken type we don't support, then
        // return an error
        if (!authTokenType.equals(EbreEscoolAccount.AUTHTOKEN_TYPE_READ_ONLY) && !authTokenType.equals(EbreEscoolAccount.AUTHTOKEN_TYPE_FULL_ACCESS)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        final AccountManager am = AccountManager.get(mContext);

        String authToken = am.peekAuthToken(account, authTokenType);

        Log.d("Ebreescool", TAG + "> peekAuthToken returned - " + authToken);

        // Lets give another try to authenticate the user
        if (TextUtils.isEmpty(authToken)) {
            final String password = am.getPassword(account);
            if (password != null) {
                try {
                    Log.d("Ebreescool", TAG + "> re-authenticating with the existing password");
                    Log.d("Ebreescool", TAG + "> Account name: " + account.name);
                    //Log.d("Ebreescool", TAG + "> Password: " + password);

                    com.squareup.okhttp.Response ebre_escool_response =
                            sServerAuthenticate.userSignIn(account.name, password, authTokenType);

                    ResponseBody body = ebre_escool_response.body();
                    String json_response = null;
                    try {
                        json_response = body.string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG,"response body: " + json_response);
                    EbreEscoolLoginResponse eeresponse = null;
                    Gson gson = new Gson();
                    eeresponse = gson.fromJson(json_response, EbreEscoolLoginResponse.class);

                    //Log.d(TAG,"authToken: " + authToken);
                    authToken = eeresponse.getApiUserProfile().getAuthToken();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
        final Intent intent = new Intent(mContext, InitialSettingsActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(InitialSettingsActivity.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(InitialSettingsActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(InitialSettingsActivity.ARG_ACCOUNT_NAME, account.name);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }


    @Override
    public String getAuthTokenLabel(String authTokenType) {
        if (EbreEscoolAccount.AUTHTOKEN_TYPE_FULL_ACCESS.equals(authTokenType))
            return EbreEscoolAccount.AUTHTOKEN_TYPE_FULL_ACCESS_LABEL;
        else if (EbreEscoolAccount.AUTHTOKEN_TYPE_READ_ONLY.equals(authTokenType))
            return EbreEscoolAccount.AUTHTOKEN_TYPE_READ_ONLY_LABEL;
        else
            return authTokenType + " (Label)";
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(KEY_BOOLEAN_RESULT, false);
        return result;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }
}
