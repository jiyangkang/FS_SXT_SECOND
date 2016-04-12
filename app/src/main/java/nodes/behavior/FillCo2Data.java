package nodes.behavior;

import java.util.HashMap;

import tools.MathTools;

/**
 *
 * Created by jiyangkang on 2016/4/12 0012.
 */
public class FillCo2Data implements FillDatas {
    @Override
    public HashMap<String, String> fillData(byte[] datas) {
        int value = MathTools.changeIntoInt(datas);
        HashMap<String, String> thisHash= new HashMap<>();
        thisHash.put("二氧化碳浓度", ""+value);
        return thisHash;
    }
}
