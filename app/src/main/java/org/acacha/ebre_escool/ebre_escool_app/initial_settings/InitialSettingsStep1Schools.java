package org.acacha.ebre_escool.ebre_escool_app.initial_settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.apis.EbreEscoolAPI;
import org.acacha.ebre_escool.ebre_escool_app.helpers.ConnectionDetector;
import org.acacha.ebre_escool.ebre_escool_app.pojos.School;
import org.acacha.ebre_escool.ebre_escool_app.settings.SettingsActivity;
import org.codepond.wizardroid.WizardStep;

import java.net.URL;
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

    CardArrayAdapter mCardArrayAdapter;

    private AlertDialog alert = null;

    private String error_message_validating_school = "";

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
        mSchools = gson.fromJson(json_schools_list, School[].class);

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

            card_on_list.setOnClickListener( new Card.OnCardClickListener() {
                 @Override
                 public void onClick(Card card, View view) {
                     Log.d(LOG_TAG,"Clickable card id: " + card.getId());
                     //Toast.makeText(getActivity(), "Clickable card id: " + card.getId(), Toast.LENGTH_LONG).show();
                     int position = mCardArrayAdapter.getPosition(card);
                     lstSchools.setItemChecked(position,true);
                     settings.edit().putString(SettingsActivity.SCHOOLS_LIST_KEY, Integer.toString(position)).apply();

                     //Check All data is completed before continue
                     boolean data_is_ok = check_all_data_is_ok(position);
                     if(data_is_ok) {
                         notifyCompleted();
                     } else {
                         notifyIncomplete();
                         showAlertDialog(getActivity(), getString(R.string.incorrect_school_data_title),
                                 error_message_validating_school + ". " + getString(R.string.incorrect_school_data_label), false);
                     }

                 }
             }
            );

            //Obtain thumbnail from an URL and add to card
            CardThumbnail thumb = new CardThumbnail(getActivity());
            //thumb.setDrawableResource(listImages[i]);
            if (mSchools[i].getLogoURL()!=""){
                thumb.setUrlResource(mSchools[i].getLogoURL());
            } else {
                thumb.setUrlResource(EbreEscoolAPI.EBRE_ESCOOL_PUBLIC_IMAGE_NOT_AVAILABLE);

            }
            card_on_list.addCardThumbnail(thumb);

            //Add card to car List
            cards.add(card_on_list);
        }

        mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards );

        lstSchools = (CardListView) getActivity().findViewById(R.id.schoolsList);
        if (lstSchools != null) {
            lstSchools.setAdapter(mCardArrayAdapter);
            lstSchools.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            //GET FROM SETTINGS WHICH SCHOOL IS USED IN SETTINGS

            String current_selected_school =
                    settings.getString(SettingsActivity.SCHOOLS_LIST_KEY,"0");

            Log.d(LOG_TAG,"Getted current selected school: " + current_selected_school);

            lstSchools.setItemChecked(Integer.parseInt(current_selected_school), true);

            //Check All data is completed before continue
            boolean data_is_ok = check_all_data_is_ok(0);
            if(data_is_ok) {
                notifyCompleted();
            } else {
                notifyIncomplete();
                // Show alert
                showAlertDialog(getActivity(), getString(R.string.incorrect_school_data_title),
                        error_message_validating_school + ". " + getString(R.string.incorrect_school_data_label), false);
            }

            String current_value = settings.getString(SettingsActivity.SCHOOLS_LIST_KEY,"0");
            settings.edit().putString(SettingsActivity.SCHOOLS_LIST_KEY, current_value).apply();
        }
    }

    /**
     * Function to display simple Alert Dialog
     * @param context - application context
     * @param title - alert dialog title
     * @param message - alert message
     * @param status - success/failure (used to set icon)
     * 				 - pass null if you don't want icon
     * */
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {

        alert = new AlertDialog.Builder(context).create();
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setCancelable(false);
        // Setting alert dialog icon
        if(status != null)
            alert.setIcon((status) ? R.drawable.success : R.drawable.fail);

        // Setting OK Button
        alert.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //DO NOTHING
            }
        });
        // Showing Alert Message
        alert.show();
    }

    private boolean check_all_data_is_ok(int position) {
        School selected_school = mSchools[position];
        Log.d(LOG_TAG, "Checking school at position " + position + " ( " + selected_school.getFullname() + " )" );

        String login_api_url = selected_school.getLogin_api_url();
        Log.d(LOG_TAG, "getLogin_api_url (login_api_url): " + login_api_url);
        String api_url = selected_school.getApi_url();
        Log.d(LOG_TAG, "getApi_url (api_url): " + api_url);

        URL ob_login_api_url = null;
        String login_api_url1 = "";
        URL ob_api_url = null;
        String api_url1 = "";
        try {
            ob_login_api_url = new URL(login_api_url);
            login_api_url1 = ob_login_api_url.getProtocol() + "://" + ob_login_api_url.getHost();
            Log.d(LOG_TAG, "login_api_url1: " + login_api_url1);
        } catch (Exception e) {
            e.printStackTrace();
            error_message_validating_school = getString(R.string.notvalid_login_url,login_api_url1);
            return false;
        }

        boolean valid_login_url = Patterns.WEB_URL.matcher(login_api_url1).matches();
        if (!valid_login_url) {
            Log.d(LOG_TAG, "Not valid login api url!");
            error_message_validating_school = getString(R.string.notvalid_login_url,login_api_url1);
            return false;
        }

        try {
            ob_api_url = new URL(api_url);
            api_url1 = ob_api_url.getProtocol() + "://" + ob_api_url.getHost();
            Log.d(LOG_TAG, "api_url1: " + api_url1);
        } catch (Exception e) {
            e.printStackTrace();
            error_message_validating_school = getString(R.string.notvalid_api_url,api_url1);
            return false;
        }

        boolean valid_api_url = Patterns.WEB_URL.matcher(api_url1).matches();

        if (!valid_api_url) {
            Log.d(LOG_TAG, "Not valid api url!");
            error_message_validating_school = getString(R.string.notvalid_api_url,api_url1);
            return false;
        }

        return true;
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