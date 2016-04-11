package nodes;


import nodes.behavior.FillDatas;
import nodes.behavior.SendCMD;
import tools.DataTools;

/**
 * base Node
 * Created by jiyangkang on 2016/4/8 0008.
 */
public abstract class Node {
    private String name;
    private boolean isConnect = false;
    private int reduceTime = 0;
    private byte[] addr;

    public void setAddr(byte[] addr) {
        this.addr = addr;
        name = NodeInfo.hashList.get(addr);
    }

    public byte[] getAddr() {
        return addr;
    }

    public byte getNetType() {
        return NodeInfo.netType;
    }

    public void setNetType(byte netType) {
        NodeInfo.netType = netType;
    }

    private IsConnect mIsConnect;

    public CheckConnectThread mCheckConnectThread = null;

    public void setCheckConnect(IsConnect mIsConnect) {
        this.mIsConnect = mIsConnect;
    }

    SendCMD mSendCMD;
    FillDatas mFillDatas;

    public void setReduceTime(int reduceTime) {
        this.reduceTime = reduceTime;
        if (!isConnect){
            mCheckConnectThread = new CheckConnectThread();
            mCheckConnectThread.start();
            mIsConnect.isConnect(true);
        }
        isConnect = true;
    }

    public Node() {

    }


    public String getName() {
        return name;
    }

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

    public interface IsConnect {
        void isConnect(boolean isConncet);
    }
}
