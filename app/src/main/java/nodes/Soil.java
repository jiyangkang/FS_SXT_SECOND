package nodes;

import nodes.behavior.FillWeatherDataSoil;

/**
 * 
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Soil extends Node {
    private volatile static Soil soil;

    private Soil(){
        byte[] addr = new byte[]{0x00, 0x06, 0x53};
        setAddr(addr);
        mFillDatas = new FillWeatherDataSoil();
        mSendCMD = null;
    }

    public static Soil getSoil(){
        if (soil == null){
            synchronized (Soil.class){
                if (soil == null)
                    soil = new Soil();
            }
        }
        return soil;
    }
}
