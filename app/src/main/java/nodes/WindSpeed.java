package nodes;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class WindSpeed extends Node {
    public volatile static WindSpeed windSpeed;

    private WindSpeed(){

    }

    public static WindSpeed getWindSpeed(){
        if (windSpeed == null){
            synchronized (WindSpeed.class){
                if (windSpeed == null)
                    windSpeed = new WindSpeed();
            }
        }
        return windSpeed;
    }
}
