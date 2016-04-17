package project;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import nodes.Node;
import nodes.NodeInfo;

/**
 * Created by jiyangkang on 2016/4/11 0011.
 */
public class Project {
    private HashMap<String, Node> nodeSet = new HashMap<>();
    private String name;
    private static volatile Project project;

    private OnProjectChanged onProjectChanged;

    public void setOnProjectChanged(OnProjectChanged onProjectChanged) {
        this.onProjectChanged = onProjectChanged;
    }

    //have different
    private JoinProject joinProject;

    private Project() {
        nodeSet = null;
        name = null;
        onProjectChanged = null;
    }

    //in everywhere just 1 Project could be newed
    public static Project getProject() {
        if (project == null) {
            synchronized (Project.class) {
                if (project == null) {
                    project = new Project();
                }
            }
        }
        return project;
    }

    public void setNodeSet(HashMap<String, Node> nodeSet) {
        this.nodeSet = nodeSet;
    }

    public void setName(String name) {

        if (this.name == null || !this.name.equalsIgnoreCase(name)) {
            this.name = name;
            joinProject = NodeInfo.projectNodeSet.get(name);
            setNodeSet(joinProject.joinProject());
            if (onProjectChanged != null) {
                Log.d("PUT", "CHANGE");
                onProjectChanged.onProjectChange(name);
            }
        }
    }


    public HashMap<String, Node> getNodeSet() {
        return this.nodeSet;
    }

    public static interface OnProjectChanged {
        void onProjectChange(String name);
    }

}
