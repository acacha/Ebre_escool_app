package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.classroom_group;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.apis.EbreEscoolAPI;
import org.acacha.ebre_escool.ebre_escool_app.helpers.FragmentBase;
import org.acacha.ebre_escool.ebre_escool_app.helpers.OnFragmentInteractionListener;
import org.acacha.ebre_escool.ebre_escool_app.pojos.Classroom_group;
import org.acacha.ebre_escool.ebre_escool_app.settings.SettingsActivity;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass. Activities that contain this fragment
 * must implement the {@link FragmentBase.OnFragmentInteractionListener}
 * interface to handle interaction events. Use the
 * {@link FragmentBase#newInstance} factory method to create an instance of
 * this fragment.
 *
 */
public class FragmentClassroomGroup extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

    private static Classroom_group[] mClassroom_group;

    private ListView lstSchools;

    //settings
    private SharedPreferences settings;

    /**
	 * Use this factory method to create a new instance of this fragment using
	 * the provided parameters.
	 *
	 * @param param1
	 *            Parameter 1.
	 * @param param2
	 *            Parameter 2.
	 * @return A new instance of fragment FragmentType0.
	 */
	// TODO: Rename and change types and number of parameters
	public static FragmentClassroomGroup newInstance(String param1, String param2) {
        FragmentClassroomGroup fragment = new FragmentClassroomGroup();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public FragmentClassroomGroup() {
		// Required empty public constructor
	}

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
        */
        //Retrieve schools on JSON format
        /*String json_schools_list = settings.getString("schools_list", "");
        Log.d(LOG_TAG, "###### json_schools_list: " + json_schools_list);*/

        /*Gson gson = new Gson();
        mmClassroom_group = gson.fromJson(json_schools_list, School[].class);*/

        Classroom_group patata1 = new Classroom_group();
        Classroom_group patata2 = new Classroom_group();
        Classroom_group patata3 = new Classroom_group();

        patata1.setId("1");
        patata2.setId("2");
        patata3.setId("3");

        patata1.setName("Nom complet 1");
        patata2.setName("Nom complet 2");
        patata3.setName("Nom complet 3");

        patata1.setShortName("Nom curt 1");
        patata2.setShortName("Nom curt 2");
        patata3.setShortName("Nom curt 3");

        mClassroom_group[0] = patata1;
        mClassroom_group[1] = patata2;
        mClassroom_group[2] = patata3;

        ArrayList<Card> cards = new ArrayList<Card>();

        for (int i = 0; i < mClassroom_group.length; i++) {
            Log.d("########## TEST: ", mClassroom_group[i].getName());
            // Create a Card
            Card card_on_list = new Card(getActivity());

            // Create a CardHeader and add Header to card_on_list
            CardHeader header = new CardHeader(getActivity());
            header.setTitle(mClassroom_group[i].getName());

            card_on_list.addCardHeader(header);

            card_on_list.setId(mClassroom_group[i].getId());
            card_on_list.setTitle(mClassroom_group[i].getShortName());
            card_on_list.setClickable(true);

            card_on_list.setOnClickListener( new Card.OnCardClickListener() {
                 @Override
                 public void onClick(Card card, View view) {
                     /*Log.d(LOG_TAG,"Clickable card id: " + card.getId());
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
    */
                 }
             }
            );

            //Obtain thumbnail from an URL and add to card
            CardThumbnail thumb = new CardThumbnail(getActivity());
            //thumb.setDrawableResource(listImages[i]);
            /*if (mClassroom_group[i].getLogoURL()!=""){
                thumb.setUrlResource(mClassroom_group[i].getLogoURL());
            } else {*/
                thumb.setUrlResource(EbreEscoolAPI.EBRE_ESCOOL_PUBLIC_IMAGE_NOT_AVAILABLE);

            //}
            card_on_list.addCardThumbnail(thumb);

            //Add card to car List
            cards.add(card_on_list);
        }
/*
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
        }*/
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
		return inflater.inflate(R.layout.fragment_classroom_group, container,
				false);
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

}
