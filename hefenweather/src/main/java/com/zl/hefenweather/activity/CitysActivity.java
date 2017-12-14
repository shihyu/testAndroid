package com.zl.hefenweather.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.zl.annotation.AnnotationUtils;
import com.zl.annotation.BindView;
import com.zl.annotation.ContentView;
import com.zl.hefenweather.DefaultItemDecoration;
import com.zl.hefenweather.FullyLinearLayoutManager;
import com.zl.hefenweather.R;
import com.zl.hefenweather.WeatherUtils;
import com.zl.hefenweather.entity.City;
import com.zl.hefenweather.entity.Provshi;
import com.zl.hefenweather.request.MyStringRequest;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_citys)
public class CitysActivity extends BaseActivity {
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
    private final String url_citys = "http://www.weather.com.cn/data/city3jdata/provshi/";

    private Toast mToast;

    private RequestQueue requestQueue;


    private List<?> citysList = new ArrayList();

    @BindView(R.id.rcv_citys)
    private RecyclerView recyclerViewCity;

    private AreaAdapter mAreaAdapter = null;

    private String currentProvshiID = null;

    public final static String CITY_ID_KEY = "city_id";
    public final static String CITY_NAME_KEY = "city_name";
    public final static String PROVSHI_KEY = "provshi_id";


    private final int PROVSHI_LEVEL = 0;
    private final int CITY_LEVEL = 1;
    private int currentLoccationLevel = PROVSHI_LEVEL;

    private List<?> areas = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(this.getApplicationContext());

