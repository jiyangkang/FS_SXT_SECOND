package nodes;

import nodes.behavior.FillCo2Data;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class CO2 extends Node{

    private volatile static CO2 co2;

    private CO2(){
        byte[] addr = new byte[]{0x00, 0x07, 0x43};
        mSendCMD = null;
        mFillDatas = new FillCo2Data();
        setAddr(addr);
    }

    public static CO2 getCo2(){
        if (co2 == null){
            synchronized (CO2.class){
                if (co2 == null)
                    co2 = new CO2();
            }
        }
        return co2;
    }
}
