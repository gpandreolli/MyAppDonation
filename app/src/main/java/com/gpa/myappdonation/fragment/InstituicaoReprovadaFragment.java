package com.gpa.myappdonation.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.adapters.AdapterInstituicoesReprovadas;
import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;
import com.gpa.myappdonation.util.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstituicaoReprovadaFragment extends Fragment {

    private RecyclerView recyclerViewInstReprovadas;
    private Query query;
    private AppBarLayout barLayout;
    private List<Instituicao> instituicoes = new ArrayList<>();
    private AdapterInstituicoesReprovadas adapterInst;
    private AlertDialog dialogCarregando;
    private TextView txtRazaoSocial, txtNomeFantasia, txtCnpj, txtTelefone, txtEmail, txtRua, txtNumeroRua, txtComplemento, txtBairro;
    private TextView txtCidade, txtEstado, txtCep;
    private Query queryReanalisaInst;
    private DatabaseReference instReanaliseRef;

    public InstituicaoReprovadaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instituicao_reprovada, container, false);
        recyclerViewInstReprovadas = (RecyclerView) view.findViewById(R.id.recyclerInstituicoesReprovadas);
        Toolbar toolbar = view.findViewById(R.id.toolbarInstReprovadas);
        barLayout = view.findViewById(R.id.appBarLayoutReprovadas);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Instituições Reprovadas");

        recuperaInstituicoes();
        atualizaInstituicoes();




        return view;
    }

    private void atualizaInstituicoes() {
        recyclerViewInstReprovadas.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(),
                        recyclerViewInstReprovadas,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                exibeDadosInstituicao(position);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                reanalisarInstituicao(position);
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );
    }

    private void recuperaInstituicoes() {
        query = FirebaseDatabase.getInstance().getReference("Instituicao").orderByChild("situacao").equalTo("3");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                instituicoes.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    instituicoes.add(ds.getValue(Instituicao.class));
                    adapterInst = new AdapterInstituicoesReprovadas(instituicoes,getContext());
                    recyclerViewInstReprovadas.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerViewInstReprovadas.setHasFixedSize(true);
                    recyclerViewInstReprovadas.setAdapter(adapterInst);
                }
                Collections.reverse(instituicoes);
               // if (instituicoes.size() > 0) {
                    adapterInst.notifyDataSetChanged();
               // }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void exibeDadosInstituicao(int position) {
        Instituicao instituicao = instituicoes.get(position);
        androidx.appcompat.app.AlertDialog.Builder mBuilder = new androidx.appcompat.app.AlertDialog.Builder(getContext());
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
        androidx.appcompat.app.AlertDialog dialog = mBuilder.create();
        dialog.show();
    }

    private void reanalisarInstituicao(final int position) {

        new androidx.appcompat.app.AlertDialog.Builder(getActivity())
                .setTitle("Analisar Instituição")
                .setMessage("Deseja realmente refazer a análise dessa Instituição")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Instituicao instituicao = instituicoes.get(position);
                        String idInst = instituicao.getUid();
                        ConfiguracaoFirebase.getFirebase().child("Instituicao").child(idInst).child("situacao").setValue("1");
                        queryReanalisaInst = ConfiguracaoFirebase.getFirebase().child("Minhas_Instituicoes").orderByChild("uid").equalTo(idInst);
                        if (queryReanalisaInst !=null){
                            instReanaliseRef = queryReanalisaInst.getRef();
                            instReanaliseRef.child("situacao").setValue("1");
                        }
                        adapterInst.notifyDataSetChanged();


                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapterInst.notifyDataSetChanged();
                    }
                }).show();
    }


}
