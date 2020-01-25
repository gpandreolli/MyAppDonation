package com.gpa.myappdonation.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.controller.ListaAdapterConta;
import com.gpa.myappdonation.model.Conta;

import java.util.ArrayList;

public class ListContaActivity extends AppCompatActivity {

    private ListView lisConta;
    private Button btnNovaConta;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("conta");
    ArrayList<Conta> contas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_conta);

       // Conta contar_data[] = new Conta[];

        lisConta = (ListView) findViewById(R.id.listConta);

        ref.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                contas.clear();

               Conta dadosConta = new Conta();

                dadosConta.setNome(dataSnapshot.getValue(Conta.class).getNome());
                dadosConta.setNumero_conta(dataSnapshot.getValue(Conta.class).getNumero_conta());

                contas.add(dadosConta);


                ListaAdapterConta adapter = new ListaAdapterConta(ListContaActivity.this,
                        R.layout.listview_conta_linha, contas);

                View header = (View)getLayoutInflater().inflate(R.layout.listview_conta_linha, null);
                lisConta.addHeaderView(header);

                lisConta.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Erro ao ler contas: " + databaseError.getCode());
            }
        });

        btnNovaConta = (Button) findViewById(R.id.btnNovaConta);

        btnNovaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaActivity();
            }
        });
    }

    private void chamaActivity() {
        Intent i = new Intent(ListContaActivity.this, CadastrarConta.class);
        startActivity(i);
    }
}
