package com.gpa.myappdonation.view;

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
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

public class InicialActivityApoiador extends AppCompatActivity {

    private GridLayout griPrincipal;
    private FirebaseAuth auth;
    private TextView txtUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial_apoiador);
        griPrincipal = (GridLayout) findViewById(R.id.griPrincipal);
        auth = FirebaseAuth.getInstance();
        setSingleEvent(griPrincipal);
        txtUsuario = (TextView) findViewById(R.id.txtUsuario);
        setNomeUsuario();
    }

    private void setNomeUsuario() {

        DatabaseReference reference = ConfiguracaoFirebase.getFirebase().child("Usuario").child(ConfiguracaoFirebase.getIdUsuario()).child("nome_usua");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nome = dataSnapshot.getValue().toString();
                txtUsuario.setText(nome);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setSingleEvent(GridLayout griPrincipal) {
        //Loop pelos cards do Grid
        for (int i = 0; i < griPrincipal.getChildCount(); i++) {
            CardView cardView = (CardView) griPrincipal.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == 0) {
                        Intent intent = new Intent(InicialActivityApoiador.this, MinhasInstituicoesActivity.class);
                        startActivity(intent);
                    } else if (finalI == 1) {
                        Intent intent = new Intent(InicialActivityApoiador.this, Lista_inst.class);
                        intent.putExtra("info", "This is activity from card item index  " + finalI);
                        Toast.makeText(getBaseContext(), "CARD: " + finalI, Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    } else if (finalI == 3) {
                        Intent intent = new Intent(InicialActivityApoiador.this, MinhasDoacoesActivity.class);
                        intent.putExtra("info", "This is activity from card item index  " + finalI);
                        Toast.makeText(getBaseContext(), "Menu: " + finalI, Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    } else if (finalI == 5) {
                        auth.signOut();
                        finish();
                        Intent i = new Intent(InicialActivityApoiador.this, LoginActivity.class);
                        startActivity(i);
                        Toast.makeText(getBaseContext(), "Usuário deslogado: ", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
