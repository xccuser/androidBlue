package com.example.administrator.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.administrator.animation.Animation1;
import com.example.administrator.animation.MultiPropertyAnimation;
import com.example.administrator.blueservice.BLEService;
import com.example.administrator.constdata.BluetoothControl;
import com.example.administrator.constdata.Constants;
import com.example.administrator.constdata.DataBulethooDevice;
import com.example.administrator.constdata.Observer;
import com.example.administrator.constdata.ObserverInterface;
import com.example.administrator.thread.BlueAdapterGet;
import com.example.administrator.thread.ThreadBluetooth;

import junit.framework.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.example.administrator.constant.Constant.IED;
import static com.example.administrator.constdata.DataBulethooDevice.getDataDevice;

public class MainActivity extends AppCompatActivity {


    private final int REQUEST_ENABLE_BT=1;
    BluetoothAdapter mBluetoothAdapter;
    List mList,mArrayAdapter;
    ListView mListView;
    Button button;
    BluetoothDevice device;
    private EditText mEditText;
    ThreadBluetooth send;
    ArrayAdapter<String> mAdapter;
    byte[] buffer=new byte[32];
    Set<BluetoothDevice> pairedDevices;
    UUID id=UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private String mac;
    public  static final String ACTION_TIMETRAVEL = "com.example.administrator.bluetooth.TIMETRAVEL";


    Observer observer;
    ArrayAdapter<String> adapter;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IED:
                    buffer= (byte[]) msg.obj;
                  /*  if(buffer[0]!=0) {
                        for (int i : buffer)
                            System.out.println(": "+Integer.toHexString(i & 0xFF));
                    }*/
                    String str="";
                    if(buffer[0]!=0) {
                        int temp=0;
                        for (int i : buffer){
                            if(temp!=16){
                                str+=Integer.toHexString(i & 0xFF)+" ";
                                temp++;
                            }else {
                                temp=0;
                                str+="\n";
                            }}}
                    System.out.println(str);
                    mAdapter.add(str);
                    // 处理消息，进行UI操
                    str="";
                    mListView.setAdapter(mAdapter);
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniWidgt();
        mAdapter=new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1);
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
                            send=new ThreadBluetooth(device,handler);
                            send.sendBlueCommand(mEditText.getText().toString());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        send.sendBlueCommand(mEditText.getText().toString());
                    }
                }

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

                break;
            default:
                break;
        }
    }






}
