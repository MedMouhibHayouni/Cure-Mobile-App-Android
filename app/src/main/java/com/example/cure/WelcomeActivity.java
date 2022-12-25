package com.example.cure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {
    private ViewPager mPager;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),firstpageActivity.class));
            finish();
        }
    }
    public void btnGoLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void btnGoSign(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}



