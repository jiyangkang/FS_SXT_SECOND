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
        //假定只有电量
        for (int i = 0; i < datas.length; i++){
            if (datas[i] != 0x00){
                datas[i] = (byte) (datas[i] - 0x33);
            }
        }
        StringBuilder stringBuilder = new
                StringBuilder(StringTools.changeIntoHexString(datas, false));
        stringBuilder.insert(stringBuilder.toString().length() - 2, '.');
        mHashMap.put("电量", stringBuilder.toString());
        return mHashMap;
    }
}
