package com.gpa.myappdonation.model;

import com.google.firebase.database.DatabaseReference;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

import java.util.List;

public class Campanha {

     private String uid;
     private String uidInstituica;
     private String permanente;
     private String dataInicial;
     private String dataFinal;
     private String status;
     private List<ItemCampanha> itens;

    public Campanha() {
    }

    public Campanha(String uidInstituica) {
        this.uidInstituica = uidInstituica;
        setUidInstituica(uidInstituica);
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference campanhaRef = firebaseRef.child("campanha_instituicao").child(uidInstituica);
        setUid(campanhaRef.push().getKey());
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUidInstituica() {
        return uidInstituica;
    }

    public void setUidInstituica(String uidInstituica) {
        this.uidInstituica = uidInstituica;
    }

    public String getPermanente() {
        return permanente;
    }

    public void setPermanente(String permanente) {
        this.permanente = permanente;
    }

    public String getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(String dataInicial) {
        this.dataInicial = dataInicial;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemCampanha> getItens() {
        return itens;
    }

    public void setItens(List<ItemCampanha> itens) {
        this.itens = itens;
    }

    public void salvarCampanha() {

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference campanhaRef = firebaseRef.child("campanha_instituicao").child(getUidInstituica());
        campanhaRef.setValue(this);


    }
}
