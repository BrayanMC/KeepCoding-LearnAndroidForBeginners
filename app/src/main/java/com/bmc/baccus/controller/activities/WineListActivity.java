package com.bmc.baccus.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bmc.baccus.R;
import com.bmc.baccus.controller.fragments.WineListFragment;
import com.bmc.baccus.controller.fragments.WineryFragment;
import com.bmc.baccus.utils.AppConstants;
import com.bmc.baccus.utils.PreferencesConstants;
import com.bmc.baccus.utils.navigation.Navigation;
import com.bmc.baccus.utils.navigation.NavigationUI;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WineListActivity extends AppCompatActivity implements WineListFragment.OnWineSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_list);
        ButterKnife.bind(this);

        setUpToolbar();

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

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public void onWineSelected(int wineIndex) {
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
}
