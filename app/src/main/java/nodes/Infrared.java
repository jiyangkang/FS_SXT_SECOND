package nodes;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Infrared extends Node {
    public volatile static Infrared infrared;

    private Infrared(){

    }

    public static Infrared getInfrared(){
        if (infrared == null){
            synchronized (Infrared.class){
                if (infrared == null)
                    infrared = new Infrared();
            }
        }
        return infrared;
    }
}
