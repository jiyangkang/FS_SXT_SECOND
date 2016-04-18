package nodes;

import nodes.behavior.FillGasData;
import nodes.behavior.FillStatusData;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Gas extends Node {
    private volatile static Gas gas;

    private Gas(){
        mFillDatas = new FillGasData();
        mSendCMD = null;
        byte[] addr = new byte[]{0x00, 0x0C, 0x47};
        setAddr(addr);
        setName("燃气传感器");
    }

    public static Gas getGas(){
        if (gas == null){
            synchronized (Gas.class){
                if (gas == null)
                    gas = new Gas();
            }
        }
        return gas;
    }
}
