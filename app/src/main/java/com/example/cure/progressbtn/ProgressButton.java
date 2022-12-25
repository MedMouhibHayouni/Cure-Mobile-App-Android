package com.example.cure.progressbtn;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.cure.R;

public class ProgressButton {

    public CardView cardView;
   public ProgressBar progressBar;
    public TextView textView;
    public ConstraintLayout constraintLayout;

    Animation fade_in;

    public ProgressButton(Context ct, View view){

        cardView= view.findViewById(R.id.cardview_id);
        constraintLayout=view.findViewById(R.id.constraint_layout);
        progressBar=view.findViewById(R.id.progressBar2);
        textView=view.findViewById(R.id.identifier);

    }

    public void buttonActivated(){
        progressBar.setVisibility(View.VISIBLE);
        textView.setText("Attendre...");
    }
    public void buttonFinished(){
        constraintLayout.setBackgroundColor(cardView.getResources().getColor(R.color.green));
        progressBar.setVisibility(View.GONE);
        textView.setText("✓");
        textView.setTextColor(cardView.getResources().getColor(R.color.active_dots));
    }
}
