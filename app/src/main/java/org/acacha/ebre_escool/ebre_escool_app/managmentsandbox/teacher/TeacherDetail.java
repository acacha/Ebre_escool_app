package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.helpers.OnFragmentInteractionListener;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher.teacher_pojos.Teacher;

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
    private int teacherId;
    public static final String ENDPOINT = "http://185.13.76.85:8769/ebre-escool/index.php/criminal/api/hell";

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
        View view= inflater.inflate(R.layout.fragment_teacher_detail, container, false);
        // data sended from teacher fragment
        Bundle extras = getArguments();
        if (extras != null) {
            teacherId = extras.getInt("id");
            Log.d("tag", "detail id :" + teacherId);
        }

        //set rest adapter
        adapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT).build();
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
        Teacher teacherObject = null;
        Log.d("tag","get :"+id);
        RetrofitApiService api =adapter.create(RetrofitApiService.class);
        api.getTeacher(id,new Callback<Teacher>() {
            @Override
            public void success(Teacher teacher, Response response) {
                // updateDisplay();
                Log.d("tag","success");
                Toast.makeText(getActivity(),"id :"+teacher.getId(),Toast.LENGTH_LONG);

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("tag","failure");
            }
        });

    }

}
