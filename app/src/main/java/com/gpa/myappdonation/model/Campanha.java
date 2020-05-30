package com.gpa.myappdonation.model;

import com.google.firebase.database.DatabaseReference;
import com.gpa.myappdonation.util.ConfiguracaoFirebase;

import java.util.List;
import java.util.UUID;

public class Campanha {

     private String uid;
     private String nomeCampanha;
     private String uidInstituicao;
     private String permanente;
     private String dataInicial;
     private String dataFinal;
     private String status;
     private List<ItemCampanha> itens;

    public Campanha() {
    }

    public Campanha(String uidInstituicao) {
        this.uidInstituicao = uidInstituicao;
        setUidInstituicao(uidInstituicao);
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference campanhaRef = firebaseRef.child("campanha_instituicao").child(uidInstituicao);
        setUid(campanhaRef.push().getKey());
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUidInstituicao() {
        return uidInstituicao;
    }

    public void setUidInstituicao(String uidInstituicao) {
        this.uidInstituicao = uidInstituicao;
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

    public String getNomeCampanha() {
        return nomeCampanha;
    }

    public void setNomeCampanha(String nomeCampanha) {
        this.nomeCampanha = nomeCampanha;
    }

    public void salvarCampanha() {

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference campanhaRef = firebaseRef.child("Campanha").child((UUID.randomUUID().toString()));
        campanhaRef.setValue(this);


    }
}
