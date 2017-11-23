package com.zl.hefenweather.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
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
import com.zl.annotation.BindView;
import com.zl.annotation.ContentView;
import com.zl.annotation.OnClick;
import com.zl.hefenweather.R;

import java.io.UnsupportedEncodingException;

/**
 * Created by zhangle on 2017-11-17.
 *
 * 获取百度天气和和风天气，目前百度天气好像是出了问题，一直是服务被禁用。
 */

@ContentView(R.layout.baidu_weather)
public class WeatherActivity extends BaseActivity {

    private static final String TAG = "WeatherActivity";

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    private String city;
    private String cityCode ;

    @BindView(R.id.tx_tianqi)
    private TextView tx_tianqi;

    @BindView(R.id.tx_status)
    private TextView tx_status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

        initLocationClientOptin();

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

    @OnClick({R.id.get_baidu_location,R.id.get_hefen_location})
    public void OnClick(View v) {
        switch (v.getId()){
            case R.id.get_baidu_location:
                getBaiduWeatherInfo(city,cityCode);
                break;
            case R.id.get_hefen_location:
                getHeFenWeatherInfo(city,cityCode);
                break;
        }

    }

    class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            String addr = location.getAddrStr();    //获取详细地址信息
            String country = location.getCountry();    //获取国家
            String province = location.getProvince();    //获取省份
            String city = location.getCity();    //获取城市
            String cityCode =  location.getCityCode();
            String district = location.getDistrict();    //获取区县
            String street = location.getStreet();    //获取街道信息
            Log.d(TAG, "addr=" + addr + " country=" + country +
                    " province=" + province + " city=" + city +
                    " cityCode=" + cityCode +
                    " district=" + district + " street=" + street);


            WeatherActivity.this.city = city;
            WeatherActivity.this.cityCode = cityCode;

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.stop();
    }

    private final String AK = "1rZ4IW380RAcSaZcMZEIj7lR4roquq5P";
    private final String SHA1= "FD:C9:88:32:E0:7B:4F:CD:32:EA:DC:AF:8A:1C:8C:0E:D8:8A:88:A6";

    public void getBaiduWeatherInfo(String city,String cityCode) {
        if(city == null || city.trim().equals(""))return;
        String url = "http://api.map.baidu.com/telematics/v3/weather?location=" + city + "&output=json&ak=" + AK;
        //String url = "http://api.map.baidu.com/telematics/v3/weather?location=" + city +  "&output=json&ak="+ AK +"&mcode="+SHA1+";com.example.demo";
        Log.d(TAG, "getWeatherInfo: url=" + url);
        RequestQueue mQueue= Volley.newRequestQueue(this);

        MyStringRequest stringRequest = new MyStringRequest(url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                Log.d(TAG, "onResponse: " + s);
                tx_status.setText("获取天气数据成功");
                tx_tianqi.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage(), volleyError);
                tx_status.setText("获取天气失败");
                tx_tianqi.setText(volleyError.getMessage());
            }
        });

        mQueue.add(stringRequest);
    }


    public void getHeFenWeatherInfo(String city,String cityCode){
        if(city == null || city.trim().equals(""))return;
        String param = "key=3f25dfd138cf44669dab1b9985fd4f15&location="+ city;
        //接口地址
        String url = "https://free-api.heweather.com/s6/weather/now?" + param;

        Log.d(TAG, "getWeatherInfo: url=" + url);
        RequestQueue mQueue= Volley.newRequestQueue(this);

        MyStringRequest stringRequest = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG, "onResponse: " + s);
                tx_status.setText("获取天气数据成功");
                tx_tianqi.setText(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage(), volleyError);
                tx_status.setText("获取天气失败");
                tx_tianqi.setText(volleyError.getMessage());
            }
        });

        mQueue.add(stringRequest);
    }

    class MyStringRequest extends StringRequest {


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
