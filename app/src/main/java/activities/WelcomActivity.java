package activities;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.hqyj.dev.ji.fs_sxt.R;

import java.util.Map;

import nodes.NodeInfo;

/**
 * the first view shown when the app start
 */
public class WelcomActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcom);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
//
//            for (Map.Entry<byte[], String> entry : NodeInfo.hashList.entrySet()){
//                byte[] datas = entry.getKey();
//                String name = entry.getValue();
//
//                Message msg = new Message();
//                msg.what = 1;
//                StringBuilder stringBuilder = new StringBuilder();
//                for (byte data : datas){
//                    stringBuilder.append((char)data);
//                    stringBuilder.append(" ");
//                }
//                stringBuilder.append(name);
//                msg.obj = stringBuilder.toString();
//                Log.d("Thread", stringBuilder.toString());
//                handler.sendMessage(msg);
//                try {
//                    sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
}
