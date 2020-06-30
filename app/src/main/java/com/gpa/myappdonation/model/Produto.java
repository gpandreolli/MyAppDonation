package com.gpa.myappdonation.model;

public class Produto {

    private String uid;
    private String nome;
    private String descricao;
    private String uidInstituicao;

    public Produto() {
    }

    public Produto(String uid, String nome, String descricao, String uidInstituicao) {
        this.uid = uid;
        this.nome = nome;
        this.descricao = descricao;
        this.uidInstituicao = uidInstituicao;
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

    public String getUidInstituicao() {
        return uidInstituicao;
    }

    public void setUidInstituicao(String uidInstituicao) {
        this.uidInstituicao = uidInstituicao;
    }
}
