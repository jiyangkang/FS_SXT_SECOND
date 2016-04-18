package nodes.behavior;

import java.util.HashMap;

import tools.MathTools;

/**
 *
 * Created by jiyangkang on 2016/4/12 0012.
 */
public class FillLightData implements FillDatas {
    @Override
    public HashMap<String, String> fillData(byte[] datas) {
        byte[] value = new byte[4];
        HashMap<String, String> v = new HashMap<>();
        System.arraycopy(datas, 2, value, 0, 4);
        v.put("光照强度", MathTools.changeIntoInt(value) + "lux");

        return v;
    }
}
