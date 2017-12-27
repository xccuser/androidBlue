package com.example.administrator.constdata;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

public class DataBulethooDevice  {

    List<String> mList;
    static DataBulethooDevice dataBulethooDevice;
    int i=0;
    public DataBulethooDevice(){
      mList=new ArrayList<String>();
      mList.add(" A");
  }

    public static DataBulethooDevice getDataDevice(){
        if (dataBulethooDevice==null)  dataBulethooDevice=new DataBulethooDevice();
        return dataBulethooDevice;
    }

    public List<String> getList() {
        return mList;
    }

    public void setList(List<String> list) {
        this.mList = list;
    }


    public void add(String str){
            if(!mList.contains(str))
                mList.add(str);
    }

    public int getItem(){
        return mList.size();

    }
    public int getOldItem(){
        return i;
    }

    public void setI(int i){
        this.i=i;
    }
}
