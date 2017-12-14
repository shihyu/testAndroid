package com.zl.hefenweather.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.zl.annotation.AnnotationUtils;
import com.zl.annotation.BindView;
import com.zl.annotation.ContentView;
import com.zl.annotation.OnClick;
import com.zl.hefenweather.FullyLinearLayoutManager;
import com.zl.hefenweather.R;
import com.zl.hefenweather.SharedPreferenceUtil;
import com.zl.hefenweather.WeatherConstant;
import com.zl.hefenweather.WeatherUtils;
import com.zl.hefenweather.entity.DailyForecast;
import com.zl.hefenweather.entity.HeWeather6;
import com.zl.hefenweather.entity.Hourly;
import com.zl.hefenweather.entity.Location;
import com.zl.hefenweather.entity.Weather;
import com.zl.hefenweather.request.MyStringRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangle on 2017-11-17.
 *
 * 获取百度天气和和风天气，目前百度天气好像是出了问题，一直是服务被禁用。
 */

@ContentView(R.layout.hefen_weathers)
public class HeFenWeatherActivity extends BaseActivity implements  SwipeRefreshLayout.OnRefreshListener{

    public static final String TAG = WeatherConstant.TAG;

    private final boolean DEBUG_RESPONSE = true;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @BindView(R.id.tv_district)
    private TextView tv_district;

    @BindView(R.id.tv_cond_txt)
    private TextView tv_cond_txt;

    @BindView(R.id.tv_tmp)
    private TextView tv_tmp;

    @BindView(R.id.tv_wind_dir)
    private TextView tv_wind_dir;

    @BindView(R.id.tv_hum)
    private TextView tv_hum;

    @BindView(R.id.iv_daily_coud)
    private ImageView iv_daily_coud;

    @BindView(R.id.gv_hourly)
    private GridView gv_hourly;

    @BindView(R.id.rv_forecast)
    private RecyclerView rv_forecast;

