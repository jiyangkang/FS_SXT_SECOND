package nodes;

import com.hqyj.dev.ji.fs_sxt.R;

import java.util.HashMap;

import project.JoinProject;
import project.SmartAGJoinProject;
import project.SmartHsJoinProject;

/**
 * define NodeInfo here
 * Created by jiyangkang on 2016/4/6 0006.
 */

public class NodeInfo {

    public static final int REDUCETIME = 5;
    public static final byte SMARTHS = 0x01;
    public static final byte SMARTAG = 0x02;

    public static String projetType = null;
    public static final String PROJECTAG = "smartAG";
    public static final String PROJECTHS = "smartHS";

    public static byte netType = 0;
    public static final byte NET485 = 0x52;
    public static final byte NETZIGBEE = 0x5A;

    //普通点
    public static final int OPEN = 0x31;
    public static final int CLOSE = 0x30;
    //彩灯
    public static final int RGB_OFF = 0x40;
    public static final int RGB_ON = 0x41;
    public static final int RGB_PLUS = 0x42;
    public static final int RGB_MOIN = 0x43;
    public static final int RGB_G = 0x45;
    public static final int RGB_R = 0x46;
    public static final int RGB_B = 0x47;
    public static final int RGB_W = 0x48;
    public static final int RGB_FLASH = 0x49;
    public static final int RGB_STROBE = 0x50;
    public static final int RGB_FADE = 0x51;
    public static final int RGB_SMOOTH = 0x52;

    //插座遥控器
    public static final int SOCKET_ON = 0x70;
    public static final int SOCKET_OFF = 0x71;
    public static final int SOCKET_ENERGY = 0x73;
    public static final int SOCKET_WATE = 0x74;
    public static final int SOCKET_UP = 0x75;
    public static final int SOCKET_DOWN = 0x78;
    public static final int SOCKET_QUIT = 0x77;

    //窗帘
    public static final int CURTAIN_ON = 144;
    public static final int CURTAIN_STOP = 145;
    public static final int CURTAIN_OFF = 146;

    public static HashMap<String, String> hashList = new HashMap<>();
    public static HashMap<String, int[]> projectMaplist = new HashMap<>();
    public static HashMap<String, int[]> viewNodeDrawable = new HashMap<>();
    public static HashMap<String, Integer> normalCtrlDrable = new HashMap<>();

    static {
        hashList.put("000141", "智能电表");//000141
        hashList.put("000254", "空气温湿度");//000254
        hashList.put("000344", "门禁");//000344
        hashList.put("00044C", "光照");//00044c
        hashList.put("000743", "二氧化碳");//000743
//        hashList.put(new byte[]{0x00, 0x05, 0x57}, "风速");
        hashList.put("000653", "土壤温湿度");//000653
        hashList.put("000850", "环境PM2.5");
        hashList.put("000A6F", "全向红外");
        hashList.put("000B52", "红外光栅");
        hashList.put("000C47", "燃气传感器");
        hashList.put("000D46", "烟雾传感器");
        hashList.put("000E49", "人体红外传感器");
        hashList.put("000F69", "灌溉一");
        hashList.put("001069", "灌溉二");
        hashList.put("001161", "报警器");
        hashList.put("001266", "风扇");

        int[] listBitmapHSProject = {R.drawable.smart_hs_enable, R.drawable.smart_hs_disable};
        int[] listBitmapAGProject = {R.drawable.smart_ag_enable, R.drawable.smart_ag_disable};
        projectMaplist.put("smartHS", listBitmapHSProject);
        projectMaplist.put("smartAG", listBitmapAGProject);


        int[] listBitmapBlank = new int[]{R.drawable.blank_ag, R.drawable.blank_hs};
        viewNodeDrawable.put("blank", listBitmapBlank);//空白
        viewNodeDrawable.put("智能电表", new int[]{0, R.drawable.ammeter_hs});//电表
        viewNodeDrawable.put("空气温湿度", new int[]{R.drawable.temper_ag, R.drawable.temper_hs});//空气温湿度
        viewNodeDrawable.put("门禁", new int[]{0, R.drawable.keeper});//门禁
        viewNodeDrawable.put("光照", new int[]{R.drawable.light_ag, R.drawable.light_hs});//光照
        viewNodeDrawable.put("土壤温湿度", new int[]{R.drawable.soil, 0});//土壤
        viewNodeDrawable.put("二氧化碳", new int[]{R.drawable.co2_ag, 0});//二氧化碳
        viewNodeDrawable.put("环境PM2.5", new int[]{R.drawable.pm25, 0});//pm2.5
        viewNodeDrawable.put("红外光栅", new int[]{0, R.drawable.raster_hs});//红外光栅
        viewNodeDrawable.put("烟雾传感器", new int[]{0, R.drawable.fog_hs});//烟雾
        viewNodeDrawable.put("燃气传感器", new int[]{0, R.drawable.gas_hs});//燃气
        viewNodeDrawable.put("人体红外传感器", new int[]{R.drawable.infrared_ag, R.drawable.infred_hs});//人体红外

        normalCtrlDrable.put("报警器", R.drawable.alarm_ag);
        normalCtrlDrable.put("风扇", R.drawable.fan_ag);
        normalCtrlDrable.put("灌溉一", R.drawable.irr_ag_1);
        normalCtrlDrable.put("灌溉二", R.drawable.irr_ag_2);

    }

}
