package org.acacha.ebre_escool.ebre_escool_app;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import org.acacha.ebre_escool.ebre_escool_app.helpers.AndroidSkeletonUtils;
import org.acacha.ebre_escool.ebre_escool_app.initial_settings.InitialSettingsActivity;
import org.acacha.ebre_escool.ebre_escool_app.managment.ManagmentActivity;
import org.acacha.ebre_escool.ebre_escool_app.settings.SettingsActivity;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks,
        FragmentType0.OnFragmentInteractionListener,
        FragmentType1.OnFragmentInteractionListener {

	private static final String TAG = "AndroidSocialLoginTemplate MainActivity";

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                //LOGOUT AND REVOKE: Not really necessary!
                mTitle = getString(R.string.title_section_managment);
                break;
            case 4:
                //LOGOUT: Not really necessary!
                mTitle = getString(R.string.title_section3);
                break;
            case 5:
                //LOGOUT: Not really necessary!
                mTitle = getString(R.string.title_section4);
                break;
        }
    }


	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
		
		Fragment fragment = null;
		switch (position)
		  {
		    case 0:
		       fragment = (Fragment) new FragmentType0();
		       break;
		    case 1:
		       fragment = (Fragment) new FragmentType1();
		       break;
            case 2:
              Log.d(TAG, "Click on managment section");
                //Start Activity drawer type for managment
                startActivity(new Intent(this, ManagmentActivity.class));
              break;
		    case 3:
		       //LOGOUT & REVOKE!
		       logout(true);
		       break;
		    case 4:
		       //ONLY LOGOUT
			   logout();
			   break;   
		    default:
		       Log.w(TAG, "Reached Default in onNavigationDrawerItemSelected!");
		       break;
		 }
		if (fragment != null)
		 {
		    FragmentTransaction ft = fragmentManager.beginTransaction();
		    ft.replace(R.id.container, fragment);
		    ft.addToBackStack(null);
		    ft.commit();
		    //mTitle = "Titol a mostrar per aquest fragment a l'Action Bar!";
		    //mTitle = getString(((GetActionBarTitle) fragment).getActionBarTitleId());
		    restoreActionBar();
		 }

	}
	
	private void logout(boolean revoke) {
		Log.d(TAG, "Logout with one parameter");
		Intent returnIntent = new Intent();
		if(revoke == true) {
			returnIntent.putExtra(AndroidSkeletonUtils.REVOKE_KEY, true);
		}
		setResult(RESULT_OK,returnIntent);
		finish();
	}

	public void logout(){
		Log.d(TAG, "Logout");
		logout(false);
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
		}
        if (id == R.id.initial_settings_wizard) {
            startActivity(new Intent(this, InitialSettingsActivity.class));
        }
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

	@Override
	public void onFragmentInteraction(Uri uri) {
		// TODO Auto-generated method stub
		
	}

}
