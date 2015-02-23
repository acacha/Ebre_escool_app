package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.person;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.apis.EbreEscoolAPI;
import org.acacha.ebre_escool.ebre_escool_app.apis.EbreEscoolApiService;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.person.api.PersonAPI;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.person.api.PersonApiService;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.person.pojos.Person;
import org.acacha.ebre_escool.ebre_escool_app.pojos.School;

import java.util.ArrayList;
import java.util.Map;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class FragmentPerson extends Fragment {

    //Look up for shared preferences
    final String LOG_TAG = "Step1Persons";

    public static final String PERSONS_LIST_KEY = "person";

    /**
     * The collection of all schools in the app.
     */
    private static Person[] mPersons = new Person[4];

    private ListView lstPersons;

    //settings
    private SharedPreferences settings;

    CardArrayAdapter mCardArrayAdapter;

    private AlertDialog alert = null;

    private String error_message_validating_person = "";

    //Wire the layout to the step
    public FragmentPerson() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        download_initial_data();

        //Retrieve persons on JSON format
        String json_persons_list = settings.getString("persons_list", "");
        Log.d(LOG_TAG,"###### json_persons_list: " + json_persons_list);

        Gson gson = new Gson();
        mPersons = gson.fromJson(json_persons_list, Person[].class);


        /*Person person1 = new Person();
        Person person2 = new Person();
        Person person3 = new Person();
        Person person4 = new Person();

        person1.setSn1("Turcan");
        person1.setGivenName("Nicolae");
        person1.setEmail1("nicolaeturcan@iesebre.com");
        person1.setNotes("Alumne");

        person2.setSn1("Domenech");
        person2.setGivenName("Marc");
        person2.setEmail1("marcdomenech@iesebre.com");
        person2.setNotes("Alumne");

        person3.setSn1("Coronciuc");
        person3.setGivenName("Liviu");
        person3.setEmail1("liviucoronciuc@iesebre.com");
        person3.setNotes("Alumne");

        person4.setSn1("Tur");
        person4.setGivenName("Sergi");
        person4.setEmail1("stur@iesebre.com");
        person4.setNotes("Professor");

        mPersons[0] = person1;
        mPersons[1] = person2;
        mPersons[2] = person3;
        mPersons[3] = person4;*/


        ArrayList<Card> cards = new ArrayList<Card>();

        for (int i = 0; i < mPersons.length; i++) {
            Log.d("########## TEST: ", mPersons[i].getGivenName());//getFullname());
            // Create a Card
            Card card_on_list = new Card(getActivity());

            // Create a CardHeader and add Header to card_on_list
            CardHeader header = new CardHeader(getActivity());

            header.setTitle(mPersons[i].getNotes());//getFullname());
            //header.setTitle(mPersons[i].getGivenName()+ " " + mPersons[i].getSn1());//getFullname());

            card_on_list.addCardHeader(header);

            //card_on_list.setId(mPersons[i].getId());

            card_on_list.setTitle("Nom: " + mPersons[i].getGivenName() + "\n" + "Cognom: " + mPersons[i].getSn1()  + "\n" +  "Correu: " + mPersons[i].getEmail1());

            //card_on_list.setTitle(mPersons[i].getNotes()); //.getSchoolNotes());

            card_on_list.setClickable(true);

            /*card_on_list.setOnClickListener( new Card.OnCardClickListener() {
                                                 @Override
                                                 public void onClick(Card card, View view) {
                                                     Log.d(LOG_TAG,"Clickable card id: " + card.getId());
                                                     //Toast.makeText(getActivity(), "Clickable card id: " + card.getId(), Toast.LENGTH_LONG).show();
                                                     int position = mCardArrayAdapter.getPosition(card);
                                                     lstPersons.setItemChecked(position,true);
                                                     settings.edit().putString(PERSONS_LIST_KEY, Integer.toString(position)).apply();

                                                     //Check All data is completed before continue
                                                     boolean data_is_ok = check_all_data_is_ok(position);
                                                     if(data_is_ok) {
                                                         notifyCompleted();
                                                     } else {
                                                         notifyIncomplete();
                                                         showAlertDialog(getActivity(), getString(R.string.incorrect_school_data_title),
                                                                 error_message_validating_person + ". " + getString(R.string.incorrect_school_data_label), false);
                                                     }

                                                 }
                                             }
            );*/

            //Obtain thumbnail from an URL and add to card
            CardThumbnail thumb = new CardThumbnail(getActivity());
            //thumb.setDrawableResource(listImages[i]);
            if (mPersons[i].getPhoto()!=""){//getLogoURL()!=""){
                thumb.setUrlResource(mPersons[i].getPhoto());//getLogoURL());
            } else {
                thumb.setUrlResource(EbreEscoolAPI.EBRE_ESCOOL_PUBLIC_IMAGE_NOT_AVAILABLE);

            }

            thumb.setUrlResource(EbreEscoolAPI.EBRE_ESCOOL_PUBLIC_IMAGE_NOT_AVAILABLE); //temporal
            card_on_list.addCardThumbnail(thumb);

            //Add card to car List
            cards.add(card_on_list);
        }

        mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards );

        lstPersons = (CardListView) getActivity().findViewById(R.id.personsList);
        if (lstPersons != null) {
            lstPersons.setAdapter(mCardArrayAdapter);
            lstPersons.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            //GET FROM SETTINGS WHICH PERSON IS USED IN SETTINGS

            String current_selected_person =
                    settings.getString(PERSONS_LIST_KEY,"0");

            Log.d(LOG_TAG,"Getted current selected person: " + current_selected_person);

            lstPersons.setItemChecked(Integer.parseInt(current_selected_person), true);

            //Check All data is completed before continue
           /* boolean data_is_ok = check_all_data_is_ok(0);
            if(data_is_ok) {
                notifyCompleted();
            } else {
                notifyIncomplete();
                // Show alert
                showAlertDialog(getActivity(), getString(R.string.incorrect_school_data_title),
                        error_message_validating_person + ". " + getString(R.string.incorrect_school_data_label), false);
            }*/

            String current_value = settings.getString(PERSONS_LIST_KEY,"0");
            settings.edit().putString(PERSONS_LIST_KEY, current_value).apply();
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

    /*private boolean check_all_data_is_ok(int position) {
        Person selected_person = mPersons[position];
        Log.d(LOG_TAG, "Checking person at position " + position + " ( " + selected_person.getGivenName() + " )" );//.getFullname() + " )" );

        String login_api_url = selected_person.getLogin_api_url();
        Log.d(LOG_TAG, "getLogin_api_url (login_api_url): " + login_api_url);
        String api_url = selected_person.getApi_url();
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
            error_message_validating_person = getString(R.string.notvalid_login_url,login_api_url1);
            return false;
        }

        boolean valid_login_url = Patterns.WEB_URL.matcher(login_api_url1).matches();
        if (!valid_login_url) {
            Log.d(LOG_TAG, "Not valid login api url!");
            error_message_validating_person = getString(R.string.notvalid_login_url,login_api_url1);
            return false;
        }

        try {
            ob_api_url = new URL(api_url);
            api_url1 = ob_api_url.getProtocol() + "://" + ob_api_url.getHost();
            Log.d(LOG_TAG, "api_url1: " + api_url1);
        } catch (Exception e) {
            e.printStackTrace();
            error_message_validating_person = getString(R.string.notvalid_api_url,api_url1);
            return false;
        }

        boolean valid_api_url = Patterns.WEB_URL.matcher(api_url1).matches();

        if (!valid_api_url) {
            Log.d(LOG_TAG, "Not valid api url!");
            error_message_validating_person = getString(R.string.notvalid_api_url,api_url1);
            return false;
        }

        return true;
    }*/

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person, container, false);
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return v;
    }

    private void download_initial_data() {
        //Connect to api to download info during splash screen execution
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(PersonAPI.EBRE_ESCOOL_PERSON_PUBLIC_API_URL)
                .build();

        PersonApiService service = restAdapter.create(PersonApiService.class);

        Callback callback = new Callback<Map<String, Person>>() {
            @Override
            public void success(Map<String, Person> persons, Response response) {
                Log.d(LOG_TAG, "************ Persons ##################: " + persons);

                //Save MAP AND LIST OF PERSONS as Shared Preferences
                Gson gson = new Gson();
                String persons_json = gson.toJson(persons);
                String persons_list_json = gson.toJson(persons.values().toArray());
                settings.edit().putString("persons_map", persons_json).apply();
                settings.edit().putString("persons_list", persons_list_json).apply();
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                String text = "!!!! ERROR: " + retrofitError;
                Log.d(LOG_TAG, text);

                int duration = Toast.LENGTH_LONG;
                //Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                //toast.show();

            }
        };
        service.persons(callback);
    }

}