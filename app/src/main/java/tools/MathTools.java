package tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * analysis datas
 * Created by jiyangkang on 2016/4/6 0006.
 */
public class MathTools {

    /**
     * change String into byte array
     *
     * @param datas the String
     * @return the byte array
     */
    public static byte[] changeIntoByte(String datas) {
        byte[] result;
        byte[] data = datas.getBytes();
        for (int i = 0; i < data.length; i++) {
            if (data[i] != ' ') {
                if (data[i] >= 'a') {
                    data[i] -= ('a' - 10);
                } else if (data[i] >= 'A') {
                    data[i] -= ('A' - 10);
                } else {
                    data[i] -= '0';
                }
            }
        }
        byte[] get = new byte[data.length];
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != ' ') {
                //once i+1 is not in the array or the data[i+1]==' '
                //means their have some error in the string
                if (i + 1 > data.length || data[i + 1] == ' ')
                    return null;
                get[j] = (byte) (data[i] << 4 | data[i + 1]);
                i++;
                j++;
            }
        }
        result = new byte[j];
        System.arraycopy(get, 0, result, 0, j);
        return result;
    }

    /**
     * change a String with Hex type into Int
     *
     * @param datas the String which has the Hex type
     * @return the result of int
     */
    public static int changeIntoInt(String datas) {
        byte[] data = changeIntoByte(datas);
        if (data == null || data.length > 4) {
            return DataTools.ERROR;
        }
        return changeIntoInt(data);
    }

    /**
     * change bytes into Int
     *
     * @param datas byte array
     * @return the result
     */
    public static int changeIntoInt(byte[] datas) {

        int result = 0;
        //if the length of the array is larger than 4
        //means the array can't translate into int
        if (datas == null || datas.length > 4) {
            return DataTools.ERROR;
        }
        short end[] = new short[datas.length];
        for (int i = 0; i < end.length; i++) {
            if (i == 0) {
                if (datas[i] >= 0) {
                    end[i] = (short) (datas[i] & 0x00ff);
                } else {
                    end[i] = (short) (datas[i] | 0xff00);
                }
            } else {
                end[i] = (short) (datas[i] & 0x00ff);
            }
        }
        for (short anEnd : end) {
            result = result << 8 | anEnd;
        }
        return result;
    }

    /**
     * check the independent data is valid or ont
     *
     * @param data independent data
     * @return true if valid, false if invalid
     */
    public static boolean checkMata(byte[] data) {
        return data != null && 0x00 == makeMate(data);
    }

    /**
     * tools for simulate crc8
     *
     * @param data data
     * @return crc8 result
     */
    public static byte makeMate(byte[] data) {
        byte crc = 0x00;
        for (byte aData : data) {
            crc ^= aData;
            for (int j = 0; j < 8; j++) {
                if ((crc & 0x80) != 0x00)
                    crc = (byte) ((crc << 1) ^ 0x07);
                else
                    crc <<= 1;
            }
        }
        return crc;
    }


    /**
     * divide package into independent array byte
     * @param data the package received
     * @return list of array byte
     */
    public static List<byte[]> divideData(byte[] data) {
        List<byte[]> result = new ArrayList<>();
        int length = 0;
        int offset = 0;

        if (data == null)
            return null;

        for (int i = 0; i < data.length; i++) {
            if (data[i] == DataTools.HEAD_RECEIVE) {
                if (i + 1 > data.length || i + 2 > data.length)
                    break;
                length = data[i + 1];
                offset = data[i + 2];
            } else
                continue;
            if (i + length + offset + 1 > data.length)
                continue;
            byte[] buffer = new byte[length + offset + 1];
            System.arraycopy(data, i, buffer, 0, buffer.length);
            if (checkMata(buffer)) {
                result.add(buffer);
                i += length + offset;
            }
        }
        Iterator<byte[]> iterator = result.iterator();
        if (iterator.hasNext())
            return result;
        else
            return null;

    }


}
