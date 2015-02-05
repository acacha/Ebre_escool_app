package org.acacha.ebre_escool.ebre_escool_app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.acacha.ebre_escool.ebre_escool_app.accounts.EbreEscoolAccount;
import org.acacha.ebre_escool.ebre_escool_app.apis.EbreEscoolAPI;
import org.acacha.ebre_escool.ebre_escool_app.apis.EbreEscoolApiService;
import org.acacha.ebre_escool.ebre_escool_app.initial_settings.InitialSettingsActivity;
import org.acacha.ebre_escool.ebre_escool_app.helpers.ConnectionDetector;
import org.acacha.ebre_escool.ebre_escool_app.pojos.School;
import org.acacha.ebre_escool.ebre_escool_app.settings.SettingsActivity;

import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashScreen extends Activity {

    // Splash screen timer
    private final static int DEFAULT_SPLASH_TIME_OUT = 10000;

	// Splash screen timer not first time
	private final static int NOT_FIRST_TIME_SPLASH_TIME_OUT = 3000;

    private int timeout = DEFAULT_SPLASH_TIME_OUT;

    //Look up for shared preferences
    final String LOG_TAG = "SplashScreen";

    //Look up for shared preferences
    final String FIRST_TIME_PREFERENCE = "first_time_execution";

    //Look up for shared preferences
    final String CONFIG_WIZARD_FINALIZED_OK = "config_wizard_ok";

    Class next_activity = InitialSettingsActivity.class;

    //settings
    private SharedPreferences settings;

    private AccountManager mAccountManager;

    AlertDialog alertDialog = null;

    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     * 				 - pass null if you don't want icon
     * */
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {

        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        // Setting alert dialog icon
        if(status != null)
            alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //CHECK CONNECTION
                ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                if (! cd.isConnectingToInternet()) {
                    Log.d(LOG_TAG,"Connectivity is not Ok. Show Alert...");
                    showAlertDialog(SplashScreen.this, getString(R.string.internet_connection_error_title),
                            getString(R.string.internet_connection_error_label), false);
                } else {
                    on_create_after_checking_connection();
                }

            }
        });

        // Setting Config
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Configuració", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                //Offer user the possibility to change wifi/network settings:
                //http://acacha.org/mediawiki/index.php/Android_HTTP#Comprovar_la_connexi.C3.B3_de_xarxa
                //Intent intent = new Intent(Intent.ACTION_MAIN);
                //intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                //startActivity(intent);

                //Para abrir la configuración de datos móviles:
                //Intent intent = new Intent();
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //intent.setAction(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
                //startActivity(intent);

                //Intent intent = new Intent(Intent.ACTION_MAIN);
                //intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                //startActivity(intent);

                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);
                startActivity(intent);
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

        settings = PreferenceManager.getDefaultSharedPreferences(this);

        mAccountManager = AccountManager.get(this);

        //CHECK CONNECTION
        ConnectionDetector cd = new ConnectionDetector(this);
        if (! cd.isConnectingToInternet()) {
            // Internet Connection is not present
            Log.d(LOG_TAG,"Connectivity is not Ok. Show Alert...");
            showAlertDialog(SplashScreen.this, getString(R.string.internet_connection_error_title),
            getString(R.string.internet_connection_error_label), false);
        } else {
            Log.d(LOG_TAG,"Connectivity is Ok. Continue execution...");
            on_create_after_checking_connection();
        }
        //END CHECK CONNECTION
    }

    private void on_create_after_checking_connection() {
        //First of all try to get AuthToken. But first we need to know wif we have accounts and if
        //one of these accounts are for account_name stored in shared preference.
        // IF not continue execution
        final Account availableAccounts[] =
                mAccountManager.getAccountsByType(EbreEscoolAccount.ACCOUNT_TYPE);

        if (availableAccounts.length == 0) {
            Log.d(LOG_TAG,"No accounts found at AccountManager!");
            continue_execution(null);
        }
        Log.d(LOG_TAG,"Found " + availableAccounts.length + " accounts of type " + EbreEscoolAccount.ACCOUNT_TYPE);
        //Example: String account_name = "sergitur" or sergiturbadenas@gmail.com;
        String account_name = settings.getString(SettingsActivity.ACCOUNT_NAME_KEY, "");
        if (account_name == "") {
            Log.d(LOG_TAG,"No account name found at SharedPreferences with key " + SettingsActivity.ACCOUNT_NAME_KEY);
            continue_execution(null);
        }

        int account_position = -1;
        for (int i = 0; i < availableAccounts.length; i++) {
            Log.d(LOG_TAG,"iteration: " + i);
            Log.d(LOG_TAG,"availableAccounts[i].name: " + availableAccounts[i].name);
            String name = availableAccounts[i].name;
            if ( name.equals(account_name)) {
                Log.d(LOG_TAG,"found coincidence! at position: " + i);
                account_position = i;
            }
        }

        if (account_position == -1) {
            Log.d(LOG_TAG,"Account " + account_name + " not found!");
            continue_execution(null);
        }

        //get AuthToken
        //AsyncTask: We have to wait task to finish to continue execution:
        //  See postExecute of AsyncTask
        //future could not be used at UI thread!
        try {
            final AccountManagerFuture<Bundle> future =
                    mAccountManager.getAuthToken(availableAccounts[account_position],
                            EbreEscoolAccount.AUTHTOKEN_TYPE_FULL_ACCESS, null, this, null, null);
            new GetAuthTokenAT().execute(future);
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            aiobe.printStackTrace();
            Log.d(LOG_TAG,"Error getting account manager info. Continue Execution");
            continue_execution(null);
        } catch (Exception e ){
            e.printStackTrace();
            Log.d(LOG_TAG,"Error getting account manager info. Continue Execution");
            continue_execution(null);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG,"onStop executed!");
        if (alertDialog !=null) {
            alertDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG,"onDestroy executed!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG,"OnPause executed!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG,"onActivityResult executed!");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG,"onRestart executed!");

        //CHECK CONNECTION AGAIN
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        if (! cd.isConnectingToInternet()) {
            Log.d(LOG_TAG,"Connectivity is not Ok. Show Alert...");
            showAlertDialog(SplashScreen.this, getString(R.string.internet_connection_error_title),
                    getString(R.string.internet_connection_error_label), false);
        } else {
            on_create_after_checking_connection();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG,"onResume executed!");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG,"onStart executed!");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(LOG_TAG,"onPostResume executed!");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d(LOG_TAG,"onPostCreate executed!");
    }

    private void wait_and_show_splash_screen(boolean download_initial_data) {
        if (download_initial_data) {
            download_initial_data();
        }
        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, next_activity);
                startActivity(i);
                // close this activity
                finish();
            }
        }, this.timeout);
    }

    private void wait_and_show_splash_screen() {
        wait_and_show_splash_screen(false);
    }

    private void download_initial_data() {
        //Connect to api to download info during splash screen execution
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(EbreEscoolAPI.EBRE_ESCOOL_PUBLIC_API_URL)
                .build();

        EbreEscoolApiService service = restAdapter.create(EbreEscoolApiService.class);

        Callback callback = new Callback<Map<String, School>>() {
            @Override
            public void success(Map<String, School> schools, Response response) {
                Log.d(LOG_TAG, "************ Schools ##################: " + schools);

                //Save MAP AND LIST OF SCHOOLS as Shared Preferences
                Gson gson = new Gson();
                String schools_json = gson.toJson(schools);
                String schools_list_json = gson.toJson(schools.values().toArray());
                settings.edit().putString("schools_map", schools_json).apply();
                settings.edit().putString("schools_list", schools_list_json).apply();

            }
            @Override
            public void failure(RetrofitError retrofitError) {
                String text = "!!!! ERROR: " + retrofitError;
                Log.d(LOG_TAG, text);

                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();

            }
        };
        service.schools(callback);
    }

    private class GetAuthTokenAT extends AsyncTask<AccountManagerFuture<Bundle>, Void, String> {

        @Override
        protected String doInBackground(AccountManagerFuture<Bundle>... args) {

            String auth_token = null;
            try {
                Bundle bnd = args[0].getResult();
                Log.d(LOG_TAG, "GetToken Bundle is " + bnd);
                Log.d(LOG_TAG, "AccountManager.KEY_AUTHTOKEN: " + AccountManager.KEY_AUTHTOKEN);

                auth_token = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                Log.d(LOG_TAG, "authtoken: " + auth_token);
                if (auth_token != null) {
                    Log.d(LOG_TAG, "Token obtained ok!");
                    return auth_token;
                } else {
                    Log.d(LOG_TAG, "AccountManager.KEY_AUTHTOKEN: " + AccountManager.KEY_AUTHTOKEN);
                    return null;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                return auth_token;
            }

        }

        @Override
        protected void onPostExecute(String auth_token) {
            Log.d(LOG_TAG,"Authtoken on onPostExecute: " + auth_token);
            continue_execution(auth_token);
        }
    }

    private void continue_execution(String auth_token){
        Log.d(LOG_TAG,"Authtoken: " + auth_token);
        //Save auth_token to SharedPreferences


        //Check if splash screen execution is needed
        // Splash screen executed always on first time or if we have to show
        // initial settings (but smaller timeout)
        boolean first_time = settings.getBoolean(FIRST_TIME_PREFERENCE, true);

        //Continue execution
        boolean initial_settings_ok = false;

        if (auth_token != null) {
            initial_settings_ok = true;
        }


        // initial_settings_ok = 1 | first_time = 0 <- MOST COMMON FIRST TO CHECK
        if ( initial_settings_ok && (!first_time) ) {
            Log.d(LOG_TAG, "NOT First time execution of ebre-escool app");
            Log.d(LOG_TAG, "INITIAL_SETTINGS OK");
            //SKIP SPLASH SCREEN AND GOT TO MAIN ACTIVITY
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        if ( (!initial_settings_ok) && first_time ) {
            //FIRST TIME EXECUTION NO PREVIOUS DATA
            Log.d(LOG_TAG, "First time execution of ebre-escool app");
            Log.d(LOG_TAG, "INITIAL_SETTINGS NOT OK");
            // record the fact that the app has been started at least once
            settings.edit().putBoolean(FIRST_TIME_PREFERENCE, false).apply();
            timeout = NOT_FIRST_TIME_SPLASH_TIME_OUT;
            next_activity = InitialSettingsActivity.class;
            wait_and_show_splash_screen(true);
        }

        if ( (!initial_settings_ok) && (!first_time) ) {
            Log.d(LOG_TAG, "NOT First time execution of ebre-escool app");
            Log.d(LOG_TAG, "INITIAL_SETTINGS NOT OK");
            //NOT FIRST TIME EXECUTION BUT PREVIOUS INITIAL SETTINGS NOT FINISHED OK
            timeout = NOT_FIRST_TIME_SPLASH_TIME_OUT;
            next_activity = InitialSettingsActivity.class;
            wait_and_show_splash_screen(true);
        }

        // initial_settings_ok = 1 | first_time = 1 <- It could be! Configured Account with Android Account manager but first time execution!
        if ( initial_settings_ok && first_time ) {
            Log.d(LOG_TAG, "First time execution of ebre-escool app");
            Log.d(LOG_TAG, "INITIAL_SETTINGS OK");
            // record the fact that the app has been started at least once
            settings.edit().putBoolean(FIRST_TIME_PREFERENCE, false).apply();
            //SHOW SPLASH SCREEN AND GOT TO MAIN ACTIVITY
            next_activity = MainActivity.class;
            wait_and_show_splash_screen();
        }
        //APP NEVER ARRIVES HERE!
    }


}
