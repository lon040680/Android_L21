package com.chien.mynotification2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 300;
    public static final String NOTIFY_ID = "abc";
    Context context;
    NotificationManager manager;
    NotificationChannel channel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.notification:
                addNotification();
                break;
            case R.id.cancel:
                manager.cancelAll();
                Toast.makeText(context, "Clear", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void addNotification() {
        Intent it = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                REQUEST_CODE,
                it,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        Notification.Builder builder = new Notification.Builder(context);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.train);
        builder.setContentTitle("火車誤點通知")
                .setContentText("155自強號 誤點15分鐘")
                .setSmallIcon(R.drawable.information)
                .setLargeIcon(bmp)
                .setContentInfo("XXXXXXX")  //無效
                .setTicker("QQQQQQQQ")  //無效
                .setLights(0xFFFFFF, 1000, 1000) //無效沒燈了
                .setPriority(Notification.PRIORITY_DEFAULT)  //優先權
                .setAutoCancel(true)
                .setVisibility(Notification.VISIBILITY_PUBLIC) //顯示位置 不管預設
                .setContentIntent(pendingIntent);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            channel = new NotificationChannel(
                    NOTIFY_ID,
                    "aaa",
                    NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(NOTIFY_ID);
            manager.createNotificationChannel(channel);
        }

        Notification notification = builder.build();
        manager.notify(REQUEST_CODE, notification);
    }
}