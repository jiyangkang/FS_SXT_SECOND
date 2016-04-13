package project;

import nodes.NodeInfo;

/**
 *
 * Created by jiyangkang on 2016/4/11 0011.
 */
public class SmartAG extends Project{
    private volatile static SmartAG smartAG;

    private SmartAG(){
        setName(NodeInfo.PROJECTAG);
        joinProject = new SmartAGJoinProject();
        setNodeSet(joinProject.joinProject());
    }

    public static SmartAG getSmartAG(){
        if (smartAG == null){
            synchronized (SmartHS.class){
                if (smartAG == null){
                    smartAG = new SmartAG();
                }
            }
        }
        return smartAG;
    }
}
