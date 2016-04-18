package activities.fragmentlist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqyj.dev.ji.fs_sxt.R;

import views.DrawViewNode;

/**
 *
 * Created by jiyangkang on 2016/4/17 0017.
 */
public class ViewFragment_ag extends Fragment {

    private View view;
    private DrawViewNode temp, light, soil, co2, pm25, infrared;

//

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    temp.setValueString(msg.getData().getString("value"));
                    temp.invalidate();
                    break;
                case 2:
                    light.setValueString(msg.getData().getString("value"));
                    light.invalidate();
                    break;
                case 3:
                    soil.setValueString(msg.getData().getString("value"));
                    soil.invalidate();
                    break;
                case 4:
                    co2.setValueString(msg.getData().getString("value"));
                    co2.invalidate();
                    break;
                case 5:
                    pm25.setValueString(msg.getData().getString("value"));
                    pm25.invalidate();
                    break;
                case 6:
                    infrared.setValueString(msg.getData().getString("value"));
                    infrared.invalidate();
                    break;
                default:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_ag, container, false);
        initShow();
        return view;
    }

    private void initShow() {
        temp = (DrawViewNode) view.findViewById(R.id.node_temp);
        temp.setmName("空气温湿度");
        temp.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Log.d("TEMP","received");
                Message msg = new Message();
                msg.what = 1;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                Log.d("TEMP", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        temp.invalidate();

        light = (DrawViewNode) view.findViewById(R.id.node_light);
        light.setmName("光照");
        light.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Message msg = new Message();
                msg.what = 2;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        light.invalidate();

        soil = (DrawViewNode) view.findViewById(R.id.node_soil);
        soil.setmName("土壤温湿度");
        soil.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Message msg = new Message();
                msg.what = 3;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        soil.invalidate();

        co2 = (DrawViewNode) view.findViewById(R.id.node_co2);
        co2.setmName("二氧化碳");
        co2.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Message msg = new Message();
                msg.what = 4;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        co2.invalidate();

        pm25 = (DrawViewNode) view.findViewById(R.id.node_pm25);
        pm25.setmName("环境PM2.5");
        pm25.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Message msg = new Message();
                msg.what = 5;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        pm25.invalidate();

        infrared = (DrawViewNode) view.findViewById(R.id.node_infrared);
        infrared.setmName("人体红外传感器");
        infrared.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Message msg = new Message();
                msg.what = 6;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        infrared.invalidate();
    }



}
