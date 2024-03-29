package com.gpa.myappdonation.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.adapters.AdapterContas;
import com.gpa.myappdonation.fragment.ContaFragment;
import com.gpa.myappdonation.model.Conta;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class ListaContaActivity extends AppCompatActivity {

    private RecyclerView recyclerContas;
    private List<Conta> contas = new ArrayList<>();
    private AdapterContas adapterContas;
    private DatabaseReference contaRef;
    private ContaFragment contaFragment;
    private Button btnEditaConta;
    private AlertDialog dialogCarregando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_conta);
        Toolbar toolbar = findViewById(R.id.toolbarContas);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Contas");
        contaRef = ConfiguracaoFirebase.getFirebase().child("Conta").child(ConfiguracaoFirebase.getIdUsuario());
        inicializarComponnetes();

        FloatingActionButton fab = findViewById(R.id.fabContas);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chamaActivity();
            }
        });
        
        recuperaContas();



       /*recyclerContas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        this,
                       recyclerContas,
                        new RecyclerItemClickListener.OnItemClickListener() {


                            @Override
                            public void onItemClick(View view, final int position) {

                                switch (view.getId()){
                                    case R.id.btnEditaConta:
                                       Toast.makeText(ListaContaActivity.this,"Teste do botão: 1", Toast.LENGTH_LONG).show();
                                    case R.id.txtNome_Conta:
                                        Toast.makeText(ListaContaActivity.this,"Teste do botão: 2", Toast.LENGTH_LONG).show();
                                        break;
                                }
                                Conta conta = contas.get(position);

                                final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                                        .setTitle("Dados da Conta")
                                        .setMessage(getConta(conta))
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

                                new AlertDialog.Builder(ListaContaActivity.this)
                                        .setTitle("Remover Conta")
                                        .setMessage("Deseja remover essa Conta?")
                                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Conta conta = contas.get(position);
                                                String idConta = conta.getUid();
                                                removeConta(idConta);
                                            }
                                        })
                                        .setNegativeButton("Não",null).show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                            }
                        }
                )
        );*/

    }

    private void chamaActivity() {
        Intent i = new Intent(ListaContaActivity.this, CadastrarConta.class);
        startActivity(i);
    }

    private void removeConta(String idConta) {
        contaRef = ConfiguracaoFirebase.getFirebase().child("Conta").child(ConfiguracaoFirebase.getIdUsuario()).child(idConta);
        contaRef.removeValue();
    }

    private void recuperaContas() {
        dialogCarregando = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando Dados")
                .setCancelable(false)
                .build();
        dialogCarregando.show();

        contaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contas.clear();
                for (DataSnapshot dsContas :dataSnapshot.getChildren()){
                    contas.add(dsContas.getValue(Conta.class));
                    adapterContas = new AdapterContas(contas,ListaContaActivity.this);
                    recyclerContas.setLayoutManager(new LinearLayoutManager(ListaContaActivity.this));
                    recyclerContas.setHasFixedSize(true);
                    recyclerContas.setAdapter(adapterContas);
                }
                Collections.reverse(contas);
                if (contas.size() > 0) {
                    adapterContas.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Erro ao ler Contas: " + databaseError.getCode());
            }
        });

        dialogCarregando.dismiss();
    }

    private void inicializarComponnetes() {
        recyclerContas = (RecyclerView) findViewById(R.id.recyclerContas);
        btnEditaConta = (Button) findViewById(R.id.btnEditaConta);
    }



    @Override
    public void onBackPressed() {
        finish();
    }


}
