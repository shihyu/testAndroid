package com.example.demo.weather.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.demo.R;
import com.example.demo.annotation.BindView;
import com.example.demo.annotation.ContentView;
import com.example.demo.weather.WeatherConstant;
import com.example.demo.weather.entity.Location;
import com.example.demo.weather.entity.Weather;
import com.example.demo.widget.BaseActivity;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangle on 2017-11-17.
 *
 * 获取百度天气和和风天气，目前百度天气好像是出了问题，一直是服务被禁用。
 */

@ContentView(R.layout.hefen_weathers)
public class HeFenWeatherActivity extends BaseActivity {

    private static final String TAG = "HeFenWeatherActivity";

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @BindView(R.id.tv_cond_txt)
    private TextView tv_cond_txt;

    @BindView(R.id.tv_tmp)
    private TextView tv_tmp;

    @BindView(R.id.tv_wind_dir)
    private TextView tv_wind_dir;

    @BindView(R.id.tv_hum)
    private TextView tv_hum;

    @BindView(R.id.gv_hourly)
    private GridView gv_hourly;

    @BindView(R.id.lv_forecast)
    private ListView lv_forecast;


    private Location mCurrentLocation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

        initLocationClientOptin();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initLocationClientOptin() {
        LocationClientOption option = new LocationClientOption();

        option.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

        mLocationClient.start();
    }

    class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            mCurrentLocation = new Location();
            mCurrentLocation.addr = location.getAddrStr();    //获取详细地址信息
            mCurrentLocation.country = location.getCountry();    //获取国家
            mCurrentLocation.province = location.getProvince();    //获取省份
            mCurrentLocation.city = location.getCity();    //获取城市
            mCurrentLocation.cityCode =  location.getCityCode();
            mCurrentLocation.district = location.getDistrict();    //获取区县
            mCurrentLocation.street = location.getStreet();    //获取街道信息
            Log.d(TAG, "addr=" + mCurrentLocation.addr + " country=" + mCurrentLocation.country +
                    " province=" + mCurrentLocation.province + " city=" + mCurrentLocation.city +
                    " cityCode=" + mCurrentLocation.cityCode +
                    " district=" + mCurrentLocation.district + " street=" + mCurrentLocation.street);

            getHeFenWeatherInfo(mCurrentLocation);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stop();
    }




    public void getNowWeatherInfo(Location location){
        if(location.city == null || location.city.trim().equals(""))return;
        String param = "?key="+WeatherConstant.KEY+"&location="+ location.city;
        String url = WeatherConstant.WEATHER_NOW_URL + param;
        Log.d(TAG, "getWeatherInfo: url=" + url);

        RequestQueue mQueue= Volley.newRequestQueue(this);
        MyStringRequest stringRequest = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, "onResponse: " + s);
                Weather w = new Weather();
                Gson gson = new Gson();
                w = gson.fromJson(s,Weather.class);
                Weather.HeWeather6 heWeather6 = w.getHeWeather6().get(0);
                tv_wind_dir.setText(heWeather6.now.wind_dir);
                tv_tmp.setText(heWeather6.now.tmp);
                tv_cond_txt.setText(heWeather6.now.cond_txt);
                tv_hum.setText(heWeather6.now.hum + "°");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage(), volleyError);

            }
        });

        mQueue.add(stringRequest);
    }

    class MyStringRequest extends  StringRequest{


        public MyStringRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        public MyStringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(url, listener, errorListener);
        }


        @Override
        protected Response<String> parseNetworkResponse(NetworkResponse response) {
            // TODO Auto-generated method stub
            String str = null ;
            try {
                str = new String(response.data,"utf-8" );
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
        }
    }
}
