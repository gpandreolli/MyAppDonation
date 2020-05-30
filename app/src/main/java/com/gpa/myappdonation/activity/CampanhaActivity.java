package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.adapters.AdapterProdutos;
import com.gpa.myappdonation.model.Campanha;
import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.model.ItemCampanha;
import com.gpa.myappdonation.model.Produto;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class CampanhaActivity extends AppCompatActivity {

    private TextView txtStatusCampanha, txtDataInicial, txtDataFinal,txtInsituicaoCampanha;
    private EditText edtNomeCampanha;
    private Button btnSalvarCampanha, btnCancelarCampanha;
    private RecyclerView recyclerProdutos;
    private List<Produto> produtos = new ArrayList<>();
    private List<ItemCampanha> itensCampanha = new ArrayList<>();
    private Switch aSwitchPermanente;
    private AlertDialog dialogCarregando;
    private String idInstituicao;
    private DatabaseReference produtoRef;
    private AdapterProdutos adapterProdutos;
    private Context context;
    private Campanha campanhaRecuperada;
    Calendar mDataAtual;
    int dia, mes, ano ,campanhaPermanente, statusCampanha;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanha);
        inicializarComponnetes();
        setNomeInstituicao();
        recuperaDadosInstituicao();
        recuperarProdutos();
        txtStatusCampanha.setVisibility(View.INVISIBLE);

        dia = mDataAtual.get(Calendar.DAY_OF_MONTH);
        mes = mDataAtual.get(Calendar.MONTH);
        ano = mDataAtual.get(Calendar.YEAR);

        txtDataInicial.setText(dia + "/" + mes + "/" + ano);
        txtDataInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CampanhaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                        mes = mes + 1;
                        txtDataInicial.setText(dia + "/" + mes + "/" + ano);
                    }
                }, ano, mes, dia);
                datePickerDialog.show();
            }
        });

        txtDataFinal.setText(dia + "/" + mes + "/" + ano);
        txtDataFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CampanhaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                        mes = mes + 1;
                        txtDataFinal.setText(dia + "/" + mes + "/" + ano);
                    }
                }, ano, mes, dia);
                datePickerDialog.show();

            }
        });


        recyclerProdutos.addOnItemTouchListener(new RecyclerItemClickListener(
                        this,
                        recyclerProdutos,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                            }

                            @Override
                            public void onLongItemClick(View view, final int position) {
                                new AlertDialog.Builder(CampanhaActivity.this).
                                        setTitle("Adicionar Produto").
                                        setMessage("Deseja adicionar esse produto?").
                                        setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Produto produtoSelecionado = produtos.get(position);
                                                ItemCampanha itemCampanha = new ItemCampanha();
                                                itemCampanha.setUid(produtoSelecionado.getUid());
                                                itemCampanha.setNome(produtoSelecionado.getNome());
                                                itemCampanha.setDescricao(produtoSelecionado.getDescricao());

                                                itensCampanha.add(itemCampanha);

                                                if (campanhaRecuperada == null) {
                                                    campanhaRecuperada = new Campanha(idInstituicao);
                                                }

                                                campanhaRecuperada.setItens(itensCampanha);
                                            }
                                        }).
                                        setNegativeButton("Não", null).show();
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        aSwitchPermanente.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    campanhaPermanente = 1;
                    txtDataInicial.setVisibility(View.INVISIBLE);
                    txtDataFinal.setVisibility(View.INVISIBLE);
                }else{
                    campanhaPermanente = 0;
                    txtDataInicial.setVisibility(View.VISIBLE);
                    txtDataFinal.setVisibility(View.VISIBLE);
                }

            }
        });

        btnSalvarCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(campanhaPermanente == 1){
                    campanhaRecuperada.setDataFinal("");
                    campanhaRecuperada.setDataInicial("");
                    campanhaRecuperada.setPermanente("1");
                }else if (campanhaPermanente == 0) {
                    campanhaRecuperada.setDataInicial(txtDataInicial.getText().toString());
                    campanhaRecuperada.setDataFinal(txtDataFinal.getText().toString());
                    campanhaRecuperada.setPermanente("0");
                }
                campanhaRecuperada.setNomeCampanha(edtNomeCampanha.getText().toString());
                campanhaRecuperada.setStatus("1");
                campanhaRecuperada.salvarCampanha();
                finish();
            }
        });

        btnCancelarCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }

    private void recuperaDadosInstituicao() {

     /*   dialogCarregando = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando Dados")
                .setCancelable(false)
                .build();
        dialogCarregando.show();*/

        idInstituicao = ConfiguracaoFirebase.getIdUsuario();


    }

    private void recuperarProdutos() {
        dialogCarregando = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando Dados")
                .setCancelable(false)
                .build();
        dialogCarregando.show();

        produtoRef = ConfiguracaoFirebase.getFirebase().child("Produto");
        produtoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtos.clear();
                for (DataSnapshot dsProdutos : dataSnapshot.getChildren()) {
                    produtos.add(dsProdutos.getValue(Produto.class));
                    adapterProdutos = new AdapterProdutos(produtos, CampanhaActivity.this);
                    recyclerProdutos.setLayoutManager(new LinearLayoutManager(CampanhaActivity.this));
                    recyclerProdutos.setHasFixedSize(true);
                    recyclerProdutos.setAdapter(adapterProdutos);
                }
                Collections.reverse(produtos);
                if (produtos.size() > 0) {
                    adapterProdutos.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dialogCarregando.dismiss();

    }

    private void inicializarComponnetes() {
        recyclerProdutos = (RecyclerView) findViewById(R.id.recyclerProdutosCampanha);
        txtDataFinal = (TextView) findViewById(R.id.txtDataFinalCampnha);
        txtDataInicial = (TextView) findViewById(R.id.txtDataInicialCampanha);
        btnSalvarCampanha = (Button) findViewById(R.id.btnSalvarCampanha);
        btnCancelarCampanha = (Button) findViewById(R.id.btnCancelarCampanha);
        mDataAtual = Calendar.getInstance();
        aSwitchPermanente = (Switch) findViewById(R.id.switchPermanente);
        edtNomeCampanha = (EditText) findViewById(R.id.edtNomeCampanha);
        txtStatusCampanha = (TextView) findViewById(R.id.txtStatusCampanha);
        txtInsituicaoCampanha = (TextView) findViewById(R.id.txtInsituicaoCampanha);
    }



    private void setNomeInstituicao() {

        DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("Instituicao").child(ConfiguracaoFirebase.getIdUsuario());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Instituicao inst = dataSnapshot.getValue(Instituicao.class);
                String nomefant = inst.getNomeFantasia();
                txtInsituicaoCampanha.setText(nomefant);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
