package com.example.administrator.constdata;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by Administrator on 2017/10/26.
 */

public class BlueToothModel {
    public static UUID blueTootId=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");;
    private ArrayList<BluetoothDevice> mBlueDevices=new ArrayList<BluetoothDevice>();

    static BlueToothModel model;
    public static BlueToothModel getBlueToothModel(){
        synchronized (BlueToothModel.class){
            if(model==null){
                     model=new BlueToothModel();
            }
            return model;
        }
    }



    public ArrayList<BluetoothDevice> getmBlueDevices() {
        return mBlueDevices;
    }

    public void setmBlueDevices(ArrayList<BluetoothDevice> mBlueDevices) {
        this.mBlueDevices = mBlueDevices;
    }
    public  boolean add(BluetoothDevice bluetoothDevice){
        if(!mBlueDevices.contains(bluetoothDevice)){
            mBlueDevices.add(bluetoothDevice);
            return true;
        }
       return false;
    }

}
