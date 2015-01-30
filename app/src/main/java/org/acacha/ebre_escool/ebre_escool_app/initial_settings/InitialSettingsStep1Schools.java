package org.acacha.ebre_escool.ebre_escool_app.initial_settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.pojos.School;
import org.codepond.wizardroid.WizardStep;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //Retrieve schools on JSON format
        String json_schools_list = settings.getString("schools_list", "");
        Log.d(LOG_TAG,"###### json_schools_list: " + json_schools_list);

        Gson gson = new Gson();
        School[] mSchools = gson.fromJson(json_schools_list, School[].class);

        ArrayList<Card> cards = new ArrayList<Card>();

        for (int i = 0; i < mSchools.length; i++) {
            Log.d("########## TEST: ", mSchools[i].getFullname());
            // Create a Card
            Card card_on_list = new Card(getActivity());

            // Create a CardHeader and add Header to card_on_list
            CardHeader header = new CardHeader(getActivity());
            header.setTitle(mSchools[i].getFullname());

            //#404040

            card_on_list.addCardHeader(header);

            card_on_list.setId(Integer.toString(i));
            card_on_list.setTitle(mSchools[i].getSchoolNotes());
            card_on_list.setClickable(true);

            card_on_list.setOnClickListener( new Card.OnCardClickListener() {
                 @Override
                 public void onClick(Card card, View view) {
                     Log.d(LOG_TAG,"Clickable card id: " + card.getId());
                     //Toast.makeText(getActivity(), "Clickable card id: " + card.getId(), Toast.LENGTH_LONG).show();
                     int position = Integer.parseInt(card.getId());
                     lstSchools.setItemChecked(position,true);
                     settings.edit().putString("school",card.getId()).apply();
                     notifyCompleted();
                 }
             }
            );

            //Obtain thumbnail from an URL and add to card
            CardThumbnail thumb = new CardThumbnail(getActivity());
            //thumb.setDrawableResource(listImages[i]);
            if (mSchools[i].getLogoURL()!=""){
                thumb.setUrlResource(mSchools[i].getLogoURL());
            } else {
                thumb.setUrlResource("http://acacha.org/acacha_manager/uploads/school_logos/no_picture.gif");
            }
            card_on_list.addCardThumbnail(thumb);

            //Add card to car List
            cards.add(card_on_list);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);

        lstSchools = (CardListView) getActivity().findViewById(R.id.schoolsList);
        if (lstSchools != null) {
            lstSchools.setAdapter(mCardArrayAdapter);
            lstSchools.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            lstSchools.setItemChecked(0, true);
            String current_value = settings.getString("school","0");
            settings.edit().putString("school", current_value).apply();
        }
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.initialsettingsstep1schools, container, false);
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return v;
    }

}