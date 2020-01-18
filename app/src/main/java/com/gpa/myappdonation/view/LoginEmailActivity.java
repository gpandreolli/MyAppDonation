package com.gpa.myappdonation.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gpa.myappdonation.R;

public class LoginEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail, edtSenha;
    private Button btnLogar, btnRecSenha;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        btnLogar = (Button)findViewById(R.id.btnLogar);
        btnRecSenha = (Button) findViewById(R.id.btnRecSenha);

        btnLogar.setOnClickListener(this);
        btnRecSenha.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnLogar:

                break;
            case R.id.btnRecSenha:

                break;

        }

    }
}
