package nodes;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Omnidirectional extends Node {
    public volatile static Omnidirectional omnidirectional;

    private Omnidirectional(){

    }

    public static Omnidirectional getOmnidirectional(){
        if (omnidirectional == null){
            synchronized (Omnidirectional.class){
                if (omnidirectional == null)
                    omnidirectional = new Omnidirectional();
            }
        }
        return omnidirectional;
    }
}
