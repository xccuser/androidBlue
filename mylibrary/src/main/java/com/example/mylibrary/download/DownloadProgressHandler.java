package com.example.mylibrary.download;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.mylibrary.Db.Db;
import com.example.mylibrary.callback.DownloadCallback;
import com.example.mylibrary.data.DownloadData;

import static com.example.mylibrary.data.Const.NOME;
import static com.example.mylibrary.data.Const.PROGRESS;
import static com.example.mylibrary.data.Const.START;

/**
 * Created by Administrator on 2017/10/24.
 * 下载断点续传
 */

public class DownloadProgressHandler {


    private String url;
    private String path;
    private String name;
    private int childTaskCount;
    private Context mContext;
    private DownloadCallback call;

    private DownloadData mDownloadData;

    private int mCurrentState=NOME;
    private FileTask mFileTask;
    private boolean isSupportRange;
    private int currentLength=0;
    private int totalLength=0;
    private int tempChildTaskCount=0;


    private Handler mHandler=new Handler(){


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int mLastState=mCurrentState;
            mCurrentState=msg.what;
            mDownloadData.setStatus(mCurrentState);

            switch (mCurrentState)
            {
                case START:
                    //开始下载
                    Bundle bundle=msg.getData();
                    totalLength=bundle.getInt("totalLength");
                    currentLength=bundle.getInt("currentLenth");
                    isSupportRange=bundle.getBoolean("isSupportRange");
                    String last_modify=bundle.getString("last_modify");




                    if(isSupportRange){
                        childTaskCount=1;
                    }else if(currentLength==0){
                        Db.getInstance(mContext).insertData(new DownloadData(url,path,childTaskCount
                                ,name,currentLength,totalLength,last_modify,System.currentTimeMillis()));
                    }
                    if(mDownloadData!=null){

                    }


                    break;
                case PROGRESS:


                    break;


            }

        }
    };

}
