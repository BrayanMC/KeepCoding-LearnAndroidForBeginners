package com.bmc.baccus.controller.fragments;

import android.app.Activity;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class WineListFragment extends Fragment {

    private OnWineSelectedListener mOnWineSelectedListener = null;

    @BindView(android.R.id.list)
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_wine_list, container, false);
        ButterKnife.bind(this, rootView);

        initViews();

        return rootView;
    }

    private void initViews() {
        Winery oWinery = Winery.getInstance();
        ArrayAdapter<Wine> wineArrayAdapter = new ArrayAdapter<Wine>(getActivity(), android.R.layout.simple_list_item_1, oWinery.getWineList());

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
