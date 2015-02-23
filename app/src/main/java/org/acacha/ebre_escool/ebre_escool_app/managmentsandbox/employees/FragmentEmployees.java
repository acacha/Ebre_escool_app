package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.employees;

import android.app.Activity;
import android.app.AlertDialog;
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
import java.lang.*;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.helpers.OnFragmentInteractionListener;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.employees.api.EmployeesAPI;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.employees.pojos.Employees;
import org.acacha.ebre_escool.ebre_escool_app.settings.SettingsActivity;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Create by PaoloDavila
 *
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentEmployees.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentEmployees#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEmployees extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEmployees.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEmployees newInstance(String param1, String param2) {
        FragmentEmployees fragment = new FragmentEmployees();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentEmployees() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_employees, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*NUEVAS LINIAS INSERTADAS QUE NO SE LO QUE HACEN*/

    //declaració de vector Employees[] mEmployees
    private static Employees[] mEmployees = new Employees[3];

    //settings
    private SharedPreferences settings;

    CardArrayAdapter mCardArrayAdapter;

    private AlertDialog alert = null;

    final String LOG_TAG = "InitialSettingsStep1Schools";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Employees employees1 = new Employees();
        Employees employees2 = new Employees();
        Employees employees3 = new Employees();

        employees1.setId("1");
        employees1.setPerson_id("1072");
        employees1.setCode("Leonord Agamundi");

        employees2.setId("2");
        employees2.setPerson_id("1017");
        employees2.setCode("Jordi Caudet");

        employees3.setId("3");
        employees3.setPerson_id("1018");
        employees3.setCode("Jaume Benaiges");

        mEmployees[0] = employees1;
        mEmployees[1] = employees2;
        mEmployees[2] = employees3;

        ArrayList<Card> cards = new ArrayList<Card>();

        for (int i = 0; i < mEmployees.length; i++) {
            Log.d("########## TEST: ", mEmployees[i].getPerson_id());
            // Create a Card
            Card card_on_list = new Card(getActivity());

            // Create a CardHeader and add Header to card_on_list
            CardHeader header = new CardHeader(getActivity());
            header.setTitle(mEmployees[i].getPerson_id());

            card_on_list.addCardHeader(header);

            card_on_list.setId(mEmployees[i].getId());
            card_on_list.setTitle(mEmployees[i].getType_id());
            card_on_list.setClickable(true);

            card_on_list.setOnClickListener( new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    Log.d(LOG_TAG, "Clickable card id: " + card.getId());
                }
            });

            //Obtain thumbnail from an URL and add to card
            /*
            CardThumbnail thumb = new CardThumbnail(getActivity());
            thumb.setDrawableResource(listImages[i]);

            if (mEmployees[i]){
                thumb.setUrlResource(mEmployees[i]);
            } else {
                thumb.setUrlResource(EmployeesAPI.EBRE_ESCOOL_PUBLIC_IMAGE_NOT_AVAILABLE);

            }

            card_on_list.addCardThumbnail(thumb);
            */

            //Add card to car List
            cards.add(card_on_list);
        }
    }

}
