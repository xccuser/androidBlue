package com.example.mylibrary;

import android.content.Context;

import com.example.mylibrary.download.OkDownBuilder;

/**
 * Created by Administrator on 2017/10/23.
 */

public class Okdown {


    public static OkDownBuilder init(Context context){
        return new OkDownBuilder(context);
    }


}
