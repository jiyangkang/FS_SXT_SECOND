package activities.fragmentlist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.hqyj.dev.ji.fs_sxt.R;

import nodes.NodeInfo;
import tools.DataTools;
import tools.MathTools;

/**
 *
 * Created by jiyangkang on 2016/4/17 0017.
 */
public class ChangeNet_AG extends Fragment implements View.OnTouchListener{

    private View view;
    private Button btnZig;
    private Button btn485;
    private TextView tx;
    private ReceiveBroadcast receiveBroadcast;
    private IntentFilter intentFilter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    tx.setText("网络状态：485");
                    btn485.setBackgroundResource(R.drawable.b485_dis);
                    btnZig.setBackgroundResource(R.drawable.zigbee);
                    break;
                case 2:
                    tx.setText("网络状态：ZigBee");
                    btn485.setBackgroundResource(R.drawable.b485);
                    btnZig.setBackgroundResource(R.drawable.zigbee_dis);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registeIntentfilter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_net_ag, container, false);
        initShow();

        btnZig.setOnTouchListener(this);
        btn485.setOnTouchListener(this);
        receiveBroadcast = new ReceiveBroadcast();
        view.getContext().registerReceiver(receiveBroadcast, intentFilter);
        return view;
    }

    private void registeIntentfilter() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("netType");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view.getContext().unregisterReceiver(receiveBroadcast);
    }

    private class ReceiveBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equalsIgnoreCase("netType")){
                switch (intent.getByteExtra("netType", (byte) 0x00)){
                    case NodeInfo.NET485:
                        handler.sendEmptyMessage(1);
                        break;
                    case NodeInfo.NETZIGBEE:
                        handler.sendEmptyMessage(2);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    private void initShow() {
        btnZig = (Button) view.findViewById(R.id.btn_zigbee);
        btn485 = (Button) view.findViewById(R.id.btn_485);
        tx = (TextView) view.findViewById(R.id.txv_net);
        if (NodeInfo.netType != 0x00){
            switch (NodeInfo.netType){
                case NodeInfo.NET485:
                    tx.setText("网络状态：485");
                    btn485.setBackgroundResource(R.drawable.b485_dis);
                    btnZig.setBackgroundResource(R.drawable.zigbee);
                    break;
                case NodeInfo.NETZIGBEE:
                    btn485.setBackgroundResource(R.drawable.b485);
                    btnZig.setBackgroundResource(R.drawable.zigbee_dis);
                    tx.setText("网络状态：ZigBee");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){
            case R.id.btn_485:
                if (NodeInfo.netType != NodeInfo.NET485){
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            btn485.setBackgroundResource(R.drawable.b485_dis);
                            btn485.invalidate();
                            break;
                        case MotionEvent.ACTION_UP:
                            btn485.setBackgroundResource(R.drawable.b485);
                            btn485.invalidate();
                            sendCmd(NodeInfo.NETZIGBEE);
                            break;
                    }
                }
                break;
            case R.id.btn_zigbee:
                if (NodeInfo.netType != NodeInfo.NETZIGBEE){
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            btnZig.setBackgroundResource(R.drawable.zigbee_dis);
                            btnZig.invalidate();
                            break;
                        case MotionEvent.ACTION_UP:
                            btnZig.setBackgroundResource(R.drawable.zigbee);
                            btnZig.invalidate();
                            sendCmd(NodeInfo.NET485);
                            break;
                    }
                }
                break;
        }

        return true;
    }

    private void sendCmd(byte netType){
        byte[] a = new byte[9];
        a[0] = DataTools.HEAD_RECEIVE;
        a[DataTools.LENGTH] = 0x01;
        a[DataTools.OFFSET] = 0x08;
        a[DataTools.DATATYPE] = DataTools.CHANGENET;
        a[DataTools.NETTYPE] = netType;
        a[DataTools.DEVICEADDR_H] = 0x00;
        a[DataTools.DEVICEADDR_L] = 0x00;
        a[DataTools.DEVICETYPE] = 0x00;

        a[DataTools.DATA] = netType;

        byte mata = MathTools.makeMate(a);
        byte[] cmd = new byte[10];
        System.arraycopy(a, 0, cmd, 0, a.length);
        cmd[DataTools.MATA] = mata;
        try {
            DataTools.sends.put(cmd);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
