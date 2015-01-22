package org.acacha.ebre_escool.ebre_escool_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.codepond.wizardroid.WizardStep;

public class InitialSettingsStep1Schools extends WizardStep {

    /**
     * The collection of all schools in the app.
     */
    private static School[] mSchools;

    private ListView lstSchools;

    //Wire the layout to the step
    public InitialSettingsStep1Schools() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.initialsettingsstep1schools, container, false);

        lstSchools = (ListView) v.findViewById(R.id.schoolslistview);

        // Instantiate the list of samples.
        mSchools = new School[] {
                new School("Institut de l'Ebre"),
                new School("Institut demo")
        };

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