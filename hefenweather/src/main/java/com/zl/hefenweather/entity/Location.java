package com.zl.hefenweather.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zhangle on 2017/11/19.
 * 百度位置信息类
 */

public class Location implements Serializable ,Parcelable{
    public String addr ;        //获取详细地址信息
    public String country ;     //获取国家
    public String province ;    //获取省份
    public String city;         //获取城市
    public String cityCode;
    public String district;     //获取区县
    public String street;       //获取街道信息


    public Location() {
    }

    protected Location(Parcel in) {
        addr = in.readString();
        country = in.readString();
        province = in.readString();
        city = in.readString();
        cityCode = in.readString();
        district = in.readString();
        street = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(addr);
        dest.writeString(country);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(cityCode);
        dest.writeString(district);
        dest.writeString(street);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
