package services;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

import java.util.HashMap;

/**
 * check project type
 */
public class CheckProject extends Service {

    private boolean threadOn = false;
    private OnProjectChange mOnProjectChange;
    private AnalysisService.Binder binder = null;

    public void setmOnProjectChange(OnProjectChange mOnProjectChange) {
        this.mOnProjectChange = mOnProjectChange;
    }

    public CheckProject() {
    }

    public class Binder extends android.os.Binder{
        public CheckProject getCheckProject(){
            return CheckProject.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (AnalysisService.Binder) service;
            binder.getAnalysisService().setOnDataReceived(new AnalysisService.OnDataReceived() {
                @Override
                public void haveData(HashMap<String, byte[]> thisHash) {

                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent iIntent = new Intent(this, AnalysisService.class);
        bindService(iIntent, connection, BIND_AUTO_CREATE);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static interface OnProjectChange{
        void onProjectChange(String projectType);
    }
}
