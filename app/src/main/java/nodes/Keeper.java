package nodes;

import nodes.behavior.FillKeeperData;
import nodes.behavior.FillLightData;
import nodes.behavior.FillStatusData;
import nodes.behavior.SendControlCMD;

/**
 *
 * Created by jiyangkang on 2016/4/17 0017.
 */
public class Keeper extends Node{
    private volatile static Keeper keeper;

    private Keeper(){
        mFillDatas = new FillKeeperData();
        byte[] addr = new byte[]{0x00, 0x03, 0x44};
        mSendCMD = null;
        setAddr(addr);
        setName("门禁");
    }


    public static Keeper getKeeper(){
        if (keeper == null){
            synchronized (Light.class){
                if (keeper == null)
                    keeper = new Keeper();
            }
        }
        return keeper;
    }
}
