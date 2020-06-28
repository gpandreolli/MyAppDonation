package com.gpa.myappdonation.util;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;


import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gpa.myappdonation.R;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public MyFirebaseMessagingService() {
        super();
    }

    @Override
    public void onMessageReceived(RemoteMessage notificacao) {

        super.onMessageReceived(notificacao);

        if (notificacao.getData() != null){
            String titulo = notificacao.getData().get("title");
            String texto = notificacao.getData().get("body");
            enviarNotificacao(titulo,texto);

        }
    }

    private void enviarNotificacao(String titulo, String texto) {
        String canal = getString(R.string.default_notification_channel_id);
        Uri somNotificacao = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(this,canal)
                .setContentTitle(titulo)
                .setContentText(texto)
                .setSmallIcon(R.drawable.logo_donation_sem_texto)
                .setSound(somNotificacao);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificacao.build());

    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }

    @Override
    public void onMessageSent(String s) {
        super.onMessageSent(s);
    }

    @Override
    public void onSendError(String s, Exception e) {
        super.onSendError(s, e);
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
    }
}
