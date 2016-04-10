package nodes;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class IrrigationFirst extends Node{
    public volatile static IrrigationFirst irrigationFirst;

    private IrrigationFirst(){

    }

    public static IrrigationFirst getIrrigationFirst(){
        if (irrigationFirst == null){
            synchronized (IrrigationFirst.class){
                if (irrigationFirst == null)
                    irrigationFirst = new IrrigationFirst();
            }
        }
        return irrigationFirst;
    }
}
