package nodes;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class PM25In extends Node {
    public volatile static PM25In pm25In;

    private PM25In(){

    }

    public static PM25In getPM25In(){
        if (pm25In == null){
            synchronized (PM25In.class){
                if (pm25In == null)
                    pm25In = new PM25In();
            }
        }
        return pm25In;
    }
}
