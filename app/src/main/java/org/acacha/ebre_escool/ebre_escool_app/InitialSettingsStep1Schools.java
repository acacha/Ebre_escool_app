package org.acacha.ebre_escool.ebre_escool_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.codepond.wizardroid.WizardStep;

import java.lang.reflect.Type;
import java.util.Map;

public class InitialSettingsStep1Schools extends WizardStep {

    //Look up for shared preferences
    final String LOG_TAG = "InitialSettingsStep1Schools";

    /**
     * The collection of all schools in the app.
     */
    private static School[] mSchools;

    private ListView lstSchools;

    //settings
    private SharedPreferences settings;

    //Wire the layout to the step
    public InitialSettingsStep1Schools() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.initialsettingsstep1schools, container, false);

        settings = getActivity().getSharedPreferences(AndroidSkeletonUtils.PREFS_NAME, 0);

        lstSchools = (ListView) v.findViewById(R.id.schoolslistview);

        // Instantiate the list of samples.
        mSchools = new School[] {
                new School("Institut de l'Ebre"),
                new School("Institut demo")
        };

        //Retrieve schools on JSON format
        String json_schools = settings.getString("schools", "");
        String json_schools_list = settings.getString("schools_list", "");
        Log.d(LOG_TAG,"###### json_schools: " + json_schools);
        Log.d(LOG_TAG,"###### json_schools_list: " + json_schools_list);

        Gson gson = new Gson();
        School[] mSchools = gson.fromJson(json_schools_list, School[].class);

        lstSchools.setAdapter(new ArrayAdapter<School>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mSchools));

        /*
        setListAdapter(new ArrayAdapter<School>(getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mSchools));
        */

        return v;
    }

}