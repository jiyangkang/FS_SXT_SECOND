package activities.fragmentlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hqyj.dev.ji.fs_sxt.R;

import views.DrawViewNode;

/**
 * Created by jiyangkang on 2016/4/17 0017.
 */
public class ViewFragment_ag extends Fragment {

    private View view;
    private DrawViewNode temp, light, soil, co2, pm25, infrared;

//


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_ag, container, false);

        initShow();


        return view;
    }

    private void initShow() {
        temp = (DrawViewNode) view.findViewById(R.id.node_temp);
        temp.setmName("空气温湿度");
        light = (DrawViewNode) view.findViewById(R.id.node_light);
        light.setmName("光照");
        soil = (DrawViewNode) view.findViewById(R.id.node_soil);
        soil.setmName("土壤温湿度");
        co2 = (DrawViewNode) view.findViewById(R.id.node_co2);
        co2.setmName("二氧化碳");
        pm25 = (DrawViewNode) view.findViewById(R.id.node_pm25);
        pm25.setmName("环境PM2.5");
        infrared = (DrawViewNode) view.findViewById(R.id.node_infrared);
        infrared.setmName("人体红外传感器");
    }

}
