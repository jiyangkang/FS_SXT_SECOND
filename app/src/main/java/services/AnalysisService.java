package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import tools.DataTools;
import tools.MathTools;

/**
 * In this service, analysis data
 * Created by jiyangkang on 2016/4/6 0006.
 */
public class AnalysisService extends Service {

    private boolean threadOn = false;
    private AnalysisThread mAnalysisThread = null;
    private OnDataReceived onDataReceived;


    public class Binder extends android.os.Binder {
        public AnalysisService getAnalysisService(){
            return AnalysisService.this;
        }
    }

    public AnalysisService(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        threadOn = true;
        mAnalysisThread = new AnalysisThread();
        mAnalysisThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        threadOn = false;
        mAnalysisThread.interrupt();
        mAnalysisThread = null;
    }

    /**
     * main loop
     */
    public class AnalysisThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (threadOn) {
                Log.d("Service","analysisThread");
                try {
                    byte[] datas = DataTools.gets.take();
                    //已经校验过的有效数据在这里
                    List<byte[]> list = MathTools.divideData(datas);
                    if (list != null) {
                        for (byte[] aList : list) {
                            byte[] value = (aList).clone();
                            analysisiData(value);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * this method is the mean method which
     * analysis the array byte by using the protocol
     * pass the result using HashMap by interface OnDataReceived
     * @param datas received
     */
    public void analysisiData(byte[] datas) {
        HashMap<String, byte[]> thisHash = new HashMap<>();
        int length = datas[DataTools.LENGTH];
        int offset = datas[DataTools.OFFSET];
        byte dataType = datas[DataTools.DATATYPE];
        byte netType = datas[DataTools.NETTYPE];
        byte[] data = new byte[length];
        System.arraycopy(datas, offset, data, 0, data.length);
        byte[] device = new byte[3];
        System.arraycopy(datas, DataTools.DEVICEADDR_H, device, 0, device.length);

        //这里重新定义字符串
        thisHash.put(DataTools._DATATYPE, new byte[]{dataType});
        thisHash.put(DataTools._NETTYPE, new byte[]{netType});
        thisHash.put(DataTools._DEVICE, device);
        thisHash.put(DataTools._DATA, data);

        if(onDataReceived != null){
            onDataReceived.haveData(thisHash);
        }
    }


    /**
     * register of the interface OnDataReceived
     * @param onDataReceived interface
     */
    public void setOnDataReceived(OnDataReceived onDataReceived) {
        this.onDataReceived = onDataReceived;
    }

    /**
     * the interface
     */
    public static interface OnDataReceived{
        void haveData(HashMap<String, byte[]> thisHash);
    }



}
