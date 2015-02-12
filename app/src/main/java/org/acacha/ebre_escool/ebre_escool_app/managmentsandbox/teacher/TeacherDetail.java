package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.helpers.OnFragmentInteractionListener;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher.api.TeacherApi;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher.api.TeacherApiService;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher.pojos.Result;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher.pojos.Teacher;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeacherDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeacherDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherDetail extends Fragment {
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
    private Teacher teacherObject;
    private int teacherId;
    //Controls
    private TextView ID;
    private EditText personId;
    private EditText userId;
    private EditText entryDate;
    private EditText lastUpdate;
    private EditText lastUpdateUserId;
    private EditText creatorId;
    private EditText markedForDeletion;
    private EditText markedForDeletionDate;
    private EditText dniNif;
    private Button btnUpdate;
    private String TAG = "tag";



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherDetail newInstance(String param1, String param2) {
        TeacherDetail fragment = new TeacherDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TeacherDetail() {
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
       // View view= inflater.inflate(R.layout.fragment_teacher_detail, container, false);
        View view= inflater.inflate(R.layout.fragment_teacher_detail, container, false);
        //Get controls
        ID =(TextView)view.findViewById(R.id.teacherId);
        personId =(EditText)view.findViewById(R.id.personId);
        userId = (EditText)view.findViewById(R.id.userId);
        entryDate = (EditText)view.findViewById(R.id.entryDate);
        lastUpdate = (EditText)view.findViewById(R.id.lastUpdate);
        lastUpdateUserId = (EditText)view.findViewById(R.id.lastUpdateUserId);
        creatorId = (EditText)view.findViewById(R.id.creatorId);
        markedForDeletion = (EditText)view.findViewById(R.id.markedForDeletion);
        markedForDeletionDate = (EditText)view.findViewById(R.id.markedForDeletionDate);
        dniNif =(EditText)view.findViewById(R.id.dniNif);
        btnUpdate = (Button)view.findViewById(R.id.btnUpdate);
        //Set click listener for button
        btnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
              //Toast.makeText(getActivity(),"Teacher ID: "+ID.getText().toString(),Toast.LENGTH_LONG).show();
              updateTeacher();

            }
        });


        // get data send from teacher fragment
        Bundle extras = getArguments();
        if (extras != null) {
            teacherId = extras.getInt("id");
            Log.d("tag", "detail id :" + teacherId);

        }

        //set rest adapter
        adapter = new RestAdapter.Builder()
                .setEndpoint(TeacherApi.ENDPOINT).build();
        getOneTeacher(teacherId);

        return view;
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
    //EXECUTE RETROFIT GET ONE TEACHER METHOD
    public void getOneTeacher(Integer id){

        Log.d("tag","get :"+id);
        TeacherApiService api =adapter.create(TeacherApiService.class);
        api.getTeacher(id,new Callback<Teacher>() {
            @Override
            public void success(Teacher teacher, Response response) {
                // updateDisplay();
                Log.d("tag","success");
                teacherObject=teacher;
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
        if(teacherObject==null){
            return;
        }
        //Set text on controls
        ID.setText(teacherObject.getId().toString());
        personId.setText(teacherObject.getPersonId());
        userId.setText(teacherObject.getUserId());
        entryDate.setText(teacherObject.getEntryDate());
        lastUpdate.setText(teacherObject.getLastUpdate());
        lastUpdateUserId.setText(teacherObject.getLastUpdateUserId());
        creatorId.setText(teacherObject.getCreatorId());
        markedForDeletion.setText(teacherObject.getMarkedForDeletion());
        markedForDeletionDate.setText(teacherObject.getMarkedForDeletionDate());
        dniNif.setText(teacherObject.getDNINIF());


    }
    private Teacher getDataTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(ID.getText().toString());
        //Check if fields are empty
        if(!personId.getText().toString().equals("")||!personId.getText().toString().equals("0"))
            teacher.setPersonId(personId.getText().toString());
        if(!userId.getText().toString().equals("")||userId.getText().toString().equals("0"))
            teacher.setUserId(userId.getText().toString());
        if(!entryDate.getText().toString().equals("")||!entryDate.getText().toString().equals("0"))
            teacher.setEntryDate(entryDate.getText().toString());
        teacher.setLastUpdate("");
        //can be null on the database
         teacher.setLastUpdateUserId(lastUpdateUserId.getText().toString());
         teacher.setCreatorId(creatorId.getText().toString());
        if(!markedForDeletion.getText().toString().equals("")||!markedForDeletion.getText().toString().equals("0"))
            teacher.setMarkedForDeletion(markedForDeletion.getText().toString());
        if(!markedForDeletionDate.getText().toString().equals("")||!markedForDeletionDate.getText().toString().equals("0"))
            teacher.setMarkedForDeletionDate(markedForDeletionDate.getText().toString());
        if(!dniNif.getText().toString().equals("")||!dniNif.getText().toString().equals("0"))
            teacher.setDNINIF(dniNif.getText().toString());
        //Return object teacher
        return teacher;
     }
    //Method to call retrofit post sending teacher to update
    private void updateTeacher(){
        //Get the teacher object
        Teacher teacher = getDataTeacher();
        Log.d(TAG,"id "+teacher.getId());
        Log.d(TAG,"id "+teacher.getPersonId());
        Log.d(TAG,"id "+teacher.getUserId());
        Log.d(TAG,"id "+teacher.getEntryDate());
        Log.d(TAG,"id "+teacher.getCreatorId());
        Log.d(TAG,"id "+teacher.getLastUpdate());
        Log.d(TAG,"id "+teacher.getLastUpdateUserId());
        Log.d(TAG,"id "+teacher.getMarkedForDeletion());
        Log.d(TAG,"id "+teacher.getMarkedForDeletionDate());
        Log.d(TAG,"id "+teacher.getDNINIF());

        //set rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(TeacherApi.ENDPOINT).build();
        TeacherApiService api =adapter.create(TeacherApiService.class);

        api.updateTeacher(teacher,new Callback<Result>() {
            @Override
            public void success(Result result, Response response) {
                Toast.makeText(getActivity(),"Teacher "+ID.getText().toString()+" UPDATED!",Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(),"ERROR!"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });






    }


}
