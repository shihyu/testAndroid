package com.example.demo.weather.entity;

import java.util.List;

/**
 * Created by zhangle on 2017/11/19.
 * 天气对象类
 */

public class Weather {

    public List<Weather.HeWeather6> getHeWeather6() {
        return HeWeather6;
    }

    public List<HeWeather6> HeWeather6;

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
    }
}
