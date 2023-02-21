package com.example.sansgarden;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReferensiArtikel extends AppCompatActivity implements RecyclerClickOnListener{
    ImageButton mBackButtonArtikel;
    RecyclerView recyclerView;
    DatabaseReference database;
    LinearLayoutManager linearLayoutManager;
    AdapterRefArtikel adapterRefArtikel;
    List<ArtikelModel> listArtikel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referensi_artikel);

        mBackButtonArtikel = findViewById(R.id.backartikel);



        recyclerView = findViewById(R.id.refArtikel);

        database = FirebaseDatabase.getInstance().getReference("RefArtikel");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listArtikel = new ArrayList<>();
        adapterRefArtikel = new AdapterRefArtikel(this, listArtikel, this);
        recyclerView.setAdapter(adapterRefArtikel);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    ArtikelModel artikelModel = dataSnapshot.getValue(ArtikelModel.class);
                    listArtikel.add(artikelModel);
                }
                adapterRefArtikel.notifyDataSetChanged();
            }
            @Override
            public void onCancelled (@NonNull DatabaseError error) {

            }
        });


        mBackButtonArtikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), bna.class));
            }
        });


    }

    @Override
    public void onItemClicked(ArtikelModel artikelModel) {


    }
}