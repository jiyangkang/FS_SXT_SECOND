package nodes;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Alarm extends Node {
    public volatile static Alarm alarm;

    private Alarm(){

    }

    public static Alarm getAlarm(){
        if (alarm == null){
            synchronized (Alarm.class){
                if (alarm == null)
                    alarm = new Alarm();
            }
        }
        return alarm;
    }

}
