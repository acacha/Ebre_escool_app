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
    /*


    public static final String LOGIN_API_URL =
            "https://www.iesebre.com/ebre-escool/index.php/api/ebreescool_login/login";

    public static final String API_KEY = "f8314111f3d35058584b37361dbde919";

    public static final String DEFAULT_REALM = "mysql";

    public static final String LOG_TAG = "OkHttpHelper";

    final OkHttpClient client = new OkHttpClient();
    */

/*

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

*/

/*
    private void postRequest(String body, OutputStream out) throws IOException {
        Writer writer = new OutputStreamWriter(out, "UTF-8");
        writer.write(body);
        writer.close();
    }*/

}