package com.gpa.myappdonation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Conta;

import java.util.List;

public class AdapterContas extends RecyclerView.Adapter<AdapterContas.MyViewHolder>  {

    private List<Conta> contas;
    private Context context;

    public AdapterContas(List<Conta> contas, Context context) {
        this.contas = contas;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_contas,parent,false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Conta conta = contas.get(position);
            holder.nomeConta.setText(conta.getNome());
            holder.numeroConta.setText(conta.getNumero_conta());
            holder.agenciaConta.setText(conta.getAgencia());
            holder.bancoConta.setText(conta.getBanco());
            holder.setListners(position);
    }

    @Override
    public int getItemCount() {
        return contas.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nomeConta;
        TextView numeroConta;
        TextView agenciaConta;
        TextView bancoConta;
        Button btnEditaConta;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeConta = itemView.findViewById(R.id.txtNome_Conta);
            numeroConta = itemView.findViewById(R.id.txtNumero_Conta);
            agenciaConta = itemView.findViewById(R.id.txtAgencia_Conta);
            bancoConta = itemView.findViewById(R.id.txtNome_Banco);
            btnEditaConta = itemView.findViewById(R.id.btnEditaConta);
        }

        public void setListners(int position) {
            btnEditaConta.setOnClickListener(MyViewHolder.this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnEditaConta:
                    Toast.makeText(context,"Conta na posição editada: ", Toast.LENGTH_LONG).show();
            }
        }
    }
}
