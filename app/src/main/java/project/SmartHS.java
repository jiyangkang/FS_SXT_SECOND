package project;

/**
 *
 * Created by jiyangkang on 2016/4/11 0011.
 */
public class SmartHS extends Project{

    private volatile static SmartHS smartHS;

    private SmartHS(){
        joinProject = new SmartHsJoinProject();
        setNodeSet(joinProject.joinProject());
    }

    public static SmartHS getSmartHS(){
        if (smartHS ==null){
            synchronized (SmartHS.class){
                if (smartHS == null){
                    smartHS = new SmartHS();
                }
            }
        }
        return smartHS;
    }

}
