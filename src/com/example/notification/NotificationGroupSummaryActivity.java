package com.example.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.example.demo1.R;

public class NotificationGroupSummaryActivity extends Activity implements View.OnClickListener{

    private final String TAG = "NotificationGroupSummaryActivity";

    private Button notification1;
    private Button notification2;
    private Button notification3;
    private int NOTIFICATION_ID = 6543;
    NotificationManager notificationManager= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);
        notification1 = (Button) this.findViewById(R.id.notification1);
        notification2 = (Button) this.findViewById(R.id.notification2);
        notification3 = (Button) this.findViewById(R.id.notification3);
        notification1.setOnClickListener(this);
        notification2.setOnClickListener(this);
        notification3.setOnClickListener(this);

        notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

    }

    private void showActionNotification() {

        Intent intent = new Intent(this,NotificationGroupSummaryActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 100, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setVisibility(Notification.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_launcher)
                .setFullScreenIntent(pi, false)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle("这是标题" + NOTIFICATION_ID++)
                .setContentText("这是内容")
                .setVibrate(new long[]{0,100,100,100,100,100,100,100})
                //.setDeleteIntent(pi)
                .addAction(R.drawable.ic_launcher, "菜单1", pi);

        Notification n = builder.build();
        n.flags |= Notification.FLAG_SHOW_LIGHTS;
        n.defaults |= Notification.DEFAULT_SOUND;
        n.ledARGB = Color.BLUE;
        n.ledOffMS = 1000;//关闭时间 毫秒
        n.ledOnMS = 1000;//开启时间 毫秒
        notificationManager.notify(NOTIFICATION_ID++, n);
    }

    protected void showSingleNotification(String title,
                                          String message,
                                          int notificationId,boolean group) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher)
                .setGroup("group");
        Notification notification = builder.build();
        notificationManager.notify(notificationId, notification);
    }

    private void showGroupSummaryNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Dummy content title")
                .setContentText("Dummy content text")
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("Line 1")
                        .addLine("Line 2")
                        .setSummaryText("Inbox summary text")
                        .setBigContentTitle("Big content title"))
                .setNumber(4)
                .setSmallIcon(R.drawable.ic_launcher)
                .setCategory(Notification.CATEGORY_EVENT)
                .setGroupSummary(true)
                .setGroup("group");
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.ledARGB = Color.GREEN;
        notification.ledOffMS = 200;//关闭时间 毫秒
        notification.ledOnMS = 200;//开启时间 毫秒
        notificationManager.notify(1234, notification);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.notification1:
                showSingleNotification("title 1", "message 1", 1,false);
                showSingleNotification("title 2", "message 2", 2,true);

            break;
            case R.id.notification2:
                showGroupSummaryNotification();
            break;
            case R.id.notification3:
                showActionNotification();
            break;

        }
    }
}