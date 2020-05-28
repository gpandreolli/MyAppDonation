package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        int position;
        Intent i = getIntent();
        int pos =0;
        extras = getIntent().getExtras();
        position = i.getIntExtra("position",pos);

        
        if (extras!=null){
            recuperaConta(position);
        }

        btnSalvarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarConta();
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

    private void recuperaConta(final int position ) {

        databaseReference = ConfiguracaoFirebase.getFirebase().child("Conta").child(ConfiguracaoFirebase.getIdUsuario());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contas.size();
                int size = contas.size();
                contas.clear();

                for (DataSnapshot dsContas :dataSnapshot.getChildren()){
                    contas.add(dsContas.getValue(Conta.class));


                }
                Collections.reverse(contas);
                Conta conta = contas.get(position);
                String idConta = conta.getUid();
                setaConta(idConta);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    private void salvarConta() {

        Conta conta = new Conta();
        conta.setUid(UUID.randomUUID().toString());
        conta.setNome(edtNomeConta.getText().toString());
        conta.setNumero_conta(edtNumeroConta.getText().toString());
        conta.setAgencia(edtAgenciaConta.getText().toString());
        conta.setBanco(edtBancoConta.getText().toString());
        conta.setNumeroBanco(edtNumeroBancoConta.getText().toString());
        databaseReference.child("Conta").child(ConfiguracaoFirebase.getIdUsuario()).child(conta.getUid()).setValue(conta);
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


}
