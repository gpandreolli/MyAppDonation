package com.gpa.myappdonation.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gpa.myappdonation.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnCadastrar ,btnLogar;
    private EditText edtEmailLogin, edtSenhaLogin;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        btnCadastrar = (Button)findViewById(R.id.btnCadastrar);
        btnLogar = (Button)findViewById(R.id.btnLogar);
        edtEmailLogin = (EditText) findViewById(R.id.edtEmailLogin);
        edtSenhaLogin = (EditText) findViewById(R.id.edtSLogin);

        btnCadastrar.setOnClickListener(this);
        btnLogar.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCadastrar:
                startActivity(new Intent(this, Cadastrar.class));
                break;

            case R.id.btnLogar:
                //executar login
                loginEmail();
                break;
        }

    }

    private void loginEmail(){

        String email = edtEmailLogin.getText().toString().trim();
        String senha = edtSenhaLogin.getText().toString().trim();

        if (email.isEmpty() || senha.isEmpty() ){
            Toast.makeText(getBaseContext(), "Favor preencher os campos de email e senha", Toast.LENGTH_LONG).show();
        }else{

            confirmaLoginEmail(email, senha);
        }
    }

    private void confirmaLoginEmail(String email,String senha){

        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    //fazer a verificação do tipo de usuario para encaminhar ele para a tela correta
                    Intent i = new Intent(LoginActivity.this, IncialActivityInsti.class);
                    startActivity(i);
                    Toast.makeText(getBaseContext(), "Usuário logado com sucesso", Toast.LENGTH_LONG).show();
                    finish();

                }else{

                    Toast.makeText(getBaseContext(), "Falha ao realizar login", Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}
