package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.study_submodules;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.helpers.OnFragmentInteractionListener;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.study_submodules.pojos.Resultat;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.study_submodules.pojos.StudySubmodules;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.study_submodules.api.StudySubmoduleApi;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.study_submodules.api.StudySubmoduleApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudySubmodulesDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudySubmodulesDetail#newInstance} factory method to
 * create an instance of this fragment.
 */

public class StudySubmodulesDetail extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    //Retrofit adapter
    private RestAdapter adapter;
    private StudySubmodules studySubmodulesObject;
    private int studysubmodulesId;
    //Controls
    private TextView ID;
    private EditText shortname;
    private EditText name;
    private EditText moduleId;
    private EditText courseId;
    private EditText order;
    private EditText description;
    private EditText entryDate;
    private EditText lastUpdate;
    private EditText creationUserId;
    private EditText lastUpdateUserId;
    private EditText markedForDeletion;
    private EditText markedForDeletionDate;
    private Button btnUpdate;
    private Button btnPut;
    private String TAG = "tag";
    private Calendar myCalendar;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StudySubmodulesDetail.
     */

    // TODO: Rename and change types and number of parameters
    public static StudySubmodulesDetail newInstance(String param1, String param2) {
        StudySubmodulesDetail fragment = new StudySubmodulesDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public StudySubmodulesDetail() {
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
        // View view= inflater.inflate(R.layout.fragment_study_submodules_detail, container, false);
        View view = inflater.inflate(R.layout.fragment_study_submodules_detail, container, false);
        //Get controls
        ID = (TextView) view.findViewById(R.id.StudySubmodulesId);
        shortname = (EditText) view.findViewById(R.id.Shortname);
        name = (EditText) view.findViewById(R.id.Name);
        moduleId = (EditText) view.findViewById(R.id.ModuleId);
        courseId = (EditText) view.findViewById(R.id.Courseid);
        order = (EditText) view.findViewById(R.id.Order);
        description = (EditText) view.findViewById(R.id.Description);
        entryDate = (EditText) view.findViewById(R.id.entryDate);
        lastUpdate = (EditText) view.findViewById(R.id.lastUpdate);
        lastUpdateUserId = (EditText) view.findViewById(R.id.lastUpdateUserId);
        creationUserId = (EditText) view.findViewById(R.id.creatoruserId);
        markedForDeletion = (EditText) view.findViewById(R.id.markedForDeletion);
        markedForDeletionDate = (EditText) view.findViewById(R.id.markedForDeletionDate);
        btnUpdate = (Button) view.findViewById(R.id.btnUpdate);
        btnPut = (Button) view.findViewById(R.id.btnPut);
        //Set click listener for button update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"StudySubmodules ID: "+ID.getText().toString(),Toast.LENGTH_LONG).show();
                updateStudySubmodule();

            }
        });
        btnPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"StudySubmodules ID: "+ID.getText().toString(),Toast.LENGTH_LONG).show();
                insertStudySubmodule();

            }
        });
        //set rest adapter
        adapter = new RestAdapter.Builder()
                .setEndpoint(StudySubmoduleApi.ENDPOINT).build();

        // get data send from studysubmodules fragment
        Bundle extras = getArguments();
        if (extras != null) {
            studysubmodulesId = extras.getInt("id");
            String action = extras.getString(StudySubmoduleApi.ACTION);
            Log.d("tag", "detail id :" + studysubmodulesId);
            switch (action) {
                case StudySubmoduleApi.DETAIL:
                    btnUpdate.setVisibility(View.INVISIBLE);
                    btnPut.setVisibility(View.INVISIBLE);
                    getOneStudySubmodulesId(studysubmodulesId);
                    break;
                case StudySubmoduleApi.EDIT:
                    btnUpdate.setVisibility(View.VISIBLE);
                    btnPut.setVisibility(View.INVISIBLE);
                    getOneStudySubmodulesId(studysubmodulesId);
                    break;
                case StudySubmoduleApi.PUT:
                    btnPut.setVisibility(View.VISIBLE);
                    btnUpdate.setVisibility(View.INVISIBLE);
                    markedForDeletion.setText("n");

            }
        }
        //open calendar when click on entry date edittext
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        entryDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    new DatePickerDialog(getActivity(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });


        return view;
    }
    //Set the new entry date
    private void updateLabel() {

        String myFormat = "yyyy-MM-dd HH:mm:ss"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        entryDate.setText(sdf.format(myCalendar.getTime()));
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

    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/

    //EXECUTE RETROFIT GET ONE StudySubmodules METHOD
    public void getOneStudySubmodulesId(Integer id){

        Log.d("tag","get :"+id);
        StudySubmoduleApiService api =adapter.create(StudySubmoduleApiService.class);
        api.getStudySubmodule(id,new Callback<StudySubmodules>() {
            @Override
            public void success(StudySubmodules studySubmodules, Response response) {
                // updateDisplay();
                Log.d("tag","success");
                studySubmodulesObject=studySubmodules;
                updateDisplay();

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("tag","failure");
            }
        });
    }

    //Update layout
    private void updateDisplay(){
        if(studySubmodulesObject==null){
            return;
        }
        //Set text on controls
        ID.setText(studySubmodulesObject.getId().toString());
        shortname.setText(studySubmodulesObject.getShortname());
        name.setText(studySubmodulesObject.getName());
        moduleId.setText(studySubmodulesObject.getModuleId());
        courseId.setText(studySubmodulesObject.getCourseid());
        order.setText(studySubmodulesObject.getOrder());
        description.setText(studySubmodulesObject.getDescription());
        entryDate.setText(studySubmodulesObject.getEntryDate());
        lastUpdate.setText(studySubmodulesObject.getLastUpdate());
        lastUpdateUserId.setText(studySubmodulesObject.getLastupdateUserId());
        creationUserId.setText(studySubmodulesObject.getCreationUserId());
        markedForDeletion.setText(studySubmodulesObject.getMarkedForDeletion());
        markedForDeletionDate.setText(studySubmodulesObject.getMarkedForDeletionDate());
    }

    private StudySubmodules getDataStudySubmodules() {
        StudySubmodules studysubmodules = new StudySubmodules();
        studysubmodules.setId(ID.getText().toString());
        Log.d(TAG, "shortname length: " + shortname.getText().toString().length());
        Log.d(TAG, courseId.getText().toString());
        Log.d(TAG,entryDate.getText().toString());
        Log.d(TAG, moduleId.getText().toString());



        //Check if fields are empty
        if(!(shortname.getText().toString().length() ==0)){
            studysubmodules.setShortname(shortname.getText().toString());
        }else{
            Toast.makeText(getActivity(),"Some field is empty",Toast.LENGTH_LONG).show();
            return null;
        }

        if(!(courseId.getText().toString().length()==0)) {
            studysubmodules.setCourseid(courseId.getText().toString());
        }else{
            Toast.makeText(getActivity(),"Some field is empty",Toast.LENGTH_LONG).show();
            return null;
        }
        if(!(entryDate.getText().toString().length()==0)) {

            studysubmodules.setEntryDate(entryDate.getText().toString());
        }else{
            Toast.makeText(getActivity(),"Some field is empty",Toast.LENGTH_LONG).show();
            return null;
        }
        //We dont need last update
        //studysubmodules.setLastUpdate("");
        //can be null on the database
        studysubmodules.setLastupdateUserId(lastUpdateUserId.getText().toString());
        studysubmodules.setCreationUserId(creationUserId.getText().toString());
        if(!(markedForDeletion.getText().toString().length()==0)) {
            studysubmodules.setMarkedForDeletion(markedForDeletion.getText().toString());
        }else{
            Toast.makeText(getActivity(),"Some field is empty",Toast.LENGTH_LONG).show();
            return null;
        }

        studysubmodules.setMarkedForDeletionDate(markedForDeletionDate.getText().toString());

        if(!(moduleId.getText().toString().length()==0)) {
            studysubmodules.setModuleId(moduleId.getText().toString());
        }else{
            Toast.makeText(getActivity(),"Some field is empty",Toast.LENGTH_LONG).show();
            return null;
        }
        //Return object studysubmodules
        return studysubmodules;
    }

    //Method to call retrofit post sending studysubmodules to update
    private void updateStudySubmodule(){
        //Get the studysubmodules object
        StudySubmodules studySubmodules = getDataStudySubmodules();

        //set rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(StudySubmoduleApi.ENDPOINT).build();
        StudySubmoduleApiService api =adapter.create(StudySubmoduleApiService.class);

        api.updateStudySubmodule(studySubmodules, new Callback<Resultat>() {
            @Override
            public void success(Resultat result, Response response) {
                Toast.makeText(getActivity(), "Study Submodule " + result.getId() + " " + result.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), "UPDATE ERROR! " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    //Method to put studysubmodules
    private void insertStudySubmodule() {
        StudySubmodules studySubmodules = getDataStudySubmodules();
        if (!(studySubmodules == null)){
            studySubmodules.setId("");

            //Call put method
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(StudySubmoduleApi.ENDPOINT).build();
            StudySubmoduleApiService api = adapter.create(StudySubmoduleApiService.class);
            api.insertStudySubmodule(studySubmodules, new Callback<Resultat>() {
                @Override
                public void success(Resultat result, Response response) {
                    Toast.makeText(getActivity(), "Study Submodule " + result.getId() + " " + result.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getActivity(), "INSERT" +
                            "" +
                            " ERROR! " + error.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


}
