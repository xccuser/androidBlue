package com.example.administrator.thread;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.administrator.blueservice.BLEService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static com.example.administrator.constant.Constant.IED;
import static com.example.administrator.constdata.BlueToothModel.blueTootId;

/**
 * Created by Administrator on 2017/12/16.
 */

public class ThreadBluetooth {

    BluetoothAdapter mBluetoothAdapter=null;
    private BluetoothSocket mmSocket;
    byte[] buffer = new byte[40];
    Handler handler;
    ConnectedThread startWrite=null;
    static ThreadBluetooth bule=null;
    static BluetoothDevice device1;

    public static ThreadBluetooth getThis(BluetoothDevice device) throws InterruptedException {
        device1=device;
        if(bule==null){
            bule=new ThreadBluetooth(device);
        }
        return bule;
    }








    public ThreadBluetooth(BluetoothDevice device,Handler handler) throws InterruptedException {
        this.handler=handler;
        new ConnectThread(device).start();
        // Thread.currentThread().sleep(1000);
        startWrite=new ConnectedThread(mmSocket);
        startWrite.start();
    }
    public ThreadBluetooth(BluetoothDevice device) throws InterruptedException {
        new ConnectThread(device).start();
        // Thread.currentThread().sleep(1000);
      //  startWrite=new ConnectedThread(mmSocket);
       // startWrite.start();
    }






/*
* 输入16进制要有空格
*
* */
    byte[] sendbyte;
    public void sendBlueCommand(String args,Context context) throws InterruptedException {
        String arg[]=args.split(" ");
        sendbyte=new byte[arg.length];
        for(int i=0;i<arg.length;i++){
            sendbyte[i]= (byte) sToh(arg[i]);
        }
        //new ConnectThread(device1).start();
        //Thread.currentThread().sleep(1000);
        if(mmSocket==null){
            Toast.makeText(context,"连接蓝牙",Toast.LENGTH_SHORT).show();
        }else{
            //new ConnectThread(device1).start();
            new ConnectedThread(mmSocket).start();
        }

        //startWrite.run();

    }

    public int sToh(String args) {
        return Integer.parseInt(args) / 10 * 16 + Integer.parseInt(args) % 10;
    }

    private class ConnectThread extends Thread {

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
            mBluetoothAdapter=BlueAdapterGet.getmBluetoothAdapter();
            // Cancel discovery because it will slow down the connection
            mBluetoothAdapter.cancelDiscovery();
            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            //    new ConnectedThread(mmSocket).start();
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
            boolean flag=false;
            // buffer store for the stream

            int bytes=0; // bytes returned from read()
            write(sendbyte);
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Keep listening to the InputStream until an exception occurs
            while (true) {
               // write(sendbyte);
                try {

                       bytes = mmInStream.read(buffer);
                       if(bytes==0){
                       }else{
                           handler.obtainMessage(IED, bytes, -1, buffer)
                                   .sendToTarget();
                         //  mmInStream.read(buffer);
                           /*for(int i=0;i<buffer.length;i++)
                               buffer[i]=0;*/
                           mmInStream.reset();
                         //  startWrite.stop();
                       }
                    //    Thread.currentThread().stop();
                  //  for(;(bytes=mmInStream.read())!=-1;)
                   // mmInStream.close();




                    Thread.currentThread().interrupt();
                    break;



                    //   Message message=Message.obtain();

                    // Send the obtained bytes to the UI activity

                } catch (IOException e) {
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
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


    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
