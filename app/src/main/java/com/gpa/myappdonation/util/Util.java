package com.gpa.myappdonation.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gpa.myappdonation.model.Instituicao;
import com.gpa.myappdonation.model.Usuario;


public class Util {

    private Activity activity;
    private int[] ids;
    static String tipoUsuario;
    static String mensagem;
    private static DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private static Query queryInstituicao;
    public static String nomeInstituicao;

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




}
