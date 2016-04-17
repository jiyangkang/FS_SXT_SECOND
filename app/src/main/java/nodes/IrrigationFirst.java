package nodes;

import nodes.behavior.FillStatusData;
import nodes.behavior.SendControlCMD;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class IrrigationFirst extends Node{
    private volatile static IrrigationFirst irrigationFirst;

    private IrrigationFirst(){
        byte[] addr = new byte[]{0x00, 0x0F, 0x69};
        mFillDatas = new FillStatusData();
        mSendCMD = new SendControlCMD(addr);
        setAddr(addr);
    }

    public static IrrigationFirst getIrrigationFirst(){
        if (irrigationFirst == null){
            synchronized (IrrigationFirst.class){
                if (irrigationFirst == null)
                    irrigationFirst = new IrrigationFirst();
            }
        }
        return irrigationFirst;
    }
}
