package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Produto;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CadastrarProdutoActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private EditText edtNomeProduto, edtDescricaoProduto;
    private Button btnSalvarProduto, btnCancelarProduto;
    private Bundle extras;
    private List<Produto> produtos = new ArrayList<>();
    private int position = 0;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,produtoEditReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);

        inicializarComponentes();

        Toolbar toolbar = findViewById(R.id.toolbarCadastraProduto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Produtos");




        Intent i = getIntent();
        extras = getIntent().getExtras();
        final String uidProduto = i.getStringExtra("uid");
        if (extras!=null){
            setaProduto(uidProduto);
        }

        btnSalvarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarProduto(uidProduto);
            }
        });

        btnCancelarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setaProduto(String idProduto) {
        produtoEditReference = ConfiguracaoFirebase.getFirebase().child("Produto").child(idProduto);
        produtoEditReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Produto produtoEdit = dataSnapshot.getValue(Produto.class);
                edtNomeProduto.setText(produtoEdit.getNome());
                edtDescricaoProduto.setText(produtoEdit.getDescricao());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void salvarProduto(String idProduto) {
        Produto produto = new Produto();
        produto.setNome(edtNomeProduto.getText().toString());
        produto.setDescricao(edtDescricaoProduto.getText().toString());

        if (extras != null) {
            produto.setUid(idProduto);
            produtoEditReference = ConfiguracaoFirebase.getFirebase();
            produtoEditReference.child("Produto").child(idProduto).setValue(produto);
        } else {
            produto.setUid(UUID.randomUUID().toString());
            databaseReference = ConfiguracaoFirebase.getFirebase();
            databaseReference.child("Produto").child(produto.getUid()).setValue(produto);
        }
        limparCampos();
        chamaActivity();
    }

    private void inicializarComponentes() {
        edtNomeProduto = (EditText) findViewById(R.id.edtNomeProduto);
        edtDescricaoProduto = (EditText) findViewById(R.id.edtDescricaoProduto);
        btnSalvarProduto = (Button) findViewById(R.id.btnSalvarProduto);
        btnCancelarProduto = (Button) findViewById(R.id.btnCancelarProduto);
    }

    private void chamaActivity() {
        Intent i = new Intent(CadastrarProdutoActivity.this, ListaProdutoActivity.class);
        startActivity(i);
        finish();
    }

    private void limparCampos() {
        edtNomeProduto.setText("");
        edtDescricaoProduto.setText("");
    }
}
