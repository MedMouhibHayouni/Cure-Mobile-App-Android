package com.example.cure.consultation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
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


public class Frmt_fav extends Fragment {
    View v;
    RecycleViewAdapter adapter;
    RecyclerView favrecyclerView;
    List<Contact> listCont;
    private Context context;
    ImageView photo;
    List<Contact> contactListFull;


    public Frmt_fav() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fav_frmt,container,false);

        favrecyclerView = v.findViewById(R.id.favorite_recyclerview);
        adapter = new RecycleViewAdapter(getContext(), listCont);
        favrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listCont = new ArrayList<>();
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
                    favrecyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
