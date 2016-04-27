package activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hqyj.dev.ji.fs_sxt.R;

import java.util.HashMap;

import nodes.NodeInfo;
import project.*;
import services.AnalysisService;
import services.SoapService;
import services.UDPService;
import tools.DataTools;
import views.DrawProjectButton;
import tools.StringTools;

/**
 * guider for operation
 * the first view shown when the app start
 */
public class WelcomActivity extends Activity implements View.OnClickListener {

    private DrawProjectButton btnAG, btnHS;
    private TextView scanUdp;
    private boolean threadOn = false;
    private boolean isUDP = false;
    private ProgressDialog progressDialog;

    private CheckUDP mCheckUdp;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://false
                    progressDialog.dismiss();
                    onCheckDialog();
                    break;
                case 2://true
                    progressDialog.setMessage("扫描到UDP数据，设置项目中...");
                    break;
                case 3://check Project Type
                    progressDialog.dismiss();
                    stopService(new Intent(WelcomActivity.this, UDPService.class));
                    String name = msg.getData().getString("project");
                    Project.getProject().setName(name);
                    Log.d("name", name + "");
                    Intent intent;
                    if (name != null) {
                        onProjectDialog(name);
                    }
                    break;
                default:
                    break;
            }
        }
    };

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

                            if (!isUDP) {
                                isUDP = true;
                                DataTools.isUDP = true;
                                threadOn = false;
                            }
                            Message msg = new Message();
                            byte[] dataType = thisHash.get("dataType");
                            byte[] netType = thisHash.get("netType");
                            byte[] device = thisHash.get("device");
                            byte[] data = thisHash.get("data");

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
                                msgProject.what = 3;
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
        setContentView(R.layout.welcom_activity);

        btnAG = (DrawProjectButton) findViewById(R.id.ag);
        btnHS = (DrawProjectButton) findViewById(R.id.hs);
        scanUdp = (TextView) findViewById(R.id.scan);
        scanUdp.setOnClickListener(this);
        Project.getProject().setOnProjectChanged(new Project.OnProjectChanged() {
            @Override
            public void onProjectChange(String name) {
                btnAG.onProjectChange(name);
                btnHS.onProjectChange(name);
                btnAG.invalidate();
                btnHS.invalidate();
                Log.d("invalidate", "invalidate");
            }
        });

        Intent startUdp = new Intent(this, UDPService.class);
        startService(startUdp);

        Intent startAny = new Intent(this, AnalysisService.class);
        startService(startAny);
        bindService(startAny, serviceConnection, Context.BIND_AUTO_CREATE);

        threadOn = true;
        mCheckUdp = new CheckUDP();

        mCheckUdp.start();
        progressDialog = ProgressDialog.show(this, null, "正在扫描UDP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
        stopService(new Intent(WelcomActivity.this, UDPService.class));
        stopService(new Intent(WelcomActivity.this, AnalysisService.class));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.scan:
                startService(new Intent(WelcomActivity.this, UDPService.class));
                threadOn = true;
                mCheckUdp = new CheckUDP();
                mCheckUdp.start();
                progressDialog = ProgressDialog.show(WelcomActivity.this, null, "正在扫描UDP");
                break;
            case R.id.ag:
                if (btnAG.isClickAble()) {
                    startActivity(new Intent(WelcomActivity.this, SmartAG.class));
                }
                break;
            case R.id.hs:
                if (btnHS.isClickAble()) {
                    startActivity(new Intent(WelcomActivity.this, SmartHouse.class));
                }
                break;
            default:
                break;
        }
    }

    //if get UDP end this
    private class CheckUDP extends Thread {
        int i = DataTools.delaytimes;

        @Override
        public void run() {
            super.run();
            while (threadOn && !isUDP) {
                i--;
                if (i == 0)
                    break;
                try {
                    sleep(DataTools.delayBase);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (i == 0) {
                isUDP = false;
                DataTools.isUDP = false;
                this.interrupt();
                handler.sendEmptyMessage(1);
            } else {

                handler.sendEmptyMessage(2);
            }
            this.interrupt();
        }
    }

    //get no UDP
    private void onCheckDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomActivity.this);
        builder.setTitle("未接收到内网UDP");
        builder.setMessage("是否使用外网模式？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(WelcomActivity.this, LoginActivities.class));
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    //get Project Info
    private void onProjectDialog(final String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomActivity.this);
        builder.setTitle("项目：");
        if (name != null) {
            builder.setMessage(name);

            isUDP = false;
            final Intent intent1 = new Intent(WelcomActivity.this, SmartAG.class);
            final Intent intent2 = new Intent(WelcomActivity.this, SmartHouse.class);


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
}
