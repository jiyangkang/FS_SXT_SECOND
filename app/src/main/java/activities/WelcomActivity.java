package activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.hqyj.dev.ji.fs_sxt.R;

import java.util.HashMap;
import java.util.Iterator;

import services.AnalysisService;
import services.UDPService;
import tools.DataTools;
import tools.StringTools;

/**
 * the first view shown when the app start
 */
public class WelcomActivity extends Activity {

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
//                    String str = msg.getData().getString("data");
//                    mTextView.setText(str);
                    break;
                case 2:
                    String str1 = msg.getData().getString("data");
                    mTextView.setText(str1);
                    break;
                default:
                    break;
            }

        }
    };

    private boolean threadOn = false;
    private TextView mTextView;
    private AnalysisService.Binder binder = null;

    private int i = 0;

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (AnalysisService.Binder) service;
            binder.getAnalysisService().setOnDataReceived(new AnalysisService.OnDataReceived() {

                @Override
                public void haveData(HashMap<String, byte[]> thisHash) {
                    Log.d("getdata", "has get data");
                    Message msg = new Message();
                    msg.what = 2;
                    byte[] dataType = thisHash.get("dataType");
                    byte[] netType = thisHash.get("netType");
                    byte[] device = thisHash.get("device");
                    byte[] data = thisHash.get("data");

                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder
                            .append("第:").append(i).append("次。")
                            .append("数据类型:")
                            .append(StringTools.changeIntoHexString(dataType,false))
                            .append("。网络类型")
                            .append(StringTools.changeIntoHexString(netType, false))
                            .append("。设备：")
                            .append(StringTools.changeIntoHexString(device, false))
                            .append("。数据:")
                            .append(StringTools.changeIntoHexString(data,false));
                    Bundle bundle = new Bundle();
                    bundle.putString("data", stringBuilder.toString());
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                    i++;
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

        mTextView = (TextView) findViewById(R.id.show);

        Intent mIntent = new Intent(WelcomActivity.this, UDPService.class);
        startService(mIntent);

        Intent intent = new Intent(WelcomActivity.this, AnalysisService.class);
        startService(intent);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

//        threadOn = true;
//        getThread a = new getThread();
//        a.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        threadOn = false;
        try {
            DataTools.gets.put(DataTools.ENDTHREAD);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        unbindService(serviceConnection);
    }

    private class getThread extends Thread{
        int j = 0;
        @Override
        public void run() {
            super.run();
            while(threadOn){
                try {
                    j++;
                    byte[] datas = DataTools.gets.take();
                    if (datas == DataTools.ENDTHREAD)
                        break;
                    Message msg = new Message();
                    Bundle mBundle = new Bundle();
                    mBundle.putString("data", j + StringTools.changeIntoHexString(datas,true));
                    msg.setData(mBundle);
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
