package com.zl.hefenweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zy1373 on 2017-11-27.
 */

public class LocationSQLiteOpenHelper extends SQLiteOpenHelper {
    private final String TAG= "CitysActivity";

    private final static String DBName = "weathers";
    private final static int version = 1;

    private static LocationSQLiteOpenHelper mDataBaseHelper = null;

    private final static boolean drop = true;

    private static final String DROP_TABLE = "drop table if exists Provshi;\ndrop table if exists Citys";

    public static final String TABLE_PROVSHI = "Provshi";
    private final String CREATE_PROVSHI_TABLE = "CREATE TABLE if not exists Provshi (\n" +
            "    id           INT       PRIMARY KEY,\n" +
            "    provshi_id   CHAR (20) NOT NULL,\n" +
            "    provshi_name CHAR (100) \n" +
            ");";

    public static final String TABLE_CITYS = "Citys";
    private final String CREATE_CITY_TABLE = "CREATE TABLE  if not exists Citys (\n" +
            "    id           INT       PRIMARY KEY,\n" +
            "    city_id   CHAR (20) NOT NULL,\n" +
            "    city_name CHAR (100), \n" +
            "    provshi_id CHAR (20) \n" +
            ");";

    private LocationSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"onCreate drop=" +drop);

        if (drop){
            db.execSQL(DROP_TABLE);
        }
        db.execSQL(CREATE_PROVSHI_TABLE);
        db.execSQL(CREATE_CITY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG,"onUpgrade drop=" +drop);
        if (drop){
            db.execSQL(DROP_TABLE);
            db.execSQL(CREATE_PROVSHI_TABLE);
            db.execSQL(CREATE_CITY_TABLE);
        }
    }

    public static LocationSQLiteOpenHelper getInstance(Context context){
        if(mDataBaseHelper == null){
            mDataBaseHelper = new LocationSQLiteOpenHelper(context,DBName,null,version);;
        }

        return  mDataBaseHelper;
    }

}
