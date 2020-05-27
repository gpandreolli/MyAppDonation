package com.gpa.myappdonation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.R;
import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

public class IncialActivityInsti extends AppCompatActivity {

    private GridLayout mainGrid;
    private FirebaseAuth auth;
    private TextView txtInstituicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incial_insti);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        auth = FirebaseAuth.getInstance();
        txtInstituicao = (TextView) findViewById(R.id.txtNomeInstituicao);

        setSingleEvent(mainGrid);
        setNomeInstituicao();

    }

    private void setNomeInstituicao() {

        DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("Instituicao").child(ConfiguracaoFirebase.getIdUsuario());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Instituicao inst = dataSnapshot.getValue(Instituicao.class);
                String nomefant = inst.getNomeFantasia();
                txtInstituicao.setText(nomefant);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setSingleEvent(GridLayout mainGrid) {

        for (int i = 0; i < mainGrid.getChildCount(); i++) {

            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (finalI == 0) {
                        Intent intent = new Intent(IncialActivityInsti.this, ListaContaActivity.class);
                        intent.putExtra("info", "This is activity from card item index  " + finalI);
                        Toast.makeText(getBaseContext(), "Cadastro de Contas: ", Toast.LENGTH_LONG).show();
                        startActivity(intent);

                    }else if(finalI == 1){
                        Intent i = new Intent(IncialActivityInsti.this, ListaCampanhasActivity.class);
                        startActivity(i);
                        Toast.makeText(getBaseContext(), "Campanhas", Toast.LENGTH_LONG).show();
                    }else if(finalI == 5){
                        auth.signOut();
                        finish();
                        Intent i = new Intent(IncialActivityInsti.this, LoginActivity.class);
                        startActivity(i);
                        Toast.makeText(getBaseContext(), "Usuario deslogado", Toast.LENGTH_LONG).show();
                    }else if (finalI == 2 ){
                        Intent i = new Intent(IncialActivityInsti.this, CadastrarInstituicaoActivity.class);
                        i.putExtra("info",finalI);
                        startActivity(i);
                        Toast.makeText(getBaseContext(), "Cadastro de Contas", Toast.LENGTH_LONG).show();

                    }else if (finalI == 3){
                        Intent intent = new Intent(IncialActivityInsti.this, UsuarioActivity.class);
                        intent.putExtra("info",finalI);
                        startActivity(intent);
                    }
                    else if (finalI == 4){
                        Intent intent = new Intent(IncialActivityInsti.this, ListaProdutoActivity.class);
                        intent.putExtra("info",finalI);
                        startActivity(intent);
                    }


                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
