package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.apis.EbreEscoolAPI;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.pojos.Users;
import org.acacha.ebre_escool.ebre_escool_app.settings.SettingsActivity;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.FragmentUsers.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.FragmentUsers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUsers extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    private OnFragmentInteractionListener mListener;
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FragmentUsers.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static FragmentUsers newInstance(String param1, String param2) {
//        FragmentUsers fragment = new FragmentUsers();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public FragmentUsers() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_users, container, false);
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

    
    final String LOG_TAG = "Users_CAD_CLICK";
    private static Users[] mUsers;
    private ListView lastUsers;
    //settings
    private SharedPreferences settings;
    CardArrayAdapter mCardArrayAdapter;
    private AlertDialog alert = null;
    private String error_message_validating_school = "";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        /*
        Fer quan es agafin les dades de l'API
        //Retrieve schools on JSON format
        //String json_schools_list = settings.getString("schools_list", "");
        //Log.d(LOG_TAG, "###### json_schools_list: " + json_schools_list);
        //Gson gson = new Gson();
        //mStudySubmodules = gson.fromJson(json_schools_list, School[].class);
        */

        mUsers = new Users[3];
        Users user1 = new Users();
        Users user2 = new Users();
        Users user3 = new Users();

        user1.setUsername("Prova 1");
        user2.setUsername("Prova 2");
        user3.setUsername("Prova 3");

        mUsers[0] = user1;
        mUsers[1] = user2;
        mUsers[2] = user3;

        ArrayList<Card> cards = new ArrayList<Card>();
        Log.d(LOG_TAG, "Users 0 " + mUsers[0]);

        for (int i = 0; i < mUsers.length; i++) {

            Card card_on_list = new Card(getActivity());

            CardHeader header = new CardHeader(getActivity());
            header.setTitle(mUsers[i].getPersonId());
            card_on_list.addCardHeader(header);
            card_on_list.setId(mUsers[i].getUsername());
            card_on_list.setTitle(mUsers[i].getUsername());
            card_on_list.setClickable(true);
            card_on_list.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Log.d(LOG_TAG, "Clicable card id: " + card.getId());
                    int position = mCardArrayAdapter.getPosition(card);
                    lastUsers.setItemChecked(position, true);
                }
            });

            CardThumbnail thumb = new CardThumbnail(getActivity());

            if (mUsers[i].getLogoURL() != "") {
                thumb.setUrlResource(mUsers[i].getLogoURL());
            } else {
                thumb.setUrlResource(EbreEscoolAPI.EBRE_ESCOOL_PUBLIC_IMAGE_NOT_AVAILABLE);
            }
            card_on_list.addCardThumbnail(thumb);
            cards.add(card_on_list);
        }
        mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
        lastUsers = (CardListView) getActivity().findViewById(R.id.schoolsList);

        if (lastUsers != null) {
            lastUsers.setAdapter(mCardArrayAdapter);
            lastUsers.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            String current_selected_school = settings.getString(SettingsActivity.SCHOOLS_LIST_KEY, "0");
            Log.d(LOG_TAG, "Getted current selected school: " + current_selected_school);
            lastUsers.setItemChecked(Integer.parseInt(current_selected_school),true);
            String current_value = settings.getString(SettingsActivity.SCHOOLS_LIST_KEY, "0");
            settings.edit().putString(SettingsActivity.SCHOOLS_LIST_KEY, current_value).apply();
        }
    }

        public void showAlertDialog(Context context, String title, String message, Boolean status){
        alert = new AlertDialog.Builder(context).create();
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setCancelable(false);

        if(status != null){
            alert.setIcon((status) ? R.drawable.success : R.drawable.fail);
            alert.setButton(AlertDialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which){
            }
        });

            alert.show();
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_users, container, false);
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return v;
    }

}
