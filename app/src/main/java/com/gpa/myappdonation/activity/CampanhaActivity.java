package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.gpa.myappdonation.adapters.AdapterProdutosCampanha;
import com.gpa.myappdonation.model.Campanha;

import com.gpa.myappdonation.model.ProdutosCampanha;
import com.gpa.myappdonation.model.Produto;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class CampanhaActivity extends AppCompatActivity {

    private TextView  txtDataInicial, txtDataFinal;
    private EditText edtNomeCampanha;
    private Button btnSalvarCampanha, btnCancelarCampanha;
    private RecyclerView recyclerProdutos;
    private RecyclerView recyclerProdutosCampanhaAdd;
    private List<Produto> produtos = new ArrayList<>();
    private List<ProdutosCampanha> produtosCampanha = new ArrayList<>();
    private List<ProdutosCampanha> produtosCampanhaRecuperados = new ArrayList<>();
    private List<ProdutosCampanha> produtosCampanhaRecuperada = new ArrayList<>();


    private Switch aSwitchPermanente;
    private AlertDialog dialogCarregando;
    private String idInstituicao;
    private DatabaseReference produtoRef,campanhaEditReference;
    private AdapterProdutos adapterProdutos;
    private AdapterProdutosCampanha adapterProdutosCampanha;
    private Context context;
    private Campanha campanhaRecuperada;
    Calendar mDataAtual;
    int dia, mes, ano ,campanhaPermanente;
    Bundle extras;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campanha);
        inicializarComponnetes();
        Intent i = getIntent();
        extras = getIntent().getExtras();
        final int[] aux = {0};


        final String uidCampanha = i.getStringExtra("uid");

        if (extras !=null){
            recuperaCampanha(uidCampanha);
        }else {
            Campanha campanhaRecuperada = new Campanha();
        }

        recuperaDadosInstituicao();
        recuperarProdutos();




        dia = mDataAtual.get(Calendar.DAY_OF_MONTH);
        mes = mDataAtual.get(Calendar.MONTH) +1;
        ano = mDataAtual.get(Calendar.YEAR);

        txtDataInicial.setText(dia + "/" + mes + "/" + ano);
        txtDataInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CampanhaActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
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

                                                ProdutosCampanha produtoCampanha = new ProdutosCampanha();
                                                produtoCampanha.setUid(UUID.randomUUID().toString());
                                                produtoCampanha.setUidProduto(produtoSelecionado.getUid());
                                                produtoCampanha.setNomeProCampanha(produtoSelecionado.getNome());
                                                produtoCampanha.setDescProdCampanha(produtoSelecionado.getDescricao());


                                                if (campanhaRecuperada == null) {
                                                    campanhaRecuperada = new Campanha(idInstituicao);
                                                    produtoCampanha.setUidCampanha(campanhaRecuperada.getUid());


                                                }






                                                  if(adapterProdutosCampanha == null){
                                                      produtosCampanha.add(produtoCampanha);
                                                      campanhaRecuperada.setItens(produtosCampanha);
                                                      produtoCampanha.setUidCampanha(campanhaRecuperada.getUid());
                                                    adapterProdutosCampanha = new AdapterProdutosCampanha(produtosCampanha,CampanhaActivity.this);
                                                    recyclerProdutosCampanhaAdd.setLayoutManager(new LinearLayoutManager(CampanhaActivity.this));
                                                    recyclerProdutosCampanhaAdd.setHasFixedSize(true);
                                                    recyclerProdutosCampanhaAdd.setAdapter(adapterProdutosCampanha);
                                                 }else{


                                                      produtosCampanhaRecuperados.add(produtoCampanha);
                                                      campanhaRecuperada.setItens(produtosCampanhaRecuperados);
                                                      produtoCampanha.setUidCampanha(campanhaRecuperada.getUid());
                                                      adapterProdutosCampanha.notifyDataSetChanged();
                                                     // recyclerProdutosCampanhaAdd.setLayoutManager(new LinearLayoutManager(CampanhaActivity.this));
                                                     // recyclerProdutosCampanhaAdd.setHasFixedSize(true);
                                                     // recyclerProdutosCampanhaAdd.setAdapter(adapterProdutosCampanha);

                                                   // produtosCampanha = produtosCampanhaRecuperados;


                                                }

                                                //adapterProdutosCampanha.notifyDataSetChanged();


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
                salvaCampanha(uidCampanha);
            }
        });

        btnCancelarCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }

    private void salvaCampanha(String uidCampanha) {

        if (extras != null){

            campanhaEditReference = ConfiguracaoFirebase.getFirebase().child("Campanha").child(uidCampanha);
            campanhaEditReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Campanha campanhaRecuperada = dataSnapshot.getValue(Campanha.class);

                   String uidCampanhaEdit = campanhaRecuperada.getUid();

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

                    int j = adapterProdutosCampanha.getItemCount();
                    for (int i = 0; i < j ; ++i ){
                        ProdutosCampanha  prodCampanha  ;
                        prodCampanha = adapterProdutosCampanha.getItem(i);
                        produtosCampanha.add(prodCampanha);
                    }

                    campanhaRecuperada.setItens(produtosCampanha);
                    campanhaRecuperada.setUid(uidCampanhaEdit);
                    campanhaRecuperada.salvarCampanha();
                    finish();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }else {
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
    }


    private void recuperaCampanha(String uidCampanha) {

        dialogCarregando = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Carregando Dados")
                .setCancelable(false)
                .build();
        dialogCarregando.show();


        campanhaEditReference = ConfiguracaoFirebase.getFirebase().child("Campanha").child(uidCampanha);

        campanhaEditReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Campanha campanhaEdit = dataSnapshot.getValue(Campanha.class);
                edtNomeCampanha.setText(campanhaEdit.getNomeCampanha());
                if (campanhaEdit.getPermanente().equals("1")){
                    aSwitchPermanente.setChecked(true);
                    txtDataInicial.setVisibility(View.INVISIBLE);
                    txtDataFinal.setVisibility(View.INVISIBLE);
                }else {
                    aSwitchPermanente.setChecked(false);
                    txtDataInicial.setText(campanhaEdit.getDataInicial());
                    txtDataFinal.setText(campanhaEdit.getDataFinal());
                }

                produtosCampanhaRecuperada = campanhaEdit.getItens();
                String auxiliar = null;
                List listaAuxiliar = new ArrayList();
                listaAuxiliar.add(auxiliar);
                produtosCampanhaRecuperada.removeAll(listaAuxiliar);

                for (ProdutosCampanha produtoCampanha: produtosCampanhaRecuperada){
                    produtosCampanhaRecuperados.add(produtoCampanha);
                    adapterProdutosCampanha = new AdapterProdutosCampanha(produtosCampanhaRecuperados,CampanhaActivity.this);
                    recyclerProdutosCampanhaAdd.setLayoutManager(new LinearLayoutManager(CampanhaActivity.this));
                    recyclerProdutosCampanhaAdd.setHasFixedSize(true);
                    recyclerProdutosCampanhaAdd.setAdapter(adapterProdutosCampanha);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dialogCarregando.dismiss();

    }

    private void recuperaDadosInstituicao() {
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
        recyclerProdutosCampanhaAdd = (RecyclerView) findViewById(R.id.recyclerProdutosCampanhaAdd);
    }




}
