package com.bmc.baccus.controller.activities;

import android.support.v4.app.Fragment;

import com.bmc.baccus.controller.fragments.WineryFragment;

public class WineryActivity extends FragmentContainerActivity {

    @Override
    protected Fragment createFragment() {
        return new WineryFragment();
    }
}
