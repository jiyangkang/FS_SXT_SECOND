package nodes.behavior;

import java.util.HashMap;

import tools.StringTools;

/**
 * Created by jiyangkang on 2016/4/18 0018.
 */
public class FillKeeperData implements FillDatas{
    @Override
    public HashMap<String, String> fillData(byte[] datas) {
        HashMap<String, String> value = new HashMap<>();

        byte[] id = new byte[4];

        System.arraycopy(datas, 2, id, 0, 4);
        value.put("ID", StringTools.changeIntoHexString(id, true));

        if (datas[7] == 0x00){
            value.put("进出", "1号刷卡器刷卡");
        } else {
            value.put("进出", "2号刷卡器刷卡");
        }

        if (datas[9] == 0x00){
            value.put("门锁","上锁");
        }else {
            value.put("门锁", "开锁");
        }

        return value;
    }
}
