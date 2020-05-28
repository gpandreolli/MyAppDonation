package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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



        int position;
        Intent i = getIntent();
        int pos =0;
        extras = getIntent().getExtras();
        position = i.getIntExtra("position",pos);
        if (extras!=null){
            recuperaProdutos(position);
        }



        btnSalvarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarProduto();
            }
        });

        btnCancelarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void recuperaProdutos(final int position) {
        databaseReference = ConfiguracaoFirebase.getFirebase().child("Produto");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtos.clear();

                for (DataSnapshot dsProdutos :dataSnapshot.getChildren()){
                    produtos.add(dsProdutos.getValue(Produto.class));


                }
                Collections.reverse(produtos);
                Produto produto = produtos.get(position);
                String idProduto = produto.getUid();
                setaProduto(idProduto);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

    private void salvarProduto() {
        Produto produto = new Produto();
        produto.setUid(UUID.randomUUID().toString());
        produto.setNome(edtNomeProduto.getText().toString());
        produto.setDescricao(edtDescricaoProduto.getText().toString());

        databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("Produto").child(produto.getUid()).setValue(produto);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getBaseContext(), "Produto destruido", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getBaseContext(), "Produto pausado", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getBaseContext(), "Produto stopado", Toast.LENGTH_LONG).show();
    }
}
