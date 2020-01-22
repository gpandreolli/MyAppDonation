package com.gpa.myappdonation.model;

public class Conta {

    private String Uid;
    private String nome;
    private String banco;
    private String agencia;
    private String numero_conta;

    public Conta(String uid, String nome, String banco, String agencia, String numero_conta) {
        Uid = uid;
        this.nome = nome;
        this.banco = banco;
        this.agencia = agencia;
        this.numero_conta = numero_conta;
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
}
