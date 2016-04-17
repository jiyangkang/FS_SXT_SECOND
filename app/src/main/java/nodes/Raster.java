package nodes;

import nodes.behavior.FillStatusData;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Raster extends Node{
    private volatile static Raster raster;

    private Raster(){
        byte[] addr = new byte[]{0x00, 0x0B, 0x52};
        setAddr(addr);
        mFillDatas = new FillStatusData();
        mSendCMD = null;
    }

    public static Raster getRaster(){
        if (raster == null){
            synchronized (Raster.class){
                if (raster == null)
                    raster = new Raster();
            }
        }
        return raster;
    }
}
