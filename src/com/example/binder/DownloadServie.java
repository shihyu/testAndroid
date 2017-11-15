package com.example.binder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.binder.DownloadActivity.IProcessCallBack;

/**
 * Created by zy1373 on 2017-11-15.
 */

public class DownloadServie extends Service {
    private final static String TAG = "DownloadServie";
    private int i = 0;

    private IProcessCallBack mCallBack;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"DownloadServie Thread Name:" + Thread.currentThread().getName() );
        return new DownloadBinder();
    }

    public void download(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(i <= 10){
                    Log.d(TAG,"download " + Thread.currentThread().getName() + " count"  + i++);
                    try {
                        Thread.sleep(1000);
                        if(mCallBack != null){
                            mCallBack.setProcess(i*10);

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    public void setCallBack(IProcessCallBack callBack){
        mCallBack = callBack;
    }
    class DownloadBinder extends Binder{
        public DownloadServie getDownloadServie(){
            return DownloadServie.this;
        }
    }


}
