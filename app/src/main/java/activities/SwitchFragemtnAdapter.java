package activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiyangkang on 2016/4/18 0018.
 */
public class SwitchFragemtnAdapter extends FragmentPagerAdapter{

    List<Fragment> fragmentList = new ArrayList<Fragment>();

    public SwitchFragemtnAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }
}
