package nodes.behavior;

import java.util.HashMap;

import tools.MathTools;

/**
 * Created by jiyangkang on 2016/4/12 0012.
 */
public class FillWeatherDataSoil implements FillDatas {
    @Override
    public HashMap<String, String> fillData(byte[] datas) {
        byte[] temp = new byte[2];
        byte[] water = new byte[2];
        HashMap<String, String> value = new HashMap<>();

        System.arraycopy(datas, 0, temp, 0, 2);
        System.arraycopy(datas, 2, water, 0, 2);
        float tempValue = (float)(MathTools.changeIntoInt(temp)/100);
        value.put("温度",tempValue+"");

        float waterValue = (float)(MathTools.changeIntoInt(water)/100);
        value.put("湿度",waterValue+"");

        return value;
    }
}
