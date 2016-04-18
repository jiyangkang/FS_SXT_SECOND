package nodes;

import java.util.HashMap;

import nodes.behavior.FillAmmeterData;

/**
 *
 * Created by jiyangkang on 2016/4/8 0008.
 */
public class Ammeter extends Node {

    private volatile static Ammeter ammeter;

    private Ammeter(){
        mFillDatas = new FillAmmeterData();
        mSendCMD = null;
        byte[] type = new byte[]{0x00, 0x01, 0x41};
        setAddr(type);
        setName("智能电表");
    }

    public static Ammeter getAmmeter(){
        if (ammeter == null){
            synchronized (Ammeter.class){
                if (ammeter == null)
                    ammeter = new Ammeter();
            }
        }
        return ammeter;
    }


}
