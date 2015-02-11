package org.acacha.ebre_escool.ebre_escool_app.initial_settings;

import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.gson.Gson;
import com.squareup.okhttp.ResponseBody;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.accounts.EbreEscoolAccount;
import org.acacha.ebre_escool.ebre_escool_app.apis.EbreEscoolServerAuthenticate;
import org.acacha.ebre_escool.ebre_escool_app.helpers.AlertDialogManager;
import org.acacha.ebre_escool.ebre_escool_app.helpers.AndroidSkeletonUtils;
import org.acacha.ebre_escool.ebre_escool_app.helpers.ConnectionDetector;
import org.acacha.ebre_escool.ebre_escool_app.pojos.School;
import org.acacha.ebre_escool.ebre_escool_app.settings.SettingsActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class SignUpActivity extends FragmentActivity implements
	OnClickListener,ConnectionCallbacks,OnConnectionFailedListener,
        SignUpActivityFragment.OnFragmentInteractionListener {

    //Data for Authenticator
    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public final static String ARG_AUTH_TYPE = "AUTH_TYPE";
    public final static String ARG_ACCOUNT_NAME = "ACCOUNT_NAME";
    public final static String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_ACCOUNT";

    // Taken from AccountAuthenticatorActivity. We cannot extends this Activity because we are using Fragments!
    // https://github.com/android/platform_frameworks_base/blob/master/core/java/android/accounts/AccountAuthenticatorActivity.java
    private AccountAuthenticatorResponse mAccountAuthenticatorResponse = null;
    private Bundle mResultBundle = null;

	// Google client to interact with Google API
	private GoogleApiClient mGoogleApiClient;

	private static final int RC_SIGN_IN_GOOGLE = 3889;

	private static final String TAG = "SignUpActivity";

	private boolean mSignInClicked;

	// Profile pic image size in pixels
	private static final int PROFILE_PIC_SIZE = 400;

    private static final int REQUEST_CODE_SIGN_UP = 94;

	private boolean OnStartAlreadyConnected = false;

    private final Gson gson = new Gson();

	//TWITTER
	// Constants
	/**
	 * Register your here app https://dev.twitter.com/apps/new and get your
	 * consumer key and secret
     * https://apps.twitter.com/app/7881829
	 * */
	static String TWITTER_CONSUMER_KEY = "eruHjVBeXWM2DwlwyZwl03usD";
	static String TWITTER_CONSUMER_SECRET = "KBSYVj3NGirdxNVAHMfnAYB6qd93G68YsAyVnLrjxFme2eyOX1"; // place your consumer secret here

	// Preference Constants
	static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

	static final String TWITTER_CALLBACK_URL = "oauth://org.acacha.ebre_escool.ebre_escool_app.signup";

	// Twitter oauth urls
	static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";

	// Twitter
	private static Twitter twitter;
	private static RequestToken requestToken;

	// Internet Connection detector
	private ConnectionDetector cd;

	//FACEBOOK
	private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private boolean isResumed = false;

    /**
	 * A flag indicating that a PendingIntent is in progress and prevents us
	 * from starting further intents.
	 */
	private boolean mIntentInProgress;

	private ConnectionResult mConnectionResult;

	private ProgressDialog progressDialog;

	private AlertDialogManager alert = new AlertDialogManager();

	private SharedPreferences mSharedPreferences;

    private SharedPreferences mSettings;

    public final static String PARAM_USER_PASS = "USER_PASS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG,"onCreate!");
        super.onCreate(savedInstanceState);

        //Uncomment for debug:
        AndroidSkeletonUtils.debugIntent(getIntent(), "SignUpActivity onCreate");

        setContentView(R.layout.activity_sign_up);
        if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.sign_up_container, new SignUpActivityFragment()).commit();
		}

        ActionBar ab = getActionBar();
        ab.setTitle(getString(R.string.initial_settings_action_bar_title));
        ab.setSubtitle(getString(R.string.initial_settings_action_bar_subtitle));

        mSettings = PreferenceManager.getDefaultSharedPreferences(this);

       	//GOOGLE
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this).addApi(Plus.API)
		.addScope(Plus.SCOPE_PLUS_LOGIN).build();

		//FACEBOOK
		uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);

        //TWITTER
        //Check if twitter keys are set
 		if(TWITTER_CONSUMER_KEY.trim().length() == 0 || TWITTER_CONSUMER_SECRET.trim().length() == 0){
 			// Internet Connection is not present
 			alert.showAlertDialog(SignUpActivity.this,
                    getString(R.string.twitter_oauth_error_title),getString(R.string.twitter_oauth_error_label), false);
 			// stop executing code by return
 			return;
 		}

 		//TODO: please use threads correctly for twitter!!
 		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy =
			new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
        builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
        Configuration configuration = builder.build();

        TwitterFactory factory = new TwitterFactory(configuration);
        twitter = factory.getInstance();

		// Shared Preferences
		mSharedPreferences = getApplicationContext().getSharedPreferences(
				"AndroidSocialTemplate", 0);

		/** This if conditions is tested once is
		 * redirected from twitter page. Parse the uri to get oAuth
		 * Verifier
		 * */
		Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(TWITTER_CALLBACK_URL)) {
            // oAuth verifier
            String verifier = uri
                    .getQueryParameter(URL_TWITTER_OAUTH_VERIFIER);

            try {
                // Get the access token
                AccessToken accessToken = twitter.getOAuthAccessToken(
                        requestToken, verifier);

                // Shared Preferences
                Editor e = mSharedPreferences.edit();

                // After getting access token, access token secret
                // store them in application preferences
                e.putString(PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
                e.putString(PREF_KEY_OAUTH_SECRET,
                        accessToken.getTokenSecret());
                // Store login status - true
                e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
                e.commit(); // save changes

                Log.d("Twitter OAuth Token", "> " + accessToken.getToken());
                fillSignupFormWithTwitter();
            } catch (Exception e) {
                // Check log for login errors
                Log.e("Twitter Login Error", "> " + e.getMessage());
            }
        }

	}

    /*
    * Fill Sign Up Form with Twitter Data
    */
    private void fillSignupFormWithTwitter() {

        SignUpActivityFragment fragment = (SignUpActivityFragment)
                getSupportFragmentManager().findFragmentById(R.id.sign_up_container);
        if (fragment != null) {
            User user =null;
            String email = "";
            String givenName = "";
            String lastName = "";
            try {
                if (twitter!=null) {
                    Log.d(TAG, "Twitter Id: " + twitter.getId());

                    user = twitter.showUser(twitter.getId());
                } else {
                    Log.d(TAG, "Twitter User not found!");
                }
                givenName = user.getName();

                Log.d(TAG, "Twitter User: " + user);

            } catch (TwitterException e) {
                Log.d(TAG, "ERROR retrieving Twitter User!");
                e.printStackTrace();
            } catch (IllegalStateException e) {
                Log.d(TAG, "ERROR retrieving Twitter User!");
                e.printStackTrace();
            }

            fragment.getEmail().setText(email);
            fragment.getGivenname().setText(givenName);
            fragment.getLastname().setText(lastName);
            fragment.getPassword().setFocusableInTouchMode(true);
            fragment.getPassword().requestFocus();

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        } else {
            Log.d(TAG, "ERROR! Fragment not found!");

            AutoCompleteTextView first_name = (AutoCompleteTextView) findViewById(R.id.first_name);
            User user =null;
            try {
                if (twitter!=null) {
                    Log.d(TAG, "Twitter Id: " + twitter.getId());

                    user = twitter.showUser(twitter.getId());
                } else {
                    Log.d(TAG, "Twitter User not found!");
                }
                Log.d(TAG, "Twitter User: " + user);

            } catch (TwitterException e) {
                Log.d(TAG, "ERROR retrieving Twitter User!");
                e.printStackTrace();
            } catch (IllegalStateException e) {
                Log.d(TAG, "ERROR retrieving Twitter User!");
                e.printStackTrace();
            }
            first_name.setText(user.getName());
        }

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    /**
     *
     * Used by facebook SDK
     *
     */
    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
		Log.d(TAG,"onSessionStateChange!");
        if (isResumed) {
        	Log.d(TAG,"is resumed!");
            FragmentManager manager = getSupportFragmentManager();
            int backStackSize = manager.getBackStackEntryCount();
            for (int i = 0; i < backStackSize; i++) {
                manager.popBackStack();
            }
            // check for the OPENED state instead of session.isOpened() since for the
            // OPENED_TOKEN_UPDATED state, the selection fragment should already be showing.
            if (state.equals(SessionState.OPENED)) {
            	Log.d(TAG,"onSessionStateChange: Logged to facebook");

                // Make an API call to get user data and define a
                // new callback to handle the response.
                Request request = Request.newMeRequest(session,
                        new Request.GraphUserCallback() {
                            @Override
                            public void onCompleted(GraphUser user, Response response) {
                                // If the response is successful

                                if (user != null) {
                                    Log.d(TAG,"Facebook User Ok: " + user);
                                    SignUpActivityFragment fragment = (SignUpActivityFragment)
                                            getSupportFragmentManager().findFragmentById(R.id.sign_up_container);
                                    if (fragment != null) {
                                        String email = "";
                                        if (user.getProperty("email")!=null) {
                                            email = user.getProperty("email").toString();
                                        } else {
                                            Log.d(TAG,"ERROR. NOT EMAIL PROPERTY FOUND!");
                                        }
                                        if (user.asMap() != null) {
                                            if (user.asMap().get("email")!=null) {
                                                email = user.asMap().get("email").toString();
                                            } else {
                                                Log.d(TAG,"user.asMap().get(\"email\") returns null!");
                                            }
                                        } else {
                                            Log.d(TAG,"asMap returns null!");
                                        }
                                        String givenName = user.getFirstName();
                                        String lastName = user.getMiddleName() +  " " + user.getLastName();
                                        fragment.getEmail().setText(email);
                                        fragment.getGivenname().setText(givenName);
                                        fragment.getLastname().setText(lastName);
                                        fragment.getPassword().setFocusableInTouchMode(true);
                                        fragment.getPassword().requestFocus();

                                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                                .permitAll().build();
                                        StrictMode.setThreadPolicy(policy);

                                    } else {
                                        Log.d(TAG, "ERROR! Fragmennt not found!");
                                    }

                                    // Set the id for the ProfilePictureView
                                    // view that in turn displays the profile picture.
                                    //profilePictureView.setProfileId(user.getId());
                                    // Set the Textview's text to the user's name.
                                    //userNameView.setText(user.getName());
                                }

                                if (response.getError() != null) {
                                    // Handle errors, will do so later.
                                    Toast.makeText(getApplicationContext(),
                                            "Error getting facebook user...",
                                            Toast.LENGTH_LONG);
                                }
                            }
                        });

                request.executeAsync();
            } else if (state.isClosed()) {
                //NO logged to facebook
                Log.d(TAG,"onSessionStateChange: Not logged to facebook");
            }
        }
    }



	@Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
        isResumed = true;

        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this);
    }

	@Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
        isResumed = false;

        // Call the 'deactivateApp' method to log an app event for use in analytics and advertising
        // reporting.  Do so in the onPause methods of the primary Activities that an app may be launched into.
        AppEventsLogger.deactivateApp(this);
    }

    /*
     * Used by facebook SDK
     *
     */
	@Override
    protected void onResumeFragments() {
		Log.d(TAG,"onResumeFragments!");
        super.onResumeFragments();
        Session session = Session.getActiveSession();

        if (session != null && session.isOpened()) {
            // if the session is already open, try to show the selection fragment

        	//Login ok
        	Log.d(TAG,"Login to facebook Ok!");

            //TODO: Get Facebook Profile info
            //TODO: Fill form fields with Facebook Data

        }  else {
            // otherwise present the splash screen and ask the user to login, unless the user explicitly skipped.
        	//showFragment(SPLASH, false);
        	Log.d(TAG,"Login to facebook not Ok!");
        }
    }

	@Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);

        //outState.putBoolean(USER_SKIPPED_LOGIN_KEY, userSkippedLogin);
    }

	protected void onStart() {
		Log.d(TAG, "onStart!");
		super.onStart();

		if (!this.OnStartAlreadyConnected) {
			//NOT ALREADY CONNECTED: CONNECT!
			Log.d(TAG, "NOT Already connected! Connecting to...");
			mGoogleApiClient.connect();
			Log.d(TAG, "...connected to Google API!");
		} else{
			Log.d(TAG, "Already connected!");
		}

	}

	protected void onStop() {
		Log.d(TAG, "onStop!");
		super.onStop();
		/* NOT DISCONNECT! WE will come back after result and OnActivityResult does not executes previously onStart!

			Log.d(TAG, "onStop mGoogleApiClient isConnected. Disconnecting...!");
			mGoogleApiClient.disconnect();
		}*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * Sign-up into google
	 * */
	private void signUpWithGplus() {
		Log.d(TAG, "signUpWithGplus!");
        //Always disconnect before reconnect
        mGoogleApiClient.disconnect();
		if (!mGoogleApiClient.isConnecting()) {
			Log.d(TAG, "Error signing up. Calling resolveSignInError!");
			mSignInClicked = true;
			resolveSignInError();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_google_sign_up:
			// Signin with Google Plus button clicked
            Log.d(TAG,"Click on Sign Up with Google");
			signUpWithGplus();
			break;
		/* NOTE: Facebook have a custom button!
		case R.id.btn_facebook_sign_in:
			// Signin with Facebook button clicked
			loginToFacebook();
			break;*/
        /* TWITTER DOES NOT PROVIDES USER EMAIL THOUGH API. LEFT HERE MAY BE IN FUTURE
		case R.id.btn_twitter_sign_up:
			// Signin with Twitter button clicked
            Log.d(TAG,"Click on Sign Up with Twitter");
            signUpWithTwitter();
			break;*/
        case R.id.btnPersonalSignUp:
                // Signin with Login Form
                signUpEbreescool(v);
                break;
        case R.id.link_to_login:
                // Signin with Login Form
                Log.d(TAG,"CLICK ON LINK TO LOGIN. Come back to Loginactivity");
                Intent i = new Intent(SignUpActivity.this, InitialSettingsActivity.class);
                startActivityForResult(i, REQUEST_CODE_SIGN_UP);
                break;

		}
	}

	/**
	 * Sign-out from google
	 * */
	private void signOutFromGplus() {
		Log.d(TAG, "signOutFromGplus!");
		if (mGoogleApiClient.isConnected()) {
			Log.d(TAG, "signOutFromGplus is connected then disconnecting!");
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			mGoogleApiClient.disconnect();
			mGoogleApiClient.connect();
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.d(TAG, "onConnected!");
		mSignInClicked = false;
		//Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
		Log.d(TAG, "User is connected to Google Plus now!");

        Person googlePerson  = getProfileInformation();

        Person.Name objpersonName = googlePerson.getName();
        String personId = googlePerson.getId();
        String personName = googlePerson.getDisplayName();
        String personPhotoUrl = googlePerson.getImage().getUrl();
        String personGooglePlusProfile = googlePerson.getUrl();
        String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

        /* TODO: Example how to pick picture/avatar
        // by default the profile url gives 50x50 px image only
        // we can replace the value with whatever dimension we want by
        // replacing sz=X
        personPhotoUrl = personPhotoUrl.substring(0,
                personPhotoUrl.length() - 2)
                + PROFILE_PIC_SIZE;

        //new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);*/

        Log.d(TAG, "Id: " + personId + "Name: " + personName + ", plusProfile: "
                + personGooglePlusProfile + ", email: " + email
                + ", Image: " + personPhotoUrl);

        //TODO: Save to shared preferences?


        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                email = Plus.AccountApi.getAccountName(mGoogleApiClient);
            }
        } catch  (Exception e) {
            e.printStackTrace();
        }

        if (email!=null) {
            Log.d(TAG, "email is not null");
            //check if user already exists. Use email to find user!
            boolean user_already_exists = false;
            if (user_already_exists) {
                //User have a Google Apps account of same center --> Allowed to login
                //LOGIN TO API TO OBTAIN auth_token
            } else {
                //OK! It could be a new username Fill form
                SignUpActivityFragment fragment = (SignUpActivityFragment)
                        getSupportFragmentManager().findFragmentById(R.id.sign_up_container);
                if (fragment != null) {
                    fragment.getEmail().setText(email);
                    fragment.getGivenname().setText(objpersonName.getGivenName());
                    fragment.getLastname().setText(objpersonName.getFamilyName());
                    fragment.getPassword().setFocusableInTouchMode(true);
                    fragment.getPassword().requestFocus();

                } else {
                    Log.d(TAG, "ERROR! Fragment not found!");
                }

            }
        } else {
            //ERROR
            Toast.makeText(getApplicationContext(),
                    getString(R.string.invalid_google_email), Toast.LENGTH_LONG).show();
        }
	}

	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private Person getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
                return currentPerson;
			} else {
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}

	@Override
	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}
	}

	/**
	 * Method to resolve any signin errors
	 * */
	private void resolveSignInError() {
		if (mConnectionResult!=null){
			if (mConnectionResult.hasResolution()) {
				try {
					mIntentInProgress = true;
					mConnectionResult.startResolutionForResult(this, RC_SIGN_IN_GOOGLE);
				} catch (SendIntentException e) {
					mIntentInProgress = false;
					mGoogleApiClient.connect();
				}
			}
		}
	}

	/**
	 * Background Async task to load user profile picture from url
	 * */
	private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public LoadProfileImage(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}

	@Override
	public void onFragmentInteraction(Uri uri) {
		// Nothing to do!?
	}

    /**
     * Login to ebre_escool
     *
     */
    private void signUpEbreescool(View v) {

        String first_name = ((EditText) findViewById(R.id.first_name)).getText().toString();
        String last_name = ((EditText) findViewById(R.id.last_name)).getText().toString();
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String sign_up_password = ((EditText)
                findViewById(R.id.sign_up_password)).getText().toString();

        //DEBUG
        Log.d(TAG,"first_name: " + first_name);
        Log.d(TAG,"last_name: " + last_name);
        Log.d(TAG,"email: " + email);
        //Log.d(TAG,"sign_up_password: " + sign_up_password);

        if (first_name.equals("")) {
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(getApplicationContext(),
                    getString(R.string.first_name_required), duration).show();
            return;
        }
        if (last_name.equals("")) {
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(getApplicationContext(),
                    getString(R.string.last_name_required), duration).show();
            return;
        }

        if (email.equals("")) {
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(getApplicationContext(),
                    getString(R.string.email_required), duration).show();
            return;
        }

        if (sign_up_password.equals("")) {
            int duration = Toast.LENGTH_LONG;
            Toast.makeText(getApplicationContext(),
                    getString(R.string.sign_up_password_required), duration).show();
            return;
        }

        String md5password = computeMD5Hash(sign_up_password);
        Log.d(TAG,"MD5 password: " + md5password);

        SignUpToEbreEscoolAsyncTask login_task = new SignUpToEbreEscoolAsyncTask(this);
        login_task.execute(first_name,last_name,email,md5password);
    }

    public String computeMD5Hash(String password){

        StringBuffer MD5Hash = new StringBuffer();

        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();


            for (int i = 0; i < messageDigest.length; i++)
            {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }



        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return MD5Hash.toString();
    }
	
	/**
	 * Function to SignUp with twitter
	 * */
	private void signUpWithTwitter() {
		Log.d(TAG,"signUpWithTwitter!");
        Log.d(TAG,"Starting twitter external auth!");
        try {
            requestToken = twitter
                    .getOAuthRequestToken(TWITTER_CALLBACK_URL);
            this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
                    .parse(requestToken.getAuthenticationURL())));
        } catch (TwitterException e) {
            e.printStackTrace();
        }
	}

    private class SignUpResultBundle {

        public static final int ERROR_TYPE_CONNECT_EXCEPTION = -1;

        public static final int ERROR_TYPE_IO_EXCEPTION = -2;

        private String firstName;

        private String lastName;

        private String email;

        private String password = "";

        private boolean result_ok;

        private int error_type = 0;

        private String error_message;

        private com.squareup.okhttp.Response response;

        //settings
        private SharedPreferences settings;

        public int getError_type() {
            return error_type;
        }

        public void setError_type(int error_type) {
            this.error_type = error_type;
        }

        public String getError_message() {
            return error_message;
        }

        public void setError_message(String error_message) {
            this.error_message = error_message;
        }

        public boolean isResult_ok() {
            return result_ok;
        }

        public void setResult_ok(boolean result_ok) {
            this.result_ok = result_ok;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            String result = "name: " + "TODO" + " | " +
                    "error_type: " + this.getError_type() + " | " +
                    "error_message: " + this.getError_message() + " | " +
                    "response: " + this.getResponse();
            return result;
        }

        public com.squareup.okhttp.Response getResponse() {
            return response;
        }

        public void setResponse(com.squareup.okhttp.Response response) {
            this.response = response;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        Log.d(TAG, "onActivityResult. RequestCode: " + requestCode + " ResponseCode:" + responseCode + "!");
        super.onActivityResult(requestCode, responseCode, intent);

        //Facebook:
        uiHelper.onActivityResult(requestCode, responseCode, intent);

        switch (requestCode) {
            case RC_SIGN_IN_GOOGLE:
                Log.d(TAG, "Result comes from Google Sign Up Button");
                if (responseCode != RESULT_OK) {
                    Log.d(TAG, "Result is not ok!");
                    mSignInClicked = false;
                }

                mIntentInProgress = false;

                if (!mGoogleApiClient.isConnecting()) {
                    Log.d(TAG, "mGoogleApiClient is not connecting. Call to connect!");
                    mGoogleApiClient.connect();
                }
                break;

            default:
                break;
        }
    }

    private class SignUpToEbreEscoolAsyncTask extends AsyncTask<String, Void, SignUpResultBundle> {

        private ProgressDialog dialog;

        public SignUpToEbreEscoolAsyncTask(SignUpActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        /** progress dialog to show user that the backup is processing. */
        /** application context. */
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getString(R.string.validating_sign_up));
            this.dialog.show();
        }

        @Override
        protected SignUpResultBundle doInBackground(final String... args) {

            //login_task.execute(first_name,last_name,email,md5password);
            String first_name = args[0];
            String last_name = args[1];
            String email = args[2];
            String password = args[3];

            com.squareup.okhttp.Response response = null;
            String error_message ="";
            SignUpResultBundle result_bundle = new SignUpResultBundle();
            result_bundle.setResult_ok(true);
            result_bundle.setFirstName(first_name);
            result_bundle.setLastName(last_name);
            result_bundle.setEmail(email);
            result_bundle.setPassword(password);
            result_bundle.setResult_ok(true);

            //HTTP POST TO ebreescoollogin
            EbreEscoolServerAuthenticate eeSA = new EbreEscoolServerAuthenticate();

            //GET LOGIN URL FROM SCHOOLS LIST --> SELECTED SCHOOL -> login_api_url
            //Get account name from shared preferences settings
            mSettings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            //Retrieve schools on JSON format To obtain selected schools
            String json_schools_list = mSettings.getString("schools_list", "");
            Log.d(TAG,"###### json_schools_list: " + json_schools_list);

            Gson gson = new Gson();

            School[] schools = gson.fromJson(json_schools_list, School[].class);
            schools = gson.fromJson(json_schools_list, School[].class);

            String current_selected_school =
                    mSettings.getString(SettingsActivity.SCHOOLS_LIST_KEY,"0");

            Log.d(TAG,"Getted current selected school: " + current_selected_school);
            String login_url = EbreEscoolServerAuthenticate.DEFAULT_LOGIN_API_URL;
            try {
                login_url =
                        schools[Integer.parseInt(current_selected_school)].getLogin_api_url();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d(TAG,"login_url: " + login_url);

            //TODO
            try {
                response = eeSA.userSignUp(first_name,last_name,
                         email, password,login_url, EbreEscoolAccount.AUTHTOKEN_TYPE_FULL_ACCESS);
            } catch (ConnectException ce) {
                error_message = ce.getLocalizedMessage();
                result_bundle.setResult_ok(false);
                result_bundle.setError_type(SignUpResultBundle.ERROR_TYPE_CONNECT_EXCEPTION);
                ce.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                error_message = e.getLocalizedMessage();
                result_bundle.setResult_ok(false);
                result_bundle.setError_type(SignUpResultBundle.ERROR_TYPE_IO_EXCEPTION);
            } catch (Exception e) {
                e.printStackTrace();
                error_message = e.getLocalizedMessage();
                result_bundle.setResult_ok(false);
                result_bundle.setError_type(SignUpResultBundle.ERROR_TYPE_IO_EXCEPTION);
            }

            Log.d(TAG,"Response: " + response);
            result_bundle.setError_message(error_message);
            result_bundle.setResponse(response);
            if (error_message!=""){
                Log.d(TAG,"Error message: " + error_message);
            }

            return result_bundle;

        }

        @Override
        protected void onPostExecute(final SignUpResultBundle result) {
            Log.d(TAG,"LoginResultBundle: " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if (result.isResult_ok()) {
                Log.d(TAG,"RESULT IS OK!");
                com.squareup.okhttp.Response response = result.getResponse();
                int response_code = 0;

                if (response!= null) {
                    response_code = response.code();
                    Log.d(TAG,"Response: " + response.toString());
                }

                Log.d(TAG,"response_code: " + response_code);

                if (response_code!=0) {
                    Log.d(TAG,"response_code not zero");
                    if (response_code != 200) {
                        Log.d(TAG,"response_code not 200");
                        //RESPONSE OBTAINED OK BUT NOT EXPECTED RESULT CODE
                        String toast_message = result.getError_message();
                        if (response_code == 404) {
                            //User not found
                            Log.d(TAG,"response_code 404. User not found");
                            toast_message = getString(R.string.user_not_found_label);
                        }
                        if (response_code == 400) {
                            //Password incorrect
                            Log.d(TAG,"response_code 400. Password incorrect!");
                            toast_message = getString(R.string.password_incorrect_label);
                        }

                        int duration = Toast.LENGTH_LONG;
                        Toast.makeText(getApplicationContext(),
                                toast_message, duration).show();
                    } else {

                        Log.d(TAG,getString(R.string.response_code_200));

                        //TODO: recover username from server signup. Server generate signup!
                        int duration = Toast.LENGTH_LONG;
                        Toast.makeText(getApplicationContext(),
                                R.string.login_ok_label, duration).show();

                        //SIGNUP OK

                        ResponseBody body = response.body();
                        String json_response = null;
                        try {
                            json_response = body.string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Log.d(TAG,"response body: " + json_response);


                        EbreEscoolSignUpResponse eeresponse = null;
                        eeresponse = gson.fromJson(json_response, EbreEscoolSignUpResponse.class);

                        //TODO: Obtain data as result of asynctask. THIS IS ONLY FOR TEST:
                        Bundle data = new Bundle();
                        //String userName = result.getUsername();
                        String accountType = EbreEscoolAccount.ACCOUNT_TYPE;

                        //TODO: Get auth token form response body (GSON!)

                        String authtoken = eeresponse.getApiUserProfile().getAuthToken();

                        Log.d(TAG,"authtoken: " + authtoken);

                        String userPass = result.getPassword();
                        //data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
                        data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                        data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                        data.putBoolean(ARG_IS_ADDING_NEW_ACCOUNT, true);
                        data.putString(PARAM_USER_PASS, userPass);

                        final Intent res = new Intent();
                        res.putExtras(data);

                        //TODO: finishSignUp(res)????;

                        //Compte creat correctament
                        //Return to login page? Login User?
                        //Wait for email confirmation?
                    }
                }
            } else {
                int duration = Toast.LENGTH_LONG;
                Toast.makeText(getApplicationContext(),
                        result.getError_message(), duration).show();
            }
        }
    }
}



