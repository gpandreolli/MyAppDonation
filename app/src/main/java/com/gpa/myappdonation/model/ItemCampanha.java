package com.gpa.myappdonation.model;

public class ItemCampanha {

    private String uid;
    private String nome;
    private String descricao;

    public ItemCampanha() {
    }

    public ItemCampanha(String uid, String nome, String descricao) {
        this.uid = uid;
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
