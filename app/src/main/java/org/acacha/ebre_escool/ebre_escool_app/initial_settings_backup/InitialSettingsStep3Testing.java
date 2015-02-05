package org.acacha.ebre_escool.ebre_escool_app.initial_settings_backup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.acacha.ebre_escool.ebre_escool_app.R;
import org.acacha.ebre_escool.ebre_escool_app.UniversalImageLoaderCard;
import org.codepond.wizardroid.WizardStep;

import java.io.File;
import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;

public class InitialSettingsStep3Testing extends WizardStep {

    DisplayImageOptions options;

    //You must have an empty constructor for every step
    public InitialSettingsStep3Testing() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.initialsettingsstep3testing, container, false);
        TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText("Test exemple");

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
                CardView cardView = (CardView) getActivity().findViewById(R.id.card);


        CardHeader cardHeader = new CardHeader(getActivity());
        cardHeader.setTitle("This is header");

        Card card = new Card(getActivity());
        card.setTitle("This is Title of Card");
        card.addCardHeader(cardHeader);

        cardView.setCard(card);

        initUniversalImageLoaderLibrary();
        initCard();
    */


    }

    /**
     * Android-Universal-Image-Loader config.
     *
     * DON'T COPY THIS CODE TO YOUR REAL PROJECT!     *
     * I would recommend doing it in an overloaded Application.onCreate().
     * It is just for test purpose
     *
     *
     */
    /*
    private void initUniversalImageLoaderLibrary(){

        File cacheDir = StorageUtils.getCacheDirectory(getActivity());
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity())
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .writeDebugLogs()
                .build();

        ImageLoader.getInstance().init(config);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .displayer(new SimpleBitmapDisplayer())
                .showImageOnFail(android.R.drawable.ic_menu_report_image)
                .build();
    }
 */

    /**
     * This method builds a simple card
     */
    /*
    private void initCard() {

        //Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < 200; i++) {
            UniversalImageLoaderCard card = new UniversalImageLoaderCard(this.getActivity(),options);
            card.setTitle("A simple card loaded with Universal-Image-Loader " + i);
            card.setSecondaryTitle("Simple text..." + i);
            card.setCount(i);
            cards.add(card);
        }

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);

        CardListView listView = (CardListView) getActivity().findViewById(R.id.carddemo_extra_list_picasso);
        if (listView != null) {
            listView.setAdapter(mCardArrayAdapter);
        }

    }
    */


}