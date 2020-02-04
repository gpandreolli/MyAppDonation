package com.gpa.myappdonation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Conta;

import java.util.ArrayList;

public class ListaAdapterConta extends ArrayAdapter<Conta> {

    private Context context;
    int layoutResourceId;
    private ArrayList<Conta> listaConta;

    public ListaAdapterConta(Context context, int layoutResourceId, ArrayList<Conta> listaConta){
        super(context,layoutResourceId,listaConta);
        this.context = context;
        this.listaConta = listaConta;
        this.layoutResourceId = layoutResourceId;
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        Conta contaPosicao = this.listaConta.get(position);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.listview_conta_linha,null);

        TextView txtNomeConta = (TextView) convertView.findViewById(R.id.txtNomeConta);
        txtNomeConta.setText(contaPosicao.getNome());

        TextView txtNumeroConta = (TextView) convertView.findViewById(R.id.txtNumeroConta);
        txtNumeroConta.setText(contaPosicao.getNumero_conta());

        TextView txtBanco = (TextView) convertView.findViewById(R.id.txtBanco);
        txtBanco.setText(contaPosicao.getBanco());

        return convertView;
    }
}
