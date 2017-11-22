package com.zl.hefenweather.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangle on 2017/11/19.
 * 天气对象类
 */

public class Weather implements Serializable{

    public List<HeWeather6> HeWeather6;


    public Weather() {

    }

    public List<HeWeather6> getHeWeather6() {
        return HeWeather6;
    }

}
