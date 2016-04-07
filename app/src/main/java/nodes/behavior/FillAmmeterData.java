package nodes.behavior;

import tools.StringTools;

/**
 *
 * Created by jiyangkang on 2016/4/8 0008.
 */
public class FillAmmeterData implements FillDatas{
    @Override
    public String fillData(byte[] datas) {
        for (int i = 0; i < datas.length; i++){
            datas[i] -= 0x33;
        }
        String str = StringTools.changeIntoHexString(datas,false);
        StringBuilder stringBuilder = new StringBuilder(str);
        stringBuilder.insert(str.length()-2,'.');

        return stringBuilder.toString();
    }
}
