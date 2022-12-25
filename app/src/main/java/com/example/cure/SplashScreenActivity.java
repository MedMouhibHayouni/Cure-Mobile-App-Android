package com.example.cure;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    ProgressBar progressBar;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

       /* requestWindowFeature(Window.FEATURE_NO_TITLE);*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){

                Intent i = new Intent(SplashScreenActivity.this,IntroActivity.class);
                startActivity(i);
                finish();


            }

        },2500);
    }
}
