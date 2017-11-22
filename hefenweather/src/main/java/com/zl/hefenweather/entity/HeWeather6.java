package com.zl.hefenweather.entity;

import java.io.Serializable;
import java.util.List;

public class HeWeather6 implements Serializable {
    /**
     * basic : {"cid":"CN101280601","location":"深圳","parent_city":"深圳","admin_area":"广东","cnty":"中国","lat":"22.54700089","lon":"114.08594513","tz":"+8.0"}
     * update : {"loc":"2017-11-19 16:54","utc":"2017-11-19 08:54"}
     * status : ok
     * now : {"cloud":"100","cond_code":"104","cond_txt":"阴","fl":"19","hum":"75","pcpn":"0","pres":"1017","tmp":"19","vis":"10","wind_deg":"43","wind_dir":"东北风","wind_sc":"微风","wind_spd":"8"}
     */

    public Basic basic;
    public Update update;
    public String status;
    public Now now;

    public List<DailyForecast> daily_forecast;


    public List<DailyForecast> getDaily_forecast() {
        return daily_forecast;
    }

    public List<Hourly> hourly;

    public List<Hourly> getHourly() {
        return hourly;
    }


}