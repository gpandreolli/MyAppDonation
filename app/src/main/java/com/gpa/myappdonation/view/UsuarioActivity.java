package com.gpa.myappdonation.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Address;
import com.gpa.myappdonation.model.Usuario;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.Util;
import com.google.firebase.database.DatabaseReference;
import com.santalu.maskedittext.MaskEditText;



public class UsuarioActivity extends AppCompatActivity {

    private EditText edtNomeUsua,edtCPFUsua, getEdtCpfUsua, edtCepUsua, edtCidadeUsua,edtRuaUsua,edtNumeroUsua,edtBairroUsua ;
    private EditText edtComplementoUsua;
    private MaskEditText edtFoneUsua;
    private Spinner spEstadoUsua;
    private Button bntSalvarUsua, btnCancelaUsua;
    private Util util;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private Bundle extras;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Toolbar toolbarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        toolbarUsuario = (Toolbar) findViewById(R.id.toolbarUsuario);
        setSupportActionBar(toolbarUsuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Dados Cadastrais");

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
        btnCancelaUsua = (Button) findViewById(R.id.btnCancelarUsua);
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

        btnCancelaUsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        extras = getIntent().getExtras();


        if (extras!=null){
            recuperUsuario();
        }
    }

    private void recuperUsuario() {
        databaseReference = ConfiguracaoFirebase.getFirebase().child("Usuario").child(ConfiguracaoFirebase.getIdUsuario());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Usuario usuarioEdit = dataSnapshot.getValue(Usuario.class);

                edtBairroUsua.setText(usuarioEdit.getBairro_usua());
                edtCidadeUsua.setText(usuarioEdit.getCidade_usua());
                edtCepUsua.setText(usuarioEdit.getCep_usua());
                edtComplementoUsua.setText(usuarioEdit.getComplemento_usua());
                edtCPFUsua.setText(usuarioEdit.getCpf_usua());
                edtFoneUsua.setText(usuarioEdit.getFone_usua());
                edtNomeUsua.setText(usuarioEdit.getNome_usua());
                edtRuaUsua.setText(usuarioEdit.getRua_usua());
                edtNumeroUsua.setText(usuarioEdit.getNumero_usua());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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

        if(extras != null){

            Intent it = getIntent();
           // int i =0;
            int numero =  it.getIntExtra("info", 0);

            if (numero == 2 ){
                usua.setTipo_usua("2");
                databaseReference.setValue(usua);
                limparCampos();
                chamaActivity(2);
            }
        }else {
            databaseReference.child("Usuario").child(ConfiguracaoFirebase.getIdUsuario()).setValue(usua);
            limparCampos();
            chamaActivity(1);
        }

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

    private void chamaActivity(Integer local) {

        if (local == 2){
            finish();
        }else if (local ==1){
            Intent i = new Intent(UsuarioActivity.this,MainActivity.class);
            startActivity(i);
        }
    }


}
