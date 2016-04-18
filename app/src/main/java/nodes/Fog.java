package nodes;

import nodes.behavior.FillFogData;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Fog extends Node {
    private volatile static Fog fog;

    private Fog(){
        byte[] addr = new byte[]{0x00, 0x0D, 0x46};
        mFillDatas = new FillFogData();
        mSendCMD = null;
        setAddr(addr);
        setName("烟雾传感器");
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
