package project;

import java.util.HashMap;

import nodes.Node;
import nodes.NodeInfo;

/**
 *
 * Created by jiyangkang on 2016/4/11 0011.
 */
public abstract class Project {
    private HashMap<String, Node> nodeSet = new HashMap<>();
    private String name ;

    JoinProject joinProject;

    public void setNodeSet(HashMap<String, Node> nodeSet) {
        this.nodeSet = nodeSet;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, Node> getNodeSet(){
        return this.nodeSet;
    }

}
