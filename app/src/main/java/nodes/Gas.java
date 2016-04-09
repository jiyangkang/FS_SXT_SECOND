package nodes;

/**
 * Created by jiyangkang on 2016/4/9 0009.
 */
public class Gas extends Node {
    public volatile static Gas gas;

    private Gas(){

    }

    public static Gas getGas(){
        if (gas == null){
            synchronized (Gas.class){
                if (gas == null)
                    gas = new Gas();
            }
        }
        return gas;
    }
}
