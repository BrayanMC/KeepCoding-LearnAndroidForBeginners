package com.bmc.baccus.controller.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.bmc.baccus.controller.fragments.SettingsFragment;
import com.bmc.baccus.utils.AppConstants;

public class SettingsActivity extends FragmentContainerActivity {

    @Override
    protected Fragment createFragment() {
        Bundle arguments = new Bundle();

        if (intentHasExtra(AppConstants.EXTRA_WINE_IMAGE_SCALE_TYPE)) {
            arguments.putSerializable(AppConstants.ARG_WINE_IMAGE_SCALE_TYPE, getIntent().getSerializableExtra(AppConstants.EXTRA_WINE_IMAGE_SCALE_TYPE));

            SettingsFragment settingsFragment = new SettingsFragment();
            settingsFragment.setArguments(arguments);

            return  settingsFragment;
        } else {
            return null;
        }
    }

    private boolean intentHasExtra(String key) {
        return getIntent().hasExtra(key);
    }
}
