package org.acacha.ebre_escool.ebre_escool_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.codepond.wizardroid.WizardStep;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardView;

public class InitialSettingsStep3Testing extends WizardStep {
    //You must have an empty constructor for every step
    public InitialSettingsStep3Testing() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CardView cardView = (CardView) getActivity().findViewById(R.id.card);

        Card card = new Card(getActivity()); // this คือ Context
        cardView.setCard(card);

    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.initialsettingsstep3testing, container, false);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText("This is an example of Step 2 and also the last step in this wizard. " +
                "By pressing Finish you will conclude this wizard and go back to the main activity." +
                "Hit the back button to go back to the previous step.");

        return v;
    }
}