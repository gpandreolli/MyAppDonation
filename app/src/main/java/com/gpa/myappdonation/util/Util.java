package com.gpa.myappdonation.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.model.Usuario;


public class Util {

    private Activity activity;
    private int[] ids;
    static  String tipoUsuario;

    private static DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    public Util(Activity activity, int... ids) {
        this.activity = activity;
        this.ids = ids;
    }


    public void lockFields(boolean isToLock) {
        for (int id : ids) {
            setLockField(id, isToLock);
        }
    }

    private void setLockField(int fieldId, boolean isToLock) {
        activity.findViewById(fieldId).setEnabled(!isToLock);
    }

    public static int verificarTipoUsuario(String iud){


        DatabaseReference usuarios = referencia.child("Usuario").child(iud);

        usuarios.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario dadoUsuario = dataSnapshot.getValue(Usuario.class);
                tipoUsuario = dadoUsuario.getTipo_usua();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (tipoUsuario == "1") {
            return 1;
        }else {
            return 2;
        }
    }




}
