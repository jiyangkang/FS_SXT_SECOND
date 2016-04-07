package nodes;

import nodes.behavior.FillDatas;
import nodes.behavior.SendCMD;

/**
 * base Node
 * Created by jiyangkang on 2016/4/8 0008.
 */
public abstract class Node {
    private String name = null;
    private String datas = null;
    private byte[] orient = null;
    private boolean isConnect = false;
    private String stata = null;

    FillDatas mFillDatas;
    SendCMD mSendCMD;

    public Node(){

    }

    public void setOrient(byte[] orient) {
        this.orient = orient;
        mFillDatas.fillData(orient);
    }

    public byte[] getOrient() {
        return orient;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public void setIsConnect(boolean isConnect) {
        this.isConnect = isConnect;
    }

    public void setStata(String stata) {
        this.stata = stata;
    }

    public String getName() {
        return name;
    }

    public String getDatas() {
        return datas;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public String getStata() {
        return stata;
    }
}
