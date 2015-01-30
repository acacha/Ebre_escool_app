package org.acacha.ebre_escool.ebre_escool_app.initial_settings;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.facebook.widget.LoginButton;

import org.acacha.ebre_escool.ebre_escool_app.R;
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
	
	private LoginButton loginButton;
	
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;
	
	private Button btnGoogleSignIn;

	private Button btnLoginTwitter;

    private Button btnLoginForm;

    private String[] addresses;

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

        //Autocomplete username
        ArrayAdapter<String> adapter = this.getEmailAddressAdapter(getActivity());

        AutoCompleteTextView textView = (AutoCompleteTextView) view.findViewById(R.id.username);

        // Numero de caracteres necesarios para que se empiece
        // a mostrar la lista
        textView.setThreshold(1);

        // Se establece el Adapter
        textView.setAdapter(adapter);

        String proposedValue = null;
        for (String s: addresses)    {
            if (s.endsWith("@iesebre.com")){
                proposedValue = s;
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
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
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
