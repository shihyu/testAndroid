package com.zl.hefenweather;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.zl.hefenweather.db.LocationDB;
import com.zl.hefenweather.entity.City;
import com.zl.hefenweather.entity.Provshi;

import java.util.ArrayList;
import java.util.List;

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

    public static Drawable blur(Context context, int drawable) {
        Bitmap bkg = BitmapFactory.decodeResource(context.getResources(),drawable);
        long startMs = System.currentTimeMillis();
        float radius = 25;
        bkg = bkg.copy(bkg.getConfig(), true);

        final RenderScript rs = RenderScript.create(context);
        final Allocation input = Allocation.createFromBitmap(rs, bkg,
                Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(rs, input.getType());
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs,
                Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        script.forEach(output);
        output.copyTo(bkg);

        rs.destroy();
        script.destroy();
        input.destroy();
        output.destroy();

        //Log.d(TAG, "blur take away:" + (System.currentTimeMillis() - startMs) + "ms");
        return new BitmapDrawable(bkg);
    }


    public static void handleProvshiResponse(Context context,String response){
        if(response != null){
            ArrayList<Provshi> list = new ArrayList<Provshi>();
            response = response.substring(1,response.length()-2);
            String[] p = response.split(",");
            for (String s :
                    p) {
                String[] str = s.split(":");
                Provshi provshi = new Provshi(str[0].replace("\"",""),
                        str[1].replace("\"",""));
                list.add(provshi);
            }
            if(list.size() >0){
                new LocationDB(context).addProvshis(list);

            }
        }
    }

    public static void handleCitysResponse(Context context,String response,String provshiID){
        if(response != null){
            ArrayList<City> list = new ArrayList<City>();
            response = response.substring(1,response.length()-2);
            String[] p = response.split(",");
            for (String s :
                    p) {
                String[] str = s.split(":");
                City c = new City(str[0].replace("\"",""),
                        str[1].replace("\"",""),
                        provshiID.replace("\"",""));
            }
            if(list.size() >0){
                new LocationDB(context).addCitys(list);

            }
        }
    }


    public static List<City> getCitysFromDB(Context context){
        return new LocationDB(context).getCityList();
    }

    public static List<City> getCitysFromDB(Context context,String provshiID){
        return new LocationDB(context).getCityList(provshiID);
    }

    public static List<Provshi> getProvshiFromDB(Context context){
        return new LocationDB(context).getProvshiList();
    }

}
