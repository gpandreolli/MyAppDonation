package com.gpa.myappdonation.model;

import com.blackcat.currencyedittext.CurrencyEditText;

public class Doacao {


    private String uid;
    private String uidInstituicao;
    private String nomeInstituicao;
    private String data;
    private String valor;
    private CurrencyEditText cet;

    public Doacao() {
    }

    public Doacao(String uid, String uidInstituicao, String nomeInstituicao,String data, String valor) {
        this.uid = uid;
        this.uidInstituicao = uidInstituicao;
        this.nomeInstituicao = nomeInstituicao;
        this.data = data;
        this.valor = valor;
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

   /* public String getValorFormatado(){

        Long valorDaLinha = getValor();
        String valorFormatado = cet.formatCurrency(Long.toString(valorDaLinha));
        return  valorFormatado;
    }*/
}
