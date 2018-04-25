package com.example.administrator.constant;

import java.util.UUID;

/**
 * Created by Administrator on 2017/12/16.
 */

public class Constant {
    public static final UUID id = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static final int IED =2;

    public static String ip;

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        Constant.ip = ip;
    }
}