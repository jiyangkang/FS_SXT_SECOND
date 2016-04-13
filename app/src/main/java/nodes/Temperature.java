package nodes;

import nodes.behavior.FillWeatherData;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Temperature extends Node{
    public volatile static Temperature temperature;

    private Temperature(){
        byte[] addr = new byte[]{0x00, 0x02, 0x54};
        setAddr(addr);
        mFillDatas = new FillWeatherData();
        mSendCMD = null;
    }

    public static Temperature getTemperature(){
        if (temperature == null){
            synchronized (Temperature.class){
                if (temperature == null)
                    temperature = new Temperature();
            }
        }
        return temperature;
    }
}
