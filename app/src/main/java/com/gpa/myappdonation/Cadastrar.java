package com.gpa.myappdonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Cadastrar extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmailInst,edtSenhaInst;
    private Button btnSalvarCadastro,btnCancelarCadastro;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        edtEmailInst = (EditText)findViewById(R.id.edtEmailInst);
        edtSenhaInst = (EditText)findViewById(R.id.edtSenhaInst);

        btnSalvarCadastro = (Button)findViewById(R.id.btnSalvarCadastro);

        btnSalvarCadastro.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSalvarCadastro:

                cadastrarInst();

                break;
        }

    }

    private void cadastrarInst() {

            auth.createUserWithEmailAndPassword("tste@teste", "1213434").addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    boolean resposta = task.isSuccessful();
                    if (resposta == true){
                        Toast.makeText(getBaseContext(),"Cadastro realizado com susesso",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(getBaseContext(),"Erro ao cadastrar",Toast.LENGTH_LONG).show();

                    }
                }
            });

    }
}
