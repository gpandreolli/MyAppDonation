package com.gpa.myappdonation.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Instituicao;

import java.util.List;

public class Adapter_instituicoes extends RecyclerView.Adapter<Adapter_instituicoes.MyViewHolder> {
    private List<Instituicao> instituicoes;
    private Context context;

    public Adapter_instituicoes(List<Instituicao> instituicoes, Context context) {
        this.instituicoes = instituicoes;
        this.context = context;
    }
    @NonNull
    @Override
    public Adapter_instituicoes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_instituicoes,parent,false);
        return new MyViewHolder(item);
    }
    @Override
    public void onBindViewHolder(@NonNull Adapter_instituicoes.MyViewHolder holder, int position) {
        Instituicao instituicao = instituicoes.get(position);
        holder.nomeInst.setText(instituicao.getNomeFantasia());
        holder.cidadeInst.setText(instituicao.getCidade());
        holder.ufInst.setText(instituicao.getUf());
    }

    @Override
    public int getItemCount() {
        return instituicoes.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeInst;
        TextView cidadeInst;
        TextView ufInst;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeInst = itemView.findViewById(R.id.txtNomeInstituicao);
            cidadeInst = itemView.findViewById(R.id.txtCidadeInstituicao);
            ufInst = itemView.findViewById(R.id.txtUfInstituicao);
        }
    }


}
