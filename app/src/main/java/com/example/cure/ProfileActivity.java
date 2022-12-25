package com.example.cure;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {


    private static final int GALLERY_INTENT_CODE = 1023;
    TextView fullName, email, phone, verifyMsg,birthd;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button resendCode;
    Button resetPassLocal, changeProfileImage;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        phone = findViewById(R.id.profilePhone2);
        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        resetPassLocal = findViewById(R.id.resetPasswordLocal);
        birthd=findViewById(R.id.profilebirthd);
        profileImage = findViewById(R.id.profileImage);
        changeProfileImage = findViewById(R.id.changeProfile);
//*****************************************************************************

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//*****************************************************************************************************************

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        verifyMsg = findViewById(R.id.verifyMsg);


        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        if (!user.isEmailVerified()) {
            verifyMsg.setVisibility(View.VISIBLE);

            verifyMsg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "onFailure: Email not sent " + e.getMessage());
                        }
                    });
                }
            });
        }


        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    phone.setText(documentSnapshot.getString("phone"));
                    fullName.setText(documentSnapshot.getString("fName"));
                    email.setText(documentSnapshot.getString("email"));
                    birthd.setText(documentSnapshot.getString("birthdate"));

                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery
                Intent i = new Intent(v.getContext(), EditProfile.class);
                i.putExtra("fullName", fullName.getText().toString());
                i.putExtra("email", email.getText().toString());
                i.putExtra("phone", phone.getText().toString());
                i.putExtra("birthdate", birthd.getText().toString());
                startActivity(i);
//

            }
        });

    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(), IntroActivity.class));
        finish();
    }


}
