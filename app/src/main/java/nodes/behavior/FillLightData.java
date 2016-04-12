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
        byte[] value = new byte[2];
        HashMap<String, String> v = new HashMap<>();
        System.arraycopy(datas, 2, value, 0, 2);
        v.put("光照强度", MathTools.changeIntoInt(value) + "");

        if (0x00 == datas[0] && 0x00 == datas[1])
            v.put("数据", 1 + "");
        else
            v.put("数据", 0 + "");
        return v;
    }
}
