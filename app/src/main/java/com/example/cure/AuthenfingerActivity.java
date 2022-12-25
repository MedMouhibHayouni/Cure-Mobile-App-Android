package com.example.cure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AuthenfingerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenfinger);
    }
    public void btnGoStarted(View view) {
        startActivity(new Intent(this, afterloginActivity.class));
    }
    public void btnGoaflog(View view) {
        startActivity(new Intent(this, afterloginActivity.class));
    }
    public void btnGoLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
