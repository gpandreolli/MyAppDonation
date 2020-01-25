package com.gpa.myappdonation.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Conta;

public class ContaAdapter extends ArrayAdapter<Conta> {

    Context context;
    int layoutResourceId;
    Conta data[]  = null;


    public ContaAdapter(@NonNull ValueEventListener context, int layoutResourceId, Conta data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ContaHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ContaHolder();
            //holder.imgIcon = (ImageView)row.findViewById(R.id.imgIcon);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtNomeConta);

            row.setTag(holder);
        }
        else
        {
            holder = (ContaHolder)row.getTag();
        }

        Conta conta = data[position];
        holder.txtTitle.setText(conta.getNome());

        return row;
    }

    static class ContaHolder
    {
        ImageView imgIcon;
        TextView txtTitle;
    }


}
