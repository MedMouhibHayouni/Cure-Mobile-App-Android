package com.example.cure.progressbtn;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.cure.R;

public class ProgressButtonSign {

    private CardView cardViewSign;
    private ProgressBar progressBarSign;
    private TextView textViewSign;
    private ConstraintLayout constraintLayout;

    Animation fade_in;

    public ProgressButtonSign(Context ct, View view){

        cardViewSign= view.findViewById(R.id.cardview_id_sign);
        constraintLayout=view.findViewById(R.id.constraint_layoutsign);
        progressBarSign=view.findViewById(R.id.progressBarSign);
        textViewSign=view.findViewById(R.id.inscrire);

    }

    public void buttonActivatedSign(){
        progressBarSign.setVisibility(View.VISIBLE);
        textViewSign.setText("Attendre...");
    }
   public void buttonFinishedSign(){
        constraintLayout.setBackgroundColor(cardViewSign.getResources().getColor(R.color.green));
        progressBarSign.setVisibility(View.GONE);
        textViewSign.setText("✓");
        textViewSign.setTextColor(cardViewSign.getResources().getColor(R.color.active_dots));
    }

}
