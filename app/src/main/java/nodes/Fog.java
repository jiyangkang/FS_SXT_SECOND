package nodes;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Fog extends Node {
    public volatile static Fog fog;

    private Fog(){

    }

    public static Fog getFog(){
        if (fog == null){
            synchronized (Fog.class){
                if (fog == null)
                    fog = new Fog();
            }
        }
        return fog;
    }
}
