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

        //Set Event
        setSingleEvent(griPrincipal);
        //setToggleEvent(mainGrid);


    }

    private void setSingleEvent(GridLayout griPrincipal) {
        //Loop all child item of Main Grid
        for (int i = 0; i < griPrincipal.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) griPrincipal.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (finalI == 1) {
                        Intent intent = new Intent(InicialActivityApoiador.this, ListaInstActivity.class);
                        intent.putExtra("info", "This is activity from card item index  " + finalI);
                        Toast.makeText(getBaseContext(), "CARD: "+finalI, Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }else if(finalI == 5){
                        auth.signOut();
                        finish();
                        Intent i = new Intent(InicialActivityApoiador.this, LoginActivity.class);
                        startActivity(i);
                        Toast.makeText(getBaseContext(), "usuario deslogado: ", Toast.LENGTH_LONG).show();


                    }

                }
            });
        }
    }
}
