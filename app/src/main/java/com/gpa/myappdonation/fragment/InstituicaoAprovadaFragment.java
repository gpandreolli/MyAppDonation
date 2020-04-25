package com.gpa.myappdonation.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public InstituicaoAprovadaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instituicao_aprovada, container, false);
        recyclerViewInstAprovadas = (RecyclerView) view.findViewById(R.id.recyclerInstituicoesAprovadas);
        inicializarComponentes();
        query = FirebaseDatabase.getInstance().getReference("Instituicao").orderByChild("situacao").equalTo("2");
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
        return  view;
    }

    private void inicializarComponentes() {


    }
}
