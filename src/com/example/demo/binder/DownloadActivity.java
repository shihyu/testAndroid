package com.example.demo.binder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.demo.R;


public class DownloadActivity extends Activity {
    private final static String TAG = "DownloadActivity";

    private MyServiceConnection myServiceConnection = null;

    private ProgressBar progress_bar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.binder_layout);

        progress_bar = (ProgressBar) this.findViewById(R.id.progress_bar);

        Intent intent = new Intent(this,DownloadServie.class);
        myServiceConnection = new MyServiceConnection();
        bindService(intent,myServiceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
       super.onStop();
        Log.d(TAG,"onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        unbindService(myServiceConnection);
    }

    public class MyServiceConnection implements ServiceConnection {


        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceDisconnected");
        }


        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"onServiceConnected");

            DownloadServie downloadServie = ((DownloadServie.DownloadBinder)service).getDownloadServie();
            downloadServie.setCallBack(new IProcessCallBack() {
                @Override
                public void setProcess(int i) {
                    progress_bar.setProgress(i);
                }
            });
            downloadServie.download();
        }
    }

    public interface IProcessCallBack{
        public void setProcess(int i);
    }
}
