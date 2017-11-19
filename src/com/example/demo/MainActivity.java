package com.example.demo;

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
import com.example.demo.annotation.BindView;
import com.example.demo.annotation.ContentView;
import com.example.demo.annotation.OnClick;
import com.example.demo.weather.Activity.HeFenWeatherActivity;
import com.example.demo.weather.Activity.WeatherActivity;
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
import com.example.demo.widget.BaseActivity;
import com.example.demo.widget.TouchActivity;
import com.example.demo.widget.slidingdrawer.SlidingDrawerDemo;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements OnClickListener {

    private final String TAG = "MainActivity";

    @BindView(R.id.notifications)
    private Button notifications;
    @BindView(R.id.notification_group)
    private Button notificationGroup;
    @BindView(R.id.ipc_aidl)
    private Button ipc_aidl;
    @BindView(R.id.ipc_binder)
    private Button ipc_binder;
    @BindView(R.id.messenger)
    private Button messenger;
    @BindView(R.id.animation)
    private Button animation;
    @BindView(R.id.shake)
    private Button shake;
    @BindView(R.id.palette)
    private Button palette;
    @BindView(R.id.handler)
    private Button handler;
    @BindView(R.id.touch)
    private Button touch;
    @BindView(R.id.preference)
    private Button preference;
    @BindView(R.id.progress_bar)
    private Button progress_bar;
    @BindView(R.id.matrix)
    private Button matrix;
    @BindView(R.id.floatball)
    private Button floatball;
    @BindView(R.id.guaguaka)
    private Button guaguaka;
    @BindView(R.id.wuzhiqi)
    private Button wuzhiqi;
    @BindView(R.id.lucky_panel)
    private Button lucky_panel;
    @BindView(R.id.horizontalscrollview_gridview)
    private Button horizontalscrollview_gridview;

    @BindView(R.id.baidu_weather)
    private Button baidu_weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick({R.id.notifications,R.id.notification_group,R.id.ipc_aidl,
            R.id.ipc_binder,R.id.messenger,R.id.animation,
            R.id.palette,R.id.shake,R.id.touch,
            R.id.handler,R.id.preference,R.id.horizontalscrollview_gridview,
            R.id.progress_bar,R.id.matrix,R.id.floatball,
            R.id.guaguaka,R.id.wuzhiqi,R.id.lucky_panel,
            R.id.baidu_weather
    })
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
            case R.id.baidu_weather:
                intent.setClass(this, HeFenWeatherActivity.class);
                break;

            default:
                break;
        }
        startActivity(intent);

    }
}
