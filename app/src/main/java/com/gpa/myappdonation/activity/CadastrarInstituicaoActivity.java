package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Address;
import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.Util;
import com.gpa.myappdonation.util.ZipCodeListener;
import com.google.firebase.*;
import com.santalu.maskedittext.MaskEditText;

import java.util.UUID;


public class CadastrarInstituicaoActivity extends AppCompatActivity {

    private EditText etZipCode, edtRazaoSocial, edtNomeFantasia, edtCnpj, et_city, et_street, et_number, et_complement;
    private MaskEditText edtFoneInst;
    private EditText et_neighbor, edtEmailCadInst;
    private Spinner sp_state;
    private Button btnSalvar, btnCancelarCadInst;
    private Util util;
    private String uidUsuario;
    private Bundle extras;
    private Toolbar toolbarCadInstituicao;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_instituicao);
        toolbarCadInstituicao = (Toolbar) findViewById(R.id.toolbarCadInstituicao);
        setSupportActionBar(toolbarCadInstituicao);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Dados Cadastrais");

       inicializarComponentes();
        inicializarFirebase();
        extras = getIntent().getExtras();


        Spinner spStates = (Spinner) findViewById(R.id.sp_state);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this,
                        R.array.states,
                        android.R.layout.simple_spinner_dropdown_item);
        spStates.setAdapter(adapter);

        util = new Util(this,
                R.id.et_street,
                R.id.et_complement,
                R.id.et_neighbor,
                R.id.et_city,
                R.id.sp_state,
                R.id.et_number);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SalvarInst();
            }
        });

        btnCancelarCadInst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (extras!=null){
            recuperaInstituicao();
        }
    }

    private void recuperaInstituicao() {
        databaseReference = ConfiguracaoFirebase.getFirebase().child("Instituicao").child(ConfiguracaoFirebase.getIdUsuario());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Instituicao instituicaoEdit = dataSnapshot.getValue(Instituicao.class);
                edtCnpj.setText(instituicaoEdit.getCnpj());
                edtEmailCadInst.setText(instituicaoEdit.getEmail());
                edtFoneInst.setText(instituicaoEdit.getTelefone());
                edtNomeFantasia.setText(instituicaoEdit.getNomeFantasia());
                edtRazaoSocial.setText(instituicaoEdit.getRazaoSocial());
                et_city.setText(instituicaoEdit.getCidade());
                et_complement.setText(instituicaoEdit.getComplemento());
                et_neighbor.setText(instituicaoEdit.getBairro());
                et_number.setText(instituicaoEdit.getNumero());
                etZipCode.setText(instituicaoEdit.getCep());
                et_street.setText(instituicaoEdit.getRua());
                //sp_state
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(CadastrarInstituicaoActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public String getUriZipCode() {

        return "https://viacep.com.br/ws/" + etZipCode.getText() + "/json/";

    }

    public void lockfields(boolean isToLock) {
        util.lockFields(isToLock);
    }

    public void setDataViews(Address address) {
        setFields(R.id.et_street, address.getLogradouro());
        //setFields(R.id.et_complement, address.getComplemento());
        setFields(R.id.et_neighbor, address.getBairro());
        setFields(R.id.et_city, address.getLocalidade());
        setSpinner(R.id.sp_state, R.array.states, address.getUf());


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

    private void SalvarInst() {

        Object posicao = sp_state.getSelectedItem();
        String itemSelecionado = posicao.toString();

        Instituicao inst = new Instituicao();
        inst.setUid(ConfiguracaoFirebase.getIdUsuario());
        inst.setRazaoSocial(edtRazaoSocial.getText().toString());
        inst.setNomeFantasia(edtNomeFantasia.getText().toString());
        inst.setCnpj(edtCnpj.getText().toString());
        inst.setRua(et_street.getText().toString());
        inst.setNumero(et_number.getText().toString());
        inst.setComplemento(et_complement.getText().toString());
        inst.setBairro(et_neighbor.getText().toString());
        inst.setCidade(et_city.getText().toString());
        inst.setCep(etZipCode.getText().toString());
        inst.setTelefone(edtFoneInst.getRawText());
        inst.setUf(itemSelecionado);
        inst.setEmail(edtEmailCadInst.getText().toString());
        inst.setSituacao("1");


        if (extras != null) {

            Intent it = getIntent();
            int numero = it.getIntExtra("info", 0);

            if (numero == 2) {
                //inst.setTipo_usua("2");
                databaseReference.setValue(inst);
                //limparCampos();
                chamaActivity(2);
            }
        } else {
            databaseReference.child("Instituicao").child(ConfiguracaoFirebase.getIdUsuario()).setValue(inst);
            limparCampos();
            chamaActivity(1);
        }


       /* databaseReference.child("Instituicao").child(ConfiguracaoFirebase.getIdUsuario()).setValue(inst);

        limparCampos();
        finish();
        chamaActivity();*/


    }

    private void chamaActivity(Integer local) {

        if (local == 1) {

            Intent i = new Intent(CadastrarInstituicaoActivity.this, IncialActivityInsti.class);
            startActivity(i);
        } else if (local == 2) {

            finish();
        }
    }

    private void limparCampos() {
        edtRazaoSocial.setText("");
        edtNomeFantasia.setText("");
        edtCnpj.setText("");
        et_street.setText("");
        et_number.setText("");
        et_complement.setText("");
        et_neighbor.setText("");
        et_city.setText("");
        etZipCode.setText("");
        edtFoneInst.setText("");
        edtEmailCadInst.setText("");
    }

    private void inicializarComponentes(){
        etZipCode = (EditText) findViewById(R.id.et_zip_code);
        etZipCode.addTextChangedListener(new ZipCodeListener(this));
        edtCnpj = (EditText) findViewById(R.id.edtCnpj);
        edtRazaoSocial = (EditText) findViewById(R.id.edtRazaoSocial);
        edtNomeFantasia = (EditText) findViewById(R.id.edtNomeFantasia);
        edtEmailCadInst = (EditText) findViewById(R.id.edtEmailCadInst);
        edtFoneInst = (MaskEditText) findViewById(R.id.edtFoneInst);
        et_city = (EditText) findViewById(R.id.et_city);
        et_street = (EditText) findViewById(R.id.et_street);
        et_number = (EditText) findViewById(R.id.et_number);
        et_neighbor = (EditText) findViewById(R.id.et_neighbor);
        et_complement = (EditText) findViewById(R.id.et_complement);
        sp_state = (Spinner) findViewById(R.id.sp_state);
        btnSalvar = (Button) findViewById(R.id.btnSalvarInst);
        btnCancelarCadInst = (Button) findViewById(R.id.btnCancelarCadInst);
    }




}

