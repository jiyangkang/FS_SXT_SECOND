package nodes;

/**
 * 
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Soil extends Node {
    public volatile static Soil soil;

    private Soil(){

    }

    public static Soil getSoil(){
        if (soil == null){
            synchronized (Soil.class){
                if (soil == null)
                    soil = new Soil();
            }
        }
        return soil;
    }
}
