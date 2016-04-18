package nodes.behavior;

import java.util.HashMap;

/**
 * Created by jiyangkang on 2016/4/18 0018.
 */
public class FillGasData implements FillDatas{
    @Override
    public HashMap<String, String> fillData(byte[] datas) {
        HashMap<String, String> value = new HashMap<>();

        if (datas[0] == 0x30){
            value.put("燃气","未超标");
        } else {
            value.put("燃气", "超标");
        }

        return value;
    }
}
