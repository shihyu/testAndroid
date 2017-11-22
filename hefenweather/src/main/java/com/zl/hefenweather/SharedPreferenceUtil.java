package com.zl.hefenweather;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by zy1373 on 2017-11-22.
 */

/**
 * Object 和String 互相转换,用于通过SharedPreference保存和读取
 */
public class SharedPreferenceUtil {

    private final static String TAG =  WeatherConstant.TAG;

    private final static String FILE_KEY_SharedPreference = "file_ley_weather";

    public final static String KEY_BAIDU_LOCATION = "baidu_location";
    public final static String KEY_NOW_WEATHER = "key_now_weather";
    public final static String KEY_FORCAT_WEATHER = "key_forcat_weather";
    public final static String KEY_HOURLY_WEATHER = "key_hourly_weather";

    public static void saveOject(Context ctx,Object object,String key){
        if(object == null || key == null || ctx == null){
            Log.d(TAG,"saveOject paras null");
            return;
        }
        SharedPreferences sp = ctx.getSharedPreferences(FILE_KEY_SharedPreference,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String s = Obje2String(object);
        editor.putString(key,s);
        editor.commit();
    }

    private static String Obje2String(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String string = new String(Base64.encode(byteArrayOutputStream.toByteArray(),
                    Base64.DEFAULT));
            objectOutputStream.close();
            return string;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Object getObject(Context ctx,String key){
        if(key == null || ctx == null){
            Log.d(TAG,"saveOject paras null");
            return null;
        }
        SharedPreferences sp = ctx.getSharedPreferences(FILE_KEY_SharedPreference,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String s = sp.getString(key,"");
        return String2Object(s);
    }

    private static Object String2Object(String s) {

        if(s != null && s.trim().length() >0){
            byte[] mobileBytes = Base64.decode(s.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
            ObjectInputStream objectInputStream = null;
            try {
                objectInputStream = new ObjectInputStream(byteArrayInputStream);
                Object object = objectInputStream.readObject();
                objectInputStream.close();
                return object;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }


}
