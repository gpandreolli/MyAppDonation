package com.gpa.myappdonation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Usuario;


public class MainActivity extends AppCompatActivity {


    private Button btnApoiador;
    private Button btnInstituicao;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        inicializarFirebase();
        FirebaseUser currentUser = mAuth.getCurrentUser();

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

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String user = currentUser.getUid();
        String usernome = currentUser.getEmail();
        Usuario usua = new Usuario();

        usua.setTipo_usua("1");


        databaseReference.child("Usuario").child(user).child("tipo_usua").setValue("1");

        Intent i = new Intent(this, CadastrarInstituicaoActivity.class);
        startActivity(i);

    }

    private void btnApoiadorOnclick() {
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }
}
