package nodes;

import nodes.behavior.FillStatusData;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Omnidirectional extends Node {
    public volatile static Omnidirectional omnidirectional;

    private Omnidirectional(){
        mFillDatas = new FillStatusData();
        byte[] addr = new byte[]{0x00, 0x09, 0x6F};
        setAddr(addr);

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
