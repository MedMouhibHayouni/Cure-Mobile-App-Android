package com.example.cure;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.cure.medicine.MedicineActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class firstpageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;


    String userId;
    StorageReference storageReference;
    FirebaseFirestore fStore;
    EditText displayNameEditText;
    ImageView assistimg,assistimgnav,imagetime;
    TextView profiletxt,assistdesc;
    FirebaseAuth fAuth;
    FirebaseUser user;
    TextView name;
    DatabaseReference reference;
    String DISPLAY_NAME = null;
    String PROFILE_IMAGE_URL = null;
    private static final int GALLERY_INTENT_CODE = 1023 ;
    private static final String TAG = "firstpageActivity";


    RelativeLayout relativefirst;


    private TextView mName;
    private TextView mLastName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_firstpage);

//***************************************************************************************************************************//
        relativefirst=findViewById(R.id.imgrel);

       assistimg=findViewById(R.id.assistimg);
       assistdesc=findViewById(R.id.assistdesc);
        name= findViewById(R.id.name);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assistimgnav = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.assistimgnav);
        TextView profileNameNav=navigationView.getHeaderView(0).findViewById(R.id.profileNamenav);

       imagetime=findViewById(R.id.imagetime);
        fAuth = FirebaseAuth.getInstance();
       TextView firsttxt=findViewById(R.id.txt);
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).resize(140, 140).into(assistimg);
            }
        });

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    name.setText(documentSnapshot.getString("fName"));
                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });


        //**********************************************************************************************************************************



        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileimagenav = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileimagenav.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(assistimgnav);
            }
        });


        DocumentReference profilenamereference = fStore.collection("users").document(userId);
        profilenamereference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {

            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){

                    profileNameNav.setText(documentSnapshot.getString("fName"));
                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });


//********************************************************************************************************************************************

        String gradientfirst="<font color=#2E7700>Prenez</font> <font color=#6FAF47> Soins</font> <font color=#B0E190> De Vous</font>";
        firsttxt.setText(Html.fromHtml(gradientfirst));


// **************************************************************************************************************************************//

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            imagetime.setImageResource(R.drawable.morning);
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            imagetime.setImageResource(R.drawable.afternoon);
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            imagetime.setImageResource(R.drawable.afternoon);
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            imagetime.setImageResource(R.drawable.night);
        }

        Calendar call = Calendar.getInstance();
        int imageOfDay = call.get(Calendar.HOUR_OF_DAY);

        if(imageOfDay >= 0 && timeOfDay < 12){
            assistdesc.setText("Bonjour");

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) assistdesc.getLayoutParams();

            // Set TextView layout margin 25 pixels to all side
            // Left Top Right Bottom Margin
            lp.setMargins(150,0,0,0);

            // Apply the updated layout parameters to TextView
            assistdesc.setLayoutParams(lp);

        }else if(imageOfDay >= 12 && timeOfDay < 16){
            assistdesc.setText("Bon aprés-midi");

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) assistdesc.getLayoutParams();

            // Set TextView layout margin 25 pixels to all side
            // Left Top Right Bottom Margin
            lp.setMargins(70,0,0,0);

            // Apply the updated layout parameters to TextView
            assistdesc.setLayoutParams(lp);

        }else if(imageOfDay >= 16 && timeOfDay < 21){
            assistdesc.setText("Bonsoir");

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) assistdesc.getLayoutParams();

            // Set TextView layout margin 25 pixels to all side
            // Left Top Right Bottom Margin
            lp.setMargins(160,0,0,0);

            // Apply the updated layout parameters to TextView
            assistdesc.setLayoutParams(lp);

        }else if(imageOfDay >= 21 && timeOfDay < 24){
            assistdesc.setText("Bonne Nuit");

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) assistdesc.getLayoutParams();

            // Set TextView layout margin 25 pixels to all side
            // Left Top Right Bottom Margin
            lp.setMargins(120,0,0,0);

            // Apply the updated layout parameters to TextView
            assistdesc.setLayoutParams(lp);

        }



//**************************************************************************************************************************************************
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        textView=findViewById(R.id.textView);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

       /*TextView textView = findViewById(R.id.textClock);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:s ");
        String currentTime = sdf.format(new Date());
        textView.setText(currentTime);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView textViewDate = findViewById(R.id.textDate);
        textViewDate.setText(currentDate);*/

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle=new
                ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

    public void btnGonav(View view) {

        startActivity(new Intent(this, MapsActivity.class));
    }
    public void btnGoFP(View view) {
        startActivity(new Intent(this, firstpageActivity.class));
    }
    public void btnGoConsultation(View view) { startActivity(new Intent(this, ConsultationActivity.class)); }
    public void btnGoHealth(View view) { startActivity(new Intent(this, HealthActivity.class)); }

    public void btnGoSign(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }
    public void btnGoProfil(View view) {
        startActivity(new Intent(this, ProfileActivity.class));
    }
    public void btnGoEdit(View view) { startActivity(new Intent(this, EditProfile.class)); }
    public void btnGoRappel(View view) {
        startActivity(new Intent(this, MedicineActivity.class));
    }
    public void btnGoInfo(View view) { startActivity(new Intent(this, InfoActivity.class)); }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent h= new Intent(firstpageActivity.this,firstpageActivity.class);
                startActivity(h);
                break;
            case R.id.nav_perfo:
                Intent i= new Intent(firstpageActivity.this,HealthActivity.class);
                startActivity(i);
                break;
            case R.id.nav_navig:
                Intent g= new Intent(firstpageActivity.this,MapsActivity.class);
                startActivity(g);
                break;
            case R.id.nav_consult:
                Intent s= new Intent(firstpageActivity.this,ConsultationActivity.class);
                startActivity(s);
            case R.id.nav_rappel:
                Intent t= new Intent(firstpageActivity.this,MedicineActivity.class);
                startActivity(t);
                break;
            case R.id.nav_profile:
                Intent e= new Intent(firstpageActivity.this,ProfileActivity.class);
                startActivity(e);
                break;
            case R.id.nav_logout:
                fAuth.signOut();
                finish();
                startActivity(new Intent(this, IntroActivity.class));
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
