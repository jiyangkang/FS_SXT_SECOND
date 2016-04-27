package services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.MulticastSocket;

import tools.DataTools;
import tools.StringTools;
import tools.UdpTools;

/**
 * communicate with local network
 * Created by jiyangkang on 2016/4/6 0006.
 */
public class UDPService extends Service{
    private boolean threadOn = false;
    private GetThread mGetThread;
    private SendThread mSendThread;
    private WifiManager wifiManager;
    private WifiManager.MulticastLock multicastLock;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        multicastLock = wifiManager.createMulticastLock("multicast.test");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        threadOn = false;
        try {
            //when stopping thread send ENDTHREAD byte array
            DataTools.sends.clear();
            DataTools.sends.put(DataTools.ENDTHREAD);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mSendThread.interrupt();
        mSendThread = null;

        mGetThread.interrupt();
        mGetThread = null;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("UDPSERVICE", "UDPService start");
        threadOn = true;
        mGetThread = new GetThread();
        mGetThread.start();
        mSendThread = new SendThread();
        mSendThread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * receive the information from local Multi broadcast group
     */
    private class GetThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(threadOn){
                Log.d("UDP", "UDPGETTHREAD--OK");
                multicastLock.acquire();
                byte[] datas = UdpTools.getFromUDP();
                multicastLock.release();
                if (datas != null){
                    try {
                        Log.d("get", StringTools.changeIntoHexString(datas, true));
                        if (threadOn){
                            DataTools.gets.put(datas);
                        }
                        Log.d("Get", "1");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    /**
     * send cmd to server using udp
     */
    private class SendThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(threadOn){
                try {
                    byte[] datas = DataTools.sends.take();
                    if (datas == DataTools.ENDTHREAD)
                        break;
                    multicastLock.acquire();
                    UdpTools.sendToUDP(datas);
                    multicastLock.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
