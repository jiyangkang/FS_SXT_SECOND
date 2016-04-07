package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

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

            }
        }
    }
}
