package com.example.administrator.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.example.administrator.blueservice.BLEService;

import java.util.Set;

import static com.example.administrator.constdata.BluetoothControl.getBluetoothControl;
import static com.example.administrator.constdata.Constants.REQUEST_ENABLE_BT;

/**
 * Created by Administrator on 2017/9/8.
 */

public class BLEDeivice extends Activity implements View.OnClickListener {
    ListView mListView;
    BluetoothAdapter mBluetoothAdapter;
    ArrayAdapter<String> mArrayAdapter;
    Button button;
    Context mContext;

    /*
    * 检测蓝牙是否可用，不可用直接关闭
    *
    * */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.ble_device);
        setProgressBarIndeterminateVisibility(true);
        setTitle("Scanning");
        button = (Button) findViewById(R.id.butemt);
        mListView = (ListView) findViewById(R.id.display_bluetooth);
       // Toast.makeText(getApplication(),button.getId()+"",Toast.LENGTH_SHORT).show();
        mContext=this;
        mArrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              /* Intent ite = new Intent(BLEDeivice.this, BLEService.class);
                String str=(String)adapterView.getAdapter().getItem(i);
                String[] s2=str.split("\n");
                ite.putExtra("select", s2[1]);
                Toast.makeText(getApplication(),s2[1],Toast.LENGTH_LONG).show();
                startService(ite);*/
                Intent ite = new Intent(BLEDeivice.this, MainActivity.class);
                String str=(String)adapterView.getAdapter().getItem(i);
                String[] s2=str.split("\n");
                ite.putExtra("select", s2[1]);
                setResult(2,ite);
                finish();
            }
        });
    }

    /*
    * 可以执行扫描监听新设备
    *
    * */
    @Override
    protected void onStart() {
        super.onStart();
        //启动蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            this.finish();
        }
  /*      IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);*/
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        beforediscovery();
        //设置蓝牙配置
        mListView.setAdapter(mArrayAdapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBluetoothAdapter.startDiscovery();
            }
        });
        //开是发现
        //getBluetoothControl(mBluetoothAdapter).start();
       // getBluetoothControl().update(mArrayAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }



/*
    private final BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            synchronized (getBluetoothControl()){
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if(!getBluetoothControl().contains(device)){
                        getBluetoothControl().add(device);
                    }
//停止监听
            }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                setProgressBarIndeterminateVisibility(false);
                setTitle("stop scanning");
                getBluetoothControl().update();
            }
                getBluetoothControl().update();
            }
        }
    };
*/



    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
            mArrayAdapter.notifyDataSetChanged();
        }
    };

    public void beforediscovery(){
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.butemt:
                Toast.makeText(getApplication(),"butoon",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getApplication(),"butoon",Toast.LENGTH_SHORT).show();
                break;

        }


    }
}
