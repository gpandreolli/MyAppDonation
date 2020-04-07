package com.gpa.myappdonation.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.graphics.Color;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gpa.myappdonation.R;


public class Cadastrar extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmailInst,edtSenhaInst;
    private Button btnSalvarCadastro,btnCancelarCadastro;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        edtEmailInst = (EditText)findViewById(R.id.edtEmailInst);
        edtSenhaInst = (EditText)findViewById(R.id.edtSenhaInst);
        mAuth = FirebaseAuth.getInstance();

        btnSalvarCadastro = (Button)findViewById(R.id.btnSalvarCadastro);

        btnSalvarCadastro.setOnClickListener(this);



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            Toast.makeText(getBaseContext(),"Sem Usuario",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getBaseContext(),currentUser.getEmail(),Toast.LENGTH_LONG).show();
        }
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnSalvarCadastro:
                cadastrarUsua();
                break;
        }

    }

    private void cadastrarUsua() {


         String email = edtEmailInst.getText().toString().trim();
         String senha = edtSenhaInst.getText().toString().trim();



         if (email.isEmpty() || senha.isEmpty() ){

             edtEmailInst.setBackgroundColor(Color.parseColor("#000eede2"));
             edtEmailInst.requestFocus();
             edtSenhaInst.setBackgroundColor(Color.parseColor("#000eede2"));
             edtSenhaInst.requestFocus();
             Toast.makeText(getBaseContext(),"Favor preencher os campos corretamente",Toast.LENGTH_LONG).show();
             return;
         }else if (senha.toString().length() < 6 ){

             edtSenhaInst.setBackgroundColor(Color.parseColor("#000eede2"));
             edtSenhaInst.requestFocus();
             Toast.makeText(getBaseContext(),"Senha com mínimo de 6 caracteres",Toast.LENGTH_LONG).show();

         }else{
             if(verificarInternet()){
                criarUsuario(email,senha);
             }else {
                 Toast.makeText(getBaseContext(),"Verifique conexão do seu aparelho com a intenet",Toast.LENGTH_LONG).show();
             }
         }

    }

    private void criarUsuario(String email, String senha) {

        mAuth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful() == true){
                    Toast.makeText(getBaseContext(),"Cadastro realizado com susesso",Toast.LENGTH_LONG).show();
                    chamaActivity();

                }else{
                    String resposta = task.getException().toString();
                    opcoesErro(resposta);

                }
            }
        });


    }

    private void opcoesErro(String resposta) {

        if (resposta.contains("address is badly") ){
            Toast.makeText(getBaseContext(),"Email invalido",Toast.LENGTH_LONG).show();
        }else if(resposta.contains("interrupted connection")) {
            Toast.makeText(getBaseContext(),"Sem conexão com banco de dados",Toast.LENGTH_LONG).show();
        }        else if(resposta.contains("is not available on this device")) {
            Toast.makeText(getBaseContext(),"Não é possivel se conectar ao banco de dados atraves desse aparelho",Toast.LENGTH_LONG).show();
        }

    }

    private void chamaActivity() {
        Intent i = new Intent(this, UsuarioActivity.class);
        startActivity(i);
    }

    private boolean verificarInternet(){

        ConnectivityManager conexeao = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo informacao = conexeao.getActiveNetworkInfo();

        if (informacao != null && informacao.isConnected()){
            return true;
        }
        return false;
    }




}
