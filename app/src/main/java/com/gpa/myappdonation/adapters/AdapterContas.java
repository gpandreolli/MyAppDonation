package com.gpa.myappdonation.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.google.firebase.database.DatabaseReference;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.activity.CadastrarConta;
import com.gpa.myappdonation.model.Conta;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

import java.util.List;

public class AdapterContas extends RecyclerView.Adapter<AdapterContas.MyViewHolder> {

    private List<Conta> contas;
    private Context context;
    private DatabaseReference contaRef;

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
        holder.btnExcluiConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeConta(position);
            }
        });

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

    private void removeConta(final int position) {
        new androidx.appcompat.app.AlertDialog.Builder(context)
                .setTitle("Excluir Conta")
                .setMessage("Deseja excluir essa conta?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Conta conta = contas.get(position);
                        String idConta = conta.getUid();
                        removerConta(idConta);
                    }
                })
                .setNegativeButton("Não",null).show();
    }

    private void removerConta(String idConta) {
        contaRef = ConfiguracaoFirebase.getFirebase().child("Conta")
                .child(ConfiguracaoFirebase.getIdUsuario())
                .child(idConta);

        contaRef.removeValue();

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
        TextView nomeConta, numeroConta, agenciaConta, bancoConta;
        Button btnEditaConta, btnExcluiConta;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeConta = (TextView) itemView.findViewById(R.id.txtNome_Conta);
            numeroConta = (TextView) itemView.findViewById(R.id.txtNumero_Conta);
            agenciaConta = (TextView) itemView.findViewById(R.id.txtAgencia_Conta);
            bancoConta = (TextView) itemView.findViewById(R.id.txtNome_Banco);
            btnEditaConta = (Button) itemView.findViewById(R.id.btnEditaConta);
            btnExcluiConta = (Button) itemView.findViewById(R.id.btnExcluiConta);
        }
    }


}
