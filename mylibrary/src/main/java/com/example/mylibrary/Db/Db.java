package com.example.mylibrary.Db;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mylibrary.data.DownloadData;

import java.nio.DoubleBuffer;

/**
 * Created by Administrator on 2017/10/23.
 */

public class Db {


    private static final String DB_NAME="db_okdown";
    private static final int VERSION=2;
    private   String TABLE_NAME_ODDOWN="";

    private static Db db;

    private SQLiteDatabase sqldb;

    private Db(Context context){
        DbHelper dbHelper=new DbHelper(context,DB_NAME,null,VERSION);
        sqldb=dbHelper.getWritableDatabase();
    }
    public static Db getInstance(Context context){
        if(db==null){
            synchronized (Db.class){
                if(db==null){
                    db=new Db(context);
                }
            }
        }
        return db;
    }


    /*
    *     private String url;
    private String path;
    private String name;
    private int currentLength;
    private int totalLength;
    private float percentage;//已完成量
    private int status=NOME;
    private int childTaskCount;//线程数
    private long date;
    private String last_modify;
    *
    *
    * */

    public void insertData(DownloadData data){
        ContentValues values=new ContentValues();
        values.put("url",data.getUrl());
        values.put("path",data.getPath());
        values.put("name",data.getName());
        values.put("currentLength",data.getCurrentLength());
        values.put("totalLength",data.getTotalLength());
        values.put("percentage",data.getPercentage());
        values.put("status",data.getStatus());
        values.put("childTaskCount",data.getChildTaskCount());
        values.put("date",data.getDate());
        values.put("last_modify",data.getLast_modify());
        sqldb.insert(TABLE_NAME_ODDOWN,null,values);
    }

    public DownloadData getDate(String url){

        Cursor cursor=sqldb.query(TABLE_NAME_ODDOWN,null,"url = ?",new String[]{url},null,null,null);
        if(!cursor.moveToFirst()) {
            return null;
        }
        DownloadData data=new DownloadData();
        data.setUrl(cursor.getString(cursor.getColumnIndex("url")));
        data.setPath(cursor.getString(cursor.getColumnIndex("path")));
        data.setName(cursor.getString(cursor.getColumnIndex("name")));
        data.setCurrentLength(cursor.getInt(cursor.getColumnIndex("currentLength")));
        data.setTotalLength(cursor.getInt(cursor.getColumnIndex("totalLength")));
        data.setPercentage(cursor.getInt(cursor.getColumnIndex("percentage")));
        data.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
        data.setChildTaskCount(cursor.getInt(cursor.getColumnIndex("childTaskCount")));
        data.setDate(cursor.getInt(cursor.getColumnIndex("date")));
        data.setLast_modify(cursor.getString(cursor.getColumnIndex("last_modify")));
        cursor.close();
        return data;
    }




    public void updata(int currentSize,float percentage,int status,String url){


    }

}
