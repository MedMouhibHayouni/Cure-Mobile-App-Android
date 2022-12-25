package com.example.cure;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.cure.consultation.Contact;
import com.example.cure.consultation.FrmtContact;
import com.example.cure.consultation.Frmt_call;
import com.example.cure.consultation.Frmt_fav;
import com.example.cure.consultation.RecycleViewAdapter;
import com.example.cure.consultation.ViewPageAdapter;
import com.example.cure.medicine.MedicineActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ConsultationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    View v;
    RecyclerView recyclerView;
    ArrayList<Contact> contactList;
    Context context;
    RecycleViewAdapter adapter;

    /*********************************************************************************************************/


    DatabaseReference reference;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;
    SearchView searchView;

    String userId;
    StorageReference storageReference;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;


    Button bt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

//************************************************************************************************************
//*******************************************************************************************


        ImageView assistimgnav = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.assistimgnav2);
        TextView profileNameNav=navigationView.getHeaderView(0).findViewById(R.id.profileNamenav2);

        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        textView = findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);
        //***********************************************************************************************************************
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_id);
        viewPager = (ViewPager) findViewById(R.id.pageViewId);

        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());

        //     Add Fragment
        viewPageAdapter.AddFrmt(new Frmt_call(), "");
        viewPageAdapter.AddFrmt(new FrmtContact(), "Docteur");
        viewPageAdapter.AddFrmt(new Frmt_fav(), "");

        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_call_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_group_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_favorite_black_24dp);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        



    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent h= new Intent(ConsultationActivity.this,firstpageActivity.class);
                startActivity(h);
                break;
            case R.id.nav_perfo:
                Intent i= new Intent(ConsultationActivity.this,HealthActivity.class);
                startActivity(i);
                break;
            case R.id.nav_navig:
                Intent g= new Intent(ConsultationActivity.this,MapsActivity.class);
                startActivity(g);
                break;
            case R.id.nav_consult:
                Intent s= new Intent(ConsultationActivity.this,ConsultationActivity.class);
                startActivity(s);
            case R.id.nav_rappel:
                Intent t= new Intent(ConsultationActivity.this, MedicineActivity.class);
                startActivity(t);
                break;
            case R.id.nav_profile:
                Intent e= new Intent(ConsultationActivity.this,ProfileActivity.class);
                startActivity(e);
                break;
            case R.id.nav_logout:
                menu.findItem(R.id.nav_logout).setVisible(false);
                menu.findItem(R.id.nav_profile).setVisible(false);

                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        if(searchView!=null)
        {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    /*search(s);
                    return true;
                }
            });
        }

    }*/

   /* private void search(String str)
    {
        ArrayList<Contact> filtre=new ArrayList<>();
        for(Contact object : contactList){

            if (object.getSpec().toLowerCase().contains(str.toLowerCase()))
            {
                filtre.add(object);

            }
        }
        RecycleViewAdapter adapter=new RecycleViewAdapter(filtre);
        recyclerView.setAdapter(adapter);
    }*/






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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                /*adapter.getFilter().filter(newText);
                return false;


            }
        });*/
        return true;
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}