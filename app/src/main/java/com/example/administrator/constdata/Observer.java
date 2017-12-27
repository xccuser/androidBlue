package com.example.administrator.constdata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/7.
 */

public class Observer implements Serializable {

    Observer observer;
    List<String> mList;
    public Observer(List<String> list){
        mList= (ArrayList<String>) list;
    }

    public void setListener(List<String> list){

    }

   public void updata(){

   }



}
