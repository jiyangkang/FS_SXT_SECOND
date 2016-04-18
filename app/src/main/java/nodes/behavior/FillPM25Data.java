package nodes.behavior;

import java.util.HashMap;

import tools.MathTools;

/**
 *
 * Created by jiyangkang on 2016/4/18 0018.
 */
public class FillPM25Data implements FillDatas{
    @Override
    public HashMap<String, String> fillData(byte[] datas) {

        HashMap<String, String> value = new HashMap<>();

        byte[] pm25 = new byte[2];
        System.arraycopy(datas, 2, pm25, 0, 2);
        byte[] pm03 = new byte[2];
        System.arraycopy(datas, 12, pm03, 0, 2);

        value.put("PM2.5浓度", MathTools.changeIntoInt(pm25)+"ug/m³");
        value.put("0.3um以上颗粒", MathTools.changeIntoInt(pm03)+"个");

        return value;
    }
}