        currentProvshiID = getIntent().getStringExtra(PROVSHI_KEY);
        Log.d(TAG,"onCreate p=" + currentProvshiID);
        getLocationData(currentProvshiID);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(this.getApplicationContext());
        }
    }

    public void initView(){
        LinearLayoutManager layoutManager = new FullyLinearLayoutManager(this );
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerViewCity.setLayoutManager(layoutManager);
        recyclerViewCity.setItemAnimator( new DefaultItemAnimator());

        mAreaAdapter = new AreaAdapter(this.getApplicationContext(),citysList);
        mAreaAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Log.d(TAG,"adapter onItemClick Id=" + view.getTag() +
                        " currentLoccationLevel=" + currentLoccationLevel);
                if(currentLoccationLevel == CITY_LEVEL){
                    startHeFenWeatherActivity((String)view.getTag());
                }else if(currentLoccationLevel == PROVSHI_LEVEL){//指的省份的城市列表
                    startCitysActivity((String)view.getTag());
                }

            }
        });

        recyclerViewCity.addItemDecoration(new DefaultItemDecoration(this.getApplicationContext(),
                LinearLayoutManager.HORIZONTAL));
        recyclerViewCity.setAdapter(mAreaAdapter);
    }

    public void getLocationData(final String provshiID){
        if(provshiID != null && provshiID.trim().length() >0){
            List<City> list = WeatherUtils.getCitysFromDB(CitysActivity.this,"" +provshiID);
            if(list != null && list.size() >0){
                currentLoccationLevel = CITY_LEVEL;
                citysList = list;
                initView();
            }else{
                getHttpData(provshiID);
            }
        }else{
            List<Provshi> provshiList = WeatherUtils.getProvshiFromDB(this.getApplicationContext());
            if(provshiList !=null && provshiList.size()>0){
                this.citysList = provshiList;
                currentLoccationLevel = PROVSHI_LEVEL;
                initView();
            }else{
                getHttpData(null);
            }
        }

    }

    /**
     * provshiID <=0 获取获取省份的数据,相反获取指定的省份的数据
     * @param provshiID 省份的ID
     *
     */
    public void getHttpData(final String provshiID){
        String url = null;
        if(provshiID !=null && provshiID.trim().length() >0){
            url = url_citys + provshiID + ".html";
        }else{
            url = url_provshis;
        }
        Log.d(TAG,"getHttpData url=" +url);
        StringRequest request = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"onResponse cityID= " + provshiID + " response=" +response);
                if(provshiID != null && provshiID.trim().length() >0){//指定的省份的数据
                    WeatherUtils.handleCitysResponse(CitysActivity.this,response,""+provshiID);
                    //startCitysActivity(provshiID);
                    getLocationData(provshiID);
                }else{//全部省份
                    WeatherUtils.handleProvshiResponse(CitysActivity.this,response);
                    getLocationData(null);
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

    private void startHeFenWeatherActivity(String cityId) {
        Intent intent = new Intent();
        City c = WeatherUtils.getCityFromDB(this.getApplicationContext(),cityId,currentProvshiID);
        intent.putExtra(CITY_ID_KEY,cityId);
        intent.putExtra(CITY_NAME_KEY,c.cityName);
        intent.putExtra(PROVSHI_KEY, c.provshiID);
        intent.setClass(this.getApplicationContext(),HeFenWeatherActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void startCitysActivity(String provshiID) {
        Intent intent = new Intent();
        intent.putExtra(PROVSHI_KEY,provshiID);
        intent.setClass(this.getApplicationContext(),CitysActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void updateView(String provshiID){

        if(provshiID != null && provshiID.trim().length() >0){//指定的省份的数据
            mAreaAdapter = new AreaAdapter(CitysActivity.this,
                    WeatherUtils.getCitysFromDB(CitysActivity.this,"" +provshiID));
            currentLoccationLevel = CITY_LEVEL;
        }else{
            mAreaAdapter = new AreaAdapter(CitysActivity.this,
                    WeatherUtils.getProvshiFromDB(CitysActivity.this));
            currentLoccationLevel = PROVSHI_LEVEL;
        }
        Log.d(TAG,"updateView provshiID=" + provshiID +" currentLoccationLevel=" +currentLoccationLevel);
        mAreaAdapter.notifyDataSetChanged();
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

    @Override
    protected void onStop() {
        super.onStop();
        if(requestQueue != null){
            requestQueue.stop();
            requestQueue = null;
        }
    }

    class AreaAdapter extends RecyclerView.Adapter<AreaViewHolder> implements View.OnClickListener {
        private OnItemClickListener mOnItemClickListener = null;
        private LayoutInflater inflater;

        private AreaAdapter(Context ctx){
            inflater = LayoutInflater.from(ctx);
        }

        public AreaAdapter(Context ctx, List<?> list) {
           this(ctx);
           areas = list;
        }

        @Override
        public AreaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.city_item,null);
            v.setOnClickListener(this);
            return new AreaViewHolder(v);
        }

        @Override
        public void onBindViewHolder(AreaViewHolder holder, int position) {
            Log.d(TAG,"onBindViewHolder currentLoccationLevel=" + currentLoccationLevel);
            if(currentLoccationLevel == CITY_LEVEL){
                City c = (City)areas.get(position);
                holder.tv_city_name_item.setText(c.cityName);
                //将position保存在itemView的Tag中，以便点击时进行获取
                holder.itemView.setTag(c.cityId);
            }else if(currentLoccationLevel == PROVSHI_LEVEL){
                Provshi p = (Provshi)areas.get(position);
                holder.tv_city_name_item.setText(p.provshiName);
                //将position保存在itemView的Tag中，以便点击时进行获取
                holder.itemView.setTag(p.provshiId);
            }
        }

        @Override
        public int getItemCount() {
            return areas.size();
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG,"AreaAdapter onClick tag=" + v.getTag());
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取position
                mOnItemClickListener.onItemClick(v);
            }
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.mOnItemClickListener = listener;
        }
    }

    class AreaViewHolder extends  RecyclerView.ViewHolder{

        @BindView(R.id.tv_city_name_item)
        private TextView tv_city_name_item;

        public AreaViewHolder(View itemView) {
            super(itemView);
            AnnotationUtils.injectBindView(this,itemView);
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view);
    }
}
