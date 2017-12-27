package com.example.mylibrary.download;

import android.content.Context;

/**
 * Created by Administrator on 2017/10/23.
 */

public class OkDownBuilder {
    private String url;//下载地址
    private String name;//文件名
    private String path;//下载路径
    private String childTaskCount;//单个任务
    Context context;

    public OkDownBuilder(Context context){
        this.context=context;
    }


    public OkDownBuilder url(String url){
        this.url=url;
        return this;
    }
    public OkDownBuilder name(String name){
        this.name=name;
        return this;
    }

    public OkDownBuilder path(String path){
        this.path=path;
        return this;
    }


}
