package com.example.cure;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.cure.stepcounter.StepListener;
import com.google.android.material.navigation.NavigationView;

public class HealthActivity extends AppCompatActivity implements SensorEventListener, StepListener, NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;
    EditText height, weight;
    TextView result;
    boolean running =false;

    private ProgressBar progBar;
    private Handler mHandler = new Handler();
    private int mProgressStatus=0;


    private TextView TvSteps;
    private Button BtnStart;
    private com.example.cure.stepcounter.StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "";
    private int numSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        progBar= (ProgressBar)findViewById(R.id.progressbarstep);

        //dosomething();

        //height = findViewById(R.id.height);
        //weight = findViewById(R.id.weight);

        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        textView=findViewById(R.id.textView);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

       /* findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {

            // Logic for validation, input can't be empty
            @Override
            public void onClick(View v) {

                String str1 = height.getText().toString();
                String str2 = weight.getText().toString();

                if(TextUtils.isEmpty(str1)){
                    height.setError("Please enter your weight");
                    height.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(str2)){
                    weight.setError("Please enter your height");
                    weight.requestFocus();
                    return;
                }

//Get the user values from the widget reference
                float weight = Float.parseFloat(str1);
                float height = Float.parseFloat(str2)/100;

//Calculate BMI value
                float bmiValue = calculateBMI(weight, height);

//Define the meaning of the bmi value
                String bmiInterpretation = interpretBMI(bmiValue);

                result.setText(String.valueOf(bmiValue + "-" + bmiInterpretation));

            }
        });*/

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new com.example.cure.stepcounter.StepDetector();
        simpleStepDetector.registerListener((StepListener) this);

        TvSteps = (TextView) findViewById(R.id.tv_steps);
        BtnStart = (Button) findViewById(R.id.BtnStart);



        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(HealthActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });




    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);

    }


    //Calculate BMI
    private float calculateBMI (float weight, float height) {
        return (float) (weight / (height * height));
    }

    // Interpret what BMI means
    private String interpretBMI(float bmiValue) {

        if (bmiValue < 16) {
            return "Severely underweight";
        } else if (bmiValue < 18.5) {

            return "Underweight";
        } else if (bmiValue < 25) {

            return "Normal";
        } else if (bmiValue < 30) {

            return "Overweight";
        } else {
            return "Obese";
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_perfo: break; case R.id.nav_home:break; case R.id.nav_consult:
                Intent intent = new Intent(HealthActivity.this, firstpageActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout: menu.findItem(R.id.nav_logout).setVisible(false);
                menu.findItem(R.id.nav_profile).setVisible(false);

                break;
            case R.id.nav_share: Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show(); break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    /*public void dosomething() {

        new Thread(new Runnable() {

            public void run() {
                final int presentage=0;
                while (mProgressStatus <0){
                    mProgressStatus += 1;
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {

                            progBar.setProgress(mProgressStatus);
                            TvSteps.setText(""+TvSteps+"");

                        }
                    });
                    try {



                        Thread.sleep(50);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }*/
}