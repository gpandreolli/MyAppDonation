package com.gpa.myappdonation.fragment;


import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gpa.myappdonation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContaFragment extends Fragment {


    public ContaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View view = inflater.inflate(R.layout.fragment_conta, container, false);
        final View contaFragment = inflater.inflate(R.layout.fragment_conta, null);
        final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                .setView(contaFragment)
                .setTitle("Dados da Conta")
                .setPositiveButton("OK", null)
                .show();
        Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    dialog.dismiss();
            }
        });
        return view;
    }

}
