package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.adapters.AdapterProdutos;
import com.gpa.myappdonation.model.Produto;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaProdutoActivity extends AppCompatActivity {

    private RecyclerView recyclerProdutos;
    private List<Produto> produtos = new ArrayList<>();
    private AdapterProdutos adapterProdutos;
    private Query produtosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produto);
        recyclerProdutos = (RecyclerView) findViewById(R.id.recyclerProdutos);
        produtosRef = ConfiguracaoFirebase.getFirebase().child("Produto");
        recuperaProdutos();

        Toolbar toolbarProdutos = findViewById(R.id.toolbarProdutos);
        setSupportActionBar(toolbarProdutos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Produtos");


        FloatingActionButton fab = findViewById(R.id.fabProdutos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CadastrarProdutoActivity.class));
            }
        });
    }

    private void recuperaProdutos() {
        produtosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtos.clear();
                for (DataSnapshot dsProdutos: dataSnapshot.getChildren()){
                    produtos.add(dsProdutos.getValue(Produto.class));
                    adapterProdutos = new AdapterProdutos(produtos,ListaProdutoActivity.this);
                    recyclerProdutos.setLayoutManager(new LinearLayoutManager(ListaProdutoActivity.this));
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
}
