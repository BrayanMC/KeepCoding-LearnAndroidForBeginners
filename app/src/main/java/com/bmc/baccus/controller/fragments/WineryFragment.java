package com.bmc.baccus.controller.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bmc.baccus.R;
import com.bmc.baccus.controller.adapters.WineryPagerAdapter;
import com.bmc.baccus.model.Winery;
import com.bmc.baccus.network.asynctask.RequestGetWines;
import com.bmc.baccus.network.asynctask.interfaces.IRequestGetWines;
import com.bmc.baccus.utils.AppConstants;
import com.bmc.baccus.utils.PreferencesConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WineryFragment extends Fragment implements ViewPager.OnPageChangeListener, IRequestGetWines {

    private static final String TAG = "WineryFragment";

    @BindView(R.id.viewPager)
    ViewPager viewPager = null;

    private ActionBar mActionBar = null;

    private Winery oWinery = null;

    private int currentIndex;

    public static WineryFragment newInstance(int newIndex) {

        Bundle arguments = new Bundle();
        arguments.putInt(AppConstants.ARG_WINE_INDEX, newIndex);
        WineryFragment fragment = new WineryFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_winery, container, false);
        ButterKnife.bind(this, rootView);

        initData();

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_winery, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (viewPager != null) {
            switch (item.getItemId()) {
                case R.id.menu_next:
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

                    return true;

                case R.id.menu_prev:
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);

                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem menuNext = menu.findItem(R.id.menu_next);
        MenuItem menuPrev = menu.findItem(R.id.menu_prev);

        if (viewPager != null && oWinery != null) {
            menuNext.setEnabled(viewPager.getCurrentItem() < oWinery.getWineCount() - 1);
            menuPrev.setEnabled(viewPager.getCurrentItem() > 0);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateActionBar(position);

        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit()
                .putInt(PreferencesConstants.PREF_LAST_WINE_INDEX, position)
                .apply();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewPager != null) {
            getArguments().putInt(AppConstants.ARG_WINE_INDEX, viewPager.getCurrentItem());
        }
    }

    private void initData() {

        if (!Winery.isInstanceAvailable()) {
            final RequestGetWines requestGetWines = new RequestGetWines(getActivity());
            requestGetWines.iRequestGetWines = this;
            requestGetWines.execute();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (requestGetWines.getStatus() == AsyncTask.Status.RUNNING) {
                        requestGetWines.cancel(true);
                    }
                }
            }, AppConstants.ASYNCTASK_TIMEOUT);
        } else {
            oWinery = Winery.getInstance();
            initViews();
        }
    }

    @Override
    public void showData(Winery winery) {
        oWinery = winery;
        initViews();
    }

    private void initViews() {
        viewPager.setAdapter(new WineryPagerAdapter(getFragmentManager()));
        viewPager.addOnPageChangeListener(this);

        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        getCurrentIndex();
        viewPager.setCurrentItem(currentIndex);
        updateActionBar(currentIndex);
    }

    private int getCurrentIndex() {
        currentIndex = argumentsContainsKey(AppConstants.ARG_WINE_INDEX) ? getArguments().getInt(AppConstants.ARG_WINE_INDEX) : 0;
        return currentIndex;
    }

    private void updateActionBar(int index) {
        Log.i(TAG, oWinery.getWine(index).getName());
        mActionBar.setTitle(oWinery.getWine(index).getName());
    }

    private boolean argumentsContainsKey(String key) {
        return getArguments().containsKey(key);
    }

    public void changeWine(int wineIndex) {
        viewPager.setCurrentItem(wineIndex);
    }
}
