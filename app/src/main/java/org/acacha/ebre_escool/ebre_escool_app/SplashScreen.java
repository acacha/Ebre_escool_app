package org.acacha.ebre_escool.ebre_escool_app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int DEFAULT_SPLASH_TIME_OUT = 10000;

	// Splash screen timer not first time
	private static int NOT_FIRST_TIME_SPLASH_TIME_OUT = 3000;

    //Look up for shared preferences
    final String LOG_TAG = "SplashScreen";

    //Look up for shared preferences
    final String FIRST_TIME_PREFERENCE = "first_time_execution";

    //Look up for shared preferences
    final String CONFIG_WIZARD_FINALIZED_OK = "config_wizard_ok";

    Class next_activity = LoginActivity.class;

    //settings
    private SharedPreferences settings;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

        settings = getSharedPreferences(AndroidSkeletonUtils.PREFS_NAME, 0);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://acacha.org/acacha_manager/index.php/ebre_escool/api")
                .build();

        EbreEscoolApiService service = restAdapter.create(EbreEscoolApiService.class);

        Callback callback = new Callback<Map<String, School>>() {
            @Override
            public void success(Map<String, School> schools, Response response) {
                /*
                CharSequence text = "Schools: " + schools;
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();*/
                Log.d(LOG_TAG, "************ Schools ##################: " + schools);

                //To Save:
                Gson gson = new Gson();
                String schools_json = gson.toJson(schools);
                String schools_list_json = gson.toJson(schools.values().toArray());
                settings.edit().putString("schools", schools_json).apply();
                settings.edit().putString("schools_list", schools_list_json).apply();


                continueSplashScreen();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d(LOG_TAG, "!!!! ERROR ##################: " + retrofitError);

                CharSequence text = "!!!! ERROR: " + retrofitError;
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();

                continueSplashScreen();
            }
        };

        service.schools(callback);
	}


    protected void continueSplashScreen() {

        int timeout = DEFAULT_SPLASH_TIME_OUT;

        if (settings.getBoolean(FIRST_TIME_PREFERENCE, true)) {
            //the app is being launched for first time, do something
            Log.d(LOG_TAG, "First time execution of ebre-escool app");
            // record the fact that the app has been started at least once
            settings.edit().putBoolean(FIRST_TIME_PREFERENCE, false).apply();

            // first time task

            //TODO: Connect to server: Obtain list of schools & save for wizard on next step

            next_activity = InitialSettingsWizardActivity.class;
        } else {
            //NOT FIRST TIME

            //TODO: Check if initial wizard is completed ok. if not show wizard on next activity
            if (true) {
                next_activity = InitialSettingsWizardActivity.class;
            }

            timeout = NOT_FIRST_TIME_SPLASH_TIME_OUT;
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
        }, timeout);

    }
}
