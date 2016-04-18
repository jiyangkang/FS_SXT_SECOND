package nodes;

import nodes.behavior.FillInfraredData;
import nodes.behavior.FillStatusData;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Infrared extends Node {
    private volatile static Infrared infrared;

    private Infrared(){
        mFillDatas = new FillInfraredData();
        mSendCMD = null;
        byte[] addr = new byte[]{0x00, 0x0E, 0x49};
        setAddr(addr);
        setName("人体红外传感器");
    }

    public static Infrared getInfrared(){
        if (infrared == null){
            synchronized (Infrared.class){
                if (infrared == null)
                    infrared = new Infrared();
            }
        }
        return infrared;
    }
}
