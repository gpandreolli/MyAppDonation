package com.gpa.myappdonation.model;

public class Conta {

    private String uid;
    private String nome;
    private String banco;
    private String numeroBanco;
    private String agencia;
    private String numero_conta;
    private String id_instituicao;

    public Conta(String uid, String nome, String banco, String numeroBanco, String agencia, String numero_conta, String id_instituicao) {
        this.uid = uid;
        this.nome = nome;
        this.banco = banco;
        this.numeroBanco = numeroBanco;
        this.agencia = agencia;
        this.numero_conta = numero_conta;
        this.id_instituicao = id_instituicao;
    }

    public Conta() {
    }

    public Conta(String nome, String banco, String numero_conta) {
        this.nome = nome;
        this.banco = banco;
        this.numero_conta = numero_conta;
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

    public String getNumeroBanco() {
        return numeroBanco;
    }

    public void setNumeroBanco(String numeroBanco) {
        this.numeroBanco = numeroBanco;
    }
}
