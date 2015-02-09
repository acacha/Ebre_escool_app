package org.acacha.ebre_escool.ebre_escool_app.managment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import org.acacha.ebre_escool.ebre_escool_app.MainActivity;
import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.helpers.FragmentBase;
import org.acacha.ebre_escool.ebre_escool_app.helpers.FragmentPerson;

public class ManagmentActivity extends ActionBarActivity
        implements ManagmentNavigationDrawerFragment.NavigationDrawerCallbacks,
        FragmentBase.OnFragmentInteractionListener, FragmentPerson.OnFragmentInteractionListener {

    private final static String TAG = "ManagmentActivity";

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private ManagmentNavigationDrawerFragment mManagmentNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managment);

        mManagmentNavigationDrawerFragment = (ManagmentNavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mManagmentNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
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
                fragment = (Fragment) new FragmentBase();
                break;
            case 1:
                //managment_title_section2_enrollments
                fragment = (Fragment) new FragmentBase();
                break;
            case 2:
                //managment_title_section3_persons
                //fragment = (Fragment) new FragmentBase();
                fragment = (Fragment) new FragmentPerson();
                break;
            case 3:
                //managment_title_section4_classroom_groups
                fragment = (Fragment) new FragmentBase();
                break;
            case 4:
                //managment_title_section5_teachers
                fragment = (Fragment) new FragmentBase();
                break;
            case 5:
                //managment_title_section6_users
                fragment = (Fragment) new FragmentBase();
                break;
            case 6:
                //managment_title_section7_incidents
                fragment = (Fragment) new FragmentBase();
                break;
            case 7:
                //managment_title_section8_study_submodules
                fragment = (Fragment) new FragmentBase();
                break;
            case 8:
                //managment_title_section9_lessons
                fragment = (Fragment) new FragmentBase();
                break;
            case 9:
                //managment_title_section10_employees
                fragment = (Fragment) new FragmentBase();
                break;
            case 10:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        if (fragment != null)
        {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.addToBackStack(null);
            ft.commit();
            restoreActionBar();
        }
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.managment_title_section1_profe);

                break;
            case 2:
                mTitle = getString(R.string.managment_title_section2_enrollments);
                break;
            case 3:
                mTitle = getString(R.string.managment_title_section3_persons);
                break;
            case 4:
                mTitle = getString(R.string.managment_title_section4_classroom_groups);
                break;
            case 5:
                mTitle = getString(R.string.managment_title_section5_teachers);
                break;
            case 6:
                mTitle = getString(R.string.managment_title_section6_users);
                break;
            case 7:
                mTitle = getString(R.string.managment_title_section7_incidents);
                break;
            case 8:
                mTitle = getString(R.string.managment_title_section8_study_submodules);
                break;
            case 9:
                mTitle = getString(R.string.managment_title_section9_lessons);
                break;
            case 10:
                mTitle = getString(R.string.managment_title_section10_employees);
                break;
            case 11:
                mTitle = getString(R.string.managment_title_section11_comeback);
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mManagmentNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.managment, menu);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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
         * Returns a new instance of this fragment for the given section
         * number.
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
            View rootView = inflater.inflate(R.layout.fragment_managment, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((ManagmentActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // TODO Auto-generated method stub

    }

}
