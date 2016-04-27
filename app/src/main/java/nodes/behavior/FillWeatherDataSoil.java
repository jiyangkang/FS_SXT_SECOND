package nodes.behavior;

import java.util.HashMap;

import tools.MathTools;

/**
 *
 * Created by jiyangkang on 2016/4/12 0012.
 */
public class FillWeatherDataSoil implements FillDatas {
    @Override
    public HashMap<String, String> fillData(byte[] datas) {
        byte[] temp = new byte[2];
        byte[] water = new byte[2];
        HashMap<String, String> value = new HashMap<>();

        System.arraycopy(datas, 0, temp, 0, 2);
        System.arraycopy(datas, 2, water, 0, 2);
        int t = MathTools.changeIntoInt(temp);
        StringBuilder tStringBuilder = new StringBuilder();
        if (t >= 100 || t <= -100){
            tStringBuilder.append(t);
            tStringBuilder.insert(tStringBuilder.toString().length() - 2 , '.');
        } else if (t <= 10 || t <= -10){
            tStringBuilder.append(t);
            tStringBuilder.insert(tStringBuilder.toString().length() - 2 , "0.");
        } else {
            tStringBuilder.append(t);
            tStringBuilder.insert(tStringBuilder.toString().length() - 1 , "0.0");
        }
        tStringBuilder.append("℃");
        value.put("温度",tStringBuilder.toString());

        int w = MathTools.changeIntoInt(water);
        StringBuilder wStringBuilder = new StringBuilder();
        if (w >= 100 || w <= -100){
            wStringBuilder.append(w);
            wStringBuilder.insert(wStringBuilder.toString().length() - 2 , '.');
        } else if (w >= 10 || w <= -10){
            wStringBuilder.append(w);
            wStringBuilder.insert(wStringBuilder.toString().length() - 2 , "0.");
        } else {
            wStringBuilder.append(w);
            wStringBuilder.insert(wStringBuilder.toString().length() - 1 , "0.0");
        }
        wStringBuilder.append("%");
        value.put("湿度",wStringBuilder.toString());

        return value;
    }
}