    @BindView(R.id.swipe_refresh)
    private SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.tv_manager_citys)
    private TextView tv_manager_citys;

    @BindView(R.id.tv_setttings)
    private TextView tv_setttings;

    @BindView(R.id.tv_about_me)
    private TextView tv_about_me;

    private Location mCurrentLocation;
    private LocationClientOption mLocationOption;

    private List<DailyForecast> mDailyForecast;
    private List<Hourly> mHourly;
    private Toast mToast;

    private final int MSG_ALL_WEATHER = 1101;
    private final int MSG_NOW_WEATHER = 1102;
    private final int MSG_HOURLY_WEATHER = 1103;
    private final int MSG_FORCAST_WEATHER = 1104;
    private final String MSG_LOCATION_KEY = "current_location";

    private MyHandler h = null;

    private String  mChooseCityId = null;
    private String  mChooseCityName = null;

    private RequestQueue mRequestQueue = null;

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Location locattion = (Location) msg.getData().getParcelable(MSG_LOCATION_KEY);
            switch (msg.what){
                case MSG_ALL_WEATHER:
                    getNowWeatherInfo(locattion);
                    getHourlyWeather(locattion);
                    getForcastWeather(locattion);
                    break;
                case MSG_NOW_WEATHER:
                    getNowWeatherInfo(locattion);
                    break;
                case MSG_HOURLY_WEATHER:
                    getHourlyWeather(locattion);
                    break;
                case MSG_FORCAST_WEATHER:
                    getForcastWeather(locattion);
                    break;
            }
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();

        h = new MyHandler();
        mChooseCityId = getIntent().getStringExtra(CitysActivity.CITY_ID_KEY);
        mChooseCityName = getIntent().getStringExtra(CitysActivity.CITY_NAME_KEY);
        Log.d(TAG,"onCreate chooseCityId=" + mChooseCityId +
                " mChooseCityName=" + mChooseCityName);

        mRequestQueue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mChooseCityId !=null && mChooseCityName != null){//已经手动选择城市
            mCurrentLocation = new Location();
            mCurrentLocation.city = mChooseCityName;
            mCurrentLocation.cityCode = mChooseCityId;
            updateLocationView(mCurrentLocation,true);
            getCityWeather(mCurrentLocation);
        }else{
            mCurrentLocation = (Location) SharedPreferenceUtil.getObject(this,SharedPreferenceUtil.KEY_BAIDU_LOCATION);
            Weather weather_n = (Weather)SharedPreferenceUtil.getObject(this,SharedPreferenceUtil.KEY_NOW_WEATHER);
            Weather weather_h = (Weather)SharedPreferenceUtil.getObject(this,SharedPreferenceUtil.KEY_HOURLY_WEATHER);
            Weather weather_f = (Weather)SharedPreferenceUtil.getObject(this,SharedPreferenceUtil.KEY_FORCAT_WEATHER);
            updateLocationView(mCurrentLocation,true);
            updateNowWeatherView(weather_n);
            updateForcastListView(weather_f);
            updateHourlyView(weather_h);

            initLocationClient();
            initLocationClientOptin();

        }
    }

    private void initLocationClient() {
        if(mLocationClient == null){
            //声明LocationClient类
            mLocationClient = new LocationClient(getApplicationContext());
        }

        //注册监听函数
        mLocationClient.registerLocationListener(myListener);

    }

    public void initView(){
        rv_forecast.setNestedScrollingEnabled(false);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
                );
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setProgressViewEndTarget(true,250);//move down progress
        setRefreshing(true);

    }

    private void initLocationClientOptin() {
        if(mLocationOption == null){
            mLocationOption = new LocationClientOption();
        }

        mLocationOption.setIsNeedAddress(true);
        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(mLocationOption);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        //更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明

        mLocationClient.start();
    }

    @Override
    public void onRefresh() {
        Log.d(TAG,"onRefresh");
        tv_district.setCompoundDrawables(getDrawable(R.drawable.ic_locate_plane),null,null,null);

        /*initLocationClient();
        initLocationClientOptin();*/
        mLocationClient.requestLocation();
    }

    @OnClick({R.id.tv_setttings,R.id.tv_about_me,R.id.tv_manager_citys})
    public void onClick(View v){

        switch (v.getId()){
            case R.id.tv_setttings:
            case R.id.tv_about_me:
                showToast("暂无功能,开发中...");
                break;
            case R.id.tv_manager_citys:
                startActivity(new Intent(this,CitysActivity.class));
                break;
        }
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


            if(mCurrentLocation.city == null || mCurrentLocation.cityCode ==null){
                //结束后停止刷新
                doNetworkErrorResponse();
                return;
            }

            updateLocationView(mCurrentLocation);

            getCityWeather(mCurrentLocation);

            SharedPreferenceUtil.saveOject(getApplicationContext(),mCurrentLocation,SharedPreferenceUtil.KEY_BAIDU_LOCATION);
        }
    }

    private void getCityWeather(Location location) {
        if(h == null) h = new MyHandler();

        Message msg = h.obtainMessage();
        msg.what = MSG_ALL_WEATHER;
        Bundle bundle = msg.getData();
        bundle.putParcelable(MSG_LOCATION_KEY,location);
        msg.setData(bundle);
        h.sendMessage(msg);
    }

    private void updateLocationView(Location location) {
        if(location !=null){
            boolean isDistrictNull = location.district==null;
            tv_district.setText(isDistrictNull?location.city:location.district);
            tv_district.setCompoundDrawables(isDistrictNull?getDrawable(R.drawable.ic_locate_plane):null,
                    null,null,null);
        }else{
            Log.d(TAG,"updateLocationView parmas null");
        }

    }

    /**
     *
     * @param location
     * @param showIcon 是否显示定位图标
     */
    private void updateLocationView(Location location,boolean showIcon) {
        if(location !=null){
            boolean isDistrictNull = location.district==null;
            tv_district.setText(isDistrictNull?location.city:location.district);
            /*tv_district.setCompoundDrawables(showIcon?getDrawable(R.drawable.ic_locate_plane):null,
                    null,null,null);*/
        }else{
            Log.d(TAG,"updateLocationView parmas null");
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        
        if(mLocationClient != null){
            mLocationClient.stop();
            mLocationOption = null;
            mLocationClient = null;
        }

        if(mRequestQueue != null){
            mRequestQueue.stop();
            mRequestQueue = null;
        }

    }

    public void getNowWeatherInfo(Location location){
        if(location.city == null || location.city.trim().equals(""))return;
        String param = "?key="+WeatherConstant.KEY+"&location="+ location.city;
        String url = WeatherConstant.WEATHER_NOW_URL + param;
        Log.d(TAG, "getNowWeatherInfo: url=" + url);

        MyStringRequest stringRequest = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(DEBUG_RESPONSE)Log.d(TAG, "getNowWeatherInfo onResponse: " + s);
                Weather w = new Weather();
                Gson gson = new Gson();
                w = gson.fromJson(s,Weather.class);
                updateNowWeatherView(w);
                setRefreshing(false);

                SharedPreferenceUtil.saveOject(getApplicationContext(),w,SharedPreferenceUtil.KEY_NOW_WEATHER);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage(), volleyError);
                doNetworkErrorResponse(volleyError);

            }
        });

        mRequestQueue.add(stringRequest);
    }

    private void updateNowWeatherView(Weather w) {
        if(w != null){
            HeWeather6 heWeather6 = w.getHeWeather6().get(0);
            tv_wind_dir.setText(heWeather6.now.wind_dir + " " + heWeather6.now.wind_sc);
            tv_tmp.setText(heWeather6.now.tmp + WeatherConstant.DU);
            tv_cond_txt.setText(heWeather6.now.cond_txt);
            tv_hum.setText(getText(R.string.tv_hum_prefix) + heWeather6.now.hum + "%");
        }else{
            Log.d(TAG,"updateNowWeatherView parmas null");
        }
    }


    public void getHourlyWeather(Location location){
        if(location.city == null || location.city.trim().equals(""))return;
        String param = "?key="+WeatherConstant.KEY+"&location="+ location.city;
        String url = WeatherConstant.WEATHER_HOURLY_URL + param;
        Log.d(TAG, "getHourlyWeather: url=" + url);

        MyStringRequest stringRequest = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(DEBUG_RESPONSE)Log.d(TAG, "getHourlyWeather onResponse: " + s);
                Weather w = new Weather();
                Gson gson = new Gson();
                w = gson.fromJson(s, Weather.class);

                updateHourlyView(w);

                setRefreshing(false);

                SharedPreferenceUtil.saveOject(getApplicationContext(),w,SharedPreferenceUtil.KEY_HOURLY_WEATHER);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage(), volleyError);

                doNetworkErrorResponse(volleyError);
            }
        });

        mRequestQueue.add(stringRequest);
    }

    private void updateHourlyView(Weather w) {
        if(w !=null && w.getHeWeather6() != null){
            mHourly = w.getHeWeather6().get(0).getHourly();

            if(mHourly != null){
                DisplayMetrics dm = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(dm);
                float density = dm.density;
                int ll_width = (int) (60 * density * mHourly.size());
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ll_width, RelativeLayout.LayoutParams.WRAP_CONTENT);
                gv_hourly.setLayoutParams(params);
                gv_hourly.setNumColumns(mHourly.size());

                gv_hourly.setAdapter(new ScrollViewAdapter(HeFenWeatherActivity.this,mHourly));
            }

        }else{
            Log.d(TAG,"updateHourlyView parmas null");
        }

    }

    public void getForcastWeather(Location location){
        if(location.city == null || location.city.trim().equals(""))return;
        String param = "?key="+WeatherConstant.KEY+"&location="+ location.city;
        String url = WeatherConstant.WEATHER_FORECAST_URL + param;
        Log.d(TAG, "getForcastWeather: url=" + url);

        MyStringRequest stringRequest = new MyStringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(DEBUG_RESPONSE)Log.d(TAG, "getForcastWeather onResponse: " + s);

                Weather w = new Weather();
                Gson gson = new Gson();
                w = gson.fromJson(s,Weather.class);

                updateForcastListView(w);
                setRefreshing(false);

                SharedPreferenceUtil.saveOject(getApplicationContext(),w,SharedPreferenceUtil.KEY_FORCAT_WEATHER);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, volleyError.getMessage(), volleyError);
                doNetworkErrorResponse(volleyError);
            }
        });

        mRequestQueue.add(stringRequest);
    }

    private void updateForcastListView(Weather w) {
        if(w != null && w.getHeWeather6() != null){
            HeWeather6 heWeather6 = w.getHeWeather6().get(0);
            List<DailyForecast> dailyForecast = heWeather6.getDaily_forecast();

            if(dailyForecast != null && dailyForecast.size() >0){
                Log.d(TAG, "updateForcastListView size＝" +dailyForecast.size());
                LinearLayoutManager layoutManager = new FullyLinearLayoutManager(this );
                layoutManager.setOrientation(OrientationHelper.VERTICAL);
                rv_forecast.setLayoutManager(layoutManager);
                rv_forecast.setAdapter(new ForcastAdapter(this.getApplicationContext(),dailyForecast));
                //rv_forecast.addItemDecoration( new DividerGridItemDecoration(this ));
                rv_forecast.setItemAnimator( new DefaultItemAnimator());
            }
        }else{
            Log.d(TAG,"updateForcastListView parmas null");
        }
    }

    class ScrollViewAdapter extends BaseAdapter {
        private Context ctx ;
        private List<Hourly> hours;

        public ScrollViewAdapter(Context ctx,List<Hourly> hours) {
            this.ctx = ctx;
            this.hours = hours;
        }

        @Override
        public int getCount() {
            return hours.size();
        }

        @Override
        public Object getItem(int position) {
            return hours.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ScrollViewAdapter.ViewHolder viewHolder = null;

            if(convertView == null){
                convertView = LayoutInflater.from(ctx).inflate(R.layout.weather_horizontalscroll_item,null);
                viewHolder = new ScrollViewAdapter.ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ScrollViewAdapter.ViewHolder)convertView.getTag();
            }

            Hourly hourly = hours.get(position);
            if(hourly != null){

                String[] time_array = hourly.time.split(" "); //2017-11-21 16:00
                String time = time_array[1]==null?hourly.time:time_array[1];
                viewHolder.tv_hour_time.setText(time);

                String[] cond_txt_array = hourly.cond_txt.split("/");//毛毛雨/细雨
                String cond_txt =cond_txt_array.length >0?cond_txt_array[0]:hourly.cond_txt;
                viewHolder.tv_hour_cond_txt.setText(cond_txt);

                viewHolder.tv_hour_tmp.setText(hourly.tmp + WeatherConstant.DU );
                Drawable coudDrawbale = WeatherUtils.getCoudDrawbale(getResources(),hourly.cond_code);
                viewHolder.iv_hour_daily_coud.setBackground(coudDrawbale);

            }
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.tv_hour_time)
            public TextView tv_hour_time;

            @BindView(R.id.tv_hour_cond_txt)
            public TextView tv_hour_cond_txt;

            @BindView(R.id.tv_hour_tmp)
            public TextView tv_hour_tmp;

            @BindView(R.id.iv_hour_daily_coud)
            public ImageView iv_hour_daily_coud;

            public ViewHolder(View rootView) {
                AnnotationUtils.injectBindView(this,rootView);

            }
        }
    }

    class ForcastAdapter extends RecyclerView.Adapter{
        private Context context;
        private LayoutInflater inflater;

        private List<DailyForecast> list;
        public ForcastAdapter(Context context,List<DailyForecast> dailyForecast) {
            this.context = context;
            inflater = LayoutInflater.from(this.context);
            this.list = dailyForecast;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(R.layout.hefen_weather_forcast_item, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ViewHolder vh = (ViewHolder)holder;
            DailyForecast daily = list.get(position);
            String xinqi = getXinQi(position,daily.date);
            String[] s = daily.date.split("-");//2017-11-20

            vh.tv_daily_date.setText(xinqi + " " +s[1] + "-" + s[2]);

            String[] cond_txt_array = daily.cond_txt_d.split("/");//毛毛雨/细雨
            String cond_txt =cond_txt_array.length >0?cond_txt_array[0]:daily.cond_txt_d;
            vh.tv_daily_coud.setText(cond_txt);

            Drawable coudDrawbale = WeatherUtils.getCoudDrawbale(getResources(),daily.cond_code_d);
            vh.iv_daily_coud.setBackground(coudDrawbale);

            vh.tv_daily_tmp.setText(daily.tmp_max + WeatherConstant.DU + "~" + daily.tmp_min + WeatherConstant.DU );
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends  RecyclerView.ViewHolder{

            @BindView(R.id.tv_daily_date)
            public TextView tv_daily_date;

            @BindView(R.id.tv_daily_coud)
            public TextView tv_daily_coud;

            @BindView(R.id.tv_daily_tmp)
            public TextView tv_daily_tmp;

            @BindView(R.id.iv_daily_coud)
            public ImageView iv_daily_coud;

            public ViewHolder(View itemView) {
                super(itemView);
                AnnotationUtils.injectBindView(this,itemView);
            }
        }
    }

    private String getXinQi(int position) {
        String[] s = new String[]{"今天","明天","后天"};

        return s[position>2?2:position];
    }

    private String getXinQi(int position,String date) {
        String[] s = new String[]{"今天","明天","后天"};
        String[] s1 = new String[]{"周日","周一","周二","周三","周四","周五","周六"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date now = sdf.parse(date);
            //Log.d(TAG,"getXinQi " + now.toString() + " position=" + position + " getDay=" +  now.getDay());
            return position <= s.length-1?s[position]:s1[now.getDay()];
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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

    private void doNetworkErrorResponse(VolleyError volleyError) {
        //停止刷新动画
        setRefreshing(false);
        if(volleyError instanceof TimeoutError){
            showToast("网络超时");
        }
    }

    private void doNetworkErrorResponse() {
        //停止刷新动画
        setRefreshing(false);
        showToast("网络超时");
    }

    public void setRefreshing(final boolean refresh){

        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                //停止/开始刷新动画
                Log.d(TAG,"setRefreshing refresh=" +refresh);
                swipeRefreshLayout.setRefreshing(refresh);
            }
        },0);

    }
}
