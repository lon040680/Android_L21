package com.chien.mynotifction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE =300; //請求碼
    public static final String NOTIFY_ID ="ACN"; //識別標籤

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showNotification(View view) {
        //點通知後 發生的事(實際第五步)
        Intent it = new Intent(getApplicationContext(), MainActivity.class);

        //暫緩執行 Intent(實際第四步)
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(),
                REQUEST_CODE,
                it,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        //管理器
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //建立並設定通知內容 (實際第一步)
        //setDefaults(NotificationCompat.DEFAULT_ALL) 這個會自動預設發出的內容 如果上面沒寫
        Notification.Builder builder = new Notification.Builder(this).
                setContentTitle("美女來找你!!").
                setContentText("她想你了").
                setSmallIcon(android.R.drawable.btn_star_big_on).
                setDefaults(NotificationCompat.DEFAULT_ALL).
                setContentIntent(pendingIntent);

        //建立頻道 (由manager管理)(O 是英文大寫 是Oreo)(實際第二步)
        //NotificationManager.IMPORTANCE_DEFAULT 優先等級
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    NOTIFY_ID,
                    "TEST",//tag
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            builder.setChannelId(NOTIFY_ID);
            manager.createNotificationChannel(channel); //放入 manager 管理
        }

        //發出通知(實際第三步)
        manager.notify(REQUEST_CODE, builder.build());
    }
}