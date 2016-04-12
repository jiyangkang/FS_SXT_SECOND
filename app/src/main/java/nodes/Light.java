package nodes;

import nodes.behavior.FillLightData;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Light extends Node{
    public volatile static Light light;

    private Light(){
        mFillDatas = new FillLightData();
        mSendCMD = null;
        byte[] addr = new byte[]{0x00, 0x03, 0x4C};
        setAddr(addr);
    }


    public static Light getLight(){
        if (light == null){
            synchronized (Light.class){
                if (light == null)
                    light = new Light();
            }
        }
        return light;
    }
}
