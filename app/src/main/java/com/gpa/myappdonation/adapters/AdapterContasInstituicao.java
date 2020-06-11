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
import com.gpa.myappdonation.model.Conta;

import java.util.List;

public class AdapterContasInstituicao extends RecyclerView.Adapter<AdapterContasInstituicao.MyViewHolder> {

    private List<Conta> contas;
    private Context context;

    public AdapterContasInstituicao(List<Conta> contas, Context context) {
        this.contas = contas;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_conta_instituicao, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Conta conta = contas.get(position);
        holder.nomeConta.setText(conta.getNome());
        holder.numeroConta.setText(conta.getNumero_conta());
        holder.agenciaConta.setText(conta.getAgencia());
        holder.bancoConta.setText(conta.getBanco());

    }

    @Override
    public int getItemCount() {
        return contas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nomeConta, numeroConta, agenciaConta, bancoConta;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeConta = (TextView) itemView.findViewById(R.id.txtNome_ContaInstituicao);
            numeroConta = (TextView) itemView.findViewById(R.id.txtNumero_ContaInstituicao);
            agenciaConta = (TextView) itemView.findViewById(R.id.txtAgencia_ContaInstituicao);
            bancoConta = (TextView) itemView.findViewById(R.id.txtNome_BancoInstituicao);

        }
    }
}
