package project;

import java.util.HashMap;

import nodes.Node;

/**
 *
 * Created by jiyangkang on 2016/4/11 0011.
 */
public abstract class Project {
    private HashMap<String, Node> nodeSet = new HashMap<>();

    JoinProject joinProject;

    public void setNodeSet(HashMap<String, Node> nodeSet) {
        this.nodeSet = nodeSet;
    }

    public HashMap<String, Node> getNodeSet(){
        return this.nodeSet;
    }
}
