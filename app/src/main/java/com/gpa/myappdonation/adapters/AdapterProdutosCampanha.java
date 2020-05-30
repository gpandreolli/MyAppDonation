package com.gpa.myappdonation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.ProdutosCampanha;

import java.util.List;

public class AdapterProdutosCampanha extends RecyclerView.Adapter<AdapterProdutosCampanha.MyViewHolder> {
    private List<ProdutosCampanha> produtosCampanha;
    private Context context;
    private DatabaseReference produtoCampanhaRef;

    public AdapterProdutosCampanha(List<ProdutosCampanha> produtosCampanha, Context contextf) {
        this.produtosCampanha = produtosCampanha;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterProdutosCampanha.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produtos_campanha_add,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProdutosCampanha.MyViewHolder holder, int position) {

        ProdutosCampanha produtos = produtosCampanha.get(position);
        holder.nomeProduto.setText(produtos.getNomeProCampanha());

    }

    @Override
    public int getItemCount() {
        return produtosCampanha.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeProduto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeProduto = itemView.findViewById(R.id.txtNomeProdutoAdd);
        }
    }
}
