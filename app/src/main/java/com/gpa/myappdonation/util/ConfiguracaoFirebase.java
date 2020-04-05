package com.gpa.myappdonation.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth referenciaAutenticacao;

    public static String getIdUsuario(){
        FirebaseAuth autenticacao = getFirebaseAutenticacao();
        return autenticacao.getCurrentUser().getUid();

    }

    public static DatabaseReference getFirebase(){
        if( referenciaFirebase == null ){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        if( referenciaAutenticacao == null ){
            referenciaAutenticacao = FirebaseAuth.getInstance();
        }
        return referenciaAutenticacao;
    }


}


