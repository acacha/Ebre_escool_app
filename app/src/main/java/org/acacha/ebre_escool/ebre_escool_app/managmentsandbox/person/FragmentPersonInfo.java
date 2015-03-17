package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.person;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.person.pojos.Person;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.person.api.PersonAPI;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.person.api.PersonApiService;


import org.acacha.ebre_escool.ebre_escool_app.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentPersonInfo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentPersonInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPersonInfo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    protected EditText mId;
    protected EditText mGivenName;
    protected EditText mSurName1;
    protected EditText mSurName2;
    protected EditText mEmail;
    protected EditText mDniNif;
    protected EditText mTelephoneNumber;
    protected EditText mNotes;
    protected EditText mEntryDate;
    protected EditText mLastUpdate;
    protected EditText mCreationUserId;
    protected EditText mLastUpdateUserId;
    protected EditText mMarkedForDeletion;
    protected EditText mMarkedForDeletionDate;
    protected Button button_person_create;
    protected Button button_person_update;



    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPersonInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPersonInfo newInstance(String param1, String param2) {
        FragmentPersonInfo fragment = new FragmentPersonInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentPersonInfo() {
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

    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_person_info, container, false);
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_person_info, container, false);

        mId = (EditText) v.findViewById(R.id.editText_person_id);
        mGivenName = (EditText) v.findViewById(R.id.editText_person_nom);
        mSurName1 = (EditText) v.findViewById(R.id.editText_person_cognom1);
        mSurName2 = (EditText) v.findViewById(R.id.editText_person_cognom2);
        mEmail = (EditText) v.findViewById(R.id.editText_person_email);
        mDniNif = (EditText) v.findViewById(R.id.editText_person_dni);
        mTelephoneNumber = (EditText) v.findViewById(R.id.editText_person_telefon);
        mNotes = (EditText) v.findViewById(R.id.editText_person_notes);
        button_person_create = (Button) v.findViewById(R.id.button_person_create);
        button_person_update = (Button) v.findViewById(R.id.button_person_update);


        button_person_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("#Crear persona: ", mId.getText().toString());
                //createPerson();

            }
        });

        button_person_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("#Actualitzar persona: ", mId.getText().toString());
                //updatePerson();

            }
        });




        return v;
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
