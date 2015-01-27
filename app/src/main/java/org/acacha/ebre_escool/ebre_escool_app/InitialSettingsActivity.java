package org.acacha.ebre_escool.ebre_escool_app;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import com.facebook.AppEventsLogger;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;



public class InitialSettingsActivity extends FragmentActivity implements
	OnClickListener,ConnectionCallbacks,OnConnectionFailedListener, InitialSettingsStep2Login.OnFragmentInteractionListener {

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
	
	private static final String TAG = "LoginActivity";
	
	private boolean mSignInClicked;
	
	// Profile pic image size in pixels
	private static final int PROFILE_PIC_SIZE = 400;


    private static final int REQUEST_CODE_FORM_LOGIN = 90;
	private static final int REQUEST_CODE_GOOGLE_LOGIN = 91;
	private static final int REQUEST_CODE_FACEBOOK_LOGIN = 92;
	private static final int REQUEST_CODE_TWITTER_LOGIN = 93;

	private boolean OnStartAlreadyConnected = false;
	
	//TWITTER
	// Constants
	/**
	 * Register your here app https://dev.twitter.com/apps/new and get your
	 * consumer key and secret
	 * */
	static String TWITTER_CONSUMER_KEY = "8IQfD73lhKsnhvNx1rk95hnNM";
	static String TWITTER_CONSUMER_SECRET = "EcCyUHMi0CTh70NijOfW2tSUsgKpmbKYGVAKUqCauadk9C15V3"; // place your consumer secret here

	// Preference Constants
	static String PREFERENCE_NAME = "twitter_oauth";
	static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
	static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";

	static final String TWITTER_CALLBACK_URL = "oauth://org.acacha.ebre_escool.ebre_escool_app";

	// Twitter oauth urls
	static final String URL_TWITTER_AUTH = "auth_url";
	static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
	static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
	
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

    public final static String PARAM_USER_PASS = "USER_PASS";

    private AccountManager mAccountManager;
    private String mAuthTokenType;

    //////// BEGIN
    // Taken from AccountAuthenticatorActivity. We cannot extends this Activity because we are using Fragments!
    // https://github.com/android/platform_frameworks_base/blob/master/core/java/android/accounts/AccountAuthenticatorActivity.java

    /**
     * Set the result that is to be sent as the result of the request that caused this
     * Activity to be launched. If result is null or this method is never called then
     * the request will be canceled.
     * @param result this is returned as the result of the AbstractAccountAuthenticator request
     */
    public final void setAccountAuthenticatorResult(Bundle result) {
        mResultBundle = result;
    }
    /**
     * Sends the result or a Constants.ERROR_CODE_CANCELED error if a result isn't present.
     */
    public void finish() {
        if (mAccountAuthenticatorResponse != null) {
            // send the result bundle back if set, otherwise send an error.
            if (mResultBundle != null) {
                mAccountAuthenticatorResponse.onResult(mResultBundle);
            } else {
                mAccountAuthenticatorResponse.onError(AccountManager.ERROR_CODE_CANCELED,
                        "canceled");
            }
            mAccountAuthenticatorResponse = null;
        }
        super.finish();
    }
    //////// END

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG,"onCreate!");
        super.onCreate(savedInstanceState);

        // Taken from AccountAuthenticatorActivity. We cannot extends this Activity because we are using Fragments!
        // https://github.com/android/platform_frameworks_base/blob/master/core/java/android/accounts/AccountAuthenticatorActivity.java
        mAccountAuthenticatorResponse =
                getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE);

        if (mAccountAuthenticatorResponse != null) {
            mAccountAuthenticatorResponse.onRequestContinued();
        }
        ////// End Taken from AccountAuthenticatorActivity

        mAccountManager = AccountManager.get(getBaseContext());

        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.login_activitycontainer, new InitialSettingsWizard()).commit();
		}

        ActionBar ab = getActionBar();
        ab.setTitle(getString(R.string.initial_settings_action_bar_title));
        ab.setSubtitle(getString(R.string.initial_settings_action_bar_subtitle));

        Class next_activity = null;
        SharedPreferences settings = getSharedPreferences(AndroidSkeletonUtils.PREFS_NAME, 0);

        int login_type = 0;
        //User already logged and remember checkbox selected -> Bypass login page
        if (settings.getBoolean(AndroidSkeletonUtils.REMEMBER_LOGIN_PREFERENCE, false)) {
            next_activity = MainActivity.class;
            Log.d(TAG, "User selected to remember login. Skipping login page...");

            //Getting which type of login is done
            //REQUEST_CODE_TWITTER_LOGIN | REQUEST_CODE_FACEBOOK_LOGIN | REQUEST_CODE_GOOGLE_LOGIN
            login_type = settings.getInt(AndroidSkeletonUtils.LOGIN_TYPE_PREFERENCE, 0);
        }

        if (next_activity!=null) {
            Intent i = new Intent(InitialSettingsActivity.this, MainActivity.class);
            startActivityForResult(i, login_type);
        }

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
 			alert.showAlertDialog(InitialSettingsActivity.this, "Twitter oAuth tokens", "Please set your twitter oauth tokens first!", false);
 			// stop executing code by return
 			return;
 		}
 		
 		//TODO: please us threads correctly!
 		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy =
			new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
 		
 		// Check if Internet present
 		cd = new ConnectionDetector(getApplicationContext());
		if (!cd.isConnectingToInternet()) {
			// Internet Connection is not present
			alert.showAlertDialog(InitialSettingsActivity.this, "Internet Connection Error",
					"Please connect to working Internet connection", false);
			// stop executing code by return
			return;
		}
		
		// Shared Preferences
		mSharedPreferences = getApplicationContext().getSharedPreferences(
				"AndroidSocialTemplate", 0);
		
		/** This if conditions is tested once is
		 * redirected from twitter page. Parse the uri to get oAuth
		 * Verifier
		 * */
		if (!isTwitterLoggedInAlready()) {
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

                    settings.edit().putInt( AndroidSkeletonUtils.LOGIN_TYPE_PREFERENCE,
                            REQUEST_CODE_TWITTER_LOGIN).commit();

					Intent i = new Intent(InitialSettingsActivity.this, MainActivity.class);
		    		startActivityForResult(i, REQUEST_CODE_TWITTER_LOGIN);
				} catch (Exception e) {
					// Check log for login errors
					Log.e("Twitter Login Error", "> " + e.getMessage());
				}
			}
		}
	}

    private void finishLogin(Intent intent) {
        Log.d("Ebreescool", TAG + "> finishLogin");

        String accountName = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        String accountPassword = intent.getStringExtra(PARAM_USER_PASS);
        final Account account = new Account(accountName, intent.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE));

        if (getIntent().getBooleanExtra(ARG_IS_ADDING_NEW_ACCOUNT, false)) {
            Log.d("Ebreescool", TAG + "> finishLogin > addAccountExplicitly");
            String authtoken = intent.getStringExtra(AccountManager.KEY_AUTHTOKEN);
            String authtokenType = mAuthTokenType;

            // Creating the account on the device and setting the auth token we got
            // (Not setting the auth token will cause another call to the server to authenticate the user)
            //TODO Change null with a Bundle with extra data if necessary or use setUserData()
            mAccountManager.addAccountExplicitly(account, accountPassword, null);
            mAccountManager.setAuthToken(account, authtokenType, authtoken);
        } else {
            /*
            Existing account with an invalidated auth-token – in this case,
            we already have a record on the AccountManager.
            The new auth-token will replace the old one without any action by you, but if the user
            had changed his password for that, you need to update the AccountManager with the new
            password too.
             */
            Log.d("udinic", TAG + "> finishLogin > setPassword");
            mAccountManager.setPassword(account, accountPassword);
        }

        setAccountAuthenticatorResult(intent.getExtras());
        setResult(RESULT_OK, intent);
        finish();
    }
	
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
                SharedPreferences settings =
                        getSharedPreferences(AndroidSkeletonUtils.PREFS_NAME, 0);
                settings.edit().putInt( AndroidSkeletonUtils.LOGIN_TYPE_PREFERENCE,
                        REQUEST_CODE_FACEBOOK_LOGIN).commit();
            	Intent i = new Intent(InitialSettingsActivity.this, MainActivity.class);
        		startActivityForResult(i, REQUEST_CODE_FACEBOOK_LOGIN);
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
	
	@Override
    protected void onResumeFragments() {
		Log.d(TAG,"onResumeFragments!");
        super.onResumeFragments();
        Session session = Session.getActiveSession();

        if (session != null && session.isOpened()) {
            // if the session is already open, try to show the selection fragment
            
        	//Login ok
        	Log.d(TAG,"Login to facebook Ok!");

            SharedPreferences settings =
                    getSharedPreferences(AndroidSkeletonUtils.PREFS_NAME, 0);
            settings.edit().putInt( AndroidSkeletonUtils.LOGIN_TYPE_PREFERENCE,
                    REQUEST_CODE_FACEBOOK_LOGIN).commit();
        	Intent i = new Intent(InitialSettingsActivity.this, MainActivity.class);
    		startActivityForResult(i, REQUEST_CODE_FACEBOOK_LOGIN);
        	
            //userSkippedLogin = false;
        } /*else if (userSkippedLogin) {
            showFragment(SELECTION, false);
        }*/ else {
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
	
	@Override
	protected void onActivityResult(int requestCode, int responseCode,
			Intent intent) {
		Log.d(TAG, "onActivityResult. RequestCode: " + requestCode + " ResponseCode:" + responseCode + "!");
		super.onActivityResult(requestCode, responseCode, intent);
		
		//Facebook:
		uiHelper.onActivityResult(requestCode, responseCode, intent);
		
		switch (requestCode) {
		case RC_SIGN_IN_GOOGLE:
			if (responseCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
			break;

		case REQUEST_CODE_GOOGLE_LOGIN:
			Log.d(TAG, "LOGOUT from google!");
			//LOGOUT from google
			if (responseCode == RESULT_OK) {
				Log.d(TAG, "LOGOUT from google withe response code RESULT_OK. Signing Out from Gplus!...");
				//Check if revoke exists!
				Bundle extras = intent.getExtras();
				boolean revoke = false;
				if (extras != null) {
					revoke = extras.getBoolean(AndroidSkeletonUtils.REVOKE_KEY);
				}
				
				if (revoke == true) {
					Log.d(TAG, "LOGOUT and also revoke!...");
					revokeGplusAccess();
				} else {
					Log.d(TAG, "Only LOGOUT (no revoke)...");
					signOutFromGplus();
				}
			}
			break;
		case REQUEST_CODE_FACEBOOK_LOGIN:
			//LOGOUT from facebook
			Log.d(TAG, "LOGOUT from facebook!");
			if (responseCode == RESULT_OK) {
				Log.d(TAG, "LOGOUT from facebook with response code RESULT_OK. Signing Out from facebook!...");
				//Check if revoke exists!
				Bundle extras = intent.getExtras();
				boolean revoke = false;
				if (extras != null) {
					revoke = extras.getBoolean(AndroidSkeletonUtils.REVOKE_KEY);
				}
				
				Session session = Session.getActiveSession();
				
				if (revoke == true) {
					Log.d(TAG, "LOGOUT and also revoke!...");
					Log.d(TAG, "First revoke...");
					progressDialog = ProgressDialog.show(
				            InitialSettingsActivity.this, "", "Revocant permisos...", true);
					new Request(
							   session,
							    "/me/permissions",
							    null,
							    HttpMethod.DELETE,
							    new Request.Callback() {
							        public void onCompleted(Response response) {
							            /* handle the result */
							        	Log.d(TAG, "Revoked. Response:" + response);
							        	progressDialog.dismiss(); 
							        }
							    }
							).executeAsync();
					session.closeAndClearTokenInformation();
				} else {
					Log.d(TAG, "Only LOGOUT (no revoke)...");					
					session.closeAndClearTokenInformation();
				}
			}
			
		case REQUEST_CODE_TWITTER_LOGIN:
			//LOGOUT from facebook
			Log.d(TAG, "LOGOUT from Twitter!");
			if (responseCode == RESULT_OK) {
				Log.d(TAG, "LOGOUT from twitter with response code RESULT_OK. Signing Out from twitter!...");
				//Check if revoke exists!
				Bundle extras = intent.getExtras();
				boolean revoke = false;
				if (extras != null) {
					revoke = extras.getBoolean(AndroidSkeletonUtils.REVOKE_KEY);
				}
				if (revoke == true) {
					Log.d(TAG, "LOGOUT and also revoke!...");
					logoutAndRevokeFromTwitter();
				} else {
					Log.d(TAG, "Only LOGOUT (no revoke)...");
					logoutFromTwitter();
				}
			}
			break;
		
		default:
			break;
		}
		
		
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
	 * Revoking access from google
	 * */
	private void revokeGplusAccess() {
		if (mGoogleApiClient.isConnected()) {
			Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
			Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
					.setResultCallback(new ResultCallback<Status>() {
						@Override
						public void onResult(Status arg0) {
							Log.e(TAG, "User access revoked!");
							mGoogleApiClient.disconnect();
							mGoogleApiClient.connect();
						}

					});
		}
	}
	
	/**
	 * Sign-in into google
	 * */
	private void signInWithGplus() {
		Log.d(TAG, "signInWithGplus!");
		if (!mGoogleApiClient.isConnecting()) {
			Log.d(TAG, "Error signing in!");
			mSignInClicked = true;
			resolveSignInError();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_google_sign_in:
			// Signin with Google Plus button clicked
			signInWithGplus();
			break;
		/* NOTE: Facebook have a custom button!
		case R.id.btn_facebook_sign_in:
			// Signin with Twitter button clicked
			loginToFacebook();
			break;*/	
		case R.id.btn_twitter_sign_in:
			// Signin with Twitter button clicked
			loginToTwitter();
			break;
        case R.id.btnPersonalLogin:
                // Signin with Login Form
                loginToEbreEscool(v);
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
		Log.d(TAG, "User is connected!");
		
		// Get user's information
		getProfileInformation();

        SharedPreferences settings =
                getSharedPreferences(AndroidSkeletonUtils.PREFS_NAME, 0);
        settings.edit().putInt( AndroidSkeletonUtils.LOGIN_TYPE_PREFERENCE,
                REQUEST_CODE_GOOGLE_LOGIN).commit();
		Intent i = new Intent(InitialSettingsActivity.this, MainActivity.class);
		startActivityForResult(i, REQUEST_CODE_GOOGLE_LOGIN);
		
		// Update the UI after signin
		//updateUI(true);
		
	}
	
	/**
	 * Fetching user's information name, email, profile pic
	 * */
	private void getProfileInformation() {
		try {
			if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
				Person currentPerson = Plus.PeopleApi
						.getCurrentPerson(mGoogleApiClient);
				String personName = currentPerson.getDisplayName();
				String personPhotoUrl = currentPerson.getImage().getUrl();
				String personGooglePlusProfile = currentPerson.getUrl();
				String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

				Log.d(TAG, "Name: " + personName + ", plusProfile: "
						+ personGooglePlusProfile + ", email: " + email
						+ ", Image: " + personPhotoUrl);

				//TODO: Save to shared preferences?
			

				// by default the profile url gives 50x50 px image only
				// we can replace the value with whatever dimension we want by
				// replacing sz=X
				personPhotoUrl = personPhotoUrl.substring(0,
						personPhotoUrl.length() - 2)
						+ PROFILE_PIC_SIZE;

				//new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

			} else {
				Toast.makeText(getApplicationContext(),
						"Person information is null", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		// TODO Auto-generated method stub
		// Nothing to do!?
	}
	
	/**
	 * Check user already logged in your application using twitter Login flag is
	 * fetched from Shared Preferences
	 * */
	private boolean isTwitterLoggedInAlready() {
		// return twitter login status from Shared Preferences
		return mSharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN, false);
	}

    /**
     * Login to ebre_escool
     *
     */
    private void loginToEbreEscool(View v) {
        //TODO: Connect to ebre_escool api log login

        String username = ((EditText) findViewById(R.id.username)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String md5password = computeMD5Hash(password);

        //Obtain and save to sharedPreferences remember_login_checkbox
        Boolean remember_login_checkbox = (
                (CheckBox)findViewById(R.id.chkRememberLogin)).isChecked();

        SharedPreferences settings = getSharedPreferences(AndroidSkeletonUtils.PREFS_NAME, 0);
        settings.edit().putBoolean(
                AndroidSkeletonUtils.REMEMBER_LOGIN_PREFERENCE, remember_login_checkbox).commit();
        settings.edit().putInt(
                AndroidSkeletonUtils.LOGIN_TYPE_PREFERENCE, REQUEST_CODE_FORM_LOGIN ).commit();

        //DEBUG
        Log.d(TAG,"username: " + username);
        //Log.d(TAG,"password: " + password);
        //Log.d(TAG,"MD5 password: " + md5password);
        Log.d(TAG,"Remember_login_checkbox: " + remember_login_checkbox);

        //HTTP POST TO ebreescoollogin
        OkHttpHelper http_helper = new OkHttpHelper();

        LoginAsyncTask login_task = new LoginAsyncTask(this);
        login_task.execute(username,md5password);

        //TODO: Obtain data as result of asynctask. THIS IS ONLY FOR TEST:
        Bundle data = new Bundle();
        String userName = "acacha";
        String accountType = EbreEscoolAccount.AUTHTOKEN_TYPE_FULL_ACCESS;
        String authtoken = "76fa4cd67623b9bd8b4e3a5161421641";
        String userPass = "MYPASSWORDHERE_NONO";
        data.putString(AccountManager.KEY_ACCOUNT_NAME, userName);
        data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
        data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
        data.putString(PARAM_USER_PASS, userPass);

        final Intent res = new Intent();
        res.putExtras(data);

        finishLogin(res);

    }

    public String computeMD5Hash(String password){

        StringBuffer MD5Hash = new StringBuffer();

        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
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
	 * Function to login twitter
	 * */
	private void loginToTwitter() {
		Log.d(TAG,"loginToTwitter!");
		// Check if already logged in
		if (!isTwitterLoggedInAlready()) {
			Log.d(TAG,"no already logged!");
			//Check is we already have tokens
			boolean already_have_tokens = already_have_twitter_tokens();
			if (already_have_tokens == true) {
				Log.d(TAG,"Already have twitter tokens -> Log in!");

                SharedPreferences settings =
                        getSharedPreferences(AndroidSkeletonUtils.PREFS_NAME, 0);
                settings.edit().putInt( AndroidSkeletonUtils.LOGIN_TYPE_PREFERENCE,
                        REQUEST_CODE_TWITTER_LOGIN).commit();
				Intent i = new Intent(InitialSettingsActivity.this, MainActivity.class);
	    		startActivityForResult(i, REQUEST_CODE_TWITTER_LOGIN);
			} else {
				Log.d(TAG,"Starting twitter external auth!");
				
				ConfigurationBuilder builder = new ConfigurationBuilder();
				builder.setOAuthConsumerKey(TWITTER_CONSUMER_KEY);
				builder.setOAuthConsumerSecret(TWITTER_CONSUMER_SECRET);
				Configuration configuration = builder.build();
				
				TwitterFactory factory = new TwitterFactory(configuration);
				twitter = factory.getInstance();

				try {
					requestToken = twitter
							.getOAuthRequestToken(TWITTER_CALLBACK_URL);
					this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
							.parse(requestToken.getAuthenticationURL())));
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}		
		} else {
			// user already logged into twitter
			Log.d(TAG,"Already Logged into twitter");

            SharedPreferences settings =
                    getSharedPreferences(AndroidSkeletonUtils.PREFS_NAME, 0);
            settings.edit().putInt( AndroidSkeletonUtils.LOGIN_TYPE_PREFERENCE,
                    REQUEST_CODE_TWITTER_LOGIN).commit();
			Intent i = new Intent(InitialSettingsActivity.this, MainActivity.class);
    		startActivityForResult(i, REQUEST_CODE_TWITTER_LOGIN);
		}
	}
	
	private boolean already_have_twitter_tokens() {
		if (mSharedPreferences.contains(PREF_KEY_OAUTH_TOKEN) && mSharedPreferences.contains(PREF_KEY_OAUTH_SECRET)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Function to logout from twitter
	 * It will just clear the application shared preferences
	 * */
	private void logoutFromTwitter() {
		// Clear the shared preferences
		Editor e = mSharedPreferences.edit();
		e.remove(PREF_KEY_TWITTER_LOGIN);
		e.commit();
	}

	/**
	 * Function to logout from twitter
	 * It will just clear the application shared preferences
	 * */
	private void logoutAndRevokeFromTwitter() {
		// Clear the shared preferences
		Editor e = mSharedPreferences.edit();
		e.remove(PREF_KEY_OAUTH_TOKEN);
		e.remove(PREF_KEY_OAUTH_SECRET);
		e.remove(PREF_KEY_TWITTER_LOGIN);
		e.commit();
	}

    private class LoginResultBundle {

        public static final int ERROR_TYPE_CONNECT_EXCEPTION = -1;

        public static final int ERROR_TYPE_IO_EXCEPTION = -2;

        private String login_api_url = OkHttpHelper.LOGIN_API_URL;

        private String realm = OkHttpHelper.DEFAULT_REALM;

        private String username = "";

        private boolean result_ok;

        private int error_type = 0;

        private String error_message;

        public int getError_type() {
            return error_type;
        }

        public void setError_type(int error_type) {
            this.error_type = error_type;
        }

        public String getLogin_api_url() {
            return login_api_url;
        }

        public void setLogin_api_url(String login_api_url) {
            this.login_api_url = login_api_url;
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
        public String getRealm() {
            return realm;
        }

        public void setRealm(String realm) {
            this.realm = realm;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }


    }

    private class LoginAsyncTask extends AsyncTask<String, Void, LoginResultBundle> {

        private ProgressDialog dialog;

        public LoginAsyncTask(InitialSettingsActivity activity) {
            dialog = new ProgressDialog(activity);
        }

        /** progress dialog to show user that the backup is processing. */
        /** application context. */
        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Validating login");
            this.dialog.show();
        }

        @Override
        protected LoginResultBundle doInBackground(final String... args) {

            //HTTP POST TO ebreescoollogin
            OkHttpHelper http_helper = new OkHttpHelper();

            String login_api_url = OkHttpHelper.LOGIN_API_URL;
            String realm = OkHttpHelper.DEFAULT_REALM;

            String username = args[0];
            String password = args[1];

            String response = "";
            String error_message ="";
            LoginResultBundle result_bundle = new LoginResultBundle();
            result_bundle.setResult_ok(true);
            result_bundle.setLogin_api_url(login_api_url);
            result_bundle.setUsername(username);
            result_bundle.setRealm(realm);

            try {
                response = http_helper.login_ebreescool(login_api_url,username,password,realm);
            } catch (ConnectException ce) {
                error_message = ce.getLocalizedMessage();
                result_bundle.setResult_ok(false);
                result_bundle.setError_type(LoginResultBundle.ERROR_TYPE_CONNECT_EXCEPTION);
                ce.printStackTrace();
            } catch (IOException e) {
                error_message = e.getLocalizedMessage();
                result_bundle.setResult_ok(false);
                result_bundle.setError_type(LoginResultBundle.ERROR_TYPE_IO_EXCEPTION);
                e.printStackTrace();
            }

            result_bundle.setError_message(error_message);


            if (error_message!=""){
                Log.d(TAG,"Error message: " + error_message);
            }

            Log.d(TAG,"Response: " + response);

            result_bundle.setError_message(error_message);
            return result_bundle;

        }

        @Override
        protected void onPostExecute(final LoginResultBundle result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if (result.isResult_ok()) {

            } else {
                int error_type = result.getError_type();
                int duration = Toast.LENGTH_LONG;

                switch (error_type) {
                    case LoginResultBundle.ERROR_TYPE_CONNECT_EXCEPTION:

                        break;
                    case LoginResultBundle.ERROR_TYPE_IO_EXCEPTION:
                        break;
                    default:
                        break;
                }

                Toast.makeText(getApplicationContext(),
                        result.getError_message(), duration).show();

            }



        }

    }
}



