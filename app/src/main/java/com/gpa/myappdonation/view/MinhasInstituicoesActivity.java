package com.gpa.myappdonation.view;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.adapters.AdapterMyInst;
import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MinhasInstituicoesActivity extends AppCompatActivity {

    private RecyclerView recyclerMinhasInstituicoes;
    private List<Instituicao> instituicoes = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private AdapterMyInst adapterMyInst;
    private DatabaseReference istituicaoUsuarioref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_instituicoes);

        istituicaoUsuarioref = ConfiguracaoFirebase.getFirebase().child("Minhas_Instituicoes").child(ConfiguracaoFirebase.getIdUsuario());

        inicializarComponnetes();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recuperaInstituicoes();

        recyclerMinhasInstituicoes.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerMinhasInstituicoes,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                                Instituicao inst = instituicoes.get(position);
                                String idInstituicao = inst.getUid();
                                removeMinhaInstituicao(idInstituicao);
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }

                )
        );
    }

    private void removeMinhaInstituicao(String idInstituicao) {
        istituicaoUsuarioref = ConfiguracaoFirebase.getFirebase()
                .child("Minhas_Instituicoes")
                .child(ConfiguracaoFirebase.getIdUsuario())
                .child(idInstituicao);

        istituicaoUsuarioref.removeValue();

    }


    private void recuperaInstituicoes() {

        istituicaoUsuarioref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                instituicoes.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    instituicoes.add(ds.getValue(Instituicao.class));
                    adapterMyInst = new AdapterMyInst(instituicoes, MinhasInstituicoesActivity.this);
                    recyclerMinhasInstituicoes.setLayoutManager(new LinearLayoutManager(MinhasInstituicoesActivity.this));
                    recyclerMinhasInstituicoes.setHasFixedSize(true);
                    recyclerMinhasInstituicoes.setAdapter(adapterMyInst);
                }
                Collections.reverse(instituicoes);
                if (instituicoes.size() > 0) {
                    adapterMyInst.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Erro ao ler Instituições: " + databaseError.getCode());
            }
        });
    }

    private void inicializarComponnetes() {
        recyclerMinhasInstituicoes = (RecyclerView) findViewById(R.id.recyclerMinhasInstituicoes);
    }


}
