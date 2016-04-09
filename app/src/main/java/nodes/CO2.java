package nodes;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class CO2 extends Node{

    public volatile static CO2 co2;

    private CO2(){

    }

    public static CO2 getCo2(){
        if (co2 == null){
            synchronized (CO2.class){
                if (co2 == null)
                    co2 = new CO2();
            }
        }
        return co2;
    }
}
