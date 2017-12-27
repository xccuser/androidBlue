package com.example.administrator.thread;

import android.bluetooth.BluetoothAdapter;

/**
 * Created by Administrator on 2017/12/16.
 */

public class BlueAdapterGet {
    static BluetoothAdapter mBluetoothAdapter=null;


    static{
        mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
    }

    public static BluetoothAdapter getmBluetoothAdapter(){
        if(mBluetoothAdapter==null){
            mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        }
        return mBluetoothAdapter;
    }



}
