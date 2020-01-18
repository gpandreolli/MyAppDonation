package com.gpa.myappdonation.Model;

import java.io.StringBufferInputStream;
import java.security.PrivateKey;
import java.util.Objects;

public class Pessoa {

    private Long Id;
    private String Nome_RzSocial;
    private String Cpf_Cnpj;
    private String NomeFantasia;
    private String Rua;
    private String Numero;
    private String Bairro;
    private String CEP;
    private String Cidade;
    private String UF;
    private String TipoPessoa;//pessoa Fisica ou Juridica
    private String NivelUser;

    public Pessoa() {
    }

    public Pessoa(Long id, String nome_RzSocial, String cpf_Cnpj, String nomeFantasia, String rua, String numero, String bairro, String CEP, String cidade, String UF, String tipoPessoa, String nivelUser) {
        Id = id;
        Nome_RzSocial = nome_RzSocial;
        Cpf_Cnpj = cpf_Cnpj;
        NomeFantasia = nomeFantasia;
        Rua = rua;
        Numero = numero;
        Bairro = bairro;
        this.CEP = CEP;
        Cidade = cidade;
        this.UF = UF;
        TipoPessoa = tipoPessoa;
        NivelUser = nivelUser;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNome_RzSocial() {
        return Nome_RzSocial;
    }

    public void setNome_RzSocial(String nome_RzSocial) {
        Nome_RzSocial = nome_RzSocial;
    }

    public String getCpf_Cnpj() {
        return Cpf_Cnpj;
    }

    public void setCpf_Cnpj(String cpf_Cnpj) {
        Cpf_Cnpj = cpf_Cnpj;
    }

    public String getNomeFantasia() {
        return NomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        NomeFantasia = nomeFantasia;
    }

    public String getRua() {
        return Rua;
    }

    public void setRua(String rua) {
        Rua = rua;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }

    public String getTipoPessoa() {
        return TipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        TipoPessoa = tipoPessoa;
    }

    public String getNivelUser() {
        return NivelUser;
    }

    public void setNivelUser(String nivelUser) {
        NivelUser = nivelUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;
        Pessoa pessoa = (Pessoa) o;
        return getId().equals(pessoa.getId()) &&
                getNome_RzSocial().equals(pessoa.getNome_RzSocial()) &&
                getCpf_Cnpj().equals(pessoa.getCpf_Cnpj()) &&
                getNomeFantasia().equals(pessoa.getNomeFantasia()) &&
                getRua().equals(pessoa.getRua()) &&
                getNumero().equals(pessoa.getNumero()) &&
                getBairro().equals(pessoa.getBairro()) &&
                getCEP().equals(pessoa.getCEP()) &&
                getCidade().equals(pessoa.getCidade()) &&
                getUF().equals(pessoa.getUF()) &&
                getTipoPessoa().equals(pessoa.getTipoPessoa()) &&
                getNivelUser().equals(pessoa.getNivelUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome_RzSocial(), getCpf_Cnpj(), getNomeFantasia(), getRua(), getNumero(), getBairro(), getCEP(), getCidade(), getUF(), getTipoPessoa(), getNivelUser());
    }
}
