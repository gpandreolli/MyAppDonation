package com.gpa.myappdonation.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.gpa.myappdonation.R;

public class InicialActivityApoiador extends AppCompatActivity {

    private GridLayout griPrincipal;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial_apoiador);
        griPrincipal = (GridLayout) findViewById(R.id.griPrincipal);
        auth = FirebaseAuth.getInstance();
        setSingleEvent(griPrincipal);
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
                        Intent intent = new Intent(InicialActivityApoiador.this, ListaInstActivity.class);
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
