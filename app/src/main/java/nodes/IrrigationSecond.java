package nodes;

import nodes.behavior.FillStatusData;
import nodes.behavior.SendControlCMD;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class IrrigationSecond extends Node {
    public volatile static IrrigationSecond irrigationSecond;

    private IrrigationSecond(){
        byte[] addr = new byte[]{0x00, 0x0F, 0x69};
        mFillDatas = new FillStatusData();
        mSendCMD = new SendControlCMD(addr);
        setAddr(addr);
    }

    public static IrrigationSecond getIrrigationSecond(){
        if (irrigationSecond == null){
            synchronized (IrrigationSecond.class){
                if (irrigationSecond == null)
                    irrigationSecond = new IrrigationSecond();
            }
        }
        return irrigationSecond;
    }
}
