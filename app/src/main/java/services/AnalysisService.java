package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Iterator;
import java.util.List;

import tools.DataTools;
import tools.MathTools;

/**
 * In this service, analysis data
 * Created by jiyangkang on 2016/4/6 0006.
 */
public class AnalysisService extends Service{

    private boolean threadOn = false;
    private AnalysisThread mAnalysisThread = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class AnalysisThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(threadOn){
                try {
                    byte[] datas = DataTools.gets.take();
                    List<byte[]> list = MathTools.divideData(datas);
                    if (list != null){
                        for (byte[] aList : list) {
                            byte[] value = (aList).clone();
                            deliverData(value);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deliverData(byte[] datas){
        switch (datas[7]){
            case 0x41:
                break;

        }
    }
}
