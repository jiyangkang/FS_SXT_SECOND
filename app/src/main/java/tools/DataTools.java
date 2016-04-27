package tools;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Global Static vars
 * In this class define two list for getting and sending CMDs
 * Created by jiyangkang on 2016/4/6 0006.
 */
public class DataTools {

    public static boolean isUDP = false;
    public static final int ERROR = -9999;




    public static final String _DATATYPE = "dataType";
    public static final String _NETTYPE = "netType";
    public static final String _DEVICE = "device";
    public static final String _DATA = "data";

    public static final byte LENGTH = 0x01;
    public static final byte OFFSET = 0x02;
    public static final byte DATATYPE = 0x03;
    public static final byte NETTYPE = 0x04;
    public static final byte DEVICEADDR_H = 0x05;
    public static final byte DEVICEADDR_L = 0x06;
    public static final byte DEVICETYPE = 0x07;
    public static final byte DATA = 0x08;
    public static final byte MATA = 0x09;


    private final byte[] protocal = {HEAD_RECEIVE,LENGTH,OFFSET,DATATYPE,
            NETTYPE, DEVICEADDR_H,DEVICEADDR_L,DEVICETYPE, DATA, MATA};


    public static final byte HEAD_RECEIVE = 0x21;
    public static final byte NORMALDATA = 0x00;
    public static final byte CONTROLDATA = 0x01;
    public static final byte SETTHRESHOLD = 0x02;
    public static final byte GETTHRESHOLD = 0x03;
    public static final byte CHANGENET = 0x04;
    public static final byte PROJECTTYPE = 0x05;

    public static final byte[] ENDTHREAD = {0x00, 0x00};

    public static final int UDPDATASIZE = 64;

    public static ArrayBlockingQueue<byte[]> gets = new ArrayBlockingQueue<>(32);
    public static ArrayBlockingQueue<byte[]> sends = new ArrayBlockingQueue<>(32);

    public final static String addrMultiGroup = "224.10.10.10";
    public final static int selfPortMultiGrop = 20000;//receive port
    public final static int selfPortSend = 20002;//receive port

    public final static int serverPortMultiGrop = 20001;//server port
    public static String serverIP = "192.168.0.200";

    public final static int delayBase = 500;
    public final static int delaytimes = 10;

    public final static String webAddr = "http://sxt.farsightdev.com/SXTWebService.asmx";
    public static final String NAMESPACE = "http://tempuri.org/";//命名空间
    public static final String DEVICE = "080001";//设备号
    public static String userId = "admin";



}
