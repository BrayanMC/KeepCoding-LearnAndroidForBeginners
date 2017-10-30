package com.bmc.baccus.controller.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bmc.baccus.controller.fragments.WineFragment;
import com.bmc.baccus.model.Winery;
import com.bmc.baccus.utils.AppConstants;

public class WineryPagerAdapter extends FragmentPagerAdapter {

    private Winery oWinery = null;

    public WineryPagerAdapter(FragmentManager fm) {
        super(fm);
        oWinery = Winery.getInstance();
    }

    @Override
    public Fragment getItem(int position) {
        WineFragment wineFragment = new WineFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(AppConstants.ARG_OBJECT_WINE, oWinery.getWine(position));
        wineFragment.setArguments(arguments);

        return wineFragment;
    }

    @Override
    public int getCount() {
        return oWinery.getWineCount();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);

        return oWinery.getWine(position).getName();
    }
}
