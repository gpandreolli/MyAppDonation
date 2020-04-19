package com.gpa.myappdonation.model;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Instituicao {

    private String Uid;
    private String razaoSocial;
    private String cnpj;
    private String nomeFantasia;
    private String rua;
    private String numero;
    private String bairro;
    private String cep;
    private String complemento;
    private String cidade;
    private String uf;
    private String telefone;
    private String email;
    private String usuario;

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Instituicao");
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth auth;
    private String uidUsuario;

    public Instituicao() {
    }

   /* public Instituicao(String Uid, String nomeFantasia, String cidade, String uf) {
        this.nomeFantasia = nomeFantasia;
        this.cidade = cidade;
        this.uf = uf;
        this.Uid = Uid;
    }*/

    public Instituicao(String uid, String razaoSocial, String cnpj, String nomeFantasia, String rua, String numero, String bairro, String cep, String complemento, String cidade, String uf, String telefone, String email, String usuario) {
        Uid = uid;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cep = cep;
        this.complemento = complemento;
        this.cidade = cidade;
        this.uf = uf;
        this.telefone = telefone;
        this.email = email;
        this.usuario = usuario;
    }

    public Instituicao(String Uid, String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
        this.Uid = Uid;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public void addInstituicao() {

        //Instituicao inst = new Instituicao();
        auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = auth.getCurrentUser();
        uidUsuario = currentUser.getUid();
        String id_inst = referencia.child("Instituicao").child(getUid()).toString();
        String teste = id_inst;
        referencia.child("Inst_Usua").child("id_usua").setValue(uidUsuario);
        referencia.child("Inst_Usua").child("id_inst").setValue(teste);


    }

    @Override
    public String toString() {
        return nomeFantasia;
    }
}
