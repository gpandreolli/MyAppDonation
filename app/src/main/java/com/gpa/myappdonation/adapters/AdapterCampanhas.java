package com.gpa.myappdonation.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DatabaseReference;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.activity.CampanhaActivity;
import com.gpa.myappdonation.model.Campanha;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

import java.util.List;

public class AdapterCampanhas extends RecyclerView.Adapter<AdapterCampanhas.MyViewHolder> {

    private List<Campanha> campanhas;
    private Context context;
    private DatabaseReference campanhaRef;

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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Campanha campanha = campanhas.get(position);
        if (campanha.getNomeCampanha() != null) {
            holder.txtNomeCampanhaLista.setText(campanha.getNomeCampanha());
        }
        else{
            holder.txtNomeCampanhaLista.setVisibility(View.INVISIBLE);
        }
        if (campanha.getStatus().equals("1")){
            holder.txtStatusCampanhaLista.setText("Ativa");
        }
        if (campanha.getPermanente().equals("1")){
            holder.txtPermanenteLista.setText("Campanha Permanente");
            holder.txtDataInicialCampanhaLista.setVisibility(View.INVISIBLE);
            holder.txtDataFinalCampanhaLista.setVisibility(View.INVISIBLE);
        }else{
            holder.txtDataInicialCampanhaLista.setVisibility(View.VISIBLE);
            holder.txtDataFinalCampanhaLista.setVisibility(View.VISIBLE);
            holder.txtPermanenteLista.setVisibility(View.INVISIBLE);
            holder.txtDataInicialCampanhaLista.setText(campanha.getDataInicial());
            holder.txtDataFinalCampanhaLista.setText(campanha.getDataFinal());
        }

        holder.btnEditaCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Campanha campanhaEdit = campanhas.get(position);
                String uid = campanhaEdit.getUid();
                Intent it = new Intent(context, CampanhaActivity.class);
                it.putExtra("uid",uid);
                context.startActivity(it);
            }
        });

        holder.btnExcluiCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removerCamapanha(position);
            }
        });

    }

    private void removerCamapanha(final int position) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Excluir Campanha")
                .setMessage("Deseja excluir essa campanha?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Campanha campanha = campanhas.get(position);
                        String idCampanha = campanha.getUid();
                        removeCampanha(idCampanha);
                        notifyItemRemoved(position);
                    }
                })
                .setNegativeButton("Não",null).show();
    }

    private void removeCampanha(String idCampanha) {
        campanhaRef = ConfiguracaoFirebase.getFirebase().child("Campanha")
                .child(idCampanha);

        campanhaRef.removeValue();


    }

    @Override
    public int getItemCount() {
        return campanhas.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView txtNomeCampanhaLista;
        TextView txtStatusCampanhaLista, txtPermanenteLista,txtDataInicialCampanhaLista,txtDataFinalCampanhaLista;
        Button btnEditaCampanha, btnExcluiCampanha;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNomeCampanhaLista = itemView.findViewById(R.id.txtNomeCampanhaLista);
            txtStatusCampanhaLista = itemView.findViewById(R.id.txtStatusCampanhaLista);
            txtPermanenteLista = itemView.findViewById(R.id.txtPermanenteLista);
            txtDataInicialCampanhaLista = itemView.findViewById(R.id.txtDataInicialCampanhaLista);
            txtDataFinalCampanhaLista = itemView.findViewById(R.id.txtDataFinalCampanhaLista);
            btnEditaCampanha = (Button) itemView.findViewById(R.id.btnEditaCampanha);
            btnExcluiCampanha = (Button) itemView.findViewById(R.id.btnExcluiCampanha);
        }
    }
}
