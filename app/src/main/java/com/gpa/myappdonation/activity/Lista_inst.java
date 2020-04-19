package com.gpa.myappdonation.activity;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.adapters.Adapter_instituicoes;
import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lista_inst extends AppCompatActivity {

    private RecyclerView recyclerInstituicoes;
    private List<Instituicao> instituicoes = new ArrayList<>();
    private Adapter_instituicoes adapterInst;
    private DatabaseReference instituicaoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituicoes);

        instituicaoRef = ConfiguracaoFirebase.getFirebase().child("Instituicao");
        inicializarComponnetes();
        recuperaInstituicoes();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Instituições");


        recyclerInstituicoes.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerInstituicoes,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Instituicao inst = instituicoes.get(position);

                                final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                                        .setTitle("Dados da Instituicao")
                                        .setMessage(getInstituicao(inst))
                                        .setPositiveButton("OK", null)
                                        .show();
                                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                                button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });



                            }

                            @Override
                            public void onLongItemClick(View view, final int position) {

                                new AlertDialog.Builder(Lista_inst.this).
                                        setTitle("Adicionar Instituição").
                                        setMessage("Deseja apoiar esta Instituição?").
                                        setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Instituicao inst = instituicoes.get(position);
                                                String idInstituicao = inst.getUid();
                                                String nomeInstituicao = inst.getNomeFantasia();
                                                String cidadeInstituicao = inst.getCidade();
                                                String ufInstituicao = inst.getUf();
                                                addInstituicao(idInstituicao, nomeInstituicao, cidadeInstituicao, ufInstituicao);
                                            }
                                        }).
                                        setNegativeButton("Não", null).show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

    }

    private void recuperaInstituicoes() {
        instituicaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                instituicoes.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    instituicoes.add(ds.getValue(Instituicao.class));
                    adapterInst = new Adapter_instituicoes(instituicoes, Lista_inst.this);
                    recyclerInstituicoes.setLayoutManager(new LinearLayoutManager(Lista_inst.this));
                    recyclerInstituicoes.setHasFixedSize(true);
                    recyclerInstituicoes.setAdapter(adapterInst);
                }
                Collections.reverse(instituicoes);
                if (instituicoes.size() > 0) {
                    adapterInst.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarComponnetes() {
        recyclerInstituicoes = (RecyclerView) findViewById(R.id.recyclerInstituicoes);
    }

    private void addInstituicao(final String idInstituicao, String nome, String cidade, String uf) {

        final Instituicao inst = new Instituicao(idInstituicao, nome, cidade, uf);
        inst.setUid(idInstituicao);
        inst.setNomeFantasia(nome);
        inst.setCidade(cidade);
        inst.setUf(uf);
        DatabaseReference minhasInstituicoes = FirebaseDatabase.getInstance().getReference().child("Minhas_Instituicoes");
        minhasInstituicoes.child(ConfiguracaoFirebase.getIdUsuario()).child(idInstituicao).setValue(inst);

    }
    private String getInstituicao(Instituicao inst) {
        return "Razão Social: " + inst.getRazaoSocial()  + "\n" +
                "Nome Fantasia: " + inst.getNomeFantasia() + "\n" +
                "CNPJ: " + inst.getCnpj() + "\n" +
                "Telefone: " + inst.getTelefone() + "\n" +
                "email: " + inst.getEmail() + "\n" +
                "----------------------------------- " + "\n" +
                "Enedereço: " + "\n" +
                "Rua: " + inst.getRua() + "\n" +
                "Número: " + inst.getNumero() + "\n" +
                "Complemento: " + inst.getComplemento() + "\n" +
                "Bairro: " + inst.getBairro() + "\n" +
                "CEP: " + inst.getCep() + "\n" +
                "Cidade: " + inst.getCidade() + "\n" +
                "Estado: " + inst.getUf() + "\n" ;
    }

}
