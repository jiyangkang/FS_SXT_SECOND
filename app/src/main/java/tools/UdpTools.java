package tools;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * In this class, all these functions are built for UDPService
 * such as get from udp and send data to udp
 * this time, use Multicast Group.
 * Created by jiyangkang on 2016/4/6 0006.
 */
public class UdpTools {

    /**
     * get byte array from Multicast Group
     * @return the byte array received from Multicast Group
     */
    public static byte[] getFromUDP(){
        byte[] datas = new byte[DataTools.UDPDATASIZE];
        try {
            MulticastSocket mMulticastSocket = new MulticastSocket(DataTools.selfPortMultiGrop);
            InetAddress group = InetAddress.getByName(DataTools.addrMultiGroup);
            mMulticastSocket.joinGroup(group);
            DatagramPacket mDatagramPacket = new DatagramPacket(datas, datas.length);
            mMulticastSocket.receive(mDatagramPacket);
            DataTools.serverIP = mDatagramPacket.getAddress().getHostName();
            DataTools.isUDP = true;
            mMulticastSocket.close();
            return datas;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * send CMD to the server host
     * @param CMDs CMD which to send
     * @return true if success, false if failed
     */
    public static boolean sendToUDP(byte[] CMDs){
        try {
            InetAddress mInetAddress = InetAddress.getByName(DataTools.serverIP);
            DatagramSocket mDatagramSocket = new DatagramSocket(DataTools.selfPortSend);
            mDatagramSocket.setBroadcast(true);
            mDatagramSocket.setSendBufferSize(CMDs.length);
            DatagramPacket mDatagramPacket = new DatagramPacket(CMDs,
                    CMDs.length, mInetAddress, DataTools.serverPortMultiGrop);
            mDatagramSocket.send(mDatagramPacket);
            mDatagramSocket.close();
            return true;
        } catch (SocketException e) {
            e.printStackTrace();
            return false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

