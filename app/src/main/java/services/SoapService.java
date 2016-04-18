package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import tools.DataTools;
import tools.MathTools;
import tools.SoapTools;
import tools.StringTools;

/**
 * Connect to WebServer and communicate with it
 * Created by jiyangkang on 2016/4/6 0006.
 */
public class SoapService extends Service {

    private boolean threadOn = false;
    private GetThread mGetThread = null;
    private SendThread mSendThread = null;

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
            //when stop service, passing the ENDTHREAD byte array
            DataTools.sends.put(DataTools.ENDTHREAD);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mSendThread.interrupt();
        mGetThread.interrupt();
        mGetThread = null;
        mSendThread = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        threadOn = true;
        mGetThread = new GetThread();
        mSendThread = new SendThread();
        mGetThread.start();
        mSendThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private class GetThread extends Thread {
        String strGet;
        byte[] datas;

        @Override
        public void run() {
            super.run();
            while (threadOn) {
                strGet = SoapTools.getFromServer(DataTools.DEVICE);
                if (strGet != null) {
                    datas = MathTools.changeIntoByte(strGet);
                    try {
                        if (datas != null && threadOn) {
                            DataTools.gets.put(datas);
                        } else
                            Log.d("DATA_SOAP", "erro datas");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    sleep(DataTools.delayBase * 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class SendThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (threadOn) {
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    byte[] datas = DataTools.sends.take();
                    if (datas == DataTools.ENDTHREAD)
                        break;
                    String sendString = StringTools.changeIntoHexString(datas, false);
                    stringBuilder.append(sendString)
                            .append(DataTools.userId)
                            .append(DataTools.DEVICE);
                    String request = SoapTools.sendToServer(stringBuilder.toString());
                    if (request != null && request.equalsIgnoreCase("ok")) {
                        Log.e("SOAP", "success");
                    } else {
                        Log.e("SOAP", "Can't Send");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
