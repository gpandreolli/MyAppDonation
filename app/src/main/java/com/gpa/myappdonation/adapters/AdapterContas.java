package com.gpa.myappdonation.adapters;

import android.app.AlertDialog;
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
        final Conta conta = contas.get(position);
        holder.nomeConta.setText(conta.getNome());
        holder.numeroConta.setText(conta.getNumero_conta());
        holder.agenciaConta.setText(conta.getAgencia());
        holder.bancoConta.setText(conta.getBanco());
       // holder.setListners(position);

        holder.btnEditaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Conta contaEdit = contas.get(position);
                String uid = contaEdit.getUid();
                Intent it = new Intent(context, CadastrarConta.class);
                it.putExtra("uid",uid);
                context.startActivity(it);

            }
        });

        holder.nomeConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialog = new AlertDialog.Builder(view.getContext())
                        .setTitle("Dados da Conta")
                        .setMessage(getConta(conta))
                        .setPositiveButton("OK", null)
                        .show();
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });



    }

    private String getConta(Conta conta) {
        return "Conta: " + conta.getNome()  + "\n" +
                "Banco: " + conta.getBanco() + "\n" +
                "Agência: " + conta.getAgencia() + "\n" +
                "Número da Conta: " + conta.getNumero_conta() + "\n" ;
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
    }


}
