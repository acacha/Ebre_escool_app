package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.helpers.OnFragmentInteractionListener;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher.api.TeacherApi;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher.api.TeacherApiService;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher.pojos.Result;
import org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher.pojos.Teacher;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardListView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TeacherFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TeacherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherFragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ////Teacher fields//////////////////////
    private CardListView list;
    private RestAdapter adapter;
    //To get teachers/teacher
    private List<Teacher> teachersList;
    private String TAG = "tag";
    CardArrayAdapter mCardArrayAdapter;
    CustomTeacherCard card_on_list;
    //This lets vibrate on click button actions
    Vibrator vibe;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherFragment newInstance(String param1, String param2) {
        TeacherFragment fragment = new TeacherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TeacherFragment() {
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
       View view = inflater.inflate(R.layout.fragment_teacher, container, false);



        return view;
    }
    @Override
    public  void onStart(){
        super.onStart();
        vibe = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE) ;
        //set rest adapter
        adapter = new RestAdapter.Builder()
                .setEndpoint(TeacherApi.ENDPOINT).build();
        getAllTeachers();


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
    //Execute retrofit getTeachers method
    private void getAllTeachers(){

        Log.d(TAG, "En el método requestData()");

        //now get the interface declared methods using this adapter
        TeacherApiService api = adapter.create(TeacherApiService.class);
        api.getTeachers(new Callback<List<Teacher>>() {
            @Override
            public void success(List<Teacher> teachers, Response response) {
                Log.d(TAG,"En el success");
                teachersList=teachers;
                updateDisplay();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();

            }
        });

    }


    //Fill listview
    protected void updateDisplay() {
        if (teachersList != null) {
            Gson gson = new Gson();
            String string = gson.toJson(teachersList);
            Teacher[] arrayTeacher = gson.fromJson(string, Teacher[].class);
            //list.setAdapter(new ArrayAdapter<Teacher>(getActivity(), android.R.layout.simple_list_item_1,
            //      android.R.id.text1, arrayTeacher));
            //Get the cardlistview
            list = (CardListView) getActivity().findViewById(R.id.teachersList);
            //create cards

            ArrayList<Card> cards = new ArrayList<Card>();

            for (int i = 0; i < arrayTeacher.length; i++) {
                Log.d(TAG, ""+arrayTeacher[i].getId());
                // Create a Card
                 card_on_list = new CustomTeacherCard(getActivity());
                //Set card id with teacher id
                 card_on_list.setId(arrayTeacher[i].getId());
                // Create a CardHeader and add Header to card_on_list
                CardHeader header = new CardHeader(getActivity());
                header.setTitle("Teacher "+arrayTeacher[i].getId());
               // header.setButtonExpandVisible(true);
                //Add a popup menu. This method sets OverFlow button to visibile
                header.setPopupMenu(R.menu.teacher_card_overflow_menu, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                            @Override
                            public void onMenuItemClick(BaseCard baseCard, MenuItem menuItem) {
                                vibe.vibrate(60); // 60 is time in ms
                                switch(menuItem.getItemId()){
                                    case(R.id.putTeacher):
                                        Log.d(TAG,"CARD ID PUT"+baseCard.getId());
                                        int put = 9999;
                                        onCardClick(put,TeacherApi.PUT);
                                        break;
                                    case(R.id.deleteTeacher):
                                        //Call the method to delete
                                        deleteTeacher(Integer.valueOf(baseCard.getId()));
                                        break;
                                    case(R.id.otherActions):
                                      Toast.makeText(getActivity(),"Future actions",Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           break;
                                }
                            }
                        });
                        card_on_list.addCardHeader(header);


                card_on_list.setTitle("DNI/NIF\n"+arrayTeacher[i].getDNINIF());
                card_on_list.setClickable(true);
                card_on_list.setShadow(true);
               CustomExpandCard expand= new CustomExpandCard(getActivity(),arrayTeacher[i]);
                card_on_list.addCardExpand(expand);
                //Set expand on click
               ViewToClickToExpand viewToClickToExpand =
                        ViewToClickToExpand.builder()
                                .highlightView(false)
                                .setupCardElement(ViewToClickToExpand.CardElementUI.CARD);
                card_on_list.setViewToClickToExpand(viewToClickToExpand);
                //card_on_list.setBackgroundColorResourceId(R.color.Silver);==>this don't works
                card_on_list.setOnClickListener(new Card.OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        vibe.vibrate(60); // 6  0 is time in ms
                        Log.d(TAG, "Clickable card id: " + card.getId());
                        int position = mCardArrayAdapter.getPosition(card);
                        list.setItemChecked(position, true);
                    }

                });
                //Now we add the thumbnail
                //Create thumbnail
                CardThumbnail thumb = new CardThumbnail(getActivity());
                //Set URL resource
                thumb.setUrlResource(TeacherApi.IMAGE);
                //Add thumbnail to a card
                card_on_list.addCardThumbnail(thumb);
                //add card to list
                cards.add(card_on_list);
            }

            mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards );

            if (list != null) {
               list.setAdapter(mCardArrayAdapter);
                //list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                //GET FROM SETTINGS WHICH SCHOOL IS USED IN SETTINGS

               // String current_selected_teacher = arrayTeacher[0].getId().toString();

               // Log.d(TAG,"Getted current selected school: " + current_selected_teacher);

                //list.setItemChecked(Integer.parseInt(current_selected_teacher), true);


            }
        }
    }


