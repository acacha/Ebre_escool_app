package org.acacha.ebre_escool.ebre_escool_app.initial_settings;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.widget.LoginButton;
import com.google.gson.Gson;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.pojos.School;
import org.acacha.ebre_escool.ebre_escool_app.settings.SettingsActivity;
import org.codepond.wizardroid.WizardStep;

/**
 * A simple {@link Fragment} subclass. Activities that contain this fragment
 * must implement the
 * {@link InitialSettingsStep2Login.OnFragmentInteractionListener} interface to
 * handle interaction events. Use the {@link InitialSettingsStep2Login#newInstance}
 * factory method to create an instance of this fragment.
 * 
 */
public class InitialSettingsStep2Login extends WizardStep {

    //Look up for shared preferences
    final String LOG_TAG = "InitialSettingsStep2Login";
	
	private LoginButton loginButton;
	
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

    private InitialSettingsActivity mActivity;
	
	private Button btnGoogleSignIn;

	private Button btnLoginTwitter;

    private Button btnLoginForm;

    private TextView tvLinkToRegister;

    private String[] addresses;

    private School[] mSchools;

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
	 * @return A new instance of fragment LoginActivityFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static InitialSettingsStep2Login newInstance(String param1, String param2) {
		InitialSettingsStep2Login fragment = new InitialSettingsStep2Login();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

    public School[] getSchools() {
        return mSchools;
    }

    public void setmSchools(School[] mSchools) {
        this.mSchools = mSchools;
    }

    public InitialSettingsStep2Login() {
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
		View view = inflater.inflate(R.layout.fragment_login_activity, container,
				false);

        Log.d("InitialSettingsStep2Login", "FRAGMENT ID: " + getId());
		
		btnGoogleSignIn = (Button) view.findViewById(R.id.btn_google_sign_in);
		
		// Button click listeners
		btnGoogleSignIn.setOnClickListener((OnClickListener) getActivity());
		
		loginButton = (LoginButton) view.findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions("user_friends");
        
        btnLoginTwitter = (Button) view.findViewById(R.id.btn_twitter_sign_in);
		btnLoginTwitter.setOnClickListener((OnClickListener) getActivity());

        btnLoginForm = (Button) view.findViewById(R.id.btnPersonalLogin);
        btnLoginForm.setOnClickListener((OnClickListener) getActivity());

        tvLinkToRegister = (TextView) view.findViewById(R.id.link_to_register);
        tvLinkToRegister.setOnClickListener((OnClickListener) getActivity());

        //Autocomplete username
        ArrayAdapter<String> adapter = this.getEmailAddressAdapter(getActivity());

        AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.username);

        // Numero de caracteres necesarios para que se empiece
        // a mostrar la lista
        textView.setThreshold(1);

        // Se establece el Adapter
        textView.setAdapter(adapter);

        //Get account name from shared preferences settings
        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //RECOVER ACCOUNT NAME FROM PREVIOUS LOGINS
        String account_name =
                settings.getString(SettingsActivity.ACCOUNT_NAME_KEY,"");

        //Retrieve schools on JSON format To obtain selected schools
        String json_schools_list = settings.getString("schools_list", "");
        Log.d(LOG_TAG,"###### json_schools_list: " + json_schools_list);

        Gson gson = new Gson();
        setmSchools(gson.fromJson(json_schools_list, School[].class));

        String current_selected_school =
                settings.getString(SettingsActivity.SCHOOLS_LIST_KEY,"0");

        Log.d(LOG_TAG,"Getted current selected school: " + current_selected_school);

        String school_dns_domain_name = "iesebre.com";

        try {
            school_dns_domain_name =
                    getSchools()[Integer.parseInt(current_selected_school)].getSchool_dns_domain();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG,"school_dns_domain_name: " + school_dns_domain_name);

        String proposedValue = null;

        if (account_name != "") {
            textView.setText(account_name);
        } else {
            for (String s: addresses)    {
                if (s.endsWith("@" + school_dns_domain_name)){
                    proposedValue = s;
                }
            }
        }

        if (proposedValue != null) {
            textView.setText(proposedValue);
        }

		return view;
	}

    private ArrayAdapter<String> getEmailAddressAdapter(Context context) {
        Account[] accounts = AccountManager.get(context).getAccountsByType("com.google");
        addresses = new String[accounts.length];
        for (int i = 0; i < accounts.length; i++) {
            addresses[i] = accounts[i].name;
        }
        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, addresses);
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

        try{
            mActivity = (InitialSettingsActivity) activity;
        }catch(ClassCastException e){
            e.printStackTrace();
            //throw new ClassCastException(activity.toString() +" must be a InitialSettingsActivity");
        }

        mActivity.setLogin_fragment(this);

	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
        mActivity = null;
	}

    @Override
    public void onStop() {
        mActivity = null;
        super.onStop();
    }

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated to
	 * the activity and potentially other fragments contained in that activity.
	 * <p>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
