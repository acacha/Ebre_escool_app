package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.apis.EbreEscoolAPI;
import org.acacha.ebre_escool.ebre_escool_app.helpers.OnFragmentInteractionListener;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.api.UsersApi;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.api.UsersApiService;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.pojos.Resultat;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.pojos.Users;
import org.acacha.ebre_escool.ebre_escool_app.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardArrayMultiChoiceAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.base.CardViewWrapper;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.FragmentUsers.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.users.FragmentUsers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUsers extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
//
    private OnFragmentInteractionListener mListener;

    //USERS//
    private CardListView list;
    private RestAdapter adapter;
    //Extraure users
    private List<Users> usersList;
    private final String TAG = "tag";
    MyCardArrayMultiChoiceAdapter mMyCardArrayMultiChoiceAdapter;
    CustomStudySubmodulesCard card_on_list;
    //This lets vibrate on click button actions
    Vibrator vibe;
    ActionMode mActionMode;



//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment FragmentUsers.
//     */
//    // TODO: Rename and change types and number of parameters
    public static FragmentUsers newInstance(String param1, String param2) {
        FragmentUsers fragment = new FragmentUsers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentUsers() {
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
        View view = inflater.inflate(R.layout.fragment_users,container,false);
        setHasOptionsMenu(true);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.users_put_button, menu);
        super.onCreateOptionsMenu(menu,inflater);
        //Get the new menu item
        MenuItem putUsers= (MenuItem)menu.findItem(R.id.putUsers);
        //Set on click listener
        putUsers.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //On click go to create a new user.
                Log.d(TAG,"PUT ITEM");
                vibe.vibrate(60); // 60 is time in ms
                int put = 9999;
                onCardClick(put, UsersApi.PUT);
                return false;
            }
        });
    }

    @Override
    public  void onStart(){
        super.onStart();
        vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;

        //set rest adapter
        adapter = new RestAdapter.Builder()
                .setEndpoint(UsersApi.ENDPOINT).build();
        getAllUsers();
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


    //Execute retrofit getallusers method
    private void getAllUsers(){

        Log.d(TAG, "En el método requestData()");

        //now get the interface declared methods using this adapter
        UsersApiService api = adapter.create(UsersApiService.class);
        api.getUsers(new Callback<List<Users>>() {
            @Override
            public void success(List<Users> Users, Response response) {
                Log.d(TAG,"En el success");
                usersList=Users;
                updateDisplay();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }




    //Fill listview
    protected void updateDisplay() {
        if (usersList != null) {
            Gson gson = new Gson();
            String string = gson.toJson(usersList);
            Users[] arrayUsers = gson.fromJson(string, Users[].class);
            //list.setAdapter(new ArrayAdapter<Users>(getActivity(), android.R.layout.simple_list_item_1,
            //      android.R.id.text1, arrayUsers));
            //Get the cardlistview
            list = (CardListView) getActivity().findViewById(R.id.usersList);
            //create cards

            ArrayList<Card> cards = new ArrayList<Card>();

            for (int i = 0; i < arrayUsers.length; i++) {
                Log.d(TAG, ""+arrayUsers[i].getId());
                // Create a Card
                card_on_list = new CustomUsersCard(getActivity());
                //Set card id with Users id
                card_on_list.setId(arrayUsers[i].getId());
                // Create a CardHeader and add Header to card_on_list
                CardHeader header = new CardHeader(getActivity());
                header.setTitle("Study Submodules "+arrayUsers[i].getId());
                // header.setButtonExpandVisible(true);
                //Add a popup menu. This method sets OverFlow button to visibile
                header.setPopupMenu(R.menu.users_card_overflow_menu, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                    @Override
                    public void onMenuItemClick(BaseCard baseCard, MenuItem menuItem) {
                        vibe.vibrate(60); // 60 is time in ms
                        switch(menuItem.getItemId()){
                            case(R.id.oneAction):
                                Log.d(TAG,"CARD ID PUT"+baseCard.getId());
                                //Future actions here
                                Toast.makeText(getActivity(),"Future action 1",Toast.LENGTH_SHORT).show();
                                break;
                            case(R.id.otherActions):
                                //Future actions here
                                Toast.makeText(getActivity(),"Future action 2",Toast.LENGTH_SHORT).show();
                                break;
                            case(R.id.deleteUsers):
                                //Call the method to delete
                                deleteUsers(Integer.valueOf(baseCard.getId()));
                                break;
                        }
                    }
                });
                card_on_list.addCardHeader(header);
                //set swipe and action it will mark for deletion
                card_on_list.setSwipeable(true);
                card_on_list.setOnSwipeListener(new Card.OnSwipeListener() {
                    @Override
                    public void onSwipe(Card card) {
                        //we mark for deletion
                        vibe.vibrate(60); // 60 is time in ms
                        markForDeletion(card.getId(),"y");

                    }
                });
                //swipe undo action
                card_on_list.setOnUndoSwipeListListener(new Card.OnUndoSwipeListListener() {
                    @Override
                    public void onUndoSwipe(Card card) {
                        //we undo the mark for deletion
                        vibe.vibrate(60); // 60 is time in ms
                        markForDeletion(card.getId(),"n");

                    }
                });


                card_on_list.setTitle("Name\n"+arrayUsers[i].getName());
                //card_on_list.setClickable(true);
                card_on_list.setShadow(true);
                CustomExpandCard expand= new CustomExpandCard(getActivity(),arrayUsers[i]);
                card_on_list.addCardExpand(expand);
                //Set expand on click
                ViewToClickToExpand viewToClickToExpand =
                        ViewToClickToExpand.builder()
                                .highlightView(false)
                                .setupCardElement(ViewToClickToExpand.CardElementUI.HEADER);
                card_on_list.setViewToClickToExpand(viewToClickToExpand);
                //Set on long click listener
                card_on_list.setOnLongClickListener(new Card.OnLongCardClickListener() {
                    @Override
                    public boolean onLongClick(Card card, View view) {
                        vibe.vibrate(60); // 60 is time in ms
                        return mMyCardArrayMultiChoiceAdapter.startActionMode(getActivity());

                    }
                });
                //Now we add the thumbnail
                //Create thumbnail
                CardThumbnail thumb = new CardThumbnail(getActivity());
                //Set URL resource
                thumb.setUrlResource(UsersApi.IMAGE);
                //Add thumbnail to a card
                card_on_list.addCardThumbnail(thumb);

                //add card to list
                cards.add(card_on_list);
            }

            mMyCardArrayMultiChoiceAdapter = new MyCardArrayMultiChoiceAdapter(getActivity(), cards );
            //enable swipe undo action
            mMyCardArrayMultiChoiceAdapter.setEnableUndo(true);

            if (list != null) {
                list.setAdapter(mMyCardArrayMultiChoiceAdapter);
                list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);//CHOICE_MODE_MULTIPLE_MODAL
            }
        }
    }



    public class CustomUsersCard extends Card implements View.OnClickListener {

        private String title;

        //Constructor
        public CustomUsersCard(Context context) {
            super(context, R.layout.study_submodules_card_inside_buttons);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {
            //Get controls and set button listeners
            TextView tx = (TextView) view.findViewById(R.id.titleUsers);
            Button btnDetail = (Button) view.findViewById(R.id.btnDetail);
            Button btnEdit = (Button) view.findViewById(R.id.btnEdit);
            tx.setText(title);
            if (btnDetail != null) {
                btnDetail.setOnClickListener(this);

            }
            if (btnEdit != null) {
                btnEdit.setOnClickListener(this);

            }
        }
        @Override
        public void onClick(View v) {
            //Vibrate on click
            vibe.vibrate(60); // 60 is time in ms
            switch(v.getId()){
                case R.id.btnDetail:
                    onCardClick(Integer.valueOf(getId()), UsersApi.DETAIL);
                    break;
                case  R.id.btnEdit:
                    onCardClick(Integer.valueOf(getId()), UsersApi.EDIT);
                    break;
            }
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public void setTitle(String title) {
            this.title = title;
        }
    }



//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p/>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }


    public class CustomExpandCard extends CardExpand {
        //We send an object Users to fill text views
        private final Users users;
        //Use your resource ID for your inner layout
        public CustomExpandCard(Context context,Users users) {
            super(context, R.layout.study_submodules_card_expand);
            this.users = users;
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            if (view == null) return;

            //Retrieve TextView elements

            TextView id = (TextView) view.findViewById(R.id.contentId);
            TextView username = (TextView) view.findViewById(R.id.contentUsername);
            TextView createdOn = (TextView) view.findViewById(R.id.contentCreatedOn);
            TextView lastLogin = (TextView) view.findViewById(R.id.contentLastLogin);



            //Set value in text views
            if (id != null) {
                id.setText("ID: "+users.getId());
            }
            if (username != null) {
                username.setText("Username: "+users.getUsername());
            }
            if (createdOn != null) {
                createdOn.setText("Created On :"+users.getCreatedOn());
            }
            if (lastLogin != null) {
                lastLogin.setText("Last login :"+users.getLastLogin());
            }


            //int color = Color.argb(255,255, 255, 0);
            //parent.setBackgroundColor(color);
            parent.setBackgroundColor(mContext.getResources().getColor(R.color.greendark));
        }
    }

    public void onCardClick(int id,String action){
        //Change the fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment usersDetail = new UsersDetails();
        transaction.replace(R.id.container,usersDetail);
        transaction.addToBackStack(null);
        transaction.commit();


        //Pass the id to the fragment detail
        Bundle extras = new Bundle();
        extras.putInt("id",id);
        extras.putString(UsersApi.ACTION,action);
        usersDetail.setArguments(extras);
    }


    //DELETE Users method
    private void deleteUsers(final int id){
        Log.d(TAG,"delete id:"+id);
        final int usersId = id;
        //We use alert yes-cancel dialog to be sure user want to delete users
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("DELETE");
        builder.setMessage("Are you sure to delete " + usersId + "?");
        builder.setIcon(R.drawable.advise);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                vibe.vibrate(60); // 60 is time in ms
                //iF YES CALL TO DELETE METHOD
                RestAdapter adapter = new RestAdapter.Builder()
                        .setEndpoint(UsersApi.ENDPOINT).build();
                UsersApiService api = adapter.create(UsersApiService.class);

                api.deleteUsers(usersId, new Callback<Resultat>() {
                    @Override
                    public void success(Resultat resultat, Response response) {
                        Toast.makeText(getActivity(), "Users" + resultat.getId() + " " + resultat.getMessage(), Toast.LENGTH_LONG).show();
                        //Refresh screen
                        onStart();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getActivity(), "DELETE ERROR! " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                vibe.vibrate(60); // 60 is time in ms
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }


    private void markForDeletion(String id,String action){
        //Object users to send with the parametres
        Users users = new Users();
        users.setId(id);
        users.setMarkedForDeletion(action);
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(UsersApi.ENDPOINT).build();
        UsersApiService api = adapter.create(UsersApiService.class);
        api.markForDeletion(users,new Callback<Resultat>() {
            @Override
            public void success(Resultat resultat, Response response) {
                Toast.makeText(getActivity(),"Users "+resultat.getId()+" "+resultat.getMessage(),Toast.LENGTH_LONG).show();

            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(),"UPDATE ERROR! "+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }


    //ADAPTER//
    public class MyCardArrayMultiChoiceAdapter extends CardArrayMultiChoiceAdapter {

        public MyCardArrayMultiChoiceAdapter(Context context, List<Card> cards) {
            super(context, cards);
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            //It is very important to call the super method
            super.onCreateActionMode(mode, menu);

            mActionMode=mode; // to manage mode in your Fragment/Activity

            //If you would like to inflate your menu
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.carddemo_multichoice, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.menu_share) {
                Toast.makeText(getContext(), "Share;" + formatCheckedCard(), Toast.LENGTH_SHORT).show();
                return true;
            }
            if (item.getItemId() == R.id.menu_discard) {
                vibe.vibrate(60); // 60 is time in ms
                discardSelectedItems(mode);
                return true;
            }
            return false;
        }

        private void discardSelectedItems(ActionMode mode) {
            ArrayList<Card> items = getSelectedCards();
            for (Card item : items) {
                //We call mark for deletion method and we delete the card from array
                markForDeletion(item.getId(),"y");
                remove(item);
            }
            mode.finish();
        }

        private String formatCheckedCard() {

            SparseBooleanArray checked = mCardListView.getCheckedItemPositions();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < checked.size(); i++) {
                if (checked.valueAt(i) == true) {
                    sb.append("\nPosition=" + checked.keyAt(i));
                }
            }
            return sb.toString();
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b, CardViewWrapper cardViewWrapper, Card card) {

            //Do something here if we want when card is selected in multichoice
        }
    }



    
//    final String LOG_TAG = "Users_CAD_CLICK";
//    private static Users[] mUsers;
//    private ListView lastUsers;
//    //settings
//    private SharedPreferences settings;
//    CardArrayAdapter mCardArrayAdapter;
//    private AlertDialog alert = null;
//    private String error_message_validating_school = "";
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
//
//        /*
//        Fer quan es agafin les dades de l'API
//        //Retrieve schools on JSON format
//        //String json_schools_list = settings.getString("schools_list", "");
//        //Log.d(LOG_TAG, "###### json_schools_list: " + json_schools_list);
//        //Gson gson = new Gson();
//        //mStudySubmodules = gson.fromJson(json_schools_list, School[].class);
//        */
//
//        mUsers = new Users[3];
//        Users user1 = new Users();
//        Users user2 = new Users();
//        Users user3 = new Users();
//
//        user1.setUsername("Prova 1");
//        user2.setUsername("Prova 2");
//        user3.setUsername("Prova 3");
//
//        mUsers[0] = user1;
//        mUsers[1] = user2;
//        mUsers[2] = user3;
//
//        ArrayList<Card> cards = new ArrayList<Card>();
//        Log.d(LOG_TAG, "Users 0 " + mUsers[0]);
//
//        for (int i = 0; i < mUsers.length; i++) {
//
//            Card card_on_list = new Card(getActivity());
//
//            CardHeader header = new CardHeader(getActivity());
//            header.setTitle(mUsers[i].getPersonId());
//            card_on_list.addCardHeader(header);
//            card_on_list.setId(mUsers[i].getUsername());
//            card_on_list.setTitle(mUsers[i].getUsername());
//            card_on_list.setClickable(true);
//            card_on_list.setOnClickListener(new Card.OnCardClickListener() {
//                @Override
//                public void onClick(Card card, View view) {
//                    Log.d(LOG_TAG, "Clicable card id: " + card.getId());
//                    int position = mCardArrayAdapter.getPosition(card);
//                    lastUsers.setItemChecked(position, true);
//                }
//            });
//
//            CardThumbnail thumb = new CardThumbnail(getActivity());
//
//            if (mUsers[i].getLogoURL() != "") {
//                thumb.setUrlResource(mUsers[i].getLogoURL());
//            } else {
//                thumb.setUrlResource(EbreEscoolAPI.EBRE_ESCOOL_PUBLIC_IMAGE_NOT_AVAILABLE);
//            }
//            card_on_list.addCardThumbnail(thumb);
//            cards.add(card_on_list);
//        }
//        mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
//        lastUsers = (CardListView) getActivity().findViewById(R.id.schoolsList);
//
//        if (lastUsers != null) {
//            lastUsers.setAdapter(mCardArrayAdapter);
//            lastUsers.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//
//            String current_selected_school = settings.getString(SettingsActivity.SCHOOLS_LIST_KEY, "0");
//            Log.d(LOG_TAG, "Getted current selected school: " + current_selected_school);
//            lastUsers.setItemChecked(Integer.parseInt(current_selected_school),true);
//            String current_value = settings.getString(SettingsActivity.SCHOOLS_LIST_KEY, "0");
//            settings.edit().putString(SettingsActivity.SCHOOLS_LIST_KEY, current_value).apply();
//        }
//    }
//
//        public void showAlertDialog(Context context, String title, String message, Boolean status){
//        alert = new AlertDialog.Builder(context).create();
//        alert.setTitle(title);
//        alert.setMessage(message);
//        alert.setCancelable(false);
//
//        if(status != null){
//            alert.setIcon((status) ? R.drawable.success : R.drawable.fail);
//            alert.setButton(AlertDialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which){
//            }
//        });
//
//            alert.show();
//        }
//    }
////    @Override
////    public View onCreateView(LayoutInflater inflater, ViewGroup container,
////                             Bundle savedInstanceState) {
////        View v = inflater.inflate(R.layout.fragment_users, container, false);
////        settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
////        return v;
////    }

}
