package nodes;

import nodes.behavior.FillStatusData;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Fog extends Node {
    private volatile static Fog fog;

    private Fog(){
        byte[] addr = new byte[]{0x00, 0x0D, 0x46};
        mFillDatas = new FillStatusData();
        mSendCMD = null;
        setAddr(addr);
    }

    public static Fog getFog(){
        if (fog == null){
            synchronized (Fog.class){
                if (fog == null)
                    fog = new Fog();
            }
        }
        return fog;
    }
}
