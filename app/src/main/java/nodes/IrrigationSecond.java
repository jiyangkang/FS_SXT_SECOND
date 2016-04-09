package nodes;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class IrrigationSecond extends Node {
    public volatile static IrrigationSecond irrigationSecond;

    private IrrigationSecond(){

    }

    public static IrrigationSecond getIrrigationSecond(){
        if (irrigationSecond == null){
            synchronized (IrrigationSecond.class){
                if (irrigationSecond == null)
                    irrigationSecond = new IrrigationSecond();
            }
        }
        return irrigationSecond;
    }
}
