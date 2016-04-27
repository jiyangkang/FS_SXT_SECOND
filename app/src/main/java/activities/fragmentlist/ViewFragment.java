package activities.fragmentlist;

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
 * Created by jiyangkang on 2016/4/15 0015.
 */
public class ViewFragment extends Fragment{
    private View view;
    private DrawViewNode ammeter, temp, keeper, light, rastar, gas, fog, infrared;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    ammeter.setValueString(msg.getData().getString("value"));
                    ammeter.invalidate();
                    break;
                case 2:
                    temp.setValueString(msg.getData().getString("value"));
                    temp.invalidate();
                    break;
                case 3:
                    keeper.setValueString(msg.getData().getString("value"));
                    keeper.invalidate();
                    break;
                case 4:
                    light.setValueString(msg.getData().getString("value"));
                    light.invalidate();
                    break;
                case 5:
                    rastar.setValueString(msg.getData().getString("value"));
                    rastar.invalidate();
                    break;
                case 6:
                    gas.setValueString(msg.getData().getString("value"));
                    gas.invalidate();
                    break;
                case 7:
                    fog.setValueString(msg.getData().getString("value"));
                    fog.invalidate();
                    break;
                case 8:
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
        view = inflater.inflate(R.layout.fragment_view_hs, container, false);
        initShow();

        return view;
    }

    private void initShow() {
        ammeter = (DrawViewNode) view.findViewById(R.id.node_ammeter);
        ammeter.setmName("智能电表");
        ammeter.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Log.d("aaaa","received");
                Message msg = new Message();
                msg.what = 1;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                Log.d("aaaaa", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        ammeter.invalidate();

        temp = (DrawViewNode) view.findViewById(R.id.node_temp_hs);
        temp.setmName("空气温湿度");
        temp.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Log.d("aaaa","received");
                Message msg = new Message();
                msg.what = 2;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                Log.d("aaaaa", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        temp.invalidate();

        keeper = (DrawViewNode) view.findViewById(R.id.node_keeper);
        keeper.setmName("门禁");
        keeper.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Log.d("aaaa","received");
                Message msg = new Message();
                msg.what = 3;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                Log.d("aaaaa", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        keeper.invalidate();

        light = (DrawViewNode) view.findViewById(R.id.node_light_hs);
        light.setmName("光照");
        light.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Log.d("aaaa","received");
                Message msg = new Message();
                msg.what = 4;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                Log.d("aaaaa", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        light.invalidate();

        rastar = (DrawViewNode) view.findViewById(R.id.node_raster);
        rastar.setmName("红外光栅");
        rastar.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Log.d("aaaa","received");
                Message msg = new Message();
                msg.what = 5;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                Log.d("aaaaa", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        rastar.invalidate();

        gas = (DrawViewNode) view.findViewById(R.id.node_gas);
        gas.setmName("燃气传感器");
        gas.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Log.d("aaaa","received");
                Message msg = new Message();
                msg.what = 6;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                Log.d("aaaaa", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        gas.invalidate();

        fog = (DrawViewNode) view.findViewById(R.id.node_fog);
        fog.setmName("烟雾传感器");
        fog.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Log.d("aaaa","received");
                Message msg = new Message();
                msg.what = 7;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                Log.d("aaaaa", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        fog.invalidate();

        infrared = (DrawViewNode) view.findViewById(R.id.node_infrared_hs);
        infrared.setmName("人体红外传感器");
        infrared.setOnReceived(new DrawViewNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Log.d("aaaa","received");
                Message msg = new Message();
                msg.what = 8;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                Log.d("aaaaa", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        infrared.invalidate();
    }
}
