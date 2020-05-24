package com.gpa.myappdonation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Campanha;

import java.util.List;

public class AdapterCampanhas extends RecyclerView.Adapter<AdapterCampanhas.MyViewHolder> {

    private List<Campanha> campanhas;
    private Context context;

    public AdapterCampanhas(List<Campanha> campanhas, Context context) {
        this.campanhas = campanhas;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterCampanhas.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_camp,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Campanha campanha = campanhas.get(position);
        holder.txtNomeCampanhaLista.setText(campanha.getNomeCampanha());
        holder.txtStatusCampanhaLista.setText(campanha.getStatus());
        holder.txtPermanenteLista.setText(campanha.getPermanente());
        holder.txtDataInicialCampanhaLista.setText(campanha.getDataInicial());
        holder.txtDataFinalCampanhaLista.setText(campanha.getDataFinal());
    }

    @Override
    public int getItemCount() {
        return campanhas.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView txtNomeCampanhaLista;
        TextView txtStatusCampanhaLista, txtPermanenteLista,txtDataInicialCampanhaLista,txtDataFinalCampanhaLista;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNomeCampanhaLista = itemView.findViewById(R.id.txtNomeCampanhaLista);
            txtStatusCampanhaLista = itemView.findViewById(R.id.txtStatusCampanhaLista);
            txtPermanenteLista = itemView.findViewById(R.id.txtPermanenteLista);
            txtDataInicialCampanhaLista = itemView.findViewById(R.id.txtDataInicialCampanhaLista);
            txtDataFinalCampanhaLista = itemView.findViewById(R.id.txtDataFinalCampanhaLista);
        }
    }
}
