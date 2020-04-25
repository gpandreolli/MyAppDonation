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
import com.gpa.myappdonation.adapters.AdapterInstituicoesReprovadas;
import com.gpa.myappdonation.model.Instituicao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstituicaoReprovadaFragment extends Fragment {

    private RecyclerView recyclerViewInstReprovadas;
    private Query query;
    private List<Instituicao> instituicoes = new ArrayList<>();
    private AdapterInstituicoesReprovadas adapterInst;

    public InstituicaoReprovadaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instituicao_reprovada, container, false);
        recyclerViewInstReprovadas = (RecyclerView) view.findViewById(R.id.recyclerInstituicoesReprovadas);

        query = FirebaseDatabase.getInstance().getReference("Instituicao").orderByChild("situacao").equalTo("2");

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
                if (instituicoes.size() > 0) {
                    adapterInst.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }


}
