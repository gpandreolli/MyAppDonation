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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.activity.CadastrarProdutoActivity;
import com.gpa.myappdonation.model.Produto;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

import java.util.List;

public class AdapterProdutos extends RecyclerView.Adapter<AdapterProdutos.MyViewHolder> {

    private List<Produto> produtos;
    private Context context;
    private DatabaseReference produtoRef;


    public AdapterProdutos(List<Produto> produtos, Context context) {
        this.produtos = produtos;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_produtos,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Produto produto = produtos.get(position);
        holder.nomeProduto.setText(produto.getNome());
        holder.descricaoProduto.setText(produto.getDescricao());
        holder.btnEditaProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editaProduto(position);
            }
        });

        holder.btnExcluiProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               removeProduto(position);

            }
        });
    }

    private void removeProduto(final int position) {
        new AlertDialog.Builder(context)
                .setTitle("Remover Produto")
                .setMessage("Deseja remover esse Produto?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Produto produto = produtos.get(position);
                        String idProduto = produto.getUid();
                        removeProduto(idProduto);
                        notifyItemRemoved(position);
                    }
                })
                .setNegativeButton("Não",null).show();
    }

    private void removeProduto(String idProduto) {
        produtoRef = ConfiguracaoFirebase.getFirebase()
                .child("Produto")
                .child(idProduto);

        produtoRef.removeValue();

    }

    private void editaProduto(int position) {
        Produto produtoEdit = produtos.get(position);
        String uid = produtoEdit.getUid();
        Intent it = new Intent(context, CadastrarProdutoActivity.class);
        it.putExtra("uid",uid);
        context.startActivity(it);
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nomeProduto;
        TextView descricaoProduto;
        Button btnEditaProduto;
        Button btnExcluiProduto;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeProduto = itemView.findViewById(R.id.txtNomeProduto);
            descricaoProduto = itemView.findViewById(R.id.txtDescricaoProduto);
            btnEditaProduto = (Button) itemView.findViewById(R.id.btnEditaProduto);
            btnExcluiProduto = (Button) itemView.findViewById(R.id.btnExcluiProduto);
        }
    }
}
