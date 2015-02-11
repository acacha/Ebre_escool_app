package org.acacha.ebre_escool.ebre_escool_app.apis;

/**
 * Sergi Tur Badenas
 */
public interface ServerAuthenticate {
    public com.squareup.okhttp.Response
        userSignUp(final String givenName,final String last_name, final String email,
                   final String pass, String url, String authType) throws Exception;
    public com.squareup.okhttp.Response
        userSignIn(final String user, final String pass, String authType) throws Exception;
    public com.squareup.okhttp.Response
        userSignIn(final String user, final String pass, String authType, String url) throws Exception;
    public com.squareup.okhttp.Response
        userSignIn(final String user, final String pass, String authType, String url, String realm) throws Exception;

}
