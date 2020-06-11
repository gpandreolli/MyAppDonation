package com.gpa.myappdonation.fragment;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.activity.AdministradorActivity;
import com.gpa.myappdonation.activity.ListaInstituicaoActivity;
import com.gpa.myappdonation.adapters.AdapterInstituicoesNaoAvaliadas;
import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.RecyclerItemClickListener;
import dmax.dialog.SpotsDialog;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstituicaoNaoAvaliadaFragment extends Fragment {

    private RecyclerView recyclerViewInstNaoAvaliadas;
    private Query query;
    private List<Instituicao> instituicoes = new ArrayList<>();
    private AdapterInstituicoesNaoAvaliadas adapterInst;
    private TextView txtRazaoSocial, txtNomeFantasia, txtCnpj, txtTelefone, txtEmail, txtRua, txtNumeroRua, txtComplemento, txtBairro;
    private TextView txtCidade, txtEstado, txtCep;
    private android.app.AlertDialog dialogCarregando;
    private DatabaseReference databaseReference;

    public InstituicaoNaoAvaliadaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instituicao_nao_avaliada, container, false);
        recyclerViewInstNaoAvaliadas = (RecyclerView) view.findViewById(R.id.recyclerInstituicoesNaoAvaliadas);
        Toolbar toolbar = view.findViewById(R.id.toolbarInstNaoAvaliadas);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Instituições Não Avaliadas");
         recuperaInstituicoes();
         atualizaInstituicoes();
        return view;
    }

    private void atualizaInstituicoes() {
        recyclerViewInstNaoAvaliadas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(),
                        recyclerViewInstNaoAvaliadas,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                exibeDadosInstituicao(position);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                aprovarInstituicao(position);
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                ));
    }

    private void aprovarInstituicao(final int position) {


        new AlertDialog.Builder(getActivity())
                .setTitle("Aprovar Instituição")
                .setMessage("Deseja realmente aprovar essa Instituição")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Instituicao instituicao = instituicoes.get(position);
                        String idInst = instituicao.getUid();
                        String idInst2 = idInst;
                        ConfiguracaoFirebase.getFirebase().child("Instituicao").child(idInst).child("situacao").setValue("2");

                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapterInst.notifyDataSetChanged();
                    }
                }).show();

    }

    private void recuperaInstituicoes() {
        dialogCarregando = new SpotsDialog.Builder()
                .setContext(getActivity())
                .setMessage("Carregando Dados")
                .setCancelable(false)
                .build();
        dialogCarregando.show();

        query = FirebaseDatabase.getInstance().getReference("Instituicao").orderByChild("situacao").equalTo("1");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                instituicoes.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    instituicoes.add(ds.getValue(Instituicao.class));
                    adapterInst = new AdapterInstituicoesNaoAvaliadas(instituicoes, getContext());
                    recyclerViewInstNaoAvaliadas.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerViewInstNaoAvaliadas.setHasFixedSize(true);
                    recyclerViewInstNaoAvaliadas.setAdapter(adapterInst);
                }
                Collections.reverse(instituicoes);
                if (instituicoes.size() > 0) {
                    adapterInst.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dialogCarregando.dismiss();
    }

    private void exibeDadosInstituicao(int position) {

        Instituicao instituicao = instituicoes.get(position);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        View mView = getLayoutInflater().inflate(R.layout.dados_instituicao, null);

        txtRazaoSocial = mView.findViewById(R.id.txtRazaoSocial);
        txtNomeFantasia = mView.findViewById(R.id.txtNomeFantasia);
        txtCnpj = mView.findViewById(R.id.txtCnpj);
        txtTelefone = mView.findViewById(R.id.txtTelefone);
        txtEmail = mView.findViewById(R.id.txtEmail);
        txtRua = mView.findViewById(R.id.txtRua);
        txtNumeroRua = mView.findViewById(R.id.txtNumeroRua);
        txtComplemento = mView.findViewById(R.id.txtComplemento);
        txtBairro = mView.findViewById(R.id.txtBairro);
        txtCidade = mView.findViewById(R.id.txtCidade_dadosInst);
        txtEstado = mView.findViewById(R.id.txtEstado);
        txtCep = mView.findViewById(R.id.txtCep);

        txtRazaoSocial.setText(instituicao.getRazaoSocial());
        txtNomeFantasia.setText(instituicao.getNomeFantasia());
        txtCnpj.setText(instituicao.getCnpj());
        txtTelefone.setText(instituicao.getTelefone());
        txtEmail.setText(instituicao.getEmail());
        txtRua.setText(instituicao.getRua());
        txtNumeroRua.setText(instituicao.getNumero());
        txtComplemento.setText(instituicao.getComplemento());
        txtBairro.setText(instituicao.getBairro());
        txtCidade.setText(instituicao.getCidade());
        txtEstado.setText(instituicao.getUf());
        txtCep.setText(instituicao.getCep());

        mBuilder.setView(mView);
        AlertDialog dialog = mBuilder.create();
        dialog.show();
    }


}
