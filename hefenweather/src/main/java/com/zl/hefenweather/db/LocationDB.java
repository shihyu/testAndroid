package com.zl.hefenweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zl.hefenweather.entity.City;
import com.zl.hefenweather.entity.Provshi;

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

    public addProvshis(List<Provshi> provshiList){
        for (Provshi p :
                provshiList) {
            addProvshi(p);
        }
        
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
