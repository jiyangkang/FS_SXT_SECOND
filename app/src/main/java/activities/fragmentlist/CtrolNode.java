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

import views.DrawCtrlHs;

/**
 *
 * Created by jiyangkang on 2016/4/15 0015.
 */
public class CtrolNode extends Fragment {

    private View view;
    private String key = "value";

    private DrawCtrlHs fan, alarm;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    fan.setValue(msg.getData().getString(key));
                    fan.invalidate();
                    break;
                case 2:
                    alarm.setValue(msg.getData().getString(key));
                    alarm.invalidate();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ctrl_hs, container, false);
        initView();
        return view;
    }

    private void initView() {
        fan = (DrawCtrlHs) view.findViewById(R.id.hs_fan);
        fan.setOnReceived(new DrawCtrlHs.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Message msg = new Message();
                msg.what = 1;
                Bundle b = new Bundle();
                b.putString(key, stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        fan.invalidate();
        alarm = (DrawCtrlHs) view.findViewById(R.id.hs_alarm);
        alarm.setOnReceived(new DrawCtrlHs.OnReceived() {
            @Override
            public void received(StringBuilder stringBuilder) {
                Message msg = new Message();
                msg.what = 2;
                Bundle b = new Bundle();
                b.putString(key, stringBuilder.toString());
                msg.setData(b);
                handler.sendMessage(msg);
            }
        });
        alarm.invalidate();
    }


}
