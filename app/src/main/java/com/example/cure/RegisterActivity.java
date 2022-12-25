package com.example.cure;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cure.progressbtn.ProgressButtonSign;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText mUsername,mEmail,mPassword,mConfirmpass,mBirthdate;
    Animation topAnim,bottomAnim;
    TextView mLoginBtn,mLoginviewbtn,inscrireTxt;
    FirebaseAuth fAuth;
    View mRegisterBtn;
    ProgressBar progressBarSign;
    FirebaseFirestore fStore;
    String userID;

    //********************************************************************************************************************//
    private EditText date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//******************************************************************************************************************************//

        date = findViewById(R.id.bd);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR); // current year
                int month = c.get(Calendar.MONTH); // current month
                int day = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year,month ,day );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                Log.d(TAG,"onDateSet,mm/dd/yyyy"+month+"/"+dayOfMonth+"/"+year);
                String mdate= month+"/"+dayOfMonth+"/"+year;
                date.setText(mdate);

            }
        };
        //*************************************************************************************************************************//
        mUsername    = findViewById(R.id.username);
        mEmail       = findViewById(R.id.em);
        mPassword    = findViewById(R.id.mp);
        mConfirmpass = findViewById(R.id.confirmmp);
        mBirthdate = findViewById(R.id.bd);
        mRegisterBtn = findViewById(R.id.signupbtn);
        mLoginBtn    = findViewById(R.id.login);
        mLoginviewbtn =findViewById(R.id.loginview);
        fAuth        = FirebaseAuth.getInstance();
        fStore       = FirebaseFirestore.getInstance();
        progressBarSign=findViewById(R.id.progressBarSign);
        inscrireTxt=findViewById(R.id.inscrire);


        //******************************************************************************************************************************************

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        mRegisterBtn.setAnimation(topAnim);

        //***********************************************************************************************************************************


        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),firstpageActivity.class));
            finish();
        }




        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mUsername.getText().toString().trim();
                final String phone    = mBirthdate.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                ProgressButtonSign progressButtonSign=new ProgressButtonSign(RegisterActivity.this,mRegisterBtn);

                progressButtonSign.buttonActivatedSign();

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser fuser = fAuth.getCurrentUser();
                            progressButtonSign.buttonFinishedSign();
                            Handler handler= new Handler();
                            handler.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {


                                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RegisterActivity.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                        }
                                    });

                            Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("phone",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),afterloginActivity.class));
                            }
                            },2000);
                        }else {
                            Toast.makeText(RegisterActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            inscrireTxt.setText("INSCRIRE");

                            progressBarSign.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });



        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });


    mLoginviewbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
    });
}
    /*public void btnGoLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }*/
    public void btnGoFP(View view) {
        startActivity(new Intent(this, firstpageActivity.class));
    }

    public void btnGoSign(View view) { startActivity(new Intent(this, RegisterActivity.class));
    }
    public void btnGoaflog(View view) {
        startActivity(new Intent(this, afterloginActivity.class));
    }
}
