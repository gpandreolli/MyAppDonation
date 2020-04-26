package com.gpa.myappdonation.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gpa.myappdonation.R;
import com.gpa.myappdonation.fragment.InstituicaoAprovadaFragment;
import com.gpa.myappdonation.fragment.InstituicaoNaoAvaliadaFragment;
import com.gpa.myappdonation.fragment.InstituicaoReprovadaFragment;

public class AdministradorActivity extends AppCompatActivity {

    private Button btnInstNaoAvalidas, btnInstAprovadas,btnInstReprovadas;
    private InstituicaoAprovadaFragment aprovadaFragment;
    private InstituicaoReprovadaFragment reprovadaFragment;
    private InstituicaoNaoAvaliadaFragment naoAvaliadaFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        inicializarComponentes();

        btnInstNaoAvalidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             naoAvaliadaFragment = new InstituicaoNaoAvaliadaFragment();
             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
             transaction.replace(R.id.frame_inst_aprovada,naoAvaliadaFragment);
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
                transaction.commit();
                ocultaBotoes();
            }
        });

        btnInstReprovadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reprovadaFragment = new InstituicaoReprovadaFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
           //this.finish();
            getFragmentManager().popBackStack();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
