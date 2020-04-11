package com.gpa.myappdonation.view;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.adapters.AdapterMinhasDoacoes;
import com.gpa.myappdonation.model.Doacao;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinhasDoacoesActivity extends AppCompatActivity {


    private List<Doacao> doacoes = new ArrayList<>();
    private RecyclerView recyclerMinhasDoacoes;
    private DatabaseReference doacoesRef;
    private AdapterMinhasDoacoes adapterMinhasDoacoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_doacoes);

        doacoesRef = ConfiguracaoFirebase.getFirebase().child("Minhas_Doacoes").child(ConfiguracaoFirebase.getIdUsuario());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Minhas Doações");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CadastrarDoacoesActivity.class));
            }
        });
        InicilizarComponentes();
        recuperaDoacoes();

       /* recyclerMinhasDoacoes.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerMinhasDoacoes,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );*/



    }

    private void recuperaDoacoes(){
        doacoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doacoes.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    doacoes.add(ds.getValue(Doacao.class));
                    adapterMinhasDoacoes = new AdapterMinhasDoacoes(doacoes,MinhasDoacoesActivity.this);
                    recyclerMinhasDoacoes.setLayoutManager(new LinearLayoutManager(MinhasDoacoesActivity.this));
                    recyclerMinhasDoacoes.setHasFixedSize(true);
                    recyclerMinhasDoacoes.setAdapter(adapterMinhasDoacoes);

                }
                Collections.reverse(doacoes);
                if (doacoes.size()>0){
                    adapterMinhasDoacoes.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Erro ao ler Doações: " + databaseError.getCode());
            }
        });
    }

    private void InicilizarComponentes() {

        recyclerMinhasDoacoes = (RecyclerView) findViewById(R.id.recyclerMinhasDoacoes);
    }


}
