package com.bmc.baccus.controller.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bmc.baccus.controller.fragments.WineFragment;
import com.bmc.baccus.utils.AppConstants;

public class WineActivity extends FragmentContainerActivity {

    @Override
    protected Fragment createFragment() {
        Bundle arguments = new Bundle();

        if (intentHasExtra(AppConstants.EXTRA_OBJECT_WINE)) {
            arguments.putSerializable(AppConstants.ARG_OBJECT_WINE, getIntent().getSerializableExtra(AppConstants.EXTRA_OBJECT_WINE));

            WineFragment wineFragment = new WineFragment();
            wineFragment.setArguments(arguments);

            return wineFragment;
        } else {
            return null;
        }
    }

    private boolean intentHasExtra(String key) {
        return getIntent().hasExtra(key);
    }
}
