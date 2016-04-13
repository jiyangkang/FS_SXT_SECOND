package nodes.behavior;

import nodes.NodeInfo;

/**
 * Created by jiyangkang on 2016/4/12 0012.
 */
public class SendOmnidirectionalCMD implements SendCMD{

    byte[] cmd = new byte[10];

    public SendOmnidirectionalCMD(byte[] addr){

    }

    @Override
    public void sendCMD(int which) {
        setCMD(which);
    }

    private void setCMD(int which) {
        switch (which){
            case NodeInfo.RGB_B:
                break;
            default:
                break;
        }
    }
}
