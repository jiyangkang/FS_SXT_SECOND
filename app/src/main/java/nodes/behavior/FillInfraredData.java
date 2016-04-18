package nodes.behavior;

import java.util.HashMap;

/**
 * Created by jiyangkang on 2016/4/18 0018.
 */
public class FillInfraredData implements FillDatas {
    @Override
    public HashMap<String, String> fillData(byte[] datas) {
        HashMap<String, String> value = new HashMap<>();

        if (datas[0] == 0x30){
            value.put("红外", "无人");
        } else {
            value.put("红外", "有人");
        }

        return value;
    }
}
