package org.acacha.ebre_escool.ebre_escool_app;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.layouts.BasicWizardLayout;

public class InitialSettingsWizard extends BasicWizardLayout {

    /**
     * Note that initially BasicWizardLayout inherits from {@link android.support.v4.app.Fragment} and therefore you must have an empty constructor
     */
    public InitialSettingsWizard() {
        super();
    }

    //You must override this method and create a wizard flow by
    //using WizardFlow.Builder as shown in this example
    @Override
    public WizardFlow onSetup() {
        //Optionally, you can set different labels for the control buttons

        this.setBackButtonText(getString(R.string.wizard_previous));
        this.setNextButtonText(getString(R.string.wizard_next));
        this.setFinishButtonText(getString(R.string.wizard_finish));

        return new WizardFlow.Builder()
                .addStep(InitialSettingsStep1Schools.class)           //Add your steps in the order you want them
                .addStep(InitialSettingsStep2Login.class)           //to appear and eventually call create()
                .addStep(InitialSettingsStep3Testing.class)         //to appear and eventually call create()
                .create();                              //to create the wizard flow.
    }

    @Override
    public void onWizardComplete() {
        super.onWizardComplete();   //Make sure to first call the super method before anything else
        //... Access context variables here before terminating the wizard
        //...
        //String fullname = firstname + lastname;
        //Store the data in the DB or pass it back to the calling activity

        //getActivity().finish();     //Terminate the wizard

        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
    }

}