package com.zl.hefenweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.zl.hefenweather.entity.Provshi;

/**
 * Created by zy1373 on 2017-11-27.
 */

public class LocationDB {
    private SQLiteDatabase db;

    public LocationDB(Context context){
        db = LocationSQLiteOpenHelper.getInstance(context).getWritableDatabase();
    }

    public void addProvshi(Provshi provshi){
        if(provshi != null && provshi.cityId != null){
            ContentValues cv = new ContentValues();
            cv.put("provshi_id",provshi.cityId);
            cv.put("provshi_name",provshi.cityName);
            //db.insert()
        }
    }
}
