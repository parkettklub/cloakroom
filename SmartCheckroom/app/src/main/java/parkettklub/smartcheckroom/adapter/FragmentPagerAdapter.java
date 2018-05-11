package parkettklub.smartcheckroom.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import parkettklub.smartcheckroom.fragments.CameraFragment;
import parkettklub.smartcheckroom.fragments.ManualFragment;

/**
 * Created by Badbeloved on 2018. 03. 30..
 */

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 2;

    public FragmentPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new CameraFragment();
            case 1: return new ManualFragment();
            default: return new CameraFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Barcode Reader";
            case 1:
                return "Manual adding";
            default:
                return "unknown";
        }
    }
}