package com.zl.hefenweather.entity;

/**
 * Created by zy1373 on 2017-11-27.
 */

public class City {
    public String cityId;
    public String cityName;
    public String provshiID;

    public City(String cityId, String cityName, String provshiID) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.provshiID = provshiID;
    }
}
