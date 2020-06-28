package com.gpa.myappdonation.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class NotificacaoDados {


    @NonNull
    private String to;
    private Notificacao notification;

    public NotificacaoDados(String to, Notificacao notificacao) {
        this.to = to;
        this.notification = notificacao;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notificacao getNotificacao() {
        return notification;
    }

    public void setNotificacao(Notificacao notificacao) {
        this.notification = notificacao;
    }
}
