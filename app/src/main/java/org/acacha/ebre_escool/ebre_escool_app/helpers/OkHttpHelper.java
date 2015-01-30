package org.acacha.ebre_escool.ebre_escool_app.helpers;

/*
* TODO
*
*/

import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class OkHttpHelper {

    public static final String LOGIN_API_URL =
            "https://www.iesebre.com/ebre-escool/index.php/api/ebreescool_login/login";

    public static final String API_KEY = "f8314111f3d35058584b37361dbde919";

    public static final String DEFAULT_REALM = "mysql";

    public static final String LOG_TAG = "OkHttpHelper";

    final OkHttpClient client = new OkHttpClient();



    public void post2_example_to_remove(String url, String body) throws IOException {
        RequestBody formBody = new FormEncodingBuilder()
                .add("search", "Jurassic Park")
                .build();
        Request request = new Request.Builder()
                .url("https://en.wikipedia.org/w/index.php")
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        System.out.println(response.body().string());
    }

    /*
    Prepare POST HTTP request for login into ebre-escool
    KEY: f8314111f3d35058584b37361dbde919 is only valid for accessing LOGIN and no other API functions
    for security reasons. Each user have to obtain his own api-key with specific access roles
    */
    public Response login_ebreescool(String url, String username, String password, String realm) throws IOException {
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

        Log.d(LOG_TAG, "Response: " + response.toString());

        int response_code = response.code();
        String response_message = response.message();

        Log.d(LOG_TAG,"response_code: " + (new Integer(response_code)).toString() );
        Log.d(LOG_TAG,"response_message: " + response_message);

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


    private void postRequest(String body, OutputStream out) throws IOException {
        Writer writer = new OutputStreamWriter(out, "UTF-8");
        writer.write(body);
        writer.close();
    }

}