package com.example.cure;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.cure.SwipeInfo.Adapter;
import com.example.cure.SwipeInfo.Model;
import com.example.cure.medicine.MedicineActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;

    ArrayAdapter<String> adapter;
    private ActionBar actionBar;
    ViewPager viewPager;
    Adapter adapterv;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ListView lv = (ListView)findViewById(R.id.listViewCountry);
        ArrayList<String> arrayCountry = new ArrayList<>();
        arrayCountry.addAll(Arrays.asList(getResources().getStringArray(R.array.array_country)));
        adapter = new ArrayAdapter<>(
                InfoActivity.this,
                android.R.layout.simple_list_item_1,
                arrayCountry);
        lv.setAdapter(adapter);


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



        models = new ArrayList<>();
        models.add(new Model(R.drawable.pillsinfo, "Medicament", "Information sur tous Les Medicament "));
        models.add(new Model(R.drawable.conseil, "Conseil", "Conseils pour une meilleur Santé"));
        models.add(new Model(R.drawable.disinfo, "Maladies", "Information sur Des Maladies."));
        models.add(new Model(R.drawable.covid, "Covid-19", "Les Derineres information sur Corona Virus Covid-19."));

        adapterv = new Adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapterv);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapterv.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }


    public void btnGoFP(View view) {
        startActivity(new Intent(this, firstpageActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                Intent h= new Intent(InfoActivity.this,firstpageActivity.class);
                startActivity(h);
                break;
            case R.id.nav_perfo:
                Intent i= new Intent(InfoActivity.this,HealthActivity.class);
                startActivity(i);
                break;
            case R.id.nav_navig:
                Intent g= new Intent(InfoActivity.this,MapsActivity.class);
                startActivity(g);
                break;
            case R.id.nav_consult:
                Intent s= new Intent(InfoActivity.this,ConsultationActivity.class);
                startActivity(s);
            case R.id.nav_rappel:
                Intent t= new Intent(InfoActivity.this, MedicineActivity.class);
                startActivity(t);
                break;
            case R.id.nav_profile:
                Intent e= new Intent(InfoActivity.this,ProfileActivity.class);
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
}


