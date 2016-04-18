package nodes.behavior;

import java.util.HashMap;

import tools.StringTools;

/**
 *
 * Created by jiyangkang on 2016/4/8 0008.
 */
public class FillAmmeterData implements FillDatas{

    @Override
    public HashMap<String, String> fillData(byte[] datas) {
        HashMap<String, String> mHashMap = new HashMap<>();
        //只有电量

        StringBuilder stringBuilder = new
                StringBuilder(StringTools.changeIntoHexString(datas, false));
        stringBuilder.insert(stringBuilder.toString().length() - 2, '.');
        stringBuilder.append("kWh");
        mHashMap.put("电量", stringBuilder.toString());
        return mHashMap;
    }
}
