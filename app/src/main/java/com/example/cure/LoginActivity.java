package com.example.cure;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cure.progressbtn.ProgressButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    EditText mEmail,mPassword;
    Animation topAnim,bottomAnim;
    View mLoginBtn;
    TextView mCreateBtn,forgotTextLink,mCreateBtnview,identifiertxt;
    ProgressBar progressBar,progressBarTwo;;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mEmail = findViewById(R.id.em);
        mPassword = findViewById(R.id.mp);
        progressBarTwo=findViewById(R.id.progressBar2);
        identifiertxt=findViewById(R.id.identifier);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.loginbtn);
        mCreateBtn = findViewById(R.id.signin);
        mCreateBtnview = findViewById(R.id.signinview);
        forgotTextLink = findViewById(R.id.forgetmp);

        //***********************************************************************************************************************************




// ******************************************************************************************************************************



        //*************************************************************************************************************************

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        mLoginBtn.setAnimation(topAnim);
        //*************************************************************************************************
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),firstpageActivity.class));
            finish();
        }
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Requis.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Mot de Passe Requis.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Mot de Passe doit contient >= 6 Characters");
                    return;
                }

               ProgressButton progressButton=new ProgressButton(LoginActivity.this,mLoginBtn);

                progressButton.buttonActivated();

                // authenticate the user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressButton.buttonFinished();
                            Handler handler= new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "Connecté avec Succés", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),afterloginActivity.class));
                                }

                            },2000);

                        }else {
                            Toast.makeText(LoginActivity.this, "Erreur ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            identifiertxt.setText("IDENTIFIER");

                            progressBarTwo.setVisibility(View.GONE);
                        }

                    }
                });

            }
        });

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        mCreateBtnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link.");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(LoginActivity.this, "Reset Link Sent To Your Email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close the dialog
                    }
                });

                passwordResetDialog.create().show();

            }
        });
    }
    public void btnGoLogin(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
    public void btnGoFP(View view) {
        startActivity(new Intent(this, firstpageActivity.class));
    }

    public void btnGoSign(View view) { startActivity(new Intent(this, RegisterActivity.class));
    }
    public void btnGoaflog(View view) {
        startActivity(new Intent(this, afterloginActivity.class));
    }
    public void btnGoFinger(View view) { startActivity(new Intent(this, AuthenfingerActivity.class));
    }
}
