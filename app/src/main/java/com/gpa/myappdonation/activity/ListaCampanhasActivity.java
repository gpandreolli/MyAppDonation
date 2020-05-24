package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.adapters.AdapterCampanhas;
import com.gpa.myappdonation.model.Campanha;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaCampanhasActivity extends AppCompatActivity {

    private RecyclerView recyclerCampanhas;
    private List<Campanha> campanhas = new ArrayList<>();
    private AdapterCampanhas adapterCampanhas;
    private Query campanhasRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_campanhas);
        Toolbar toolbarCampanhas = findViewById(R.id.toolbarCampanhas);
        setSupportActionBar(toolbarCampanhas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Campanhas");

        recyclerCampanhas = (RecyclerView) findViewById(R.id.recyclerCampanhas);
        campanhasRef = ConfiguracaoFirebase.getFirebase().child("Campanha");
        recuperaCampanhas();

        FloatingActionButton fab = findViewById(R.id.fabCampanhas);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CampanhaActivity.class));
            }
        });



    }

    private void recuperaCampanhas() {
            campanhasRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    campanhas.clear();
                    for (DataSnapshot dsCampanhas: dataSnapshot.getChildren()){
                        campanhas.add(dsCampanhas.getValue(Campanha.class));
                        adapterCampanhas = new AdapterCampanhas(campanhas,ListaCampanhasActivity.this);
                        recyclerCampanhas.setLayoutManager(new LinearLayoutManager(ListaCampanhasActivity.this));
                        recyclerCampanhas.setHasFixedSize(true);
                        recyclerCampanhas.setAdapter(adapterCampanhas);
                    }
                    Collections.reverse(campanhas);
                    if (campanhas.size()> 0){
                        adapterCampanhas.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }
}
