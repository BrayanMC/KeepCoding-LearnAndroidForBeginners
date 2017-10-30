package com.bmc.baccus.controller.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bmc.baccus.R;
import com.bmc.baccus.controller.fragments.WineListFragment;
import com.bmc.baccus.controller.fragments.WineryFragment;
import com.bmc.baccus.utils.AppConstants;
import com.bmc.baccus.utils.PermissionConstants;
import com.bmc.baccus.utils.PreferencesConstants;
import com.bmc.baccus.utils.navigation.Navigation;
import com.bmc.baccus.utils.navigation.NavigationUI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WineListActivity extends AppCompatActivity implements WineListFragment.OnWineSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private int indexSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_list);
        ButterKnife.bind(this);

        setUpToolbar();

        if (hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            initFragments();
        } else {
            requestPermissionsSafely(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PermissionConstants.PERMISSION_WRITE_EXTERNAL_STORAGE);
        }
    }

    private void initFragments() {
        FragmentManager fm = getSupportFragmentManager();

        if (findViewById(R.id.list) != null) {
            Fragment listFragment = fm.findFragmentById(R.id.list);
            if (listFragment == null) {
                listFragment = new WineListFragment();
                fm.beginTransaction().add(R.id.list, listFragment).commit();
            }
        }

        if (findViewById(R.id.winery) != null) {
            Fragment wineryFragment = fm.findFragmentById(R.id.winery);
            if (wineryFragment == null) {
                wineryFragment = WineryFragment.newInstance(PreferenceManager.getDefaultSharedPreferences(this).getInt(PreferencesConstants.PREF_LAST_WINE_INDEX, 0));
                fm.beginTransaction().add(R.id.winery, wineryFragment).commit();
            }
        }
    }

    @Override
    public void onWineSelected(int wineIndex) {
        if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            initFragment(wineIndex);
        } else {
            indexSelected = wineIndex;
            requestPermissionsSafely(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PermissionConstants.PERMISSION_READ_EXTERNAL_STORAGE);
        }
    }

    private void initFragment(@Nullable int wineIndex) {
        WineryFragment wineryFragment = (WineryFragment) getSupportFragmentManager().findFragmentById(R.id.winery);

        if (wineryFragment != null) {
            wineryFragment.changeWine(wineIndex);
        } else {
            Navigation.getInstance().startActivityWithExtras(this
                    , new Intent().putExtra(AppConstants.EXTRA_WINE_INDEX, wineIndex)
                    , NavigationUI.Activity.WINERY
                    , false);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionConstants.PERMISSION_WRITE_EXTERNAL_STORAGE: {
                initFragments();

                break;
            }

            case PermissionConstants.PERMISSION_READ_EXTERNAL_STORAGE: {
                initFragment(indexSelected);

                break;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
    }
}
