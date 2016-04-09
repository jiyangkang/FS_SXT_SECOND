package nodes;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Fan extends Node {
    public volatile static Fan fan;

    private Fan(){

    }

    public static Fan getFan(){
        if (fan == null){
            synchronized (Fan.class){
                if (fan == null)
                    fan = new Fan();
            }
        }
        return fan;
    }
}
