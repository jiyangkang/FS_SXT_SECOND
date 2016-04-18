package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
                byte[] datas = UdpTools.getFromUDP();
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
                    UdpTools.sendToUDP(datas);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
