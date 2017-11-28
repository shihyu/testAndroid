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
    private SQLiteDatabase db;

    public LocationDB(Context context){
        db = LocationSQLiteOpenHelper.getInstance(context).getWritableDatabase();
    }

    public void addProvshi(Provshi provshi){
        if(provshi != null && provshi.provshiId != null){
            ContentValues cv = new ContentValues();
            cv.put("provshi_id",provshi.provshiId);
            cv.put("provshi_name",provshi.provshiName);
            db.insert(LocationSQLiteOpenHelper.TABLE_PROVSHI,null,cv);
        }
    }

    public void addProvshis(List<Provshi> provshiList){
        for (Provshi p :
                provshiList) {
            addProvshi(p);
        }
    }

    public List<Provshi> getProvshiList(){
        ArrayList<Provshi> list = new ArrayList<Provshi>();
        Cursor cursor = db.query(LocationSQLiteOpenHelper.TABLE_PROVSHI,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            Provshi p = new Provshi(cursor.getString(1),cursor.getString(2));
            list.add(p);
        }
        return list;
    }

    public List<String> getProvshNameiList(){
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.query(LocationSQLiteOpenHelper.TABLE_PROVSHI,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String s = new String(cursor.getString(1));
            list.add(s);
        }
        return list;
    }

    public List<String> getCityNameiList(){
        ArrayList<String> list = new ArrayList<String>();
        Cursor cursor = db.query(LocationSQLiteOpenHelper.TABLE_CITYS,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            String s = new String(cursor.getString(1));
            list.add(s);
        }
        return list;
    }

    public List<City> getCityList(String provshiId){
        ArrayList<City> list = new ArrayList<City>();
        Cursor cursor = db.query(LocationSQLiteOpenHelper.TABLE_CITYS,null,"provshi_id=?",new String[]{provshiId},null,null,null);
        while (cursor.moveToNext()){
            City city = new City(cursor.getString(1),cursor.getString(2),cursor.getString(3));
            list.add(city);
        }
        return list;
    }

    public List<City> getCityList(){
        ArrayList<City> list = new ArrayList<City>();
        Cursor cursor = db.query(LocationSQLiteOpenHelper.TABLE_CITYS,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            City city = new City(cursor.getString(1),cursor.getString(2),cursor.getString(3));
            list.add(city);
        }
        return list;
    }

    public void addCity(City city){
        if(city != null && city.cityId != null){
            ContentValues cv = new ContentValues();
            cv.put("city_id",city.cityId);
            cv.put("city_name",city.cityName);
            cv.put("provshi_id",city.provshiID);
            db.insert(LocationSQLiteOpenHelper.TABLE_PROVSHI,null,cv);
        }
    }
    public void addCitys(List<City> cityList){
        for (City c :
                cityList) {
            addCity(c);
        }
    }
}
