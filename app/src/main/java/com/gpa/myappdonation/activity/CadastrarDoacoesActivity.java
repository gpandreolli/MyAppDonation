package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
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
    private Button btnSalvarDoacao;
    DatabaseReference doacaoRef;
    DatabaseReference instituicaoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_doacoes);

        inicializarComponentes();
        carregarDadosSpinner();

        dia = mDataAtual.get(Calendar.DAY_OF_MONTH);
        mes = mDataAtual.get(Calendar.MONTH);
        ano = mDataAtual.get(Calendar.YEAR);
        mes = mes + 1;

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
                }, ano, mes, dia);
                datePickerDialog.show();
            }
        });
        
        btnSalvarDoacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarDoacao();
            }
        });
    }

    private void salvarDoacao() {
        int posicao = spMyInst.getSelectedItemPosition();

        Instituicao instituicao = instituicoes.get(posicao);
        String idInstituicao = instituicao.getUid();
        String nomeInstituicao = instituicao.getNomeFantasia();

        Doacao doacao = new Doacao();
        doacao.setUid(UUID.randomUUID().toString());
        doacao.setData(txtDataDoacao.getText().toString());
        doacao.setValor(edtValorDoacao.getText().toString());
        doacao.setUidInstituicao(idInstituicao);
        doacao.setNomeInstituicao(nomeInstituicao);
        doacaoRef = ConfiguracaoFirebase.getFirebase().child("Minhas_Doacoes");
        doacaoRef.child(ConfiguracaoFirebase.getIdUsuario()).child(UUID.randomUUID().toString()).setValue(doacao);

    }

    private void carregarDadosSpinner() {
        instituicaoRef = ConfiguracaoFirebase.getFirebase().child("Minhas_Instituicoes").child(ConfiguracaoFirebase.getIdUsuario());
        instituicaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                String nomeInstituicao;
                String uidInstituicao;
                for (DataSnapshot contaSnapshot : dataSnapshot.getChildren()) {

                    Instituicao inst = contaSnapshot.getValue(Instituicao.class);
                    nomeInstituicao = inst.getNomeFantasia();
                    uidInstituicao = inst.getUid();
                    Instituicao dadosInstituicao = new Instituicao(uidInstituicao,nomeInstituicao);
                    dadosInstituicao.setNomeFantasia(nomeInstituicao);
                    instituicoes.add(dadosInstituicao);
                }
                ArrayAdapter adapter = new ArrayAdapter(CadastrarDoacoesActivity.this, android.R.layout.simple_spinner_item,instituicoes);
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
        spinnerInstituicaoLista = new ArrayList<>();
        //Configura localidade
        Locale locale = new Locale("pt", "BR");
        edtValorDoacao.setLocale(locale);


    }


}
