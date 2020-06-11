package com.gpa.myappdonation.activity;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.adapters.AdapterContasInstituicao;
import com.gpa.myappdonation.adapters.AdapterMyInst;
import com.gpa.myappdonation.model.Conta;
import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MinhasInstituicoesActivity extends AppCompatActivity {

    private RecyclerView recyclerMinhasInstituicoes, recyclerviewContaInstituicao;
    private List<Instituicao> instituicoes = new ArrayList<>();
    private List<Conta> contas = new ArrayList<>();
    private AdapterMyInst adapterMyInst;
    private AdapterContasInstituicao adapterContasInstituicao;
    private DatabaseReference istituicaoUsuarioref, contasInstituicaoRef;
    private AppCompatTextView txtRazaoSocial, txtNomeFantasia, txtCnpj, txtTelefone,  txtRua, txtNumeroRua, txtComplemento, txtBairro;
    private AppCompatTextView txtCidade, txtEstado;
    private android.app.AlertDialog dialogCarregando;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_instituicoes);

        istituicaoUsuarioref = ConfiguracaoFirebase.getFirebase().child("Minhas_Instituicoes").child(ConfiguracaoFirebase.getIdUsuario());

        inicializarComponnetes();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Minhas Instituições");
        recuperaInstituicoes();

        recyclerMinhasInstituicoes.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                        recyclerMinhasInstituicoes,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                exibeMinhasInstituicoes(position);
                            }

                            @Override
                            public void onLongItemClick(View view, final int position) {

                                new AlertDialog.Builder(MinhasInstituicoesActivity.this)
                                        .setTitle("Remover Instiuição")
                                        .setMessage("Deseja remover essa instituição da sua lista?")
                                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Instituicao inst = instituicoes.get(position);
                                                String idInstituicao = inst.getUid();
                                                removeMinhaInstituicao(idInstituicao);
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

    private void exibeMinhasInstituicoes(int position) {
        Instituicao instituicao = instituicoes.get(position);

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MinhasInstituicoesActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dados_minha_instituicao, null);
        String idInstituicao = instituicao.getUid();

        txtRazaoSocial = mView.findViewById(R.id.txtRazaoSocialInstituicao_alert);
        txtNomeFantasia = mView.findViewById(R.id.txtNomeInstituicao_alert);
        txtTelefone = mView.findViewById(R.id.txtFoneInstituicao_alert);
        txtCnpj = mView.findViewById(R.id.txtCnpjInstituicao_alert);
        txtRua = mView.findViewById(R.id.txtRuaInstituicao_alert);
        txtNumeroRua = mView.findViewById(R.id.txtNumeroRuaInsti_alert);
        txtComplemento = mView.findViewById(R.id.txtComplInsti_alert);
        txtBairro = mView.findViewById(R.id.txtBairroInstituicao_alert);
        txtCidade = mView.findViewById(R.id.txtCidadeInstituicao_alert);
        txtEstado = mView.findViewById(R.id.txtEstadoInstituicao_alert);
        recyclerviewContaInstituicao = mView.findViewById(R.id.recyclerviewContaInstituicao);

        txtRazaoSocial.setText(instituicao.getRazaoSocial());
        txtNomeFantasia.setText(instituicao.getNomeFantasia());
        txtCnpj.setText(instituicao.getCnpj());
        txtTelefone.setText(instituicao.getTelefone());
        txtRua.setText(instituicao.getRua());
        txtNumeroRua.setText(instituicao.getNumero());
        txtComplemento.setText(instituicao.getComplemento());
        txtBairro.setText(instituicao.getBairro());
        txtCidade.setText(instituicao.getCidade());
        txtEstado.setText(instituicao.getUf());

        recuperaContas(idInstituicao);
        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();

    }

    private void recuperaContas(String idInstituicao) {

        dialogCarregando = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando Dados")
                .setCancelable(false)
                .build();
        dialogCarregando.show();

        contasInstituicaoRef = ConfiguracaoFirebase.getFirebase()
                .child("Conta")
                .child(idInstituicao);
        contasInstituicaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contas.clear();
                for (DataSnapshot dsContas : dataSnapshot.getChildren()) {
                    contas.add(dsContas.getValue(Conta.class));
                    adapterContasInstituicao = new AdapterContasInstituicao(contas, MinhasInstituicoesActivity.this);
                    recyclerviewContaInstituicao.setLayoutManager(new LinearLayoutManager(MinhasInstituicoesActivity.this));
                    recyclerviewContaInstituicao.setHasFixedSize(true);
                    recyclerviewContaInstituicao.setAdapter(adapterContasInstituicao);
                }
                Collections.reverse(contas);
                if (contas.size() > 0) {
                    adapterContasInstituicao.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dialogCarregando.dismiss();
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
