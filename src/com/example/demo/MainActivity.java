package com.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.doov.floatball.FloatBallActivity;
import com.doov.guaguaka.GuaGuaKaActivity;
import com.doov.luckypanel.LuckyPanelActivity;
import com.doov.wuzhiqi.WuZhiQiActivity;
import com.example.demo.animation.AnimationActivity;
import com.example.demo.binder.AIDLActivity;
import com.example.demo.binder.DownloadActivity;
import com.example.demo.handler.HandlerActivity;
import com.example.demo.horizontalscrollview.HorizontalscrollActivity;
import com.example.demo.matrix.MatrixActivity;
import com.example.demo.messenger.MessengerActivity;
import com.example.demo.notification.NotificationGroupSummaryActivity;
import com.example.demo.palette.PaletteActivity;
import com.example.demo.preference.DoovPreferenceActivity;
import com.example.demo.progressbar.ProgressActivity;
import com.example.demo.shake.ShakeActivity;
import com.example.demo.widget.TouchActivity;
import com.example.demo.widget.slidingdrawer.SlidingDrawerDemo;

public class MainActivity extends Activity implements OnClickListener {

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button horizontalscrollview_gridview = (Button) findViewById(R.id.horizontalscrollview_gridview);
        horizontalscrollview_gridview.setOnClickListener(this);

        Button notifications = (Button) findViewById(R.id.notifications);
        notifications.setOnClickListener(this);

        Button notification_group = (Button) findViewById(R.id.notification_group);
        notification_group.setOnClickListener(this);

        Button ipc_aidl = (Button) findViewById(R.id.ipc_aidl);
        ipc_aidl.setOnClickListener(this);

        Button ipc_binder = (Button) findViewById(R.id.ipc_binder);
        ipc_binder.setOnClickListener(this);

        Button messenger = (Button) findViewById(R.id.messenger);
        messenger.setOnClickListener(this);

        Button animation = (Button) findViewById(R.id.animation);
        animation.setOnClickListener(this);

        Button palette = (Button) findViewById(R.id.palette);
        palette.setOnClickListener(this);

        Button shake = (Button) findViewById(R.id.shake);
        shake.setOnClickListener(this);

        Button touch = (Button) findViewById(R.id.touch);
        touch.setOnClickListener(this);

        Button handler = (Button) findViewById(R.id.handler);
        handler.setOnClickListener(this);

        Button preference = (Button) findViewById(R.id.preference);
        preference.setOnClickListener(this);

        Button progress_bar = (Button) findViewById(R.id.progress_bar);
        progress_bar.setOnClickListener(this);

        Button matrix = (Button) findViewById(R.id.matrix);
        matrix.setOnClickListener(this);

        Button floatball = (Button) findViewById(R.id.floatball);
        floatball.setOnClickListener(this);

        Button guaguaka = (Button) findViewById(R.id.guaguaka);
        guaguaka.setOnClickListener(this);

        Button wuzhiqi = (Button) findViewById(R.id.wuzhiqi);
        wuzhiqi.setOnClickListener(this);

        Button lucky_panel = (Button) findViewById(R.id.lucky_panel);
        lucky_panel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        int id = v.getId();
        switch (id) {
            case R.id.notifications:
                intent.setClass(this, SlidingDrawerDemo.class);
                break;
            case R.id.notification_group:
                intent.setClass(this, NotificationGroupSummaryActivity.class);
                break;
            case R.id.ipc_aidl:
                intent.setClass(this, AIDLActivity.class);
                break;
            case R.id.ipc_binder:
                intent.setClass(this, DownloadActivity.class);
                break;
            case R.id.messenger:
                intent.setClass(this, MessengerActivity.class);
                break;
            case R.id.animation:
                intent.setClass(this, AnimationActivity.class);
                break;
            case R.id.palette:
                intent.setClass(this, PaletteActivity.class);
                break;
            case R.id.shake:
                intent.setClass(this, ShakeActivity.class);
                break;
            case R.id.touch:
                intent.setClass(this, TouchActivity.class);
                break;
            case R.id.handler:
                intent.setClass(this, HandlerActivity.class);
                break;
            case R.id.preference:
                intent.setClass(this, DoovPreferenceActivity.class);
                break;
            case R.id.horizontalscrollview_gridview:
                intent.setClass(this, HorizontalscrollActivity.class);
                break;
            case R.id.progress_bar:
                intent.setClass(this, ProgressActivity.class);
                break;
            case R.id.matrix:
                intent.setClass(this, MatrixActivity.class);
                break;
            case R.id.floatball:
                intent.setClass(this, FloatBallActivity.class);
                break;
            case R.id.guaguaka:
                intent.setClass(this, GuaGuaKaActivity.class);
                break;
            case R.id.wuzhiqi:
                intent.setClass(this, WuZhiQiActivity.class);
                break;
            case R.id.lucky_panel:
                intent.setClass(this, LuckyPanelActivity.class);
                break;

            default:
                break;
        }
        startActivity(intent);

    }
}
