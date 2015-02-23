package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.lesson;

import android.app.Activity;
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

import com.google.gson.Gson;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;
import retrofit.RestAdapter;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.apis.EbreEscoolAPI;
import org.acacha.ebre_escool.ebre_escool_app.helpers.OnFragmentInteractionListener;
import org.acacha.ebre_escool.ebre_escool_app.pojos.School;
import org.acacha.ebre_escool.ebre_escool_app.settings.SettingsActivity;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.lesson.pojos.*;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LessonFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LessonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LessonFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CardArrayAdapter mCardArrayAdapter;
    private static lesson[] lessons;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LessonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LessonFragment newInstance(String param1, String param2) {
        LessonFragment fragment = new LessonFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LessonFragment() {
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
        return inflater.inflate(R.layout.fragment_lesson, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ArrayList<Card> cards = new ArrayList<Card>();

        for (int i = 0; i < lessons.length; i++) {
            Log.d("########## TEST: ", lessons[i].getId());
            // Create a Card
            Card card_on_list = new Card(getActivity());

            // Create a CardHeader and add Header to card_on_list
            CardHeader header = new CardHeader(getActivity());
            header.setTitle(lessons[i].getId());

            card_on_list.addCardHeader(header);

            card_on_list.setId(lessons[i].getId());
            card_on_list.setTitle(lessons[i].getId());
            card_on_list.setClickable(true);



            //Obtain thumbnail from an URL and add to card
            CardThumbnail thumb = new CardThumbnail(getActivity());
            //thumb.setDrawableResource(listImages[i]);
            if (lessons[i].getDNINIF()!=""){
                thumb.setUrlResource(lessons[i].getDNINIF());
            } else {
                thumb.setUrlResource(EbreEscoolAPI.EBRE_ESCOOL_PUBLIC_IMAGE_NOT_AVAILABLE);

            }
            card_on_list.addCardThumbnail(thumb);

            //Add card to car List
            cards.add(card_on_list);
        }

        mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards );


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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
