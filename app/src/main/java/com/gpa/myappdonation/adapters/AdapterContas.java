package com.gpa.myappdonation.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.gpa.myappdonation.activity.CadastrarConta;
import com.gpa.myappdonation.model.Conta;

import java.util.List;

public class AdapterContas extends RecyclerView.Adapter<AdapterContas.MyViewHolder> {

    private List<Conta> contas;
    private Context context;

    public AdapterContas(List<Conta> contas, Context context) {
        this.contas = contas;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_contas, parent, false);
        return new MyViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        Conta conta = contas.get(position);
        holder.nomeConta.setText(conta.getNome());
        holder.numeroConta.setText(conta.getNumero_conta());
        holder.agenciaConta.setText(conta.getAgencia());
        holder.bancoConta.setText(conta.getBanco());
       // holder.setListners(position);

        holder.btnEditaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editaConta(position);
                //Toast.makeText(context, "Teste do botão: 1", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void editaConta(int position) {
        Intent it = new Intent(context, CadastrarConta.class);
        it.putExtra("position",position);
        context.startActivity(it);
    }

    @Override
    public int getItemCount() {
        return contas.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView nomeConta;
        TextView numeroConta;
        TextView agenciaConta;
        TextView bancoConta;
        Button btnEditaConta;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeConta = (TextView) itemView.findViewById(R.id.txtNome_Conta);
            numeroConta = (TextView) itemView.findViewById(R.id.txtNumero_Conta);
            agenciaConta = (TextView) itemView.findViewById(R.id.txtAgencia_Conta);
            bancoConta = (TextView) itemView.findViewById(R.id.txtNome_Banco);
            btnEditaConta = (Button) itemView.findViewById(R.id.btnEditaConta);
        }

        /*public void setListners(int position) {

            btnEditaConta.setOnClickListener(MyViewHolder.this);
            nomeConta.setOnClickListener(MyViewHolder.this);


        }*/

        /*@Override
        public void onClick(View view) {
            if (view.getId() == R.id.btnEditaConta) {
                Toast.makeText(context, "Teste do botão: 1", Toast.LENGTH_LONG).show();

            } else if (view.getId() == R.id.txtNome_Conta) {
                Toast.makeText(context, "Teste do botão: 2", Toast.LENGTH_LONG).show();
            }
        }*/


    }


}
