package com.hqyj.dev.ji.testswitch;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MainActivity extends Activity implements View.OnClickListener {

    private WifiManager wifiManager;
    private WifiManager.MulticastLock multicastLock;
    private TextView textView;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    byte[] datas = msg.getData().getByteArray("value");
                    if (datas != null) {
                        textView.setText(StringTools.changeIntoHexString(datas, true));
                    }
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        multicastLock = wifiManager.createMulticastLock("multicast.test");

        textView = (TextView) findViewById(R.id.tx1);
        findViewById(R.id.btn1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                new getThread().start();
                break;
            default:
                break;
        }
    }


    private class getThread extends Thread {
        @Override
        public void run() {
            super.run();
            byte[] datas = new byte[64];
            try {
                MulticastSocket multicastSocket = new MulticastSocket(20000);

                multicastSocket.setLoopbackMode(true);

                InetAddress group = InetAddress.getByName("224.10.10.10");
                multicastSocket.joinGroup(group);
                DatagramPacket datagramPacket = new DatagramPacket(datas, datas.length);
                Log.d("UUUU", "1");
//                multicastLock.acquire();
                Log.d("UUUU", "2");

                multicastSocket.receive(datagramPacket);
                Log.d("UUUU", "3");

//                multicastLock.release();
                Log.d("UUUU", "4");

                multicastSocket.close();
                Bundle b = new Bundle();
                b.putByteArray("value", datas);
                Message msg = new Message();
                msg.what = 1;
                msg.setData(b);
                handler.sendMessage(msg);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
