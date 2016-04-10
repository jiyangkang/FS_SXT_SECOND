package tools;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Global Static vars
 * In this class define two list for getting and sending CMDs
 * Created by jiyangkang on 2016/4/6 0006.
 */
public class DataTools {

    public static boolean isUDP = false;
    public static final String ISUDP = "UDPMODE";
    public static final int ERROR = -9999;

    public static String netType = null;
    public static final String NET485 = "485";
    public static final String NETZIGBEE = "ZIGBEE";

    public static String projetType = null;
    public static final String PROJECTAG = "smartAG";
    public static final String PROJECTHS = "smartHS";

    public static final byte HEAD_RECEIVE = 0x21;

    public static final byte[] ENDTHREAD = {0x00, 0x00};

    public static final int UDPDATASIZE = 64;

    public static ArrayBlockingQueue<byte[]> gets = new ArrayBlockingQueue<>(32);
    public static ArrayBlockingQueue<byte[]> sends = new ArrayBlockingQueue<>(32);

    public final static String addrMultiGroup = "224.10.10.10";
    public final static int selfPortMultiGrop = 20000;//receive port
    public final static int selfPortSend = 20002;//receive port

    public final static int serverPortMultiGrop = 20001;//server port
    public static String serverIP = "192.168.0.200";

    public final static int delayBase = 100;
    public final static int delaytimes = 10;

    public final static String webAddr = "http://iot.farsightdev.com/SAWebService.asmx";
    public static final String NAMESPACE = "http://tempuri.org/";//命名空间
    public static final String DEVICE = "000106";//设备号
    public static String userId = "admin";


}
