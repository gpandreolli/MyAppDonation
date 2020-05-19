package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.adapters.AdapterProdutos;
import com.gpa.myappdonation.model.ItemCampanha;
import com.gpa.myappdonation.model.Produto;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class CampanhaActivity extends AppCompatActivity {

    private TextView txtInsituicaoCampanha, txtDataInicial, txtDatafinal;
    private RecyclerView recyclerProdutos;
    private List<Produto> produtos = new ArrayList<>();
    private List<ItemCampanha> itensCampanha = new ArrayList<>();
    private Switch stwPermanente;
    private AlertDialog dialogCarregando;
    private String idInstituicao;
    private DatabaseReference produtoRef;
    private AdapterProdutos adapterProdutos;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanha);
        inicializarComponnetes();

        recyclerProdutos.addOnItemTouchListener(new RecyclerItemClickListener(
                        this,
                        recyclerProdutos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, final int position) {
                                new AlertDialog.Builder(CampanhaActivity.this).
                                        setTitle("Adicionar Instituição").
                                        setMessage("Deseja apoiar esta Instituição?").
                                        setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Produto produtoSelecionado = produtos.get(position);
                                                ItemCampanha itemCampanha = new ItemCampanha();
                                                itemCampanha.setUid(produtoSelecionado.getUid());
                                                itemCampanha.setNome(produtoSelecionado.getNome());
                                                itemCampanha.setDescricao(produtoSelecionado.getDescricao());

                                                itensCampanha.add(itemCampanha);

                                            }
                                        }).
                                        setNegativeButton("Não", null).show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );


    }

    private void recuperaDadosInstituicao() {

        dialogCarregando = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando Dados")
                .setCancelable(false)
                .build();
        dialogCarregando.show();

        idInstituicao = ConfiguracaoFirebase.getIdUsuario();


    }

    private void recuperarProdutos() {
        produtoRef = ConfiguracaoFirebase.getFirebase().child("Produto");
        produtoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtos.clear();
                for (DataSnapshot dsProdutos : dataSnapshot.getChildren()) {
                    produtos.add(dsProdutos.getValue(Produto.class));
                    adapterProdutos = new AdapterProdutos(produtos, CampanhaActivity.this);
                    recyclerProdutos.setLayoutManager(new LinearLayoutManager(CampanhaActivity.this));
                    recyclerProdutos.setHasFixedSize(true);
                    recyclerProdutos.setAdapter(adapterProdutos);
                }
                Collections.reverse(produtos);
                if (produtos.size() > 0) {
                    adapterProdutos.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void inicializarComponnetes() {
        recyclerProdutos = (RecyclerView) findViewById(R.id.recyclerProdutos);
        txtDatafinal = (TextView) findViewById(R.id.txtDataFinalCampnha);
        txtDataInicial = (TextView) findViewById(R.id.txtDataInicialCampanha);

    }


}
