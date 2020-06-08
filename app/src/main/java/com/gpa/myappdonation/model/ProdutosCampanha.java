package com.gpa.myappdonation.model;

public class ProdutosCampanha {

    private String uid;
    private String uidProduto;
    private String nomeProCampanha;
    private String descProdCampanha;
    private String uidCampanha;


    public ProdutosCampanha() {
    }

    public ProdutosCampanha(String uid, String uidProduto, String nomeProCampanha, String descProdCampanha, String uidCampanha) {
        this.uid = uid;
        this.uidProduto = uidProduto;
        this.nomeProCampanha = nomeProCampanha;
        this.descProdCampanha = descProdCampanha;
        this.uidCampanha = uidCampanha;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUidProduto() {
        return uidProduto;
    }

    public void setUidProduto(String uidProduto) {
        this.uidProduto = uidProduto;
    }

    public String getNomeProCampanha() {
        return nomeProCampanha;
    }

    public void setNomeProCampanha(String nomeProCampanha) {
        this.nomeProCampanha = nomeProCampanha;
    }

    public String getDescProdCampanha() {
        return descProdCampanha;
    }

    public void setDescProdCampanha(String descProdCampanha) {
        this.descProdCampanha = descProdCampanha;
    }

    public String getUidCampanha() {
        return uidCampanha;
    }

    public void setUidCampanha(String uidCampanha) {
        this.uidCampanha = uidCampanha;
    }
}
