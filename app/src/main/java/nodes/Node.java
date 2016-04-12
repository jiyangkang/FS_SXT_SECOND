package nodes;


import java.util.HashMap;

import nodes.behavior.FillDatas;
import nodes.behavior.SendCMD;
import tools.DataTools;

/**
 * base Node
 * Created by jiyangkang on 2016/4/8 0008.
 */
public abstract class Node {
    private String name;//节点名
    private boolean isConnect = false;//节点状态
    private int reduceTime = 0;//断点判断
    private byte[] addr;//节点地址和节点类型
    private HashMap<String, String> value = new HashMap<>();//状态或数据

    private OnValueReceived mOnValueReceived;//数据接口
    private IsConnect mIsConnect;//通断接口
    private CheckConnectThread mCheckConnectThread = null;

    SendCMD mSendCMD;//命令发送行为
    FillDatas mFillDatas;//数据填充行为

    /**
     * on destroy Node
     */
    public void release (){
        mCheckConnectThread.interrupt();
        mCheckConnectThread = null;
        mIsConnect = null;
        mOnValueReceived = null;
        mSendCMD = null;
        mFillDatas = null;
    }

    /**
     * on Create Node
     */
    public Node() {

    }

    /**
     * interface let others knows the name of the Node
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * refresh reduceTime
     * @param reduceTime time not received value
     */
    public void setReduceTime(int reduceTime) {
        this.reduceTime = reduceTime;
        if (!isConnect){
            mCheckConnectThread = new CheckConnectThread();
            isConnect = true;
            mCheckConnectThread.start();
            mIsConnect.isConnect(true);
        }
    }

    /**
     * callback when disconnect is suspend
     */
    public interface IsConnect {
        void isConnect(boolean isConncet);
    }

    /**
     * register the callback OnValueReceived
     * @param mOnValueReceived
     */
    public void setmOnValueReceived(OnValueReceived mOnValueReceived){
        this.mOnValueReceived = mOnValueReceived;
    }

    /**
     * set Node's value in HashMap style
     * @param datas the byte array received
     */
    public void setValue(byte[] datas){
        value = mFillDatas.fillData(datas);
        setReduceTime(NodeInfo.REDUCETIME);
        mOnValueReceived.onValueReceived(value);
    }

    /**
     * callback when received value
     */
    public interface OnValueReceived{
        void onValueReceived(HashMap<String, String> value);
    }

    /**
     * set the type of Node and the name of Node
     * @param addr byte array with information of addr_h addr_l deviceType
     */
    public void setAddr(byte[] addr) {
        this.addr = addr;
        name = NodeInfo.hashList.get(addr);
    }

    /**
     * return the byte array with information of addr_h addr_l deviceType
     * @return information of Node
     */
    public byte[] getAddr() {
        return addr;
    }

    /**
     * return netType of Node
     * @return netType
     */
    public byte getNetType() {
        return NodeInfo.netType;
    }

    /**
     * set netType
     * @param netType netType
     */
    public void setNetType(byte netType) {
        NodeInfo.netType = netType;
    }

    /**
     * thread to check if the Node is connected
     */
    private class CheckConnectThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (reduceTime > 0) {
                reduceTime--;
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isConnect = false;
            mIsConnect.isConnect(false);
            this.interrupt();
        }
    }


}
