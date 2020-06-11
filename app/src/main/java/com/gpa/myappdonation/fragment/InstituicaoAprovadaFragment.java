package com.gpa.myappdonation.fragment;

import android.app.AlertDialog;
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
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.activity.ListaInstituicaoActivity;
import com.gpa.myappdonation.adapters.AdapterInstituicoesAprovadas;
import com.gpa.myappdonation.adapters.Adapter_instituicoes;
import com.gpa.myappdonation.model.Instituicao;
import dmax.dialog.SpotsDialog;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstituicaoAprovadaFragment extends Fragment {

    private RecyclerView recyclerViewInstAprovadas;
    private Query query;
    private List<Instituicao> instituicoes = new ArrayList<>();
    private AdapterInstituicoesAprovadas adapterInst;
    private AppBarLayout barLayout;
    private AlertDialog dialogCarregando;

    public InstituicaoAprovadaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instituicao_aprovada, container, false);
        recyclerViewInstAprovadas = (RecyclerView) view.findViewById(R.id.recyclerInstituicoesAprovadas);
        Toolbar toolbar = view.findViewById(R.id.toolbarInstInstAprovadas);
        barLayout = view.findViewById(R.id.appBarLayoutInstAprovadas);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Instituições Aprovadas");

        barLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Mensagem",Toast.LENGTH_LONG).show();
            }
        });


        inicializarComponentes();
        query = FirebaseDatabase.getInstance().getReference("Instituicao").orderByChild("situacao").equalTo("2");

        dialogCarregando = new SpotsDialog.Builder()
                .setContext(getContext())
                .setMessage("Carregando Dados")
                .setCancelable(false)
                .build();
        dialogCarregando.show();

        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                instituicoes.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    instituicoes.add(ds.getValue(Instituicao.class));
                    adapterInst = new AdapterInstituicoesAprovadas(instituicoes,getContext());
                    recyclerViewInstAprovadas.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerViewInstAprovadas.setHasFixedSize(true);
                    recyclerViewInstAprovadas.setAdapter(adapterInst);
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
        return  view;

    }

    private void inicializarComponentes() {


    }
}
