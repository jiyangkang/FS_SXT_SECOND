package nodes;

import nodes.behavior.FillStatusData;
import nodes.behavior.SendControlCMD;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Fan extends Node {
    private volatile static Fan fan;

    private Fan(){
        byte[] addr = new byte[]{0x00, 0x12, 0x66};
        mFillDatas = new FillStatusData();
        mSendCMD = new SendControlCMD(addr);
        setAddr(addr);
        setName("风扇");
    }

    public static Fan getFan(){
        if (fan == null){
            synchronized (Fan.class){
                if (fan == null)
                    fan = new Fan();
            }
        }
        return fan;
    }
}
