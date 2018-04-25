package com.example.administrator.packetbean;

/**
 * Created by Administrator on 2018/1/7.
 */

/*

 //帧头0x31 字节长度 开始盘存0x01
    //帧头0x32 字节长度 找到的标签数目
    存盘指令：
    0         1         2        3     4-6       7         8-21
    帧头0x44 字节长度 找到目的标签 RSSI 工作频率 EPC和PC字节长度 PC+EPC

*/

public class InputReadRFID {
    private byte[] save1={0x31,0x03,0x01};
    private byte[] save2={0x43,0x03,0x01};

    public byte[] getSave1() {
        return save1;
    }

    public void setSave1(byte[] save1) {
        this.save1 = save1;
    }

    public byte[] getSave2() {
        return save2;
    }

    public void setSave2(byte[] save2) {
        this.save2 = save2;
    }
}
