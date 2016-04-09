package com.hqyj.dev.ji.testan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText editText;
    private TextView textView;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    String show = msg.obj.toString();
                    textView.setText(show);
                    break;
                case 2:
                    String get = msg.obj.toString();
                    textView.setText(get);
                    break;
                case 3:
                    String mate = msg.obj.toString();
                    textView.setText(mate);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.edt1);
        textView = (TextView) findViewById(R.id.tx1);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Message msg = new Message();

                String num = editText.getText().toString();
                Log.e("EDIT", num);
                int data = Integer.parseInt(num);
                String show = changeIntoHexString(data);
                msg.obj = show;
                msg.what = 1;
                handler.sendMessage(msg);
                break;
            case R.id.btn2:
                Message msg2 = new Message();
                String num2 = editText.getText().toString();
                Log.e("EDIT", num2);
                int change = changeIntoInt(num2);
                msg2.obj = change + "";
                msg2.what = 2;
                handler.sendMessage(msg2);
                break;
            case R.id.btn3:
                Message msg3 = new Message();
                String num3 = editText.getText().toString();
                byte[] datas = num3.getBytes();
                byte[] dataByte = changeIntoByte(datas);
                byte result = checkMata(dataByte);

                Log.d("String", changeIntoHexString(result));
                msg3.what = 3;
                msg3.obj = changeIntoHexString(result);
                handler.sendMessage(msg3);
                break;
            default:
                break;
        }
    }

    public String changeIntoHexString(int data) {
        Log.e("EDIT", data + "");
        StringBuilder stringBuilder = new StringBuilder();
        byte[] datas = new byte[4];
        for (int i = datas.length - 1; i >= 0; i--) {
            datas[i] = (byte) (data >> (24 - i * 8) & 0x00ff);
        }

        for (byte data1 : datas) {
            byte a = (byte) (data1 >> 4 & 0x0f);
            byte b = (byte) (data1 & 0x0f);

            if (a > 9) {
                char c = (char) (a + 55);
                stringBuilder.append(c);
            } else {
                stringBuilder.append(a);
            }
            if (b > 9) {
                char c = (char) (b + 55);
                stringBuilder.append(c);
            } else {
                stringBuilder.append(b);
            }
            stringBuilder.append(' ');

        }

        return stringBuilder.toString();
    }

    public byte[] changeIntoByte(byte[] datas){
        for (int i = 0; i < datas.length; i++){
            if (datas[i] != ' ') {
                if (datas[i] >= 'a') {
                    datas[i] -= ('a' - 10);

                } else if (datas[i] >= 'A') {
                    datas[i] -= ('A' - 10);
                } else {
                    datas[i] -= '0';
                }
                Log.e("DATA", datas[i] + "");
            }
        }
        byte[] get = new byte[datas.length];
        int j = 0;
        for (int i = 0; i < datas.length; i++) {
            if (datas[i] != ' ') {
                get[j] = (byte) (datas[i] << 4 | datas[i + 1]);
                i++;
                Log.e("GET", get[j] + "");
                j++;
            }
        }
        byte[] result = new byte[j];
        System.arraycopy(get, 0, result, 0, j);
        return result;
    }

    public int changeIntoInt(String datas) {

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
                Log.e("DATA", data[i] + "");
            }
        }
        byte[] get = new byte[data.length];
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != ' ') {
                get[j] = (byte) (data[i] << 4 | data[i + 1]);
                i++;
                Log.e("GET", get[j] + "");
                j++;
            }
        }

        int result = 0;
        short end[] = new short[j];
        for (int i = 0; i < end.length; i++) {
            if (i == 0) {
                if (get[i] >= 0) {
                    end[i] = (short) (get[i] & 0x00ff);
                } else {
                    end[i] = (short) (get[i] | 0xff00);
                }
            } else {
                end[i] = (short) (get[i] & 0x00ff);
            }
        }

//        for (int i  = end.length - 1; i >= 0; i--){
//            result = end[i] << ((end.length - 1 -i)*8) | result;
//        }

        for (short anEnd : end) {
            result = result << 8 | anEnd;
            Log.e("result", result + "");
        }

        return result;
    }

    public byte checkMata(byte[] data) {
        byte crc = 0x00;
        if (data != null) {
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
        return 0x00;
    }
}
