package nodes.behavior;

import nodes.NodeInfo;
import tools.DataTools;
import tools.MathTools;

/**
 *
 * Created by jiyangkang on 2016/4/11 0011.
 */
public class SendControlCMD implements SendCMD{
    byte[] cmd = new byte[10];
    private byte[] addr;


    public SendControlCMD (byte[] addr){
        this.addr = addr;
    }

    @Override
    public void sendCMD(int which) {
        setCmd(which);
        try {
            DataTools.sends.put(cmd);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setCmd(int which){
        byte[] a = new byte[9];
        a[0] = DataTools.HEAD_RECEIVE;
        a[DataTools.LENGTH] = 0x01;
        a[DataTools.OFFSET] = 0x08;
        a[DataTools.DATATYPE] = DataTools.CONTROLDATA;
        a[DataTools.NETTYPE] = NodeInfo.netType;
        a[DataTools.DEVICEADDR_H] = addr[0];
        a[DataTools.DEVICEADDR_L] = addr[1];
        a[DataTools.DEVICETYPE] = addr[2];

        switch (which){
            case NodeInfo.OPEN:
                a[DataTools.DATA] = NodeInfo.OPEN;
                break;
            case NodeInfo.CLOSE:
                a[DataTools.DATA] = NodeInfo.CLOSE;
                break;
            case NodeInfo.STOP:
                a[DataTools.DATA] = NodeInfo.STOP;
                break;
            default:
                a[DataTools.DATA] = 0x00;
                break;
        }

        byte mata = MathTools.makeMate(a);
        System.arraycopy(a, 0, cmd, 0, a.length);
        cmd[DataTools.MATA] = mata;
    }
}
