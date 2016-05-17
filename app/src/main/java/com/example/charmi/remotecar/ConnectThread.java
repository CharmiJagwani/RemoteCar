package com.example.charmi.remotecar;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Charmi on 22-03-2016.
 */
class ConnectedThread extends Thread {

    private static final String LOG = "LOG" ;
    private final OutputStream mmOutStream;


        public ConnectedThread(BluetoothSocket socket) {

            OutputStream tmpOut = null;

            try {
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            //mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }



        public void write(String input) {
            byte[] msgBuffer = input.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                //Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                Log.d(LOG,"Connection Failure");
                //finish();
            }
        }
    }

