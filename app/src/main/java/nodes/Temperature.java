package nodes;

/**
 *
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Temperature extends Node{
    public volatile static Temperature temperature;

    private Temperature(){

    }

    public static Temperature getTemperature(){
        if (temperature == null){
            synchronized (Temperature.class){
                if (temperature == null)
                    temperature = new Temperature();
            }
        }
        return temperature;
    }
}
