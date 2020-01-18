package com.gpa.myappdonation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gpa.myappdonation.R;


public class MainActivity extends AppCompatActivity {


    private Button btnApoiador;
    private Button btnInstituicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnApoiador = (Button)findViewById(R.id.btnApoiador);
        btnInstituicao = (Button)findViewById(R.id.btnInstituicao);

        btnInstituicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnInstituicaoOnclick();
            }
        });

        btnApoiador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnApoiadorOnclick();
            }


        });

    }




    private void btnInstituicaoOnclick() {
        Intent i = new Intent(this, CadastrarInstituicaoActivity.class);
        startActivity(i);

    }

    private void btnApoiadorOnclick() {
    }
}
