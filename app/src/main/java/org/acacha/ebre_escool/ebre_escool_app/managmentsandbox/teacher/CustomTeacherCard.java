package org.acacha.ebre_escool.ebre_escool_app.managmentsandbox.teacher;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.acacha.ebre_escool.ebre_escool_app.R;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by criminal on 13/02/15.
 */
public class CustomTeacherCard extends Card implements View.OnClickListener {


    private String title;

    //Constructor
    public CustomTeacherCard (Context context){
        super(context,R.layout.teacher_card_inside_buttons);
    }
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView tx= (TextView)view.findViewById(R.id.titleTeacher);
        Button btnDetail = (Button)view.findViewById(R.id.btnDetail);
        Button btnEdit = (Button)view.findViewById(R.id.btnEdit);
        tx.setText(title);
        if (btnDetail != null) {
            btnDetail.setOnClickListener(this);

        }
            if (btnEdit != null) {
                btnEdit.setOnClickListener(this);

            }


        //.... set the other ui elements
    }
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnDetail:
                Toast.makeText(getContext(), "Click Listener card="+getId(),
                        Toast.LENGTH_LONG).show();
            case  R.id.btnEdit:
                Toast.makeText(getContext(), "Click Listener card="+getId(),
                        Toast.LENGTH_LONG).show();
        }

    }
}

