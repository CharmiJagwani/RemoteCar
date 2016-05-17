package com.example.charmi.remotecar;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Charmi on 22-03-2016.
 */
public class MainActivity extends Activity {

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    ImageView downleft,up,down,downright,upleft,upright,brake,headlight;
    private ConnectedThread mConnectedThread;

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
       // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        downleft = (ImageView)findViewById(R.id.downleft);
        downright = (ImageView)findViewById(R.id.downright);
        up = (ImageView)findViewById(R.id.up);
        down = (ImageView)findViewById(R.id.down);
        upleft = (ImageView)findViewById(R.id.upleft);
        upright = (ImageView)findViewById(R.id.upright);
        brake =  (ImageView)findViewById(R.id.brake);
        headlight = (ImageView)findViewById(R.id.headlight);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();


        up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("F");
            }
        });

        down.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("B");
            }
        });

        upleft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("G");
            }
        });

        upright.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("I");
            }
        });

        brake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("S");
            }
        });

        downleft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("H");
            }
        });

        downright.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("J");
            }
        });

        headlight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mConnectedThread.write("W");
            }
        });


    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    @Override
    public void onResume() {
        super.onResume();

        BluetoothDevice device = btAdapter.getRemoteDevice("98:D3:31:90:66:90");

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, "Socket creation failed", Toast.LENGTH_LONG).show();
        }

        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {

            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        mConnectedThread.write("x");


    }


    private void checkBTState() {

        if (btAdapter == null) {
            Toast.makeText(MainActivity.this, "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else if (!btAdapter.isEnabled()) {

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

