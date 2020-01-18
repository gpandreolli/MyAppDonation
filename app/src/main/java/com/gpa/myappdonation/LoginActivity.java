package com.gpa.myappdonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCadastrar ,btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        btnLogar = (Button)findViewById(R.id.btnLogar);

        btnCadastrar.setOnClickListener(this);
        btnLogar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCadastrar:
                startActivity(new Intent(this, Cadastrar.class));
                break;

            case R.id.btnLogar:
                //executar login
                break;
        }

    }
}
