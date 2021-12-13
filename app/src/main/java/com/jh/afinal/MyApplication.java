package com.jh.afinal;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class MyApplication extends Application {

    public static final String CHANNEL_NAME = "LINFITNER";
    public static final String CHANNEL_ID = "com.jh.afinal.notice";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    // notification 채널 생성

    private void createNotificationChannel() {

        // 공지사항 알림 채널을 생성한다
        NotificationChannel noticeChannel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
        );

        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(noticeChannel);
    }
}
