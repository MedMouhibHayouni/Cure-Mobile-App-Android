package com.example.cure.consultation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cure.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FrmtContact extends Fragment {
    RecycleViewAdapter adapter;
    View v;
    RecyclerView recyclerView;
    List<Contact> listCont;
    private Context context;
    ImageView btCall;
    SearchView searchView;


    public FrmtContact() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.contact_frmt, container, false);
        btCall = v.findViewById(R.id.call);
        recyclerView = v.findViewById(R.id.contact_recycleView);
        adapter = new RecycleViewAdapter(getContext(), listCont);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listCont = new ArrayList<>();
        listCont=new ArrayList<>();
        DatabaseReference nm = FirebaseDatabase.getInstance().getReference("medecin");
        nm.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Contact l = snapshot.getValue(Contact.class);
                        listCont.add(l);
                    }
                    adapter = new RecycleViewAdapter(getContext(), listCont);
                    recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




         /*listCont = new ArrayList<>();

         btCall.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String mobileNo=adapter.contactList.getPhn();



             }
         });*/




        /* listCont.add(new Contact("Nom Docteur1","Desc Docteur",R.drawable.profidoc));
         listCont.add(new Contact("Nom Docteur2","Desc Docteur",R.drawable.profidocfe));
         listCont.add(new Contact("Nom Docteur3","Desc Docteur",R.drawable.profidoc));
         listCont.add(new Contact("Nom Docteur4","Desc Docteur",R.drawable.profidocfe));
         listCont.add(new Contact("Nom Docteur5","Desc Docteur",R.drawable.profidoc));
         listCont.add(new Contact("Nom Docteur6","Desc Docteur",R.drawable.profidocfe));
         listCont.add(new Contact("Nom Docteur7","Desc Docteur",R.drawable.profidoc));
         listCont.add(new Contact("Nom Docteur8","Desc Docteur",R.drawable.profidocfe));
         listCont.add(new Contact("Nom Docteur9","Desc Docteur",R.drawable.profidoc));
         listCont.add(new Contact("Nom Docteur10","Desc Docteur",R.drawable.profidocfe));
         listCont.add(new Contact("Nom Docteur11","Desc Docteur",R.drawable.profidoc));
         listCont.add(new Contact("Nom Docteur12","Desc Docteur",R.drawable.profidocfe));
         listCont.add(new Contact("Nom Docteur13","Desc Docteur",R.drawable.profidoc));
         listCont.add(new Contact("Nom Docteur14","Desc Docteur",R.drawable.profidocfe));
         listCont.add(new Contact("Nom Docteur15","Desc Docteur",R.drawable.profidoc));
         listCont.add(new Contact("Nom Docteur16","Desc Docteur",R.drawable.profidocfe));
         listCont.add(new Contact("Nom Docteur17","Desc Docteur",R.drawable.profidoc));
         listCont.add(new Contact("Nom Docteur18","Desc Docteur",R.drawable.profidocfe));
         listCont.add(new Contact("Nom Docteur19","Desc Docteur",R.drawable.profidoc));
         listCont.add(new Contact("Nom Docteur20","Desc Docteur",R.drawable.profidocfe));*/


    }


    /*@Override
    public void onStart() {
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
                    search(s);
                    return true;
                }
            });
        }

    }

    private void search(String str)
    {
        ArrayList<Contact> filtre=new ArrayList<>();
        for(Contact object : listCont){

            if (object.getSpec().toLowerCase().contains(str.toLowerCase()))
            {
                filtre.add(object);

            }
        }
        RecycleViewAdapter adapter=new RecycleViewAdapter(filtre);
        recyclerView.setAdapter(adapter);
    }*/


}
