package org.acacha.ebre_escool.ebre_escool_app;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;


public class InitialSettingsWizardActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_settings_wizard);

        ActionBar ab = getActionBar();
        ab.setTitle(getString(R.string.initial_settings_action_bar_title));
        ab.setSubtitle(getString(R.string.initial_settings_action_bar_subtitle));
    }

}
