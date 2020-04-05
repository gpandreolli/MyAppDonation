package com.gpa.myappdonation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gpa.myappdonation.R;

import com.gpa.myappdonation.model.Instituicao;

import java.util.ArrayList;

public class ListaAdapterInstituicao extends ArrayAdapter<Instituicao> {

    private Context context;
    int layoutResourceId;
    private ArrayList<Instituicao> listaInstituicao;

    public ListaAdapterInstituicao(Context context, int layoutResourceId, ArrayList<Instituicao> listaInstituicao){
        super(context,layoutResourceId,listaInstituicao);
        this.context = context;
        this.listaInstituicao = listaInstituicao;
        this.layoutResourceId = layoutResourceId;
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        Instituicao InstituicaoPosicao = this.listaInstituicao.get(position);
        convertView = LayoutInflater.from(this.context).inflate(R.layout.listview_inst_linha,null);

        TextView txtInstituicao = (TextView) convertView.findViewById(R.id.txtInstituicao);
        txtInstituicao.setText(InstituicaoPosicao.getNomeFantasia());

        TextView txtCidade = (TextView) convertView.findViewById(R.id.txtCidade);
        txtCidade.setText(InstituicaoPosicao.getCidade());

        TextView txtUf = (TextView) convertView.findViewById(R.id.txtUf);
        txtUf.setText(InstituicaoPosicao.getUf());



        return convertView;
    }


}
