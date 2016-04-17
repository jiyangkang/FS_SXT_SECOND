package activities;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.hqyj.dev.ji.fs_sxt.R;

import java.util.HashMap;
import java.util.List;

import nodes.Node;
import nodes.NodeInfo;
import project.Project;
import services.AnalysisService;
import services.SoapService;
import services.UDPService;
import tools.DataTools;

/**
 * show information on the plate
 * Created by jiyangkang on 2016/4/6 0006.
 */
public class SmartAG extends FragmentActivity{
    HashMap<String, Node> thisList;
    private ViewPager mViewPager;
    private List<Fragment> fragmentList;
    private int screenWith;
    private int currentTab;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Project.getProject().setName(NodeInfo.PROJECTHS);
                    changeProject();
                    break;
                case 2:
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
            binder.getAnalysisService().setOnDataReceived(new AnalysisService.OnDataReceived() {
                @Override
                public void haveData(HashMap<String, byte[]> thisHash) {
                    byte[] dataType = thisHash.get("dataType");
                    byte[] netType = thisHash.get("netType");
                    byte[] device = thisHash.get("device");
                    byte[] data = thisHash.get("data");
                    Log.d("datas", "havadata");
                    if (dataType[0] == DataTools.PROJECTTYPE){
                        if (data[0] == NodeInfo.SMARTHS){
                            handler.sendEmptyMessage(1);//发现工程变动。
                            stopService(new Intent(SmartAG.this, UDPService.class));
                        }
                    }else if (thisList.get(NodeInfo.hashList.get(device)) != null){
                        thisList.get(NodeInfo.hashList.get(device)).setValue(data);
                        thisList.get(NodeInfo.hashList.get(device)).setNetType(netType[0]);
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
        setContentView(R.layout.smartag_activity);


        thisList = Project.getProject().getNodeSet();
        initView();

        Log.d("UDP", DataTools.isUDP+"");
        if (DataTools.isUDP){
            startService(new Intent(this, UDPService.class));
        } else {
            startService(new Intent(this, SoapService.class));
        }

        Intent intent = new Intent(this, AnalysisService.class);
        startService(intent);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);



    }

    private void initView() {

    }

    private void changeProject(){
        AlertDialog.Builder builder = new AlertDialog.Builder(SmartAG.this);
        builder.setTitle("项目改变");
        builder.setMessage("项目改变为智能家居");
        builder.setPositiveButton("跳转", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(SmartAG.this, SmartHouse.class));
                SmartAG.this.finish();
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
