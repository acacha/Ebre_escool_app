package org.acacha.ebre_escool.ebre_escool_app.apis;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Handles the comminication with Parse.com
 *
 * User: udinic
 * Date: 3/27/13
 * Time: 3:30 AM
 */
public class EbreEscoolServerAuthenticate implements ServerAuthenticate{

    private String LOG_TAG = "EbreEscoolServerAuthenticate";

    public static final String DEFAULT_LOGIN_API_URL =
            "https://www.iesebre.com/ebre-escool/index.php/api/ebreescool_login/login";

    public static final String API_KEY = "f8314111f3d35058584b37361dbde919";

    @Override
    public com.squareup.okhttp.Response
        userSignUp( String givenName, String last_name,
                    String username,  String email, String pass, String authType) throws Exception {
        //TODO
        /*
        String url = "https://api.parse.com/1/users";


        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("X-Parse-Application-Id","XUafJTkPikD5XN5HxciweVuSe12gDgk2tzMltOhr");
        httpPost.addHeader("X-Parse-REST-API-Key", "8L9yTQ3M86O4iiucwWb4JS7HkxoSKo7ssJqGChWx");
        httpPost.addHeader("Content-Type", "application/json");

        String user = "{\"username\":\"" + email + "\",\"password\":\"" + pass + "\",\"phone\":\"415-392-0202\"}";
        HttpEntity entity = new StringEntity(user);
        httpPost.setEntity(entity);

        String authtoken = null;
        try {
            HttpResponse response = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != 201) {
                ParseComError error = new Gson().fromJson(responseString, ParseComError.class);
                throw new Exception("Error creating user["+error.code+"] - " + error.error);
            }


            User createdUser = new Gson().fromJson(responseString, User.class);

            authtoken = createdUser.sessionToken;

        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        //TODO
        com.squareup.okhttp.Response response = null;
        return response;
    }

    @Override
    public com.squareup.okhttp.Response userSignIn(String username, String password,
                                                   String authType) throws Exception {
        return userSignIn(username, password, authType, DEFAULT_LOGIN_API_URL);
    }

    @Override
    public com.squareup.okhttp.Response userSignIn(String username, String password,
                                                   String authType, String realm) throws Exception {
        return userSignIn(username, password, authType, realm, "mysql");
    }

    @Override
    public com.squareup.okhttp.Response userSignIn(String username, String password,
                                       String authType, String url, String realm) throws Exception {

        /*
        Prepare POST HTTP request for login into ebre-escool
        KEY: f8314111f3d35058584b37361dbde919 is only valid for accessing LOGIN and no other API
        functions for security reasons. Each user have to obtain his own api-key with specific
        access roles
        */
        RequestBody formBody = new FormEncodingBuilder()
            .add("username", username)
            .add("password", password)
            .add("realm", realm)
            .build();
        Request request = new Request.Builder()
                .url(url)
                .header("X-API-KEY", API_KEY)
                .post(formBody)
                .build();

        Log.d(LOG_TAG, "Request: " + request.toString());
        OkHttpClient selfsignedcertificateclient = getUnsafeOkHttpClient();
        Response response = selfsignedcertificateclient.newCall(request).execute();

        int response_code = response.code();
        String response_message = response.message();

        Log.d(LOG_TAG,"response_code: " + (new Integer(response_code)).toString() );
        Log.d(LOG_TAG,"response_message: " + response_message);
        Log.d(LOG_TAG,"Response: " + response);

        return response;
    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setSslSocketFactory(sslSocketFactory);
            okHttpClient.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}
