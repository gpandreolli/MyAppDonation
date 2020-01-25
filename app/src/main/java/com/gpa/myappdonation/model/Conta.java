package com.gpa.myappdonation.model;

public class Conta {

    private String Uid;
    private String nome;
    private String banco;
    private String agencia;
    private String numero_conta;
    private String id_instituicao;

    public Conta(String uid, String nome, String banco, String agencia, String numero_conta, String id_instituicao) {
        Uid = uid;
        this.nome = nome;
        this.banco = banco;
        this.agencia = agencia;
        this.numero_conta = numero_conta;
        this.id_instituicao = id_instituicao;
    }

    public Conta() {
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumero_conta() {
        return numero_conta;
    }

    public void setNumero_conta(String numero_conta) {
        this.numero_conta = numero_conta;
    }

    public String getId_instituicao() {
        return id_instituicao;
    }

    public void setId_instituicao(String id_instituicao) {
        this.id_instituicao = id_instituicao;
    }
}
