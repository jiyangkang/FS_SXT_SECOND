package nodes;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Light extends Node{
    public volatile static Light light;

    private Light(){

    }

    public static Light getLight(){
        if (light == null){
            synchronized (Light.class){
                if (light == null)
                    light = new Light();
            }
        }
        return light;
    }
}
