package activities.fragmentlist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqyj.dev.ji.fs_sxt.R;

import views.DrawNormalContrlNode;

/**
 *
 * Created by jiyangkang on 2016/4/18 0018.
 */
public class CtrolNode_AG extends Fragment {

    private View view;
    private DrawNormalContrlNode alarm, fan, irr_1, irr_2;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    alarm.setValue(msg.getData().getString("value"));
                    alarm.invalidate();
                    break;
                case 2:
                    fan.setValue(msg.getData().getString("value"));
                    fan.invalidate();
                    break;
                case 3:
                    irr_1.setValue(msg.getData().getString("value"));
                    irr_1.invalidate();
                    break;
                case 4:
                    irr_2.setValue(msg.getData().getString("value"));
                    irr_2.invalidate();
                    break;
                default:
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ctrl_ag, container, false);
        initShow();
        return view;
    }

    private void initShow() {
        alarm = (DrawNormalContrlNode) view.findViewById(R.id.alarm_ag);
        alarm.setOnReceived(new DrawNormalContrlNode.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Message msg = new Message();
                msg.what = 1;
                Bundle b = new Bundle();
                b.putString("value", stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        alarm.invalidate();
        fan = (DrawNormalContrlNode) view.findViewById(R.id.fan_ag);
        fan.setOnReceived(new DrawNormalContrlNode.OnReceived() {
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
        irr_1 = (DrawNormalContrlNode) view.findViewById(R.id.irr_1);
        irr_1.setOnReceived(new DrawNormalContrlNode.OnReceived() {
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
        irr_2 = (DrawNormalContrlNode) view.findViewById(R.id.irr_2);
        irr_2.setOnReceived(new DrawNormalContrlNode.OnReceived() {
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
    }
}
