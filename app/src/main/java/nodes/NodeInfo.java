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

    public static HashMap<byte[], String> hashList = new HashMap<>();
    public static HashMap<String, int[]> projectMaplist = new HashMap<>();
    public static HashMap<String, JoinProject> projectNodeSet = new HashMap<>();
    public static HashMap<String, int[]> viewNodeDrawable = new HashMap<>();

    static {
        hashList.put(new byte[]{0x00, 0x01, 0x41}, "智能电表");//000141
        hashList.put(new byte[]{0x00, 0x02, 0x54}, "空气温湿度");//000254
        hashList.put(new byte[]{0x00, 0x03, 0x44}, "门禁");//000344
        hashList.put(new byte[]{0x00, 0x04, 0x4C}, "光照");//00044c
        hashList.put(new byte[]{0x00, 0x07, 0x43}, "二氧化碳");//000743
//        hashList.put(new byte[]{0x00, 0x05, 0x57}, "风速");
        hashList.put(new byte[]{0x00, 0x06, 0x53}, "土壤温湿度");//000653
        hashList.put(new byte[]{0x00, 0x08, 0x50}, "环境PM2.5");
        hashList.put(new byte[]{0x00, 0x0A, 0x6F}, "全向红外");
        hashList.put(new byte[]{0x00, 0x0B, 0x52}, "红外光栅");
        hashList.put(new byte[]{0x00, 0x0C, 0x47}, "燃气传感器");
        hashList.put(new byte[]{0x00, 0x0D, 0x46}, "烟雾传感器");
        hashList.put(new byte[]{0x00, 0x0E, 0x49}, "人体红外传感器");
        hashList.put(new byte[]{0x00, 0x0F, 0x69}, "灌溉一");
        hashList.put(new byte[]{0x00, 0x10, 0x69}, "灌溉二");
        hashList.put(new byte[]{0x00, 0x11, 0x61}, "报警器");
        hashList.put(new byte[]{0x00, 0x12, 0x66}, "风扇");

        int[] listBitmapHSProject = {R.drawable.smart_hs_enable, R.drawable.smart_hs_disable};
        int[] listBitmapAGProject = {R.drawable.smart_ag_enable, R.drawable.smart_ag_disable};
        projectMaplist.put("smartHS", listBitmapHSProject);
        projectMaplist.put("smartAG", listBitmapAGProject);

        projectNodeSet.put(PROJECTAG, new SmartAGJoinProject());
        projectNodeSet.put(PROJECTHS, new SmartHsJoinProject());

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

    }

}
