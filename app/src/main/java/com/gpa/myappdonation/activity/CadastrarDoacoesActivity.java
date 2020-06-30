package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Doacao;
import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class CadastrarDoacoesActivity extends AppCompatActivity {

    private CurrencyEditText edtValorDoacao;
    private TextView txtDataDoacao;
    Spinner spMyInst;
    private List<Instituicao> instituicoes = new ArrayList<>();
    private ArrayList<String> spinnerInstituicaoLista;
    Calendar mDataAtual;
    int dia, mes, ano;
    private Button btnSalvarDoacao,btnCancelarDoacao;
    DatabaseReference doacaoRef;
    DatabaseReference instituicaoRef;
    private Switch aSwitchProdutos;
    private String produtos;
    private Bundle extras;
    private DatabaseReference doacaoEditReference,doacaoEditandoReference;
    private Query qryInstituicaoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_doacoes);

        Toolbar toolbar = findViewById(R.id.toolbarCadastraDoacao);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Cadastrar Doação");

        inicializarComponentes();
        carregarDadosSpinner();
        Intent i = getIntent();
        extras = getIntent().getExtras();
        final String uidDoacao = i.getStringExtra("uid");
        if (extras!=null){
            carregaDoacoes(uidDoacao);
        }

        dia = mDataAtual.get(Calendar.DAY_OF_MONTH)+1;
        mes = mDataAtual.get(Calendar.MONTH)+1;
        ano = mDataAtual.get(Calendar.YEAR);


        txtDataDoacao.setText(dia + "/" + mes + "/" + ano);
        txtDataDoacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CadastrarDoacoesActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                        mes = mes + 1;
                        txtDataDoacao.setText(dia + "/" + mes + "/" + ano);
                    }
                }, ano, mes-1, dia);
                datePickerDialog.show();
            }
        });
        
        btnSalvarDoacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( verificarPreenchimento() ){
                    salvarDoacao(uidDoacao);
                }
            }
        });

        btnCancelarDoacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        aSwitchProdutos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    produtos = "1";
                }else{
                    produtos = "0";
                }

            }
        });


    }

    private boolean verificarPreenchimento() {
        if (spMyInst.getSelectedItem() != null){
            if (!edtValorDoacao.getText().toString().equals("R$0,00")){
                    return true;
            }else{
                edtValorDoacao.setError("Ops! favor inserir um valor maior que R$0,00");
                //exibirMensagem("Ops! favor preenhcer o Banco da conta");
                edtValorDoacao.setTextColor(Color.RED);
                edtValorDoacao.requestFocus();
                return false;
            }
        }else {
            //spMyInst.setBackground(getDrawable(R.color.colorRosaEscuro));
            exibirMensagem("favor selecionar uma instituição");
            //spinnerInstituicaoLista.
            return false;
        }
    }

    private void exibirMensagem(String mensagem) {

        Toast.makeText(this,mensagem,Toast.LENGTH_SHORT).show();
    }

    private void carregaDoacoes(String uidDoacao) {
        doacaoEditReference = ConfiguracaoFirebase.getFirebase().child("Minhas_Doacoes").child(ConfiguracaoFirebase.getIdUsuario()).child(uidDoacao);
        doacaoEditReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Doacao doacaoEdit = dataSnapshot.getValue(Doacao.class);
                edtValorDoacao.setText(doacaoEdit.getValor());
                txtDataDoacao.setText(doacaoEdit.getData());
                spinnerInstituicaoLista.add(doacaoEdit.getNomeInstituicao());
                if (doacaoEdit.getProduto().equals("1")){
                    aSwitchProdutos.setChecked(true);
                }else {
                    aSwitchProdutos.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void salvarDoacao(String uidDoacao) {

        Doacao doacao = new Doacao();
        doacao.setData(txtDataDoacao.getText().toString());
        doacao.setValor(edtValorDoacao.getText().toString());
        int posicao = spMyInst.getSelectedItemPosition();
        Instituicao instituicao = instituicoes.get(posicao);
        String idInstituicao = instituicao.getUid();
        String nomeInstituicao = instituicao.getNomeFantasia();
        doacao.setUidInstituicao(idInstituicao);
        doacao.setNomeInstituicao(nomeInstituicao);
        doacao.setProduto(produtos);

        if(extras != null){
            doacao.setUid(uidDoacao);
            doacaoEditandoReference = ConfiguracaoFirebase.getFirebase();
            doacaoEditandoReference.child("Minhas_Doacoes").child(ConfiguracaoFirebase.getIdUsuario()).child(uidDoacao).setValue(doacao);

        }else {
            doacao.setUid(UUID.randomUUID().toString());
            doacaoRef = ConfiguracaoFirebase.getFirebase().child("Minhas_Doacoes");
            doacaoRef.child(ConfiguracaoFirebase.getIdUsuario()).child(doacao.getUid()).setValue(doacao);
        }
        finish();
    }

    private void carregarDadosSpinner() {
        qryInstituicaoRef = ConfiguracaoFirebase.getFirebase().child("Minhas_Instituicoes").orderByChild("uidUsuario").equalTo(ConfiguracaoFirebase.getIdUsuario());
        qryInstituicaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                String nomeInstituicao;
                String uidInstituicao;
                for (DataSnapshot contaSnapshot : dataSnapshot.getChildren()) {

                    Instituicao inst = contaSnapshot.getValue(Instituicao.class);
                    if (inst.getSituacao().equals("2")){
                        nomeInstituicao = inst.getNomeFantasia();
                        uidInstituicao = inst.getUid();
                        Instituicao dadosInstituicao = new Instituicao(uidInstituicao,nomeInstituicao);
                        dadosInstituicao.setNomeFantasia(nomeInstituicao);
                        instituicoes.add(dadosInstituicao);
                    }
                }
                ArrayAdapter adapter = new ArrayAdapter(CadastrarDoacoesActivity.this, android.R.layout.simple_spinner_dropdown_item,instituicoes);
                spMyInst.setAdapter(adapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void inicializarComponentes() {
        txtDataDoacao = (TextView) findViewById(R.id.txtDataDoacao);
        mDataAtual = Calendar.getInstance();
        edtValorDoacao = (CurrencyEditText) findViewById(R.id.edtValorDoacao);
        spMyInst = (Spinner) findViewById(R.id.spMinhasInst);
        btnSalvarDoacao = (Button) findViewById(R.id.btnSalvarDoacao);
        btnCancelarDoacao = (Button) findViewById(R.id.btnCancelarDoacao);
        spinnerInstituicaoLista = new ArrayList<>();
        //Configura localidade
        Locale locale = new Locale("pt", "BR");
        edtValorDoacao.setLocale(locale);
        aSwitchProdutos = (Switch) findViewById(R.id.switchProduto);


    }


}
