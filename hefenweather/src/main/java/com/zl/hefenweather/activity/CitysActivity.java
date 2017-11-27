package com.zl.hefenweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zl.hefenweather.R;
import com.zl.hefenweather.WeatherUtils;
import com.zl.hefenweather.request.MyStringRequest;

public class CitysActivity extends Activity {
    private final String TAG = "CitysActivity";


    /*   天气网把城市分为了3级
    *    1级列表获取地址：http://www.weather.com.cn/data/city3jdata/china.html。通过访问这个地址，天气
    *    网会返回一级省（自治区）的名称、ID信息；
    *    2级城市城市列表获取地址：http://www.weather.com.cn/data/city3jdata/provshi/10120.html。其中“10120”
    *    为一级城市的ID，返回结果是归属于该城市的2级省市的名称、ID；
    *    3级城市列表获取地址：http://www.weather.com.cn/data/city3jdata/station/1012002.html。其中“1012002”
    *    为2级省市ID，返回结果就是3级城市的名称和ID了。
    *    获取到3级城市的名称和ID之后，就可以根据上面那篇博客里的内容获取当地的天气信息了！
    */
    private final String url_provshis = "http://www.weather.com.cn/data/city3jdata/china.html";
    private final String url_citys = "http://www.weather.com.cn/data/city3jdata/station/";

    private Toast mToast;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citys);

        requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        getHttpData(0);
    }



    public void getHttpData(final int cityID){
        String url = null;
        if(cityID >0){
            url = url_citys + cityID + ".html";
        }else{
            url = url_provshis;
        }

        StringRequest request = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"onResponse " +response);
                if(cityID >0){
                    WeatherUtils.handleCitysResponse(response);
                }else{
                    WeatherUtils.handleProvshiResponse(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"onErrorResponse " +error.getMessage(),error);
                if(error instanceof TimeoutError){
                    showToast("网络超时,获取数据失败");

                }
            }
        });

        requestQueue.add(request);
    }

    void showToast(CharSequence helpSequence) {
        if (mToast != null) {
            mToast.cancel();
        }
        Activity activity = this;
        if (activity != null && !activity.isFinishing()) {
            mToast = Toast.makeText(activity, helpSequence, Toast.LENGTH_LONG);
            mToast.show();
        }
    }
}
