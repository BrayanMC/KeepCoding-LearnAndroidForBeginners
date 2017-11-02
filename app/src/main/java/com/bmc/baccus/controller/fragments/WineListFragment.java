package com.bmc.baccus.controller.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.bmc.baccus.R;
import com.bmc.baccus.model.Wine;
import com.bmc.baccus.model.Winery;
import com.bmc.baccus.network.asynctask.RequestGetWines;
import com.bmc.baccus.network.asynctask.interfaces.IRequestGetWines;
import com.bmc.baccus.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class WineListFragment extends Fragment implements IRequestGetWines {

    private OnWineSelectedListener mOnWineSelectedListener = null;

    @BindView(android.R.id.list)
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wine_list, container, false);
        ButterKnife.bind(this, rootView);

        initData();

        return rootView;
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
            initViews(Winery.getInstance());
        }
    }

    @Override
    public void showData(Winery winery) {
        if (winery != null) {
            initViews(winery);
        }
    }

    private void initViews(Winery winery) {
        ArrayAdapter<Wine> wineArrayAdapter = new ArrayAdapter<Wine>(getActivity(), android.R.layout.simple_list_item_1, winery.getWineList());

        listView.setAdapter(wineArrayAdapter);
    }

    @OnItemClick(android.R.id.list)
    void onListItemClick(int position) {
        Log.i("", ((Wine) listView.getItemAtPosition(position)).toString());

        if (mOnWineSelectedListener != null) {
            mOnWineSelectedListener.onWineSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mOnWineSelectedListener = (OnWineSelectedListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnWineSelectedListener = null;
    }

    public interface OnWineSelectedListener {
        void onWineSelected(int wineIndex);
    }
}
