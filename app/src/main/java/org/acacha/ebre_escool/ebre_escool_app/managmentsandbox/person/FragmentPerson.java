package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.person;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

import java.io.StringReader;
import java.util.List;
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
     * The collection of all persons in the app.
     */

    //private static Person[] mPersons = new Person[4];
    private static Person[] mPersons;

    private List<Person> listOfPersons;

    // List of Card
    private CardListView lstPersons;
    private Card card_on_list;
    private RestAdapter restAdapter;


    //settings
    private SharedPreferences settings;

    CardArrayAdapter mCardArrayAdapter;

    private AlertDialog alert = null;

    private String error_message_validating_person = "";


    //Wire the layout to the step
    public FragmentPerson() {

    }



    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        // Connect to api to download info.
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(PersonAPI.EBRE_ESCOOL_PERSON_PUBLIC_API_URL)
                .build();

        get_data();

    }
    */


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


    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person, container, false);
        //settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();

        Log.d(LOG_TAG, "************ Persons ##################: " + PersonAPI.EBRE_ESCOOL_PERSON_PUBLIC_API_URL);

        // Connect to api to download info.
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(PersonAPI.EBRE_ESCOOL_PERSON_PUBLIC_API_URL)
                .build();

        get_data();
    }

    protected void get_data() {

        PersonApiService service = restAdapter.create(PersonApiService.class);

        //Callback callback = new Callback<List<Person>>() {

        //service.personsAsList(new Callback<List<Person>>() {


        //Callback callback = new Callback<Map<String, School>>() {
        //  @Override
        //public void success(Map<String, School> schools, Response response) {

        /*service.persons(new Callback<Map<String, Person>>(){
            @Override
            public void success(Map<String, Person> persons, Response response) {
                Log.d(LOG_TAG, "************ Persons ##################: " + persons);

                // Saving the LIST OF PERSONS.
                listOfPersons = persons;
                reload_data();
            }*/

        /*
                Callback callback = new Callback<Map<String, School>>() {
            @Override
            public void success(Map<String, School> schools, Response response) {
                Log.d(LOG_TAG, "************ Schools ##################: " + schools);

                //Save MAP AND LIST OF SCHOOLS as Shared Preferences
                Gson gson = new Gson();
                String schools_json = gson.toJson(schools);
                String schools_list_json = gson.toJson(schools.values().toArray());
                settings.edit().putString("schools_map", schools_json).apply();
                settings.edit().putString("schools_list", schools_list_json).apply();

            }
        * */






        Callback callback = new Callback<Map<String, Person>>() {
        //Callback callback = new Callback<List<Person>>() {
            @Override
            public void success(Map<String, Person> persons, Response response) {
            //public void success(List<Person> persons, Response response) {
                Log.d(LOG_TAG, "************ Persons ##################: " + persons);
                listOfPersons = (List<Person>) persons;
                reload_data();
            }

           /* @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();

            }*/

            @Override
            public void failure(RetrofitError retrofitError) {
                String text = "!!!! ERROR: " + retrofitError;
                Log.d(LOG_TAG, text);

                int duration = Toast.LENGTH_LONG;
                Activity activity = getActivity();
                Toast toast = Toast.makeText(activity.getApplicationContext(), text, duration);
                toast.show();
            }

        };
        service.persons(callback);
    }


    protected void reload_data() {

        if (listOfPersons != null) {

            /*
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new StringReader(result1));
            reader.setLenient(true);
            Userinfo userinfo1 = gson.fromJson(reader, Userinfo.class);
            *
            * */


            Gson gson = new Gson();
            String grossData = gson.toJson(listOfPersons);
            //JsonReader reader = new JsonReader(new StringReader(grossData));
            //reader.setLenient(true);



            Log.d(LOG_TAG, "###### json_persons_list: " + grossData);

            //Retrieve persons on JSON format
            //String json_persons_list = gson.toString("persons_list", "" + listOfPersons);
            //Log.d(LOG_TAG, "###### json_persons_list: " + json_persons_list);

            Person[] arrayData = gson.fromJson(grossData.trim(), Person[].class);



            lstPersons = (CardListView) getActivity().findViewById(R.id.personsList);

            ArrayList<Card> cards = new ArrayList<Card>();

            for (int i = 0; i < arrayData.length; i++) {
                Log.d("########## TEST: ", arrayData[i].getGivenName());//getFullname());
                // Create a Card
                card_on_list = new Card(getActivity());

                // Create a CardHeader and add Header to card_on_list
                CardHeader header = new CardHeader(getActivity());

                header.setTitle(arrayData[i].getNotes());//getFullname());
                //header.setTitle(mPersons[i].getGivenName()+ " " + mPersons[i].getSn1());//getFullname());

                card_on_list.addCardHeader(header);

                //card_on_list.setId(mPersons[i].getId());

                card_on_list.setTitle("Nom: " + arrayData[i].getGivenName() + "\n" + "Cognom: " + arrayData[i].getSn1() + "\n" + "Correu: " + arrayData[i].getEmail1());

                //card_on_list.setTitle(mPersons[i].getNotes()); //.getSchoolNotes());

                card_on_list.setClickable(true);
                card_on_list.setSwipeable(true);

                //Obtain thumbnail from an URL and add to card
                CardThumbnail thumb = new CardThumbnail(getActivity());
                //thumb.setDrawableResource(listImages[i]);
                if (arrayData[i].getPhoto() != "") {//getLogoURL()!=""){
                    thumb.setUrlResource(arrayData[i].getPhoto());//getLogoURL());
                } else {
                    thumb.setUrlResource(EbreEscoolAPI.EBRE_ESCOOL_PUBLIC_IMAGE_NOT_AVAILABLE);

                }
                //thumb.setUrlResource(EbreEscoolAPI.EBRE_ESCOOL_PUBLIC_IMAGE_NOT_AVAILABLE); //temporal

                card_on_list.addCardThumbnail(thumb);

                //Add card to car List
                cards.add(card_on_list);
            }

            mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);

            mCardArrayAdapter.setEnableUndo(true);

            /*lstPersons = (CardListView) getActivity().findViewById(R.id.personsList);
            if (lstPersons != null) {
                lstPersons.setAdapter(mCardArrayAdapter);
                lstPersons.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                //GET FROM SETTINGS WHICH PERSON IS USED IN SETTINGS

                String current_selected_person =
                        settings.getString(PERSONS_LIST_KEY, "0");

                Log.d(LOG_TAG, "Getted current selected person: " + current_selected_person);

                lstPersons.setItemChecked(Integer.parseInt(current_selected_person), true);

                String current_value = settings.getString(PERSONS_LIST_KEY, "0");
                settings.edit().putString(PERSONS_LIST_KEY, current_value).apply();
            }*/

            if (lstPersons != null) {
                lstPersons.setAdapter(mCardArrayAdapter);
            }
        }

    }
}