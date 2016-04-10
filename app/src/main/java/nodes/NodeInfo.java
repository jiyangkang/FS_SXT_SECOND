package nodes;

import java.util.HashMap;

/**
 * define NodeInfo here
 * Created by jiyangkang on 2016/4/6 0006.
 */

public class NodeInfo {

    public static final int REDUCETIME = 5;
    public static final byte SMARTAG = 0x01;
    public static final byte SMARTHS = 0x02;

    public static final byte NET485 = 0x52;
    public static final byte NETZIGBEE = 0x5A;

    public static HashMap<byte[], String> hashList = new HashMap<>();

    static{
        hashList.put(new byte[]{0x41, 0x00, 0x01},"智能电表");
        hashList.put(new byte[]{0x54, 0x00, 0x02},"空气温湿度");
        hashList.put(new byte[]{0x4C, 0x00, 0x03},"光照");
        hashList.put(new byte[]{0x43, 0x00, 0x04},"二氧化碳");
        hashList.put(new byte[]{0x57, 0x00, 0x05},"风速");
        hashList.put(new byte[]{0x53, 0x00, 0x06},"土壤温湿度");
        hashList.put(new byte[]{0x50, 0x00, 0x07},"外部环境PM2.5");
        hashList.put(new byte[]{0x50, 0x00, 0x08},"内部环境PM2.5");
        hashList.put(new byte[]{0x6F, 0x00, 0x09},"全向红外");
        hashList.put(new byte[]{0x52, 0x00, 0x0A},"红外光栅");
        hashList.put(new byte[]{0x47, 0x00, 0x0B},"燃气传感器");
        hashList.put(new byte[]{0x46, 0x00, 0x0C},"烟雾传感器");
        hashList.put(new byte[]{0x49, 0x00, 0x0D},"人体红外传感器");
        hashList.put(new byte[]{0x69, 0x00, 0x0E},"灌溉一");
        hashList.put(new byte[]{0x69, 0x00, 0x0F},"灌溉二");
        hashList.put(new byte[]{0x61, 0x00, 0x10},"报警器");
        hashList.put(new byte[]{0x66, 0x00, 0x11},"风扇");
    }

}
