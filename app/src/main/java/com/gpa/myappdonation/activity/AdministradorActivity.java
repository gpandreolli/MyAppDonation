package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.fragment.InstituicaoAprovadaFragment;
import com.gpa.myappdonation.fragment.InstituicaoNaoAvaliadaFragment;
import com.gpa.myappdonation.fragment.InstituicaoReprovadaFragment;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

public class AdministradorActivity extends AppCompatActivity {

    private Button btnInstNaoAvalidas, btnInstAprovadas,btnInstReprovadas;
    private InstituicaoAprovadaFragment aprovadaFragment;
    private InstituicaoReprovadaFragment reprovadaFragment;
    private InstituicaoNaoAvaliadaFragment naoAvaliadaFragment;
    private TextView txtNomeAdministrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        inicializarComponentes();
        setNomeUsuario();

        btnInstNaoAvalidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             naoAvaliadaFragment = new InstituicaoNaoAvaliadaFragment();
             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
             transaction.replace(R.id.frame_inst_aprovada,naoAvaliadaFragment);
                transaction.addToBackStack(null);
             transaction.commit();
             ocultaBotoes();


            }
        });

        btnInstAprovadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aprovadaFragment = new InstituicaoAprovadaFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_inst_aprovada,aprovadaFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                ocultaBotoes();
            }
        });

        btnInstReprovadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reprovadaFragment = new InstituicaoReprovadaFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.frame_inst_aprovada,reprovadaFragment);
                transaction.commit();
                ocultaBotoes();
            }
        });
    }

    private void ocultaBotoes() {
        btnInstNaoAvalidas.setVisibility(View.INVISIBLE);
        btnInstAprovadas.setVisibility(View.INVISIBLE);
        btnInstReprovadas.setVisibility(View.INVISIBLE);
    }

    private void inicializarComponentes() {
        btnInstAprovadas = (Button) findViewById(R.id.btnInstAprovadas);
        btnInstNaoAvalidas = (Button) findViewById(R.id.btnInstNaoAvalidas);
        btnInstReprovadas = (Button) findViewById(R.id.btnInstReprovadas);
        txtNomeAdministrador = (TextView) findViewById(R.id.txtNomeAdministrador);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            onPause();
            this.finish();

        } else {
            getSupportFragmentManager().popBackStack();
            btnInstNaoAvalidas.setVisibility(View.VISIBLE);
            btnInstAprovadas.setVisibility(View.VISIBLE);
            btnInstReprovadas.setVisibility(View.VISIBLE);
        }
    }

    private void setNomeUsuario() {

        DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("Usuario").child(ConfiguracaoFirebase.getIdUsuario()).child("nome_usua");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nome = dataSnapshot.getValue().toString();
                txtNomeAdministrador.setText(nome);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
