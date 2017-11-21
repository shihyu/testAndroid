package com.example.demo.weather.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zhangle on 2017/11/19.
 * 天气对象类
 */

public class Weather {

    public List<HeWeather6> HeWeather6;

    @SerializedName("HeWeather6")
    public List<Weather.HeWeather6> getHeWeather6() {
        return HeWeather6;
    }

    public static class HeWeather6 {
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

        public static class Basic {
            /**
             * cid : CN101280601
             * location : 深圳
             * parent_city : 深圳
             * admin_area : 广东
             * cnty : 中国
             * lat : 22.54700089
             * lon : 114.08594513
             * tz : +8.0
             */

            public String cid;
            public String location;
            public String parent_city;
            public String admin_area;
            public String cnty;
            public String lat;
            public String lon;
            public String tz;
        }

        public static class Update {
            /**
             * loc : 2017-11-19 16:54
             * utc : 2017-11-19 08:54
             */

            public String loc;
            public String utc;
        }

        public static class Now {
            /**
             * cloud : 100
             * cond_code : 104
             * cond_txt : 阴
             * fl : 19
             * hum : 75
             * pcpn : 0
             * pres : 1017
             * tmp : 19
             * vis : 10
             * wind_deg : 43
             * wind_dir : 东北风
             * wind_sc : 微风
             * wind_spd : 8
             */

            public String cloud;
            public String cond_code;
            public String cond_txt;
            public String fl;
            public String hum;
            public String pcpn;
            public String pres;
            public String tmp;
            public String vis;
            public String wind_deg;
            public String wind_dir;
            public String wind_sc;
            public String wind_spd;
        }

        public static class DailyForecast {
            /**
             * cond_code_d : 104
             * cond_code_n : 104
             * cond_txt_d : 阴
             * cond_txt_n : 阴
             * date : 2017-11-20
             * hum : 67
             * mr : 07:53
             * ms : 19:10
             * pcpn : 0.0
             * pop : 0
             * pres : 1020
             * sr : 06:39
             * ss : 17:39
             * tmp_max : 18
             * tmp_min : 14
             * uv_index : 5
             * vis : 11
             * wind_deg : 0
             * wind_dir : 无持续风向
             * wind_sc : 微风
             * wind_spd : 8
             */

            public String cond_code_d;
            public String cond_code_n;
            public String cond_txt_d;
            public String cond_txt_n;
            public String date;
            public String hum;
            public String mr;
            public String ms;
            public String pcpn;
            public String pop;
            public String pres;
            public String sr;
            public String ss;
            public String tmp_max;
            public String tmp_min;
            public String uv_index;
            public String vis;
            public String wind_deg;
            public String wind_dir;
            public String wind_sc;
            public String wind_spd;
        }

        public static class Hourly {
            /**
             * cloud : 67
             * cond_code : 103
             * cond_txt : 晴间多云
             * dew : 13.0
             * hum : 66
             * pop : 7
             * pres : 1016
             * time : 2017-11-21 16:00
             * tmp : 18
             * wind_deg : 93
             * wind_dir : 东风
             * wind_sc : 微风
             * wind_spd : 12
             */

            public String cloud;
            public String cond_code;
            public String cond_txt;
            public String dew;
            public String hum;
            public String pop;
            public String pres;
            public String time;
            public String tmp;
            public String wind_deg;
            public String wind_dir;
            public String wind_sc;
            public String wind_spd;
        }
    }
}
