package com.gpa.myappdonation.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gpa.myappdonation.util.RecyclerItemClickListener;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.adapters.AdapterMinhasDoacoes;
import com.gpa.myappdonation.model.Doacao;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

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
        swipe();
        recuperaDoacoes();


        recyclerMinhasDoacoes.addOnItemTouchListener(
                            new RecyclerItemClickListener(
                        this,
                        recyclerMinhasDoacoes,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, final int position) {



                            }

                            @Override
                            public void onLongItemClick(View view, final int position) {
                                new AlertDialog.Builder(MinhasDoacoesActivity.this)
                                        .setTitle("Editar Doação")
                                        .setMessage("Deseja Editar essa Doação")
                                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Doacao doacaoEdit = doacoes.get(position);
                                                String uid = doacaoEdit.getUid();
                                                Intent it = new Intent(MinhasDoacoesActivity.this, CadastrarDoacoesActivity.class);
                                                it.putExtra("uid",uid);
                                                startActivity(it);
                                            }
                                        })
                                        .setNegativeButton("Não",null).show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );



    }

    public void swipe(){

        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags =  ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags,swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    Log.i("Swipe","item Arrastado");
            }
        };
        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerMinhasDoacoes);
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
