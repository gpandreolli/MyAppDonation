package com.gpa.myappdonation.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Conta;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContaFragment extends Fragment {

    private TextView txtNomeContaFragment, txtBancoFragment, txtAgenciaFragment, txtNumeroContaFragment;
    private DatabaseReference contaRef;
    private String iDconta;


    public ContaFragment() {
        // Required empty public constructor
    }


    public ContaFragment(String iDconta) {

        this.iDconta = iDconta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_conta, container, false);


        final View contaFragment = inflater.inflate(R.layout.fragment_conta, null);
        contaFragment.getContext().getClass().getDeclaredFields();

        contaRef = ConfiguracaoFirebase.getFirebase().child("Conta").child(ConfiguracaoFirebase.getIdUsuario());

        contaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        txtAgenciaFragment = contaFragment.findViewById(R.id.txtAgenciaFragment);
        txtBancoFragment = contaFragment.findViewById(R.id.txtBancoFragment);
        txtNomeContaFragment = contaFragment.findViewById(R.id.txtNomeContaFragment);
        txtNumeroContaFragment = contaFragment.findViewById(R.id.txtNumeroContaFragment);



        txtNumeroContaFragment.setText("6565");
        txtNomeContaFragment.setText("Conta Teste");
        txtBancoFragment.setText("Banco Real");
        txtAgenciaFragment.setText("2121");



        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

}
