package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Conta;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class CadastrarConta extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private EditText edtNomeConta, edtNumeroConta, edtBancoConta, edtAgenciaConta, edtNumeroBancoConta;
    private Button btnSalvarConta, btnCancelarConta;
    private Bundle extras;
    private List<Conta> contas = new ArrayList<>();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference,contaEditReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_conta);
        inicilizarComponentes();
        inicializarFirebase();

        Toolbar toolbar = findViewById(R.id.toolbarCadastraConta);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Contas");

        Intent i = getIntent();
        extras = getIntent().getExtras();
        final String uidConta = i.getStringExtra("uid");

        
        if (extras!=null){
            setaConta(uidConta);
        }

        btnSalvarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPreenchimento()) {
                    salvarConta(uidConta);
                }
            }
        });
        btnCancelarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void inicilizarComponentes() {
        edtAgenciaConta = (EditText) findViewById(R.id.edtAgenciaConta);
        edtNumeroConta = (EditText) findViewById(R.id.edtNumeroConta);
        edtBancoConta = (EditText) findViewById(R.id.edtBancoConta);
        edtNumeroBancoConta = (EditText) findViewById(R.id.edtNumeroBancoConta);
        edtNomeConta = (EditText) findViewById(R.id.edtNomeConta);
        btnSalvarConta = (Button) findViewById(R.id.btnSalvarConta);
        btnCancelarConta = (Button) findViewById(R.id.btnCancelarConta);
    }


    private void setaConta(String idConta) {
        contaEditReference = ConfiguracaoFirebase.getFirebase().child("Conta").child(ConfiguracaoFirebase.getIdUsuario()).child(idConta);

        contaEditReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Conta contaEdit = dataSnapshot.getValue(Conta.class);

                edtNomeConta.setText(contaEdit.getNome().toString());
                edtNumeroConta.setText(contaEdit.getNumero_conta().toString());
                edtBancoConta.setText(contaEdit.getBanco().toString());
                edtAgenciaConta.setText(contaEdit.getAgencia().toString());
                edtNumeroBancoConta.setText(contaEdit.getNumeroBanco().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(CadastrarConta.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    private void salvarConta(String uidConta) {

        Conta conta = new Conta();
        conta.setNome(edtNomeConta.getText().toString());
        conta.setNumero_conta(edtNumeroConta.getText().toString());
        conta.setAgencia(edtAgenciaConta.getText().toString());
        conta.setBanco(edtBancoConta.getText().toString());
        conta.setNumeroBanco(edtNumeroBancoConta.getText().toString());

        if (extras !=null){
            conta.setUid(uidConta);
            databaseReference.child("Conta").child(ConfiguracaoFirebase
                    .getIdUsuario()).child(uidConta).setValue(conta);

        }else{
            conta.setUid(UUID.randomUUID().toString());
            databaseReference.child("Conta").child(ConfiguracaoFirebase
                    .getIdUsuario()).child(conta.getUid()).setValue(conta);
        }


        limparCampos();
        chamaActivity();
    }

    private void chamaActivity() {
        Intent i = new Intent(CadastrarConta.this, ListaContaActivity.class);
        startActivity(i);
    }

    private void limparCampos() {

        edtAgenciaConta.setText("");
        edtNomeConta.setText("");
        edtBancoConta.setText("");
        edtNumeroBancoConta.setText("");
        edtNumeroConta.setText("");
    }

    public boolean verificarPreenchimento() {

        if (!edtNomeConta.getText().toString().equals("")) {
            if (!edtNumeroBancoConta.getText().toString().equals("")) {
                if (!edtAgenciaConta.getText().toString().equals("")) {
                    if (!edtBancoConta.getText().toString().equals("")) {
                        if (!edtNumeroConta.getText().toString().equals("")) {
                            return true;
                        } else {
                            exibirMensagem("Ops! favor preenhcer a número da conta");
                            edtNumeroConta.setHintTextColor(Color.RED);
                            edtNumeroConta.requestFocus();
                            return false;

                        }
                    } else {
                        exibirMensagem("Ops! favor preenhcer o Banco da conta");
                        edtBancoConta.setHintTextColor(Color.RED);
                        edtBancoConta.requestFocus();
                        return false;
                    }
                } else {
                    exibirMensagem("Ops! favor preenhcer a agência da conta");
                    edtAgenciaConta.setHintTextColor(Color.RED);
                    edtAgenciaConta.requestFocus();
                    return false;
                }
            } else {
                exibirMensagem("Ops! favor preenhcer o número do Banco");
                edtNumeroBancoConta.setHintTextColor(Color.RED);
                edtNumeroBancoConta.requestFocus();
                return false;
            }
        } else {
            exibirMensagem("Ops! favor preenhcer o nome da Conta");
            edtNomeConta.setHintTextColor(Color.RED);
            edtNomeConta.requestFocus();
            return false;
        }
        //return false;
    }

    public void exibirMensagem(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }




}
