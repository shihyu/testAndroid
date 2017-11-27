package com.zl.hefenweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zy1373 on 2017-11-27.
 */

public class LocationSQLiteOpenHelper extends SQLiteOpenHelper {

    private final static String DBName = "weathers";
    private final static int version = 1;

    private static LocationSQLiteOpenHelper mDataBaseHelper = null;

    private final String CREATE_PROVSHI_TABLE = "CREATE TABLE Provshi (\n" +
            "    id           INT       PRIMARY KEY,\n" +
            "    provshi_id   CHAR (20) NOT NULL,\n" +
            "    provshi_name CHAR (100) \n" +
            ");";
    private final String CREATE_CITY_TABLE = "CREATE TABLE Citys (\n" +
            "    id           INT       PRIMARY KEY,\n" +
            "    city_id   CHAR (20) NOT NULL,\n" +
            "    city_name CHAR (100) \n" +
            "    provshi_id CHAR (20) \n" +
            ");";

    private LocationSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVSHI_TABLE);
        db.execSQL(CREATE_CITY_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static LocationSQLiteOpenHelper getInstance(Context context){
        if(mDataBaseHelper == null){
            mDataBaseHelper = new LocationSQLiteOpenHelper(context,DBName,null,version);;
        }

        return  mDataBaseHelper;
    }

}
