package com.example.administrator.constdata;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.administrator.bluetooth.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.administrator.constdata.Constants.REQUEST_ENABLE_BT;
import static com.example.administrator.constdata.DataBulethooDevice.getDataDevice;

/**
 * Created by Administrator on 2017/10/26.
 */

public class BluetoothControl {


    private BluetoothAdapter mBtAdapter;
    ArrayAdapter adapter;
    ArrayList<BluetoothDevice> mBlueToothdevices=new ArrayList<>();//获取蓝牙设备
    static BluetoothControl mBluetoothControl;
    ArrayList<String> mBlueToothdevicesStr=new ArrayList<String>();//显示蓝牙设备具体名称的


    public synchronized ArrayList<String> getmBlueToothdevicesStr() {
        return mBlueToothdevicesStr;
    }

    public synchronized ArrayList<BluetoothDevice> getmBlueToothdevices() {
        return mBlueToothdevices;
    }

    public BluetoothControl(BluetoothAdapter mBtAdapter){
        this.mBtAdapter=mBtAdapter;
    }

    public void setmBtAdapter(BluetoothAdapter mBtAdapter) {
        this.mBtAdapter = mBtAdapter;
    }


    public synchronized static BluetoothControl getBluetoothControl(BluetoothAdapter mBtAdapter){
        if(mBluetoothControl==null){
            mBluetoothControl=new BluetoothControl(mBtAdapter);
        }
        return mBluetoothControl;
    }
    public synchronized static BluetoothControl getBluetoothControl(){
        if(mBluetoothControl==null){
            System.exit(0);
        }
        return mBluetoothControl;
    }
    //开蓝牙扫描
    public void start(){
        mBtAdapter.startDiscovery();
    }

    //关蓝牙
    public void stop(){
        if (mBtAdapter != null) {
            mBtAdapter.disable();
        }
    }
    //更新蓝牙显示
    public synchronized void update(ArrayAdapter adapter){
        this.adapter=adapter;
        update();
    }
    //更新蓝牙显示
    public synchronized void update(){
        if(adapter.getCount()!=mBlueToothdevicesStr.size()){
            adapter.clear();
            adapter.addAll(mBlueToothdevicesStr);
            adapter.notifyDataSetChanged();
        }

    }
    //防止重复扫描包含
    public synchronized boolean contains(BluetoothDevice bluetoothDevice){
        for(int i=0;i<mBlueToothdevices.size();i++) {
            if (bluetoothDevice.getName().equals(getmBlueToothdevices().get(i).getName())) {
                return true;
            }
        }
        return false;
    }
    //添加蓝牙设备的
    public synchronized void add(BluetoothDevice bluetoothDevice){
        if(!contains(bluetoothDevice)){
            getmBlueToothdevices().add(bluetoothDevice);
            getmBlueToothdevicesStr().add(bluetoothDevice.getName()+"\n"+bluetoothDevice.getAddress());
        }
    }
}
