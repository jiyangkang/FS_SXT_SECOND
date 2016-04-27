package activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hqyj.dev.ji.fs_sxt.R;

import java.util.HashMap;

import nodes.NodeInfo;
import services.AnalysisService;
import services.SoapService;
import tools.DataTools;
import tools.SoapTools;

/**
 * for web login if their is no such udp packet received
 * Created by jiyangkang on 2016/4/6 0006.
 */
public class LoginActivities extends Activity implements View.OnClickListener {
    private EditText editTextUserName;
    private EditText editTextPassword;
    private boolean threadOn = false;
    private CheckConnect checkConnect;
    private ProgressDialog mProgressDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(LoginActivities.this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(LoginActivities.this, "已连接到外部网络", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(LoginActivities.this, "未连接到外部网络，请检查网络连接", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(LoginActivities.this, "用户名或密码错误，请检查", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(LoginActivities.this, "未从服务器得到响应，请检查", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    mProgressDialog = ProgressDialog.show(LoginActivities.this, null, "登录成功，等待接收...");
                    startService(new Intent(LoginActivities.this, SoapService.class));
                    break;
                case 7:
                    mProgressDialog.dismiss();
                    String projectName = msg.getData().getString("project");
                    onProjectDialog(projectName);
                    break;
                case 8:
                    mProgressDialog.setMessage("收到数据，等待设置项目...");
                    break;
                default:
                    break;
            }
        }
    };



    private void onProjectDialog(final String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivities.this);
        builder.setTitle("项目：");
        if (name != null) {
            builder.setMessage(name);
            final Intent intent1 = new Intent(LoginActivities.this, SmartAG.class);
            final Intent intent2 = new Intent(LoginActivities.this, SmartHouse.class);


            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (name) {
                        case NodeInfo.PROJECTAG:
                            startActivity(intent1);
                            break;
                        case NodeInfo.PROJECTHS:
                            startActivity(intent2);
                            break;
                    }
                    dialog.dismiss();
                }
            });
        }
        builder.create().show();
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AnalysisService.Binder binder = (AnalysisService.Binder) service;
            binder.getAnalysisService()
                    .setOnDataReceived(new AnalysisService.OnDataReceived() {
                        //on received info from UDP or SOAP
                        @Override
                        public void haveData(HashMap<String, byte[]> thisHash) {
                            Log.d("getdata", "has get data");
                            Message msg = new Message();
                            byte[] dataType = thisHash.get("dataType");
                            byte[] netType = thisHash.get("netType");
                            byte[] device = thisHash.get("device");
                            byte[] data = thisHash.get("data");

                            handler.sendEmptyMessage(8);

                            if (dataType[0] == DataTools.PROJECTTYPE) {
                                String ProjectName = null;
                                switch (data[0]) {
                                    case NodeInfo.SMARTHS:
                                        ProjectName = NodeInfo.PROJECTHS;
                                        Log.d("Project", "Project type:" +
                                                data[0] + ":" + ProjectName);
                                        break;
                                    case NodeInfo.SMARTAG:
                                        ProjectName = NodeInfo.PROJECTAG;
                                        Log.d("Project", "Project type:" +
                                                data[0] + ":" + ProjectName);
                                        break;
                                    default:
                                        Log.d("ERROR", "Project type:" +
                                                data[0] + "");
                                        break;
                                }
                                Message msgProject = new Message();
                                Bundle bundle = new Bundle();
                                bundle.putString("project", ProjectName);
                                msgProject.what = 7;
                                msgProject.setData(bundle);
                                handler.sendMessage(msgProject);
                            }
                        }
                    });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        initView();
        findViewById(R.id.btn_login).setOnClickListener(this);
        threadOn = true;
        checkConnect = new CheckConnect();
        checkConnect.start();


        Intent i = new Intent(this, AnalysisService.class);
        startService(i);
        bindService(i, serviceConnection, BIND_AUTO_CREATE);
    }

    private void initView() {
        editTextUserName = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.pass);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(LoginActivities.this, SoapService.class));
        threadOn = false;
        checkConnect.interrupt();
        checkConnect = null;
        unbindService(serviceConnection);
        stopService(new Intent(LoginActivities.this, AnalysisService.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                String pass = editTextPassword.getText().toString();
                String name = editTextUserName.getText().toString();

                if (pass.equalsIgnoreCase("null") || name.equalsIgnoreCase("null")) {
                    handler.sendEmptyMessage(1);
                } else {
                    handler.sendEmptyMessage(6);
                }

//                String response = SoapTools.checkUser(name, pass);
//                if (response == null) {
//                    handler.sendEmptyMessage(5);
//                } else if (!response.equalsIgnoreCase("OK")) {
//                    handler.sendEmptyMessage(4);
//                } else {
//                    handler.sendEmptyMessage(6);
//                }


                break;
            default:
                break;
        }
    }

    private class CheckConnect extends Thread {
        @Override
        public void run() {
            super.run();

            int i = DataTools.delaytimes;
            while (threadOn) {
                if (SoapTools.checkConnect()) {
                    handler.sendEmptyMessage(2);
                    break;
                } else {
                    if (i == 0)
                        break;
                }
                i--;
            }
            if (i == 0) {
                handler.sendEmptyMessage(3);
            }
            this.interrupt();
        }
    }
}
