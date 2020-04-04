package com.gpa.myappdonation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Address;
import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.model.Usuario;
import com.gpa.myappdonation.util.Util;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.santalu.maskedittext.MaskEditText;

import java.util.UUID;

public class UsuarioActivity extends AppCompatActivity {

    private EditText edtNomeUsua,edtCPFUsua, getEdtCpfUsua, edtCepUsua, edtCidadeUsua,edtRuaUsua,edtNumeroUsua,edtBairroUsua ;
    private EditText edtComplementoUsua;
    private MaskEditText edtFoneUsua;
    private Spinner spEstadoUsua;
    private Button bntSalvarUsua;
    private Util util;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);


        edtNomeUsua = (EditText) findViewById(R.id.edtNomeUsua);
        edtCPFUsua = (EditText) findViewById(R.id.edtCpfUsua);
        edtCepUsua = (EditText) findViewById(R.id.edtCepUsua);
        edtCidadeUsua =(EditText) findViewById(R.id.edtCidadeUsua);
        edtBairroUsua = (EditText)findViewById(R.id.edtBairroUsua);
        edtRuaUsua = (EditText) findViewById(R.id.edtRuaUsua);
        edtNumeroUsua = (EditText) findViewById(R.id.edtNumeroUsua);
        edtComplementoUsua = (EditText) findViewById(R.id.edtComplementoUsua);
        edtFoneUsua = (MaskEditText) findViewById(R.id.edtFoneUsua);
        spEstadoUsua = (Spinner) findViewById(R.id.spEstadoUsua);
        bntSalvarUsua = (Button) findViewById(R.id.btnSalvarUsua);
        mAuth = FirebaseAuth.getInstance();


        inicializarFirebase();
        FirebaseUser currentUser = mAuth.getCurrentUser();


        Spinner spStates = (Spinner) findViewById(R.id.spEstadoUsua);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this,
                        R.array.states,
                        android.R.layout.simple_spinner_item);
        spStates.setAdapter(adapter);

        util = new Util(this,
                R.id.edtBairroUsua,
                R.id.edtCidadeUsua,
                R.id.spEstadoUsua
                );

        bntSalvarUsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarUsua();
            }
        });




    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(UsuarioActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    public String getUriZipCode() {

        return "https://viacep.com.br/ws/" + edtCepUsua.getText() + "/json/";

    }

    public void lockfields(boolean isToLock) {
        util.lockFields(isToLock);
    }

    public void setDataViews(Address address) {
        setFields(R.id.edtRuaUsua, address.getLogradouro());
        setFields(R.id.edtBairroUsua, address.getLocalidade());
        setFields(R.id.edtCidadeUsua, address.getBairro());
        setSpinner(R.id.spEstadoUsua, R.array.states, address.getUf());


    }

    private void setFields(int id, String data) {
        ((EditText) findViewById(id)).setText(data);

    }

    private void setSpinner(int id, int arrayId, String data) {

        String[] itens = getResources().getStringArray(R.array.states);

        for (int i = 0; i < itens.length; i++) {

            if (itens[i].endsWith("(" + data + ")")) {
                ((Spinner) findViewById(id)).setSelection(i);
                return;
            }

        }
        ((Spinner) findViewById(id)).setSelection(0);

    }




    private void SalvarUsua() {

        Object posicao = spEstadoUsua.getSelectedItem();
        String itemSelecionado = posicao.toString();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        Usuario usua = new Usuario();
        usua.setUid(currentUser.getUid());
        usua.setNome_usua(edtNomeUsua.getText().toString());
        usua.setCpf_usua(edtCPFUsua.getText().toString());
        usua.setRua_usua(edtRuaUsua.getText().toString());
        usua.setNumero_usua(edtNumeroUsua.getText().toString());
        usua.setComplemento_usua(edtComplementoUsua.getText().toString());
        usua.setBairro_usua(edtBairroUsua.getText().toString());
        usua.setCidade_usua(edtCidadeUsua.getText().toString());
        usua.setCep_usua(edtCepUsua.getText().toString());
        usua.setFone_usua(edtFoneUsua.getRawText());
        usua.setUf_usua(itemSelecionado);




        databaseReference.child("Usuario").child(usua.getUid()).setValue(usua);

        limparCampos();
        chamaActivity();
    }

    private void limparCampos(){
        edtNomeUsua.setText("");
        edtCPFUsua.setText("");
        edtRuaUsua.setText("");
        edtNumeroUsua.setText("");
        edtComplementoUsua.setText("");
        edtBairroUsua.setText("");
        edtCidadeUsua.setText("");
        edtCepUsua.setText("");
        edtFoneUsua.setText("");

    }

    private void chamaActivity() {
        Intent i = new Intent(UsuarioActivity.this,MainActivity.class);
        startActivity(i);
    }


}
