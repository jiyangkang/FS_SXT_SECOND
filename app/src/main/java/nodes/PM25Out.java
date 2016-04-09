package nodes;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class PM25Out extends Node {
    public volatile static PM25Out pm25Out;

    private PM25Out(){

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
