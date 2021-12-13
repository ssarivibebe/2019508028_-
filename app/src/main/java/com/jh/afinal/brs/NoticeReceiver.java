package com.jh.afinal.brs;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.net.Uri;

import com.jh.afinal.MainActivity;
import com.jh.afinal.MyApplication;
import com.jh.afinal.R;

public class NoticeReceiver extends BroadcastReceiver {     // 공지사항 방송 수신자

    // [구현사항 6번. 방송 수신자 사용]

    @Override
    public void onReceive(Context context, Intent intent) {

        // 공지사항 내용을 확인한다
        String content = intent.getStringExtra("content");
        if (content == null) {
            return;
        }

        // 노티피케이션 작성
        Notification notification;

        // 노티피케이션 클릭 시 네이버가 실행되도록 한다
        Intent activityIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.naver.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                activityIntent,
                PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        Icon smallIcon = Icon.createWithResource(context, R.drawable.ic_notice);

        // 노티피케이션을 만든다
        notification = new Notification.Builder(context,
                MyApplication.CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(smallIcon)
                .setContentText(content)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .build();

        // 노티피케이션 띄우기
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100, notification);
    }

}