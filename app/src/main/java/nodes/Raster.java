package nodes;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Raster extends Node{
    public volatile static Raster raster;

    private Raster(){

    }

    public static Raster getRaster(){
        if (raster == null){
            synchronized (Raster.class){
                if (raster == null)
                    raster = new Raster();
            }
        }
        return raster;
    }
}
