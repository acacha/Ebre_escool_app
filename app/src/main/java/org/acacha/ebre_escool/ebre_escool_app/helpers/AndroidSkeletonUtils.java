package org.acacha.ebre_escool.ebre_escool_app.helpers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AndroidSkeletonUtils {

	public static final String REVOKE_KEY = null;

    public static final String REMEMBER_LOGIN_PREFERENCE = "remember_login";

    public static final String LOGIN_TYPE_PREFERENCE = "login_type";

    //Look up for shared preferences
    //TODO: remove
    //public static final String PREFS_NAME = "GeneralPreferences";

    public static void debugIntent(Intent intent, String tag) {
        Log.v(tag, "action: " + intent.getAction());
        Log.v(tag, "component: " + intent.getComponent());
        Bundle extras = intent.getExtras();
        if (extras != null) {
            for (String key: extras.keySet()) {
                Log.v(tag, "key [" + key + "]: " +
                        extras.get(key));
            }
        }
        else {
            Log.v(tag, "no extras");
        }
    }

}
