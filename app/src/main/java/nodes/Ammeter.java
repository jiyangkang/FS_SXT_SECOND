package nodes;

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
