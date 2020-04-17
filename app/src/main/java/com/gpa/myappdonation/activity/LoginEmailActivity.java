package com.gpa.myappdonation.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.gpa.myappdonation.R;

public class LoginEmailActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail, edtSenha;
    private Button btnLogar;
    private TextView btnRecSenha;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

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

                recuperarSenha();
                break;

        }

    }

    private void recuperarSenha(){
        auth.sendPasswordResetEmail("email@email.com").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getBaseContext(),"Enviamos um email com o link para redifinição, verifique sua caixa de entrada!",Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                String erro = e.toString();

            }
        });
    }
}
