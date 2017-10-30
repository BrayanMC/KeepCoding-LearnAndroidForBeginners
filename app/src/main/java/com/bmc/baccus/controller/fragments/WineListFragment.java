package com.bmc.baccus.controller.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class WineListFragment extends Fragment {

    private OnWineSelectedListener mOnWineSelectedListener = null;
    private ProgressDialog progressDialog = null;

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
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Winery> wineryAsyncTask = new AsyncTask<Void, Void, Winery>() {
            @Override
            protected Winery doInBackground(Void... voids) {
                return Winery.getInstance();
            }

            @Override
            protected void onPostExecute(Winery winery) {
                super.onPostExecute(winery);
                initViews(winery);

                progressDialog.dismiss();
            }
        };

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(R.string.loading);

        if (!Winery.isInstanceAvailable()) {
            progressDialog.show();
        }

        wineryAsyncTask.execute();
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
        mOnWineSelectedListener = (OnWineSelectedListener)getActivity();
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
