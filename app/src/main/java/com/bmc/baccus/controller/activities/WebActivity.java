package com.bmc.baccus.controller.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.bmc.baccus.controller.fragments.WebFragment;
import com.bmc.baccus.utils.AppConstants;

public class WebActivity extends FragmentContainerActivity {

    @Override
    protected Fragment createFragment() {
        Bundle arguments = new Bundle();

        setUpToolbar();

        if (intentHasExtra(AppConstants.EXTRA_OBJECT_WINE)) {
            arguments.putSerializable(AppConstants.ARG_OBJECT_WINE, getIntent().getSerializableExtra(AppConstants.EXTRA_OBJECT_WINE));

            WebFragment webFragment = new WebFragment();
            webFragment.setArguments(arguments);

            return webFragment;
        } else {
            return null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean intentHasExtra(String key) {
        return getIntent().hasExtra(key);
    }
}
