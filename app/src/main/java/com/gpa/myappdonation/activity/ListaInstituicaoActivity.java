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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.adapters.Adapter_instituicoes;
import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaInstituicaoActivity extends AppCompatActivity {

    private RecyclerView recyclerInstituicoes;
    private List<Instituicao> instituicoes = new ArrayList<>();
    private Adapter_instituicoes adapterInst;
    private DatabaseReference instituicaoRef_;
    private Query instituicaoRef;
    private TextView txtRazaoSocial, txtNomeFantasia, txtCnpj, txtTelefone, txtEmail, txtRua, txtNumeroRua, txtComplemento, txtBairro;
    private TextView txtCidade, txtEstado, txtCep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instituicoes);
        //setContentView(R.layout.dados_instituicao);

        instituicaoRef = ConfiguracaoFirebase.getFirebase().child("Instituicao").orderByChild("situacao").equalTo("2");
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
                                exibeDadosInstituicao(position);
                            }

                            @Override
                            public void onLongItemClick(View view, final int position) {

                                new AlertDialog.Builder(ListaInstituicaoActivity.this).
                                        setTitle("Adicionar Instituição").
                                        setMessage("Deseja apoiar esta Instituição?").
                                        setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Instituicao inst = instituicoes.get(position);
                                                addInstituicao(inst);
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

    private void exibeDadosInstituicao(int position) {

        Instituicao instituicao = instituicoes.get(position);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ListaInstituicaoActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dados_instituicao, null);

        txtRazaoSocial = mView.findViewById(R.id.txtRazaoSocial);
        txtNomeFantasia = mView.findViewById(R.id.txtNomeFantasia);
        txtCnpj = mView.findViewById(R.id.txtCnpj);
        txtTelefone = mView.findViewById(R.id.txtTelefone);
        txtEmail = mView.findViewById(R.id.txtEmail);
        txtRua = mView.findViewById(R.id.txtRua);
        txtNumeroRua = mView.findViewById(R.id.txtNumeroRua);
        txtComplemento = mView.findViewById(R.id.txtComplemento);
        txtBairro = mView.findViewById(R.id.txtBairro);
        txtCidade = mView.findViewById(R.id.txtCidade_dadosInst);
        txtEstado = mView.findViewById(R.id.txtEstado);
        txtCep = mView.findViewById(R.id.txtCep);

        txtRazaoSocial.setText(instituicao.getRazaoSocial());
        txtNomeFantasia.setText(instituicao.getNomeFantasia());
        txtCnpj.setText(instituicao.getCnpj());
        txtTelefone.setText(instituicao.getTelefone());
        txtEmail.setText(instituicao.getEmail());
        txtRua.setText(instituicao.getRua());
        txtNumeroRua.setText(instituicao.getNumero());
        txtComplemento.setText(instituicao.getComplemento());
        txtBairro.setText(instituicao.getBairro());
        txtCidade.setText(instituicao.getCidade());
        txtEstado.setText(instituicao.getUf());
        txtCep.setText(instituicao.getCep());

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void recuperaInstituicoes() {
        instituicaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                instituicoes.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    instituicoes.add(ds.getValue(Instituicao.class));
                    adapterInst = new Adapter_instituicoes(instituicoes, ListaInstituicaoActivity.this);
                    recyclerInstituicoes.setLayoutManager(new LinearLayoutManager(ListaInstituicaoActivity.this));
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

    private void addInstituicao(Instituicao instituicao) {
        DatabaseReference minhasInstituicoes = FirebaseDatabase.getInstance().getReference().child("Minhas_Instituicoes");
        minhasInstituicoes.child(ConfiguracaoFirebase.getIdUsuario()).child(instituicao.getUid()).setValue(instituicao);

    }
}
