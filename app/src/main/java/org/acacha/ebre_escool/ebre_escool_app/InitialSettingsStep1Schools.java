package org.acacha.ebre_escool.ebre_escool_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import java.util.ArrayList;
import java.util.Map;

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
            card_on_list.addCardHeader(header);


            card_on_list.setId(mSchools[i].getId());
            card_on_list.setTitle(mSchools[i].getSchoolNotes());
            card_on_list.setClickable(true);

            //card_on_list.setOnClickListener();

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

        CardListView listView = (CardListView) getActivity().findViewById(R.id.schoolsList);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.initialsettingsstep1schools, container, false);
        settings = getActivity().getSharedPreferences(AndroidSkeletonUtils.PREFS_NAME, 0);
        return v;
    }

}