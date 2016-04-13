package nodes;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class PM25Out extends Node {
    public volatile static PM25Out pm25Out;

    private PM25Out(){
        byte[] addr = new byte[]{0x00, 0x07, 0x50};
        setAddr(addr);
    }

    public static PM25Out getPM25Out(){
        if (pm25Out == null){
            synchronized (PM25Out.class){
                if (pm25Out == null)
                    pm25Out = new PM25Out();
            }
        }
        return pm25Out;
    }
}
