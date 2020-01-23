package com.gpa.myappdonation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Address;
import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.util.Util;
import com.gpa.myappdonation.util.ZipCodeListener;
import com.google.firebase.*;
import com.gpa.myappdonation.view.IncialActivityInsti;

import java.util.UUID;


public class CadastrarInstituicaoActivity extends AppCompatActivity {

    private EditText etZipCode, edtRazaoSocial, edtNomeFantasia,edtCnpj,et_city,et_street,et_number, et_complement;
    private EditText et_neighbor, edtEmailCadInst,edtFoneInst;
    private Spinner sp_state;
    private Button btnSalvar;
    private Util util;


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_instituicao);

        etZipCode = (EditText) findViewById(R.id.et_zip_code);
        etZipCode.addTextChangedListener(new ZipCodeListener(this));
        edtCnpj = (EditText) findViewById(R.id.edtCnpj);
        edtRazaoSocial = (EditText) findViewById(R.id.edtRazaoSocial);
        edtNomeFantasia = (EditText) findViewById(R.id.edtNomeFantasia);
        edtEmailCadInst = (EditText) findViewById(R.id.edtEmailCadInst);
        edtFoneInst = (EditText) findViewById(R.id.edtFoneInst);
        et_city =  (EditText) findViewById(R.id.et_city);
        et_street = (EditText) findViewById(R.id.et_street);
        et_number = (EditText) findViewById(R.id.et_number);
        et_neighbor =  (EditText) findViewById(R.id.et_neighbor);
        et_complement = (EditText) findViewById(R.id.et_complement);
        sp_state = (Spinner) findViewById(R.id.sp_state);
        btnSalvar = (Button) findViewById(R.id.btnSalvarInst);



        inicializarFirebase();


        Spinner spStates = (Spinner) findViewById(R.id.sp_state);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this,
                        R.array.states,
                        android.R.layout.simple_spinner_item);
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
        setFields(R.id.et_complement, address.getComplemento());
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

    private void SalvarInst(){

        Object posicao = sp_state.getSelectedItem();
        String itemSelecionado = posicao.toString();

        Instituicao inst = new Instituicao();
        inst.setUid(UUID.randomUUID().toString());
        inst.setRazaoSocial(edtRazaoSocial.getText().toString());
        inst.setNomeFantasia(edtNomeFantasia.getText().toString());
        inst.setCnpj(edtCnpj.getText().toString());
        inst.setRua(et_street.getText().toString());
        inst.setNumero(et_number.getText().toString());
        inst.setComplemento(et_complement.getText().toString());
        inst.setBairro(et_neighbor.getText().toString());
        inst.setCidade(et_city.getText().toString());
        inst.setCep(etZipCode.getText().toString());
        inst.setTelefone(edtFoneInst.getText().toString());
        inst.setUf(itemSelecionado);
        inst.setEmail(edtEmailCadInst.getText().toString());



        databaseReference.child("").child(inst.getUid()).setValue(inst);

            limparCampos();
            chamaActivity();



    }

    private void chamaActivity() {
        Intent i = new Intent(CadastrarInstituicaoActivity.this,IncialActivityInsti.class);
        startActivity(i);
    }

    private void limparCampos(){
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

}
