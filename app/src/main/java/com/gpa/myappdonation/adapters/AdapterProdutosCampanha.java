package com.gpa.myappdonation.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.ProdutosCampanha;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

import java.util.List;

public class AdapterProdutosCampanha extends RecyclerView.Adapter<AdapterProdutosCampanha.MyViewHolder> {
    private List<ProdutosCampanha> produtosCampanha;
    private Context context;
    private DatabaseReference produtoCampanhaRef;
    ProdutosCampanha prodCampanha = new ProdutosCampanha();

    public AdapterProdutosCampanha(List<ProdutosCampanha> produtosCampanha, Context context) {
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
    public void onBindViewHolder(@NonNull AdapterProdutosCampanha.MyViewHolder holder, final int position) {
        //produtosCampanha.clear();
        ProdutosCampanha produtos = produtosCampanha.get(position);
        holder.nomeProduto.setText(produtos.getNomeProCampanha());
        holder.btnRemoveProdutoCampanha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeProdutoCampanha(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return produtosCampanha.size();
    }

    public ProdutosCampanha getItem(int position){
        return produtosCampanha.get(position);
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeProduto;
        Button btnRemoveProdutoCampanha;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeProduto = itemView.findViewById(R.id.txtNomeProdutoAdd);
            btnRemoveProdutoCampanha = itemView.findViewById(R.id.btnRemoveProdutoCampanha);
        }
    }

    private void removeProdutoCampanha(final int position) {
        new AlertDialog.Builder(context)
                .setTitle("Remover Produto")
                .setMessage("Deseja remover esse Produto da Campanha?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        produtosCampanha.remove(position);
                        notifyItemRemoved(position);
                        notifyDataSetChanged();

                    }
                })
                .setNegativeButton("Não",null).show();
    }

}
