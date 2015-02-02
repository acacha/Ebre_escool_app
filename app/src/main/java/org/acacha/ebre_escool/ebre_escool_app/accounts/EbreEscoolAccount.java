package org.acacha.ebre_escool.ebre_escool_app.accounts;

import org.acacha.ebre_escool.ebre_escool_app.apis.EbreEscoolServerAuthenticate;
import org.acacha.ebre_escool.ebre_escool_app.apis.ServerAuthenticate;

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

    public static final ServerAuthenticate sServerAuthenticate = new EbreEscoolServerAuthenticate();

}
