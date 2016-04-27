package activities.fragmentlist;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hqyj.dev.ji.fs_sxt.R;

import nodes.NodeInfo;
import tools.DataTools;
import tools.MathTools;
import tools.StringTools;

/**
 *
 * Created by jiyangkang on 2016/4/17 0017.
 */
public class CodeAndTel_ag extends Fragment implements View.OnTouchListener{

    private View view;
    private EditText editText;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(view.getContext(), "请输入正确格式的电话号码", Toast.LENGTH_SHORT).show();
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
        view = inflater.inflate(R.layout.tel_code_ag, container, false);

        initShow();
        view.findViewById(R.id.btn_submit).setOnTouchListener(this);
        return view;
    }

    private void initShow() {
        editText = (EditText) view.findViewById(R.id.edt_alarm);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.btn_submit){
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundResource(R.drawable.submit_dis);
                    break;
                case MotionEvent.ACTION_UP:
                    v.setBackgroundResource(R.drawable.submit);
                    sendCMD(editText.getText().toString());
                    break;
            }
        }
        return true;
    }

    private void sendCMD(String tel){
        byte[] a = new byte[19];
        a[0] = DataTools.HEAD_RECEIVE;
        a[DataTools.LENGTH] = 0x0b;
        a[DataTools.OFFSET] = 0x08;
        a[DataTools.DATATYPE] = DataTools.CONTROLDATA;
        a[DataTools.NETTYPE] = NodeInfo.netType;
        a[DataTools.DEVICEADDR_H] = 0x00;
        a[DataTools.DEVICEADDR_L] = 0x00;
        a[DataTools.DEVICETYPE] = 0x00;

        byte[] telByte = tel.getBytes();


        if (tel.equalsIgnoreCase("null") && telByte.length != 11 && telByte[0] != '1'){
            handler.sendEmptyMessage(1);
        }else {
            System.arraycopy(telByte, 0, a, 8, telByte.length);
            byte mata = MathTools.makeMate(a);
            byte[] cmd = new byte[20];
            System.arraycopy(a, 0, cmd, 0, 19);
            cmd[19] = mata;
            Log.d("CMD", StringTools.changeIntoHexString(cmd, true));
            try {
                DataTools.sends.put(cmd);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
