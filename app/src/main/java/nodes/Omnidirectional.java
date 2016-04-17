package nodes;

import nodes.behavior.FillStatusData;
import nodes.behavior.SendControlCMD;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Omnidirectional extends Node {
    private volatile static Omnidirectional omnidirectional;

    private Omnidirectional(){
        mFillDatas = new FillStatusData();
        byte[] addr = new byte[]{0x00, 0x0F, 0x6F};
        setAddr(addr);
        mSendCMD = new SendControlCMD(addr);

    }

    public static Omnidirectional getOmnidirectional(){
        if (omnidirectional == null){
            synchronized (Omnidirectional.class){
                if (omnidirectional == null)
                    omnidirectional = new Omnidirectional();
            }
        }
        return omnidirectional;
    }
}
