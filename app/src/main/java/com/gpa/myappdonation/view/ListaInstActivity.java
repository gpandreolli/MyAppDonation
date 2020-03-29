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
import com.gpa.myappdonation.adapters.ListaAdapterInstituicao;
import com.gpa.myappdonation.model.Instituicao;

import java.util.ArrayList;

public class ListaInstActivity extends AppCompatActivity {

    private ListView listaInstituicao;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Instituicao");
    ArrayList<Instituicao> instituicoes = new ArrayList<Instituicao>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_inst);

        listaInstituicao = (ListView) findViewById(R.id.listaInstituicao);
        instituicoes.clear();

        ref.addValueEventListener(new ValueEventListener(){

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                int i = 0;

                String nomeInstituicao;
                String cidadeInstituicao;
                String ufInstituicao;

                for (DataSnapshot contaSnapshot : dataSnapshot.getChildren()) {

                    Instituicao inst = contaSnapshot.getValue(Instituicao.class);

                    nomeInstituicao = inst.getNomeFantasia();
                    cidadeInstituicao = inst.getCidade();
                    ufInstituicao = inst.getUf();
                    Instituicao dadosInstituicao = new Instituicao(nomeInstituicao,cidadeInstituicao,ufInstituicao);

                    dadosInstituicao.setNomeFantasia(nomeInstituicao);
                    dadosInstituicao.setCidade(cidadeInstituicao);
                    dadosInstituicao.setUf(ufInstituicao);
                    instituicoes.add(dadosInstituicao);

                }


                ListaAdapterInstituicao adapter = new ListaAdapterInstituicao(ListaInstActivity.this,
                        R.layout.listview_inst_linha, instituicoes);

                View header = (View) getLayoutInflater().inflate(R.layout.listview_inst_linha, null);

                listaInstituicao.addHeaderView(header);
                listaInstituicao.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Erro ao ler Instituições: " + databaseError.getCode());
            }


        });



    }
}
