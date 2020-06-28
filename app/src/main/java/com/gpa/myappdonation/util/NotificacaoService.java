package com.gpa.myappdonation.util;


import com.gpa.myappdonation.model.NotificacaoDados;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificacaoService {


    @Headers({
            "Authorization:key=AAAAghaua1w:APA91bFESTLElrTVhyQULgmsYxgRgrPMVcIIXg4ZF3u8uq1Df_TtDqTWelha0XfHkwganiE34FSPptHqanGQepYGy2EiCmqn7JmSufq1Mvh4RCs3UCPYqJIg-fG06uUjcH0X3xb6M4bu",
            "Content-Type:application/json"
    })

    @POST("send")
    Call<NotificacaoDados> salvarNotificacao(@Body NotificacaoDados notificacaoDados);
}
