package com.gpa.myappdonation.fragment;


import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

      /* //d LayoutInflater inflater = getActivity().getLayoutInflater();
        View filterFragment = inflater.inflate(R.layout.fragment_conta, null);
        //final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                .setView(filterFragment)
                .setTitle("Contas")
                .setPositiveButton("OK", null) //Setar para null. Vamos sobreescrever o onclick
                .setNegativeButton("OK", null)
                .show();*/
        return inflater.inflate(R.layout.fragment_conta, container, false);
    }

}
