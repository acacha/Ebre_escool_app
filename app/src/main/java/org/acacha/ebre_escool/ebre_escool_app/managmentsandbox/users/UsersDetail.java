package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users;

import android.app.Fragment;

import org.acacha.ebre_escool.ebre_escool_app.helpers.OnFragmentInteractionListener;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.pojos.Users;

import retrofit.RestAdapter;

/**
 * Created by dorian on 11/03/15.
 */
public class UsersDetail extends Fragment{
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
    private Users usersObject;
    private int usersId;
    //Controls
}
