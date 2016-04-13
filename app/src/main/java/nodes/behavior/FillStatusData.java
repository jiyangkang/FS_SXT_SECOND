package nodes.behavior;

import java.util.HashMap;

import nodes.NodeInfo;

/**
 *
 * Created by jiyangkang on 2016/4/11 0011.
 */
public class FillStatusData implements FillDatas {
    @Override
    public HashMap<String, String> fillData(byte[] datas) {
        HashMap<String, String> thisHash = new HashMap<>();
        switch(datas[0]){
            case NodeInfo.CLOSE:
                thisHash.put("状态", "关");
                break;
            case NodeInfo.OPEN:
                thisHash.put("状态", "开");
                break;
            default:
                break;
        }
        return thisHash;
    }
}
