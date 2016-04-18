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

import activities.fragmentlist.ViewFragment_ag;
import nodes.Node;
import nodes.NodeInfo;
import project.Project;
import services.AnalysisService;
import services.SoapService;
import services.UDPService;
import tools.DataTools;
import tools.StringTools;

/**
 * show information on the plate
 * Created by jiyangkang on 2016/4/6 0006.
 */
public class SmartAG extends FragmentActivity {
    HashMap<String, Node> thisList;
    private ViewPager mViewPager;
    private int screenWith;
    private int currentTab;
    private boolean isChanging;
    private List<Fragment> fragmentList;
    private Fragment view_ag;

    private Project project;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Project.getProject().setName(NodeInfo.PROJECTHS);
                    if (!isChanging) {
                        changeProject();
                    }
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
                    byte[] dataType = thisHash.get(DataTools._DATATYPE);
                    byte[] netType = thisHash.get(DataTools._NETTYPE);
                    byte[] device = thisHash.get(DataTools._DEVICE);
                    byte[] data = thisHash.get(DataTools._DATA);

                    Log.d("datas", "havadata"+dataType[0]+StringTools.changeIntoHexString(device,false));
                    if (dataType[0] == DataTools.PROJECTTYPE) {
                        if (data[0] == NodeInfo.SMARTHS) {
                            handler.sendEmptyMessage(1);//发现工程变动。
                            stopService(new Intent(SmartAG.this, UDPService.class));
                        } else {
                            Project.getProject().setName(NodeInfo.PROJECTAG);
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
                        }else {
                            Log.d("Data", "this is null");
                        }
//                        thisList.get(name).setValue(data);
//                        thisList.get(NodeInfo.hashList.get(StringTools.changeIntoHexString(device,false))).setNetType(netType[0]);
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
        stopService(new Intent(SmartAG.this, SoapService.class));
        stopService(new Intent(SmartAG.this, UDPService.class));
        unbindService(serviceConnection);
        stopService(new Intent(SmartAG.this, AnalysisService.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.smartag_activity);

        Project project = Project.getProject();
        project.setName(NodeInfo.PROJECTAG);
        thisList = Project.getProject().getNodeSet();
        initView();

        mViewPager.setAdapter(new SwitchFragemtnAdapter(getSupportFragmentManager(), fragmentList));
        Log.d("UDP", DataTools.isUDP + "");
        if (DataTools.isUDP) {
            startService(new Intent(SmartAG.this, UDPService.class));
        } else {
            startService(new Intent(SmartAG.this, SoapService.class));
        }

        Intent intent = new Intent(SmartAG.this, AnalysisService.class);
        startService(intent);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);


    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        fragmentList = new ArrayList<Fragment>();
        view_ag = new ViewFragment_ag();
        fragmentList.add(view_ag);

    }

    private void changeProject() {
        isChanging = true;
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
        builder.create().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isChanging = false;
            }
        });
        builder.create().show();
    }

}