//Custom card class
public class CustomTeacherCard extends Card implements View.OnClickListener {


    private String title;

    //Constructor
    public CustomTeacherCard(Context context) {
        super(context, R.layout.teacher_card_inside_buttons);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        //Get controls and set button listeners
        TextView tx = (TextView) view.findViewById(R.id.titleTeacher);
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
            vibe.vibrate(60); // 50 is time in ms
            switch(v.getId()){
                case R.id.btnDetail:
                    onCardClick(Integer.valueOf(getId()), TeacherApi.DETAIL);
                    break;
                case  R.id.btnEdit:
                    onCardClick(Integer.valueOf(getId()), TeacherApi.EDIT);
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
//We use helpers interface
   /* public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/

    public class CustomExpandCard extends CardExpand {
        //We send an object teacher to fill text views
          private Teacher teacher;
        //Use your resource ID for your inner layout
        public CustomExpandCard(Context context,Teacher teacher) {
            super(context, R.layout.teacher_card_expand);
            this.teacher = teacher;
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            if (view == null) return;

            //Retrieve TextView elements
            TextView id = (TextView) view.findViewById(R.id.contentId);
            TextView personId = (TextView) view.findViewById(R.id.contentPersonId);
            TextView userId = (TextView) view.findViewById(R.id.contentUserId);
            TextView entryDate = (TextView) view.findViewById(R.id.contentEntryDate);
            TextView lastUpdate = (TextView) view.findViewById(R.id.contentLastUpdate);
            TextView lastUpdateUserId = (TextView) view.findViewById(R.id.contentLastUpdateUserId);
            TextView creatorId = (TextView) view.findViewById(R.id.contentCreatorId);
            TextView markedForDeletion = (TextView) view.findViewById(R.id.contentMarkedForDeletion);
            TextView markedForDeletionDate = (TextView) view.findViewById(R.id.contentMarkedForDeletionDate);
            TextView dniNif = (TextView) view.findViewById(R.id.contentDniNif);

            //Set value in text views
            if (id != null) {
                id.setText("ID:"+teacher.getId());
            }

            if (personId != null) {
                personId.setText("Person ID:" + teacher.getPersonId());
            }
            if (userId!= null) {
                userId.setText("User ID:" + teacher.getUserId());
            }
            if (entryDate != null) {
                entryDate.setText("Entry Date:" + teacher.getEntryDate());
            }
            if (lastUpdate != null) {
                lastUpdate.setText("Last Update:" + teacher.getLastUpdate());
            }
            if (lastUpdateUserId != null) {
                lastUpdateUserId.setText("Last Update user ID:" + teacher.getLastUpdateUserId());
            }
            if (creatorId != null) {
                creatorId.setText("Creator ID:" + teacher.getCreatorId());
            }
            if (markedForDeletion != null) {
                markedForDeletion.setText("Marked For Deletion:" + teacher.getMarkedForDeletion());
            }
            if (markedForDeletionDate != null) {
                markedForDeletionDate.setText("Marked For Deletion Date:"+teacher.getMarkedForDeletionDate());
            }
            if (dniNif != null) {
                dniNif.setText("DNI/NIF:" + teacher.getDNINIF());
            }
            //int color = Color.argb(255,255, 255, 0);
            //parent.setBackgroundColor(color);
            parent.setBackgroundColor(mContext.getResources().getColor(R.color.Yellow));


        }

    }

    public void onCardClick(int id,String action){
       //Change the fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment teacherDetail = new TeacherDetail();
        //transaction.hide(TeacherFragment.this);
        transaction.replace(R.id.container,teacherDetail);
        transaction.addToBackStack(null);
        transaction.commit();


        //Pass the id to the fragment detail
         Bundle extras = new Bundle();
        extras.putInt("id",id);
        extras.putString(TeacherApi.ACTION,action);
        teacherDetail.setArguments(extras);
    }
    //DELETE teacher method
    private void deleteTeacher(int id){
        Log.d(TAG,"delete id:"+id);
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(TeacherApi.ENDPOINT).build();
        TeacherApiService api = adapter.create(TeacherApiService.class);
        api.deleteTeacher(id,new Callback<Result>() {
            @Override
            public void success(Result result, Response response) {
                Toast.makeText(getActivity(),"Teacher "+result.getId()+" "+result.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(),"DELETE ERROR! "+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });

    }

}
