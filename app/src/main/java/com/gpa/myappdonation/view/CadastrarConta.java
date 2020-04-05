package com.gpa.myappdonation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Conta;

import java.util.UUID;

public class CadastrarConta extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private EditText edtNomeConta, edtNumeroConta, edtBancoConta, edtAgenciaConta, edtNumeroBancoConta;
    private Button btnSalvarConta;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_conta);

        edtAgenciaConta = (EditText) findViewById(R.id.edtAgenciaConta);
        edtNumeroConta = (EditText) findViewById(R.id.edtNumeroConta);
        edtBancoConta = (EditText) findViewById(R.id.edtBancoConta);
        edtNumeroBancoConta = (EditText) findViewById(R.id.edtNumeroBancoConta);
        edtNomeConta = (EditText) findViewById(R.id.edtNomeConta);
        btnSalvarConta = (Button) findViewById(R.id.btnSalvarConta);
        inicializarFirebase();
        btnSalvarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarConta();
            }
        });

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(CadastrarConta.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void salvarConta() {

        Conta conta = new Conta();
        conta.setUid(UUID.randomUUID().toString());
        conta.setNome(edtNomeConta.getText().toString());
        conta.setNumero_conta(edtNumeroConta.getText().toString());
        conta.setAgencia(edtAgenciaConta.getText().toString());
        conta.setBanco(edtBancoConta.getText().toString());
        conta.setNumeroBanco(edtNumeroBancoConta.getText().toString());
        databaseReference.child("Conta").child(conta.getUid()).setValue(conta);
        limparCampos();
        chamaActivity();
    }

    private void chamaActivity() {
        Intent i = new Intent(CadastrarConta.this, ListContaActivity.class);
        startActivity(i);
    }

    private void limparCampos() {

        edtAgenciaConta.setText("");
        edtNomeConta.setText("");
        edtBancoConta.setText("");
        edtNumeroBancoConta.setText("");
        edtNumeroConta.setText("");
    }
}
