package com.bmc.baccus.controller.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.bmc.baccus.controller.fragments.WineryFragment;
import com.bmc.baccus.utils.AppConstants;

public class WineryActivity extends FragmentContainerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpToolbar();
    }

    @Override
    protected Fragment createFragment() {
        return WineryFragment.newInstance(getIntent().getIntExtra(AppConstants.EXTRA_WINE_INDEX, 0));
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
}
