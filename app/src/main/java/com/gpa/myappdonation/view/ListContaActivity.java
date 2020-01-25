package com.gpa.myappdonation.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.controller.ContaAdapter;
import com.gpa.myappdonation.model.Conta;

public class ListContaActivity extends AppCompatActivity {

    private ListView lisConta;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("conta");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_conta);

       // Conta contar_data[] = new Conta[];

        lisConta = (ListView) findViewById(R.id.listConta);

        ref.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Conta conta = dataSnapshot.getValue(Conta.class);

                ContaAdapter adapter = new ContaAdapter(this,
                        R.layout.listview_conta_linha, conta);

                View header = (View)getLayoutInflater().inflate(R.layout.listview_conta_linha, null);
                lisConta.addHeaderView(header);

                lisConta.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Erro ao ler contas: " + databaseError.getCode());
            }
        });






    }
}
