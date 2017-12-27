package com.example.administrator.blueservice;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.constdata.BluetoothControl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static com.example.administrator.bluetooth.MainActivity.ACTION_TIMETRAVEL;
import static com.example.administrator.constdata.BlueToothModel.blueTootId;
import static com.example.administrator.constdata.BluetoothControl.getBluetoothControl;
import static com.example.administrator.constdata.DataBulethooDevice.getDataDevice;

/**
 * Created by Administrator on 2017/9/17.
 */

public class BLEService extends Service {

    ArrayList<String> mArrayAdapter;
    BluetoothAdapter mBluetoothAdapter =null;





    public class MyBinder extends Binder{
        public ArrayList<String> getAdapter(){
            return mArrayAdapter;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

      //  Toast.makeText(this,(int) intent.getSerializableExtra("select"),Toast.LENGTH_SHORT).show();
       // Toast.makeText(getApplicationContext(),(int) intent.getSerializableExtra("select"),Toast.LENGTH_SHORT).show();
//       Log.i("select",  getBluetoothControl().getmBlueToothdevices()
//                .get((Integer)intent.getSerializableExtra("select")).getName());

            String mac= (String) intent.getSerializableExtra("select");
        BluetoothDevice device=mBluetoothAdapter.getRemoteDevice(mac);


        new ConnectThread(device).start();
        Message message=Message.obtain();



        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;
            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(blueTootId);
            } catch (IOException e) { }
            mmSocket = tmp;
        }
        public void run() {
            // Cancel discovery because it will slow down the connection
            mBluetoothAdapter.cancelDiscovery();
            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
                new ConnectedThread(mmSocket).start();




            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }
            // Do work to manage the connection (in a separate thread)

        }
        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            boolean flag=true;
            byte[] buffer = new byte[50];  // buffer store for the stream
            byte[] bufferwrite = new byte[3];
            int bytes; // bytes returned from read()
            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                  if(flag){
                        bufferwrite[0]=0x43;
                        bufferwrite[1]=0x03;
                        bufferwrite[2]=0x01;
                        write(bufferwrite);
                        flag=false;
                    }
                    bytes = mmInStream.read(buffer);
                    for(int i:buffer) {
                        System.out.print(i+""+Integer.toHexString(i & 0xFF));
                        System.out.print(" ");
                    }
                    // Send the obtained bytes to the UI activity
                } catch (IOException e) {
                    break;
                }
            }
        }
        /* Call this from the main activity to send data to the remote device */
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
                mmOutStream.flush();
            } catch (IOException e) { }
        }
        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }






}
