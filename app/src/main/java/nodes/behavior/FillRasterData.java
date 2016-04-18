package nodes.behavior;

import java.util.HashMap;

/**
 * Created by jiyangkang on 2016/4/18 0018.
 */
public class FillRasterData implements FillDatas{
    @Override
    public HashMap<String, String> fillData(byte[] datas) {
        HashMap<String, String> value = new HashMap<>();

        if (datas[0] == 0x30){
            value.put("光栅","无遮拦");
        } else {
            value.put("光栅", "有遮拦");
        }

        return value;
    }
}
