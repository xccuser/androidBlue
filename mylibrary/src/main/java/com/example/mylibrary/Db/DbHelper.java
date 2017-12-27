package com.example.mylibrary.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/10/23.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String CREATE_DOWNLOAD_INFO="create table download_info("+
            "id integer primary key autoincrement," +
            "url text," +
            "path text,"+
            "name text," +
            "child_task_count integer" +
            "current_length integer" +
            "total_length integer" +
            "percentage real" +
            "status integer" +
            "last_modify text" +
            "date text)";




    public DbHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
           sqLiteDatabase.execSQL(CREATE_DOWNLOAD_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
           switch (oldVersion){
               case 1:
                   sqLiteDatabase.execSQL("alter table download_info add column status integer");
                   break;
               default:
                   break;
           }
    }
}
