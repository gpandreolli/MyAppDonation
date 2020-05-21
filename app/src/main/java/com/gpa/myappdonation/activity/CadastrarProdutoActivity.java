package com.gpa.myappdonation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Produto;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

import java.util.UUID;

public class CadastrarProdutoActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private EditText edtNomeProduto, edtDescricaoProduto;
    private Button btnSalvarProduto, btnCancelarProduto;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);
        inicializarComponentes();
        btnSalvarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarProduto();
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

}
