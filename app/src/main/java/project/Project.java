package project;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;

import nodes.Alarm;
import nodes.CO2;
import nodes.Fan;
import nodes.Infrared;
import nodes.IrrigationFirst;
import nodes.IrrigationSecond;
import nodes.Light;
import nodes.Node;
import nodes.NodeInfo;
import nodes.PM25In;
import nodes.Soil;
import nodes.Temperature;

/**
 * Created by jiyangkang on 2016/4/11 0011.
 */
public class Project {
    private HashMap<String, Node> nodeSet;
    private String name;
    private static volatile Project project;

    private OnProjectChanged onProjectChanged;

    public void setOnProjectChanged(OnProjectChanged onProjectChanged) {
        this.onProjectChanged = onProjectChanged;
    }

    //have different
    private JoinProject joinProject;

    private Project() {
        nodeSet = new HashMap<>();
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


    public void setName(String name) {

        if (!name.equalsIgnoreCase(this.name)) {

            this.name = name;
            HashMap<String, Node> set = new HashMap<>();
            switch (name) {
                case NodeInfo.PROJECTAG:
                    joinProject = new SmartAGJoinProject();
                    break;
                case NodeInfo.PROJECTHS:
                    joinProject = new SmartHsJoinProject();
                    break;
                default:
                    break;
            }
            joinProject.joinProject(set);
            Log.d("Hash", set.size() + "");
            nodeSet = set;
            Log.d("Hash", nodeSet.size()+"11111");
            if (onProjectChanged != null) {
                Log.d("PUT", "CHANGE");
                if (!name.equalsIgnoreCase(this.name)) {
                    onProjectChanged.onProjectChange(name);
                }
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
