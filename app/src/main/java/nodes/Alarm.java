package nodes;

import nodes.behavior.FillStatusData;
import nodes.behavior.SendControlCMD;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Alarm extends Node {
    public volatile static Alarm alarm;
    private String value;//状态
    private OnValueReceived onValueReceived;

    public void setOnValueReceived(OnValueReceived onValueReceived) {
        this.onValueReceived = onValueReceived;
    }

    private Alarm(){
        mFillDatas = new FillStatusData();
        mSendCMD = new SendControlCMD();
    }

    public static Alarm getAlarm(){
        if (alarm == null){
            synchronized (Alarm.class){
                if (alarm == null)
                    alarm = new Alarm();
            }
        }
        return alarm;
    }

    public void setValue(byte[] data) {
        String value = mFillDatas.fillData(data).get("状态");
        this.value = value;
        if (onValueReceived != null){
            onValueReceived.onValueReceived(value);
        }

    }

    public String getValue() {
        return value;
    }

    public void sendCMD(int what){
        mSendCMD.sendCMD(what, getAddr());
    }

    public interface OnValueReceived{
        void onValueReceived(String value);
    }

}
