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
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.adapters.AdapterProdutos;
import com.gpa.myappdonation.adapters.AdapterProdutosCampanha;
import com.gpa.myappdonation.model.Campanha;

import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.model.Notificacao;
import com.gpa.myappdonation.model.NotificacaoDados;
import com.gpa.myappdonation.model.ProdutosCampanha;
import com.gpa.myappdonation.model.Produto;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.NotificacaoService;
import com.gpa.myappdonation.util.RecyclerItemClickListener;
import com.gpa.myappdonation.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CampanhaActivity extends AppCompatActivity {

    Calendar mDataAtual;
    int dia, mes, ano, campanhaPermanente;
    Bundle extras;
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
    private Retrofit retrofit;
    private String baseUrl;
    private String nomeInstituicao;
    private Query queryInstituicao, queryProduto;


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

        dia = mDataAtual.get(Calendar.DAY_OF_MONTH)+1;
        mes = mDataAtual.get(Calendar.MONTH) +1;
        ano = mDataAtual.get(Calendar.YEAR);

        txtDataInicial.setText(dia + "/" + mes + "/" + ano);

        txtDataInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CampanhaActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                        mes = mes+1;
                        txtDataInicial.setText(dia + "/" + mes + "/" + ano);
                    }
                }, ano, mes-1, dia);
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
                        mes = mes+1;
                        txtDataFinal.setText(dia + "/" + mes + "/" + ano);
                    }
                }, ano, mes-1, dia);
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

                                                if (adapterProdutosCampanha == null) {

                                                    produtosCampanha.add(produtoCampanha);
                                                    campanhaRecuperada.setItens(produtosCampanha);
                                                    produtoCampanha.setUidCampanha(campanhaRecuperada.getUid());
                                                    adapterProdutosCampanha = new AdapterProdutosCampanha(produtosCampanha, CampanhaActivity.this);
                                                    recyclerProdutosCampanhaAdd.setLayoutManager(new LinearLayoutManager(CampanhaActivity.this));
                                                    recyclerProdutosCampanhaAdd.setHasFixedSize(true);
                                                    recyclerProdutosCampanhaAdd.setAdapter(adapterProdutosCampanha);
                                                } else if (adapterProdutosCampanha != null && extras != null) {

                                                    produtosCampanhaRecuperados.add(produtoCampanha);
                                                    campanhaRecuperada.setItens(produtosCampanhaRecuperados);
                                                    produtoCampanha.setUidCampanha(campanhaRecuperada.getUid());
                                                    adapterProdutosCampanha.notifyDataSetChanged();

                                                } else if (adapterProdutosCampanha != null && extras == null) {

                                                    produtosCampanha.add(produtoCampanha);
                                                    campanhaRecuperada.setItens(produtosCampanha);
                                                    produtoCampanha.setUidCampanha(campanhaRecuperada.getUid());
                                                    adapterProdutosCampanha.notifyDataSetChanged();

                                                }
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
                salvaCampanha(uidCampanha, view);
            }
        });

        btnCancelarCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        baseUrl = "https://fcm.googleapis.com/fcm/";
        // baseUrl = "https://fcm.googleapis.com/wp/";
        retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        String teste = "";
        //FirebaseMessaging.getInstance().subscribeToTopic("campanha");
    }

    public void enviarNotificacao(View view) {

        String nomeCampanha = campanhaRecuperada.getNomeCampanha();
        String itensNotificacao = "" ;
        String item;
        for (int i = 0; i< produtosCampanha.size(); i++){
                item = produtosCampanha.get(i).getNomeProCampanha();
                itensNotificacao = itensNotificacao+" \n "+item;
        }

        String to = "/topics/campanha";

        String title = "A Instituição: "+ nomeInstituicao;
        String body = "Criou a campanha: "+nomeCampanha+ "\n e necesita dos produtos abaixo: \n \n"+itensNotificacao;

        Notificacao notificacao = new Notificacao(title,body);

        NotificacaoDados notificacaoDados = new NotificacaoDados(to, notificacao);
        notificacaoDados.setNotificacao(notificacao);
        notificacaoDados.setTo(to);

        NotificacaoService service = retrofit.create(NotificacaoService.class);
        Call<NotificacaoDados> call = service.salvarNotificacao(notificacaoDados);

        String noti = notificacaoDados.getNotificacao().getTitle();
        String teste = notificacaoDados.getNotificacao().getBody();


        try {
            call.enqueue(new Callback<NotificacaoDados>() {
                @Override
                public void onResponse(Call<NotificacaoDados> call, Response<NotificacaoDados> response) {


                    if (response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "codigo: " + response.code() + " - "
                                + " Enviada ! Corpo: "+teste+" - Title: "+noti, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<NotificacaoDados> call, Throwable t) {

                }
            });
        }catch (Exception e ){
            e.getMessage();
        }


    }

    private void salvaCampanha(String uidCampanha, final View view) {

        if (!edtNomeCampanha.getText().toString().equals("")) {

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

                        if (produtosCampanha.size() > 0) {
                            campanhaRecuperada.setItens(produtosCampanha);
                            campanhaRecuperada.setUid(uidCampanhaEdit);
                            campanhaRecuperada.salvarCampanha();
                            enviarNotificacao(view);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Favor adicionar produtos a sua campanha", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }else {


                if (campanhaRecuperada != null) {

                    if (campanhaRecuperada.getItens().size() > 0) {

                        if (campanhaPermanente == 1) {
                            campanhaRecuperada.setDataFinal("");
                            campanhaRecuperada.setDataInicial("");
                            campanhaRecuperada.setPermanente("1");
                        } else if (campanhaPermanente == 0) {
                            campanhaRecuperada.setDataInicial(txtDataInicial.getText().toString());
                            campanhaRecuperada.setDataFinal(txtDataFinal.getText().toString());
                            campanhaRecuperada.setPermanente("0");
                        }

                        campanhaRecuperada.setNomeCampanha(edtNomeCampanha.getText().toString());
                        campanhaRecuperada.setStatus("1");

                        campanhaRecuperada.salvarCampanha();
                        enviarNotificacao(view);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Favor adicionar produtos a sua campanha", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Favor adicionar produtos a sua campanha", Toast.LENGTH_SHORT).show();
                }

            }
        }else {
            Toast.makeText(getApplicationContext(), "Favor preencher o nome da Campanha", Toast.LENGTH_SHORT).show();
            edtNomeCampanha.setHintTextColor(Color.RED);
            edtNomeCampanha.requestFocus();
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

        //produtoRef = ConfiguracaoFirebase.getFirebase().child("Produto");
        queryProduto = ConfiguracaoFirebase.getFirebase().child("Produto").orderByChild("uidInstituicao").equalTo(ConfiguracaoFirebase.getIdUsuario());
        queryProduto.addValueEventListener(new ValueEventListener() {
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
        nomeInstituicao = retornaNomeInstituicao();
    }

    private String retornaNomeInstituicao(){

        queryInstituicao = ConfiguracaoFirebase.getFirebase().child("Instituicao").orderByChild("uid").equalTo(ConfiguracaoFirebase.getIdUsuario());

        queryInstituicao.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot instDs : dataSnapshot.getChildren()) {
                    Instituicao instituicao = instDs.getValue(Instituicao.class);
                    nomeInstituicao = instituicao.getNomeFantasia();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return nomeInstituicao;

    }




}
