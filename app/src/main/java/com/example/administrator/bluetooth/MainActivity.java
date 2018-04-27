package com.example.administrator.bluetooth;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.animation.AlertDlog;
import com.example.administrator.constant.Constant;
import com.example.administrator.constdata.BluetoothControl;
import com.example.administrator.constdata.Observer;
import com.example.administrator.httpsocket.HttpRequest;
import com.example.administrator.thread.BlueAdapterGet;
import com.example.administrator.thread.ThreadBluetooth;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

import static com.example.administrator.constant.Constant.IED;

public class MainActivity extends AppCompatActivity {


    private final int REQUEST_ENABLE_BT=1;
    BluetoothAdapter mBluetoothAdapter;
    List mList,mArrayAdapter;
    ListView mListView;
    private Spinner spinner;
    Button button;
    BluetoothDevice device;
    private EditText mEditText;
    ThreadBluetooth send;
    ArrayAdapter<String> mAdapter;

    Set<BluetoothDevice> pairedDevices;
    UUID id=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private String mac;
    public  static final String ACTION_TIMETRAVEL ="com.example.administrator.bluetooth.TIMETRAVEL";
    List<String>  viewlist;

    private String strIp;

    boolean flag = true;
    Observer observer;
    ArrayAdapter<String> adapter;

    Handler handler = new Handler() {
        /*
        0         1         2        3     4-6       7         8-21
        帧头0x44 字节长度 找到目的标签 RSSI 工作频率 EPC和PC字节长度 PC+EPC
        */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IED:

                    byte[] buffer=new byte[msg.arg1];
                    buffer= (byte[]) msg.obj;

                    String str="";
                    for(byte b:buffer){

                        str+= Integer.toHexString(b & 0xFF)+" ";
                    }
                    int temp[]=new int[buffer.length];

                    if(buffer[1]==0x05){
                        str="未识别标签";
                        mAdapter.add(str);
                        // 处理消息，进行UI操
                        mListView.setAdapter(mAdapter);
                        break;
                    }


                    if(buffer[2]!=0){
                        str+="标签数："+Integer.toHexString(buffer[2] & 0xFF)+"\n";
                        int temp1=Integer.parseInt(Integer.toHexString(buffer[6] & 0xFF),16)<<0x16;
                        //0D<<16|35<<08 | A4
                        //	int temp1=Integer.parseInt(Integer.toHexString(buffer[6] & 0xFF))<<0x16;
                        //	System.out.println(temp1);
                        int temp2=Integer.parseInt(Integer.toHexString(buffer[5] & 0xFF),16)<<0x08;
                        int temp3=Integer.parseInt(Integer.toHexString(buffer[4] & 0xFF),16);
                        // str+="RSSI:"+buffer[3]+"\n";
                        //   int freq=buffer[6]<<16|buffer[5]<<0x08|buffer[4];
                        // String str2=""+temp1+temp2+temp3;
                        int temp4=Integer.parseInt(Integer.toHexString(buffer[6] & 0xFF)+Integer.toHexString(buffer[5] & 0xFF)+Integer.toHexString(buffer[4] & 0xFF),16);
                        str+="工作频率：0x"+Integer.toHexString(buffer[6] & 0xFF)+Integer.toHexString(buffer[5] & 0xFF)+Integer.toHexString(buffer[4] & 0xFF)+"="+temp4/1000.0+"MHz\n";
                        str+="EPC:"+Integer.toHexString(buffer[8]& 0xFF)+" "+Integer.toHexString(buffer[9]& 0xFF)+" "+Integer.toHexString(buffer[10]& 0xFF)+" "+Integer.toHexString(buffer[11]& 0xFF)+" "+Integer.toHexString(buffer[12]& 0xFF)+" "+
                                Integer.toHexString(buffer[13]& 0xFF)+" "+Integer.toHexString(buffer[14]& 0xFF)+" "+Integer.toHexString(buffer[15]& 0xFF)+" "+Integer.toHexString(buffer[16]& 0xFF)+" "+
                                Integer.toHexString(buffer[17]& 0xFF)+" "+Integer.toHexString(buffer[18]& 0xFF)+" "+Integer.toHexString(buffer[19]& 0xFF)+" "+Integer.toHexString(buffer[20]& 0xFF)+" "
                        ;
                        mAdapter.clear();
                        mAdapter.add(str);



                        // 处理消息，进行UI操
                        mListView.setAdapter(mAdapter);


                        break;
                    }
                    mAdapter.add(str);
                    mListView.setAdapter(mAdapter);
                    break;
                default:
                    break;
            }
        }
    };

    private Handler httphandler=new Handler()
    {
        public void handleMessage(Message msg)
        {
            if(msg.what==0)
            {
                //msg.obj是获取handler发送信息传来的数据
                @SuppressWarnings("unchecked")
                String stri= (String) msg.obj;
                //给ListView绑定数据
                mAdapter.clear();
                mAdapter.add(stri);
                mListView.setAdapter(mAdapter);
            }
        }
    };























    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniWidgt();
        viewlist=new ArrayList<>();


        /*
        * 下拉菜单显示
        * */

        mAdapter=new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1);

        spinner = (Spinner) findViewById(R.id.spinenr);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 3:
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {

                                            String sr= HttpRequest.hell(mEditText.getText().toString(),Constant.ip);
                                            Toast.makeText(MainActivity.this,sr,Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        });

                        break;

                    case 0:
/*
        弹出框
* */
                        if(flag){
                            new AlertDlog(MainActivity.this).show();
                            flag =!flag;



                        }
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //Toast.makeText(MainActivity.this,mEditText.getText().toString(),Toast.LENGTH_SHORT).show();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {

                                            try {
                                                String rs=HttpRequest.hell1(mEditText.getText().toString(),Constant.ip);

                                              //  String srt= new String(rs.getBytes("ISO8859-1"),"utf-8");
                                                httphandler.sendMessage(handler.obtainMessage(0, rs));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();


                                }
                            });














                        break;

                    case 1:
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(mac==null|mac==""){
                                    Intent mIntent=new Intent(MainActivity.this, BLEDeivice.class);
                                    startActivityForResult(mIntent, 2);
                                }else{
                                    Toast.makeText(getApplicationContext(),mac,Toast.LENGTH_LONG).show();
                                    if(send==null){
                                        device = BlueAdapterGet.getmBluetoothAdapter().getRemoteDevice(mac);
                                        try {
                                            //send=new ThreadBluetooth(device,handler);
                                            send.sendBlueCommand(mEditText.getText().toString(),getApplication());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }else{
                                        try {
                                            send.sendBlueCommand(mEditText.getText().toString(),getApplication());
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        });

                        break;




                    default:
                        break;












                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {



            }
        });




















    }


    public void iniWidgt(){
        button= (Button) findViewById(R.id.button);
        mEditText= (EditText) findViewById(R.id.command);
        mListView= (ListView) findViewById(R.id.viewshow);

        mEditText.setText("43 03 01");
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(BluetoothControl.getBluetoothControl()!=null)
            BluetoothControl.getBluetoothControl().stop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mac = data.getStringExtra("select");
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case 0:

                break;
            case 2:
                try {
                    send=ThreadBluetooth.getThis(device);
                    send.setHandler(handler);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;
        }
    }





}
