package com.gpa.myappdonation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Instituicao;

import java.util.List;

public class AdapterInstituicoesNaoAvaliadas extends RecyclerView.Adapter<AdapterInstituicoesNaoAvaliadas.MyViewHolder> {

    private List<Instituicao> instituicoes;
    private Context context;

    public AdapterInstituicoesNaoAvaliadas(List<Instituicao> instituicoes, Context context) {
        this.instituicoes = instituicoes;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterInstituicoesNaoAvaliadas.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_instituicoes_nao_avaliadas,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Instituicao instituicao = instituicoes.get(position);
        holder.nomeInst.setText(instituicao.getNomeFantasia());
        holder.cidadeInst.setText(instituicao.getCidade());
        holder.ufInst.setText(instituicao.getUf());

    }

    @Override
    public int getItemCount() {
        return instituicoes.size();
    }

    public class MyViewHolder   extends RecyclerView.ViewHolder{

        TextView nomeInst;
        TextView cidadeInst;
        TextView ufInst;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeInst = itemView.findViewById(R.id.txtNomeInstNaoAvaliada);
            cidadeInst = itemView.findViewById(R.id.txtCidadeInstNaoAvaliada);
            ufInst = itemView.findViewById(R.id.txtUfInstNaoAvaliada);
        }
    }
}
