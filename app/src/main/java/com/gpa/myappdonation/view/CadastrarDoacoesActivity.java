package com.gpa.myappdonation.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.gpa.myappdonation.R;

import java.util.Calendar;
import java.util.Currency;
import java.util.Locale;

public class CadastrarDoacoesActivity extends AppCompatActivity {

    private CurrencyEditText edtValorDoacao;
    private TextView txtDataDoacao;
    Calendar mDataAtual;
    int dia, mes, ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_doacoes);

        inicializarComponentes();

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
    }

    private void inicializarComponentes() {
        txtDataDoacao = (TextView) findViewById(R.id.txtDataDoacao);
        mDataAtual = Calendar.getInstance();
        edtValorDoacao = (CurrencyEditText) findViewById(R.id.edtValorDoacao);
        //Configura localidade
        Locale locale = new Locale("pt", "BR");
        edtValorDoacao.setLocale(locale);

    }


}
