package com.zl.hefenweather;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by zy1373 on 2017-11-20.
 */

public class WeatherUtils {
    public static Drawable getCoudDrawbale(Resources resources, int cond_txt_d){

       /* public final static int h100 = R.drawable.h100;
        public final static int h101 = R.drawable.h101;
        public final static int h102 = R.drawable.h102;
        public final static int h103 = R.drawable.h103;
        public final static int h104 = R.drawable.h104;
        public final static int h200 = R.drawable.h200;
        public final static int h201 = R.drawable.h201;
        public final static int h202 = R.drawable.h100;
        public final static int h203 = R.drawable.h100;
        public final static int h204 = R.drawable.h100;
        public final static int h205 = R.drawable.h100;
        public final static int h206 = R.drawable.h100;
        public final static int h207 = R.drawable.h100;
        public final static int h208 = R.drawable.h100;
        public final static int h209 = R.drawable.h100;
        public final static int h210 = R.drawable.h100;
        public final static int h211 = R.drawable.h100;
        public final static int h212 = R.drawable.h100;
        public final static int h213 = R.drawable.h100;
        public final static int h300 = R.drawable.h100;
        public final static int h100 = R.drawable.h100;*/
       int resId = R.drawable.h100;
        switch (cond_txt_d){
            case 100:
                resId = R.drawable.h100;
                break;
            case 101:
                resId = R.drawable.h100;
                break;
            case 102:
                resId = R.drawable.h100;
                break;
            case 103:
                resId = R.drawable.h100;
                break;
            case 104:
                resId = R.drawable.h100;
                break;
            case 200:
                resId = R.drawable.h200;
                break;
            case 201:
                resId = R.drawable.h201;
                break;
            case 202:
                resId = R.drawable.h202;
                break;
            case 203:
                resId = R.drawable.h203;
                break;
            case 204:
                resId = R.drawable.h204;
                break;
            case 205:
                resId = R.drawable.h205;
                break;
            case 206:
                resId = R.drawable.h206;
                break;
            case 207:
                resId = R.drawable.h207;
                break;
            case 208:
                resId = R.drawable.h208;
                break;
            case 209:
                resId = R.drawable.h209;
                break;
            case 210:
                resId = R.drawable.h210;
                break;
            case 211:
                resId = R.drawable.h211;
                break;
            case 212:
                resId = R.drawable.h212;
                break;
            case 213:
                resId = R.drawable.h213;
                break;
            case 300:
                resId = R.drawable.h300;
                break;
            case 301:
                resId = R.drawable.h301;
                break;
            case 302:
                resId = R.drawable.h302;
                break;
            case 303:
                resId = R.drawable.h303;
                break;
            case 304:
                resId = R.drawable.h304;
                break;
            case 305:
                resId = R.drawable.h305;
                break;
            case 306:
                resId = R.drawable.h306;
                break;
            case 307:
                resId = R.drawable.h307;
                break;
            case 308:
                resId = R.drawable.h308;
                break;
            case 309:
                resId = R.drawable.h309;
                break;
            case 310:
                resId = R.drawable.h310;
                break;
            case 311:
                resId = R.drawable.h311;
                break;
            case 312:
                resId = R.drawable.h312;
                break;
            case 313:
                resId = R.drawable.h313;
                break;
            case 400:
                resId = R.drawable.h400;
                break;
            case 401:
                resId = R.drawable.h401;
                break;
            case 402:
                resId = R.drawable.h402;
                break;
            case 403:
                resId = R.drawable.h403;
                break;
            case 404:
                resId = R.drawable.h404;
                break;
            case 405:
                resId = R.drawable.h405;
                break;
            case 406:
                resId = R.drawable.h406;
                break;
            case 407:
                resId = R.drawable.h407;
                break;
            case 500:
                resId = R.drawable.h500;
                break;
            case 501:
                resId = R.drawable.h501;
                break;
            case 502:
                resId = R.drawable.h502;
                break;
            case 503:
                resId = R.drawable.h503;
                break;
            case 504:
                resId = R.drawable.h504;
                break;
            case 507:
                resId = R.drawable.h507;
                break;
            case 508:
                resId = R.drawable.h508;
                break;
            case 999:
                resId = R.drawable.h999;
                break;

        }

        return resources.getDrawable(resId);
    }

    public static Drawable getCoudDrawbale(Resources resources, String cond_txt_d){
        //Log.d(HeFenWeatherActivity.TAG,"getCoudDrawbale cond_txt_d=" + cond_txt_d);
        if(cond_txt_d == null) return  null;
        return getCoudDrawbale(resources,Integer.parseInt(cond_txt_d));

    }
}
