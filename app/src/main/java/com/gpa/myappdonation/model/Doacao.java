package com.gpa.myappdonation.model;

import com.blackcat.currencyedittext.CurrencyEditText;

public class Doacao {


    private String uid;
    private String uidInstituicao;
    private String nomeInstituicao;
    private String data;
    private String valor;
    private CurrencyEditText cet;
    private String produto;

    public Doacao() {
    }

    public Doacao(String uid, String uidInstituicao, String nomeInstituicao, String data, String valor, CurrencyEditText cet, String produto) {
        this.uid = uid;
        this.uidInstituicao = uidInstituicao;
        this.nomeInstituicao = nomeInstituicao;
        this.data = data;
        this.valor = valor;
        this.cet = cet;
        this.produto = produto;
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

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }

    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public CurrencyEditText getCet() {
        return cet;
    }

    public void setCet(CurrencyEditText cet) {
        this.cet = cet;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    /* public String getValorFormatado(){

        Long valorDaLinha = getValor();
        String valorFormatado = cet.formatCurrency(Long.toString(valorDaLinha));
        return  valorFormatado;
    }*/
}
