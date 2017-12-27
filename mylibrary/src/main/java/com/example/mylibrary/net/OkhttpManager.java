package com.example.mylibrary.net;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/10/23.
 */

public class OkhttpManager {

    private  OkHttpClient.Builder builder;

    public OkhttpManager() {
        builder=new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS);
    }

    public static OkhttpManager getInstance(){
        return OkHttpHolder.instance;
    }

    private static class OkHttpHolder{
        private static final OkhttpManager instance=new OkhttpManager();
    }

//异步

    /*
    * url
    * start 哪个字节开始
    * end哪个字节结束
    *
    *
    * */
    public Call iniRequest(String url, long start, long end, Callback callback){
        Request request= new Request.Builder()
                .url(url)
                .header("Range","byte"+start+"-"+end)
                .build();
        Call call= builder.build().newCall(request);
        call.enqueue(callback);
        return call;
    }
    public Response request(String url) throws IOException {
        Request request=new Request.Builder().url(url).header("Range","byte=0-").build();
            return builder.build().newCall(request).execute();
    }







}
