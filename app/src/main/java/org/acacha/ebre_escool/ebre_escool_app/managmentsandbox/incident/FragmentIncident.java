package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.incident;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.incident.api.IncidentApi;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.incident.pojos.incident;
import org.acacha.ebre_escool.ebre_escool_app.settings.SettingsActivity;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.*;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;


public class FragmentIncident extends Fragment {
    /**
     * The collection of all schools in the app.
     */

    final String LOG_TAG = "Incidents_CARD_CLICK";

    private static incident[] mIncidents;

    private ListView lstIncidents;

    //settings
    private SharedPreferences settings;

    CardArrayAdapter mCardArrayAdapter;

    private AlertDialog alert = null;

    private String error_message_validating_school = "";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());


        //Retrieve schools on JSON format
        //String json_incidents_list = settings.getString("incidents_list", "");
        //Json Manual
        String json_incidents_list = "[{\"incident_id\":\"1\",\"incident_student_id\":\"5758\",\"incident_time_slot_id\":\"11\",\"incident_day\":\"4\",\"incident_date\":\"2014-12-04\",\"incident_study_submodule_id\":\"179\",\"incident_type\":\"3\",\"incident_notes\":\"\",\"incident_entryDate\":\"2014-12-04 17:11:49\",\"incident_last_update\":\"2014-12-04 17:11:49\",\"incident_creationUserId\":\"1\",\"incident_lastupdateUserId\":\"1\",\"incident_markedForDeletion\":\"n\",\"incident_markedForDeletionDate\":\"0000-00-00 00:00:00\"}," +
                "{\"incident_id\":\"2\",\"incident_student_id\":\"5758\",\"incident_time_slot_id\":\"11\",\"incident_day\":\"4\",\"incident_date\":\"2014-12-04\",\"incident_study_submodule_id\":\"179\",\"incident_type\":\"3\",\"incident_notes\":sdfsdf\",\"incident_entryDate\":\"2014-12-04 17:11:49\",\"incident_last_update\":\"2014-12-04 17:11:49\",\"incident_creationUserId\":\"1\",\"incident_lastupdateUserId\":\"1\",\"incident_markedForDeletion\":\"n\",\"incident_markedForDeletionDate\":\"0000-00-00 00:00:00\"}]";
        Log.d(LOG_TAG, "###### json_incidents_list: " + json_incidents_list);

        Gson gson = new Gson();
        mIncidents = gson.fromJson(json_incidents_list, incident[].class);

        ArrayList<Card> cards = new ArrayList<Card>();


        for (int i = 0; i < mIncidents.length; i++) {
            //Log.d("########## TEST: ", mIncidents[i].getFullname());
            // Create a Card
            Card card_on_list = new Card(getActivity());
            //Swipe Acction
            card_on_list.setSwipeable(true);
            card_on_list.setClickable(true);
            card_on_list.setId(mIncidents[i].getIncidentId());
            card_on_list.setTitle(mIncidents[i].getIncidentNotes());

            // Create a CardHeader and add Header to card_on_list
            CardHeader header = new CardHeader(getActivity());
            header.setTitle(mIncidents[i].getIncidentId());
            card_on_list.addCardHeader(header);

            //Inner Text
            card_on_list.setTitle(mIncidents[i].getIncidentDate());

            //Add a popup menu. This method sets OverFlow button to visibile
            header.setPopupMenu(R.menu.incident_menu, new CardHeader.OnClickCardHeaderPopupMenuListener(){

                public void onButtonItemClick(Card card, View view) {
                    //Example to change dinamically the button resources

                    card.getCardView().refreshCard(card);

                }



                @Override
                public void onMenuItemClick(BaseCard baseCard, MenuItem menuItem) {



                }
            });


            card_on_list.setOnSwipeListener(new Card.OnSwipeListener() {
                @Override
                public void onSwipe(Card card) {


                }
            });
            //swipe undo action
            card_on_list.setOnUndoSwipeListListener(new Card.OnUndoSwipeListListener() {
                @Override
                public void onUndoSwipe(Card card) {

                }
            });

            // Image Not Available
            CardThumbnail thumb = new CardThumbnail(getActivity());
            thumb.setUrlResource(IncidentApi.EBRE_ESCOOL_PUBLIC_IMAGE_NOT_AVAILABLE);
            card_on_list.addCardThumbnail(thumb);

            card_on_list.setOnClickListener(new Card.OnCardClickListener() {
                                                @Override
                                                public void onClick(Card card, View view) {
                                                    Log.d(LOG_TAG,"Clickable card id: " + card.getId());
                                                    Toast.makeText(getActivity(), "Clickable card id: " + card.getId(), Toast.LENGTH_LONG).show();

                                                }
                                            }
            );

            //Obtain thumbnail from an URL and add to card
            //CardThumbnail thumb = new CardThumbnail(getActivity());
            //thumb.setDrawableResource(listImages[i]);
            // if (mIncidents[i].getLogoURL() != "") {
            //     thumb.setUrlResource(mIncidents[i].getLogoURL());
            //} else {
            //    thumb.setUrlResource(IncidentApi.EBRE_ESCOOL_PUBLIC_IMAGE_NOT_AVAILABLE);
            // }
            // card_on_list.addCardThumbnail(thumb);

            //Add card to car List
            cards.add(card_on_list);
        }

        mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);

        lstIncidents = (CardListView) getActivity().findViewById(R.id.incidentList);
        if (lstIncidents != null) {
            lstIncidents.setAdapter(mCardArrayAdapter);
            lstIncidents.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            //GET FROM SETTINGS WHICH SCHOOL IS USED IN SETTINGS

            String current_selected_school =
                    settings.getString(SettingsActivity.SCHOOLS_LIST_KEY, "0");

            Log.d(LOG_TAG, "Getted current selected school: " + current_selected_school);

            String current_value = settings.getString(SettingsActivity.SCHOOLS_LIST_KEY, "0");
            settings.edit().putString(SettingsActivity.SCHOOLS_LIST_KEY, current_value).apply();
        }
    }

    /**
     * Function to display simple Alert Dialog
     *
     * @param context - application context
     * @param title   - alert dialog title
     * @param message - alert message
     * @param status  - success/failure (used to set icon)
     *                - pass null if you don't want icon
     */
    public void showAlertDialog(Context context, String title, String message,
                                Boolean status) {

        alert = new AlertDialog.Builder(context).create();
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setCancelable(false);
        // Setting alert dialog icon
        if (status != null)
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

    /*private boolean check_all_data_is_ok(int position) {
        School selected_school = mIncidents[position];
        Log.d(LOG_TAG, "Checking school at position " + position + " ( " + selected_school.getFullname() + " )");

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
            error_message_validating_school = getString(R.string.notvalid_login_url, login_api_url1);
            return false;
        }

        boolean valid_login_url = Patterns.WEB_URL.matcher(login_api_url1).matches();
        if (!valid_login_url) {
            Log.d(LOG_TAG, "Not valid login api url!");
            error_message_validating_school = getString(R.string.notvalid_login_url, login_api_url1);
            return false;
        }

        try {
            ob_api_url = new URL(api_url);
            api_url1 = ob_api_url.getProtocol() + "://" + ob_api_url.getHost();
            Log.d(LOG_TAG, "api_url1: " + api_url1);
        } catch (Exception e) {
            e.printStackTrace();
            error_message_validating_school = getString(R.string.notvalid_api_url, api_url1);
            return false;
        }

        boolean valid_api_url = Patterns.WEB_URL.matcher(api_url1).matches();

        if (!valid_api_url) {
            Log.d(LOG_TAG, "Not valid api url!");
            error_message_validating_school = getString(R.string.notvalid_api_url, api_url1);
            return false;
        }

        return true;
    }
*/
    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_incident, container, false);
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return v;
    }

}