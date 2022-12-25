package com.example.cure;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.DrawableCompat;

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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private static final String[] items=new String[]{"Homme","Femme"};


    public static final String TAG = "TAG";
    EditText profileFullName, profileEmail, profilePhone, profilebirthd;
    Button saveBtn;
    String userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;
    CardView edit_card;
    ImageView profileImageView;
    Button resetPassLocal;
    private static final String[] paths = {"item 1", "item 2", "item 3"};
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        final String fullName = data.getStringExtra("fullName");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");
        String birthd = data.getStringExtra("birthdate");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        userId = fAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        CircleImageView profileImage = findViewById(R.id.profileImage);
        Button upload_photo = findViewById(R.id.upload_photo);
        profileFullName = findViewById(R.id.profileFullName);
        profileEmail = findViewById(R.id.profileEmailAddress);
        profilePhone = findViewById(R.id.profilePhoneNo);
        profileImageView = findViewById(R.id.profileImageView);
        saveBtn = findViewById(R.id.saveProfileInfo);
        profilebirthd=findViewById(R.id.birthd);
        resetPassLocal = findViewById(R.id.resetPasswordLocal);

//*********************************************************************************************************************************************************
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//*********************************************Date Calendar****************************************************************************************//



        profilebirthd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR); // current year
                int month = c.get(Calendar.MONTH); // current month
                int day = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                DatePickerDialog dialog = new DatePickerDialog(EditProfile.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year,month ,day );
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
                profilebirthd.setText(mdate);

            }
        };


        resetPassLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetPassword = new EditText(v.getContext());

                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter New Password > 6 Characters long.");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String newPassword = resetPassword.getText().toString();
                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfile.this, "Password Reset Failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close
                    }
                });

                passwordResetDialog.create().show();

            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDateSet,mm/dd/yyyy" + month + "/" + dayOfMonth + "/" + year);
                String mdate = month + "/" + dayOfMonth + "/" + year;
                profilebirthd.setText(mdate);

            }
        };

//******************************************Edit text Clear*******************************************************************************************
        Button namedelete = findViewById(R.id.name_delete);
        Button emaildelete = findViewById(R.id.email_delete);
        Button bddelete = findViewById(R.id.bd_delete);
        Button numberdelete = findViewById(R.id.phone_delete);

        namedelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileFullName.setText(null);
            }
        });
        emaildelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileEmail.setText(null);
            }
        });
        bddelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilebirthd.setText(null);
            }
        });
        numberdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePhone.setText(null);
            }
        });


        //*******************************************Underligne***/******************************************************************
        profileFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable wrappedDrawable = DrawableCompat.wrap(profileFullName.getBackground());
                DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.blue));
                profileFullName.setBackgroundDrawable(wrappedDrawable);
            }
        });


//************************************Spinnneeeeeeeerrrrr*******************************************************************************

        Spinner spinner = (Spinner) findViewById(R.id.dynamic_spinner);

        String[] items = new String[]{"Genre","Homme","Femme"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
                String text = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        /*AutoCompleteTextView spinner=(AutoCompleteTextView) findViewById(R.id.dynamic_spinner);
        Button btngender=findViewById(R.id.btngender);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        spinner.setAdapter(adapter);

        btngender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.showDropDown();
            }
        });*/

        //**************************************************************************************

        //*******************************************************************************************

        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).resize(431, 315).into(profileImageView);

            }
        });
        upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        StorageReference profileRefupload = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).resize(160, 140).into(profileImage);
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileFullName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty() || profilePhone.getText().toString().isEmpty()
                        || profilebirthd.getText().toString().isEmpty() || spinner.getSelectedItem().toString().isEmpty()) {
                    Toast.makeText(EditProfile.this, "Un ou plusieurs champs sont Vides.", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String email = profileEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("email", email);
                        edited.put("fName", profileFullName.getText().toString());
                        edited.put("phone", profilePhone.getText().toString());
                        edited.put("birthdate",profilebirthd.getText().toString());
                        edited.put("gender",spinner.getSelectedItem().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Profil mis à jour", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                                finish();
                            }
                        });
                        Toast.makeText(EditProfile.this, "L'email est changé", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    profilePhone.setText(documentSnapshot.getString("phone"));
                    profileFullName.setText(documentSnapshot.getString("fName"));
                    profileEmail.setText(documentSnapshot.getString("email"));
                    profilebirthd.setText(documentSnapshot.getString("birthdate"));
                    spinner.setSelection(adapter.getPosition(documentSnapshot.getString("gender")));

                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

       /* profileEmail.setText(email);
        profileFullName.setText(fullName);
        profilebirthd.setText(birthd);
        profilePhone.setText(phone);

        Log.d(TAG, "onCreate: " + fullName + " " + email + " " + birthd + " " + phone);*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();

                //profileImage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);


            }
        }

    }

    private void uploadImageToFirebase(Uri imageUri) {
        // uplaod image to firebase storage
        final StorageReference fileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
