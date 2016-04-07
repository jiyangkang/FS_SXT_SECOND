package tools;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;

/**
 * In this class, all these functions are built for WebServices
 * such as get data from server and send data to server
 * Created by jiyangkang on 2016/4/6 0006.
 */
public class SoapTools{

    /**
     * get information on the server by using special device name
     * @param data device name
     * @return null if error occurred and request information from server if success
     */
    public static String getFromServer(String data){
        HttpTransportSE mHttpTransportSE = new HttpTransportSE(DataTools.webAddr);
        SoapObject mSoapObject = new SoapObject(DataTools.NAMESPACE, "CliGet");
        SoapSerializationEnvelope mSoapSerializationEnvelope =
                new SoapSerializationEnvelope(SoapEnvelope.VER12);

        try {

            mSoapObject.addProperty("data", data);
            mSoapSerializationEnvelope.bodyOut = mSoapObject;
            mSoapSerializationEnvelope.dotNet = true;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

        try {
            mHttpTransportSE.call(null, mSoapSerializationEnvelope);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            SoapPrimitive mSoapPrimitive =
                    (SoapPrimitive) mSoapSerializationEnvelope.getResponse();
            if (mSoapPrimitive != null){
                return mSoapPrimitive.toString();
            } else {
                return null;
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
            return null;
        }
    }
    /**
     * send CMD to the server by using special device name
     * @param data CMDs and Device name
     * @return null if error occurred and request information from server if success
     */
    public static String sendToServer(String data){
        HttpTransportSE mHttpTransportSE = new HttpTransportSE(DataTools.webAddr);
        SoapObject mSoapObject = new SoapObject(DataTools.NAMESPACE, "CliPut");
        SoapSerializationEnvelope mSoapSerializationEnvelope =
                new SoapSerializationEnvelope(SoapEnvelope.VER12);

        try {

            mSoapObject.addProperty("data", data);
            mSoapSerializationEnvelope.bodyOut = mSoapObject;
            mSoapSerializationEnvelope.dotNet = true;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

        try {
            mHttpTransportSE.call(null, mSoapSerializationEnvelope);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            SoapPrimitive mSoapPrimitive =
                    (SoapPrimitive) mSoapSerializationEnvelope.getResponse();
            if (mSoapPrimitive != null){
                return mSoapPrimitive.toString();
            } else {
                return null;
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
            return null;
        }
    }

    /**
     * check user name by passing name and password to the server
     * @param name name of user
     * @param pwd password of user
     * @return null if error occurred or request from server
     */
    public static String checkUser(String name, String pwd){
        HttpTransportSE mHttpTransportSE = new HttpTransportSE(DataTools.webAddr);
        SoapObject mSoapObject = new SoapObject(DataTools.NAMESPACE, "UserCheck");
        SoapSerializationEnvelope mSoapSerializationEnvelope =
                new SoapSerializationEnvelope(SoapEnvelope.VER12);

        try {
            mSoapObject.addProperty("name", name);
            mSoapObject.addProperty("pwd", pwd);
            mSoapSerializationEnvelope.bodyOut = mSoapObject;
            mSoapSerializationEnvelope.dotNet = true;

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }

        try {
            mHttpTransportSE.call(null, mSoapSerializationEnvelope);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            SoapPrimitive mSoapPrimitive =
                    (SoapPrimitive) mSoapSerializationEnvelope.getResponse();
            if (mSoapPrimitive != null){
                return mSoapPrimitive.toString();
            } else {
                return null;
            }
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
            return null;
        }
    }

    /**
     * check connection of internet
     * by pinging www.baidu.com
     * @return false if error occured or true if get request
     */
    public static boolean checkConnect(){
        String result = null;
        String ip = "www.baidu.com";
        try {
            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100" + ip);

            int status = p.waitFor();

            if (status == 0){
                result = "successful";
                return true;
            } else {
                result = "failed";
                return false;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            result = "failed + " + e.toString();
        } finally {
            Log.e("Connect", result);
        }
        return false;
    }
}
