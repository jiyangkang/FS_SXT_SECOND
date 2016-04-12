package nodes;

import nodes.behavior.FillStatusData;
import nodes.behavior.SendControlCMD;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Alarm extends Node {
    public volatile static Alarm alarm;

    private Alarm(){
        byte[] addr = new byte[]{0x00, 0x10, 0x61};
        mFillDatas = new FillStatusData();
        mSendCMD = new SendControlCMD(addr);
        setAddr(addr);
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


}
