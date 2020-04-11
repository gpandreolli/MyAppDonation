package com.gpa.myappdonation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Doacao;

import java.util.List;

public class AdapterMinhasDoacoes extends RecyclerView.Adapter<AdapterMinhasDoacoes.MyViewHolder>  {

    private List<Doacao> doacoes;
    private Context context;

    public AdapterMinhasDoacoes(List<Doacao> doacoes, Context context){
        this.doacoes = doacoes;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterMinhasDoacoes.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_minhas_doacoes,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Doacao doacao = doacoes.get(position);
        //holder.idInstituicao.setText(doacao.getUidInstituicao().toString());
        holder.nomeInst.setText(doacao.getNomeInstituicao().toString());
        holder.dataDoacao.setText(doacao.getData().toString());
        holder.valorDoacao.setText(doacao.getValor().toString());

    }

    @Override
    public int getItemCount() {
        return doacoes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //TextView idInstituicao;
        TextView nomeInst;
        TextView dataDoacao;
        TextView valorDoacao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeInst = itemView.findViewById(R.id.txtNomeInstituicaoDoacao);
            dataDoacao = itemView.findViewById(R.id.txtDataRealizadaDoacao);
            valorDoacao = itemView.findViewById(R.id.txtValorRealizadoDoacao);
        }
    }
}
