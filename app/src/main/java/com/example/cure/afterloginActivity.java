package com.example.cure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class afterloginActivity extends AppCompatActivity {
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView text;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afterlogin);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);



        image = findViewById(R.id.image);
        text = findViewById(R.id.text);
        button = findViewById(R.id.button7);

        text.setAnimation(bottomAnim);
        button.setAnimation(bottomAnim);
        image.setAnimation(topAnim);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    public void btnGoFP(View view) {
        startActivity(new Intent(this, firstpageActivity.class));
    }
}
