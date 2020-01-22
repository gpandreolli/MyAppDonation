package com.gpa.myappdonation.model;

public class Usuario {

    private String Uid;
    private String nome_usua;
    private String cpf_usua;
    private String rua_usua;
    private String bumero_usua;
    private String bairro_usua;
    private String cidade_usua;
    private String uf_usua;
    private String cep_usua;
    private String fone_usua;
    private String tipo_usua;

    public Usuario() {
    }

    public Usuario(String uid, String nome_usua, String cpf_usua, String rua_usua, String bumero_usua, String bairro_usua, String cidade_usua, String uf_usua, String cep_usua, String fone_usua, String tipo_usua) {
        Uid = uid;
        this.nome_usua = nome_usua;
        this.cpf_usua = cpf_usua;
        this.rua_usua = rua_usua;
        this.bumero_usua = bumero_usua;
        this.bairro_usua = bairro_usua;
        this.cidade_usua = cidade_usua;
        this.uf_usua = uf_usua;
        this.cep_usua = cep_usua;
        this.fone_usua = fone_usua;
        this.tipo_usua = tipo_usua;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getNome_usua() {
        return nome_usua;
    }

    public void setNome_usua(String nome_usua) {
        this.nome_usua = nome_usua;
    }

    public String getCpf_usua() {
        return cpf_usua;
    }

    public void setCpf_usua(String cpf_usua) {
        this.cpf_usua = cpf_usua;
    }

    public String getRua_usua() {
        return rua_usua;
    }

    public void setRua_usua(String rua_usua) {
        this.rua_usua = rua_usua;
    }

    public String getBumero_usua() {
        return bumero_usua;
    }

    public void setBumero_usua(String bumero_usua) {
        this.bumero_usua = bumero_usua;
    }

    public String getBairro_usua() {
        return bairro_usua;
    }

    public void setBairro_usua(String bairro_usua) {
        this.bairro_usua = bairro_usua;
    }

    public String getCidade_usua() {
        return cidade_usua;
    }

    public void setCidade_usua(String cidade_usua) {
        this.cidade_usua = cidade_usua;
    }

    public String getUf_usua() {
        return uf_usua;
    }

    public void setUf_usua(String uf_usua) {
        this.uf_usua = uf_usua;
    }

    public String getCep_usua() {
        return cep_usua;
    }

    public void setCep_usua(String cep_usua) {
        this.cep_usua = cep_usua;
    }

    public String getFone_usua() {
        return fone_usua;
    }

    public void setFone_usua(String fone_usua) {
        this.fone_usua = fone_usua;
    }

    public String getTipo_usua() {
        return tipo_usua;
    }

    public void setTipo_usua(String tipo_usua) {
        this.tipo_usua = tipo_usua;
    }
}


