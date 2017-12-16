package com.zl.hefenweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zl.hefenweather.entity.City;
import com.zl.hefenweather.entity.Provshi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zy1373 on 2017-11-27.
 */

public class LocationDB {
    private static SQLiteDatabase db = null;
    private static LocationDB mLocationDB = null;

    private LocationDB(Context context){
        if(db == null){
            db = LocationSQLiteOpenHelper.getInstance(context).getWritableDatabase();
        }
    }

    public static LocationDB getInstance(Context context){
        if(mLocationDB == null){
            mLocationDB = new LocationDB(context);

        }
        return mLocationDB;
    }

    public void closeDB(){
        if(db != null){
            db.close();

        }
    }

    /**
     * 保存单个省份信息
     * @param provshi
     */
    public void addProvshi(Provshi provshi){
        if(provshi != null && provshi.provshiId != null){
            ContentValues cv = new ContentValues();
            cv.put("provshi_id",provshi.provshiId);
            cv.put("provshi_name",provshi.provshiName);
            db.insert(LocationSQLiteOpenHelper.TABLE_PROVSHI,null,cv);
        }
    }

    /**
     * 保存多个省份的信息
     * @param provshiList
     */
    public void addProvshis(List<Provshi> provshiList){
        for (Provshi p :
                provshiList) {
            addProvshi(p);
        }
    }

    /**
     * 获取省份列表全部信息
     * @return
     */
    public List<Provshi> getProvshiList(){
        ArrayList<Provshi> list = new ArrayList<Provshi>();
        Cursor cursor = db.query(LocationSQLiteOpenHelper.TABLE_PROVSHI,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            Provshi p = new Provshi(cursor.getString(1),cursor.getString(2));
            list.add(p);
        }
        return list;
    }

    /**
     * 获取省份列表的名称
     * @return
     */
    public List<String> getProvshNameiList(){
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.query(LocationSQLiteOpenHelper.TABLE_PROVSHI,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String s = new String(cursor.getString(1));
            list.add(s);
        }
        return list;
    }

    /**
     * 获取全部的城市的列表名称
     * @return
     */
    public List<String> getCityNameiList(){
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.query(LocationSQLiteOpenHelper.TABLE_CITYS,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String s = new String(cursor.getString(1));
            list.add(s);
        }
        return list;
    }


    /**
     * 获取指定省份的以下的城市信息
     * @param provshiId
     * @return
     */
    public List<City> getCityList(String provshiId){
        ArrayList<City> list = new ArrayList<City>();
        Cursor cursor = db.query(LocationSQLiteOpenHelper.TABLE_CITYS,null,"provshi_id=?",
                new String[]{provshiId},null,null,null);
        while (cursor.moveToNext()){
            City city = new City(cursor.getString(1),cursor.getString(2),cursor.getString(3));
            list.add(city);
        }
        return list;
    }

    /**
     * 获取全部的城市列表信息
     * @return
     */
    public List<City> getCityList(){
        ArrayList<City> list = new ArrayList<City>();
        Cursor cursor = db.query(LocationSQLiteOpenHelper.TABLE_CITYS,null,null,
                null,null,null,null);
        while (cursor.moveToNext()){
            City city = new City(cursor.getString(1),cursor.getString(2),cursor.getString(3));
            list.add(city);
        }
        return list;
    }

    /**
     * 根据省份ID 和城市ID 获取指定城市的信息
     * @param cityID
     * @param provshiID
     * @return
     */
    public City getCityByID(String cityID,String provshiID){
        City city = new City();
        Cursor cursor = db.query(LocationSQLiteOpenHelper.TABLE_CITYS,null,"city_id=? and provshi_id=?",
                new String[]{cityID,provshiID},null,null,null);
        while (cursor.moveToNext()){
            city = new City(cursor.getString(1),cursor.getString(2),cursor.getString(3));
        }
        return city;
    }

    /**
     * 保存单个城市的信息
     * @param city
     */
    public void addCity(City city){
        if(city != null && city.cityId != null){
            ContentValues cv = new ContentValues();
            cv.put("city_id",city.cityId);
            cv.put("city_name",city.cityName);
            cv.put("provshi_id",city.provshiID);
            db.insert(LocationSQLiteOpenHelper.TABLE_CITYS,null,cv);
        }
    }

    /**
     * 保存多个城市的信息
     * @param cityList
     */
    public void addCitys(List<City> cityList){
        for (City c :
                cityList) {
            addCity(c);
        }
    }
}
