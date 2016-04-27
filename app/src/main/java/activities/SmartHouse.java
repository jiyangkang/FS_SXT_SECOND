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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import activities.fragmentlist.ChangeNet;
import activities.fragmentlist.ChangeNet_AG;
import activities.fragmentlist.CodeAndTel_ag;
import activities.fragmentlist.CtrolNode;
import activities.fragmentlist.ViewFragment;
import nodes.Node;
import nodes.NodeInfo;
import project.Project;
import services.AnalysisService;
import services.SoapService;
import services.UDPService;
import tools.DataTools;
import tools.StringTools;

/**
 *
 * Created by jiyangkang on 2016/4/8 0008.
 */
public class SmartHouse extends FragmentActivity{
    HashMap<String, Node> thisList;
    private ViewPager mViewPager;
    private boolean isChanging;
    private List<Fragment> fragmentList;

    private Fragment view_hs;
    private Fragment ctrl_hs;
    private Fragment change_net;
    private Fragment code_tel;

    private Project project;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Project.getProject().setName(NodeInfo.PROJECTAG);
                    if (!isChanging){
                        changeProject();
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
            binder.getAnalysisService().setOnDataReceived(new AnalysisService.OnDataReceived() {

                @Override
                public void haveData(HashMap<String, byte[]> thisHash) {
                    byte[] dataType = thisHash.get(DataTools._DATATYPE);
                    byte[] netType = thisHash.get(DataTools._NETTYPE);
                    byte[] device = thisHash.get(DataTools._DEVICE);
                    byte[] data = thisHash.get(DataTools._DATA);

                    if (netType[0] != NodeInfo.netType){
                        Intent intent = new Intent();
                        intent.putExtra("netType", netType[0]);
                        intent.setAction("netType");
                        sendBroadcast(intent);
                        NodeInfo.netType = netType[0];
                    }

                    Log.d("datas", "havadata"+dataType[0]+ StringTools.changeIntoHexString(device,false));
                    if (dataType[0] == DataTools.PROJECTTYPE) {
                        if (data[0] == NodeInfo.SMARTAG) {
                            handler.sendEmptyMessage(1);//发现工程变动。
                            stopService(new Intent(SmartHouse.this, UDPService.class));
                        } else {
                            Project.getProject().setName(NodeInfo.PROJECTHS);
                        }
                    } else {

                        String addr = StringTools.changeIntoHexString(device, false);
                        Log.d("DATA", addr+"");

                        String name = NodeInfo.hashList.get(addr);
                        Log.d("DATA", name+"");
                        thisList = Project.getProject().getNodeSet();
                        Log.d("DATA", thisList.size()+"");
                        Node node = thisList.get(name);
                        if (node != null){
                            Log.d("Data", "this is for test");
                            node.setValue(data);
                            node.setNetType(netType[0]);
                        }else {
                            Log.d("Data", "this is null");
                        }

                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(SmartHouse.this, SoapService.class));
        stopService(new Intent(SmartHouse.this, UDPService.class));
        unbindService(serviceConnection);
        stopService(new Intent(SmartHouse.this, AnalysisService.class));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.smarths_activity);

        Project project = Project.getProject();
        project.setName(NodeInfo.PROJECTHS);
        thisList = Project.getProject().getNodeSet();

        initShow();

        mViewPager.setAdapter(new SwitchFragemtnAdapter(getSupportFragmentManager(), fragmentList));
        Log.d("UDP", DataTools.isUDP + "");
        if (DataTools.isUDP) {
            startService(new Intent(SmartHouse.this, UDPService.class));
        } else {
            startService(new Intent(SmartHouse.this, SoapService.class));
        }

        Intent intent = new Intent(SmartHouse.this, AnalysisService.class);
        startService(intent);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

    }

    private void initShow() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager_hs);
        fragmentList = new ArrayList<Fragment>();
        view_hs = new ViewFragment();
        ctrl_hs = new CtrolNode();
        change_net = new ChangeNet_AG();
        code_tel = new CodeAndTel_ag();
        fragmentList.add(view_hs);
        fragmentList.add(ctrl_hs);
        fragmentList.add(change_net);
        fragmentList.add(code_tel);
    }

    private void changeProject() {
        isChanging = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(SmartHouse.this);
        builder.setTitle("项目改变");
        builder.setMessage("项目改变为智能家居");
        builder.setPositiveButton("跳转", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(SmartHouse.this, SmartHouse.class));
                SmartHouse.this.finish();
                dialog.dismiss();
            }
        });
        builder.create().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isChanging = false;
            }
        });
        builder.create().show();
    }
}
