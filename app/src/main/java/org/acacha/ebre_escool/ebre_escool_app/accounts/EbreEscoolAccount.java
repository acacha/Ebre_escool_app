package org.acacha.ebre_escool.ebre_escool_app.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: Udini
 * Date: 20/03/13
 * Time: 18:11
 */
public class EbreEscoolAccount {

    /**
     * Account type id
     */
    public static final String ACCOUNT_TYPE = "org.acacha.ebre_escool.ebre_escool_app.EbreEscoolAccount";

    /**
     * key of account_name SharedPreferences
     */
    public static final String ACCOUNT_NAME_KEY = "account_name";

    /**
     * Account name
     */
    public static final String ACCOUNT_NAME = "EbreEscool";

    /**
     * Auth token types
     */
    public static final String AUTHTOKEN_TYPE_READ_ONLY = "Read only";
    public static final String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "Read only access to an EbreEscool account";

    public static final String AUTHTOKEN_TYPE_FULL_ACCESS = "Full access";
    public static final String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "Full access to an EbreEscool account";

    //public static final ServerAuthenticate sServerAuthenticate = new ParseComServerAuthenticate();

}
