package com.bmc.baccus.controller.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bmc.baccus.R;
import com.bmc.baccus.model.Wine;
import com.bmc.baccus.model.Winery;
import com.bmc.baccus.network.asynctask.RequestGetWines;
import com.bmc.baccus.network.asynctask.interfaces.IRequestGetWines;
import com.bmc.baccus.utils.AppConstants;

import butterknife.ButterKnife;

public class WineListFragment extends Fragment implements IRequestGetWines {

    private OnWineSelectedListener mOnWineSelectedListener = null;

    private Winery oWinery = null;

    private ExpandableListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wine_list, container, false);
        ButterKnife.bind(this, rootView);

        listView = (ExpandableListView) rootView.findViewById(android.R.id.list);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int index = oWinery.getAbsolutePosition(Winery.WineType.values()[groupPosition], childPosition);

                if (mOnWineSelectedListener != null) {
                    mOnWineSelectedListener.onWineSelected(index);
                }

                return true;
            }
        });

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
            oWinery = winery;
            initViews(winery);
        }
    }

    private void initViews(Winery winery) {
        WineListAdapter wineListAdapter = new WineListAdapter(getActivity(), winery);
        listView.setAdapter(wineListAdapter);
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

    class WineListAdapter extends BaseExpandableListAdapter {

        private Typeface tf = null;

        private Context context = null;
        private Winery winery = null;

        public WineListAdapter(Context context, Winery winery) {
            this.context = context;
            this.winery = winery;
            tf = Typeface.createFromAsset(context.getAssets(), "Valentina-Regular.otf");
        }

        @Override
        public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewRow = inflater.inflate(R.layout.list_item_wine, parent, false);

            final ImageView ivItemWineImage = viewRow.findViewById(R.id.ivItemWineImage);
            TextView tvItemWineName = viewRow.findViewById(R.id.tvItemWineName);
            TextView tvItemWineCompany = viewRow.findViewById(R.id.tvItemWineCompany);

            final Wine currentWine = getChild(groupPosition, childPosition);

            @SuppressLint("StaticFieldLeak") final AsyncTask<Void, Void, Bitmap> showDataAsyncTask = new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... voids) {
                    return currentWine.getPhoto(getActivity());
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    ivItemWineImage.setImageBitmap(bitmap);
                }
            };

            showDataAsyncTask.execute();

            tvItemWineName.setText(currentWine.getName());
            tvItemWineCompany.setText(currentWine.getCompanyName());

            Typeface tf = Typeface.createFromAsset(context.getAssets(), "Valentina-Regular.otf");
            tvItemWineName.setTypeface(tf);
            tvItemWineCompany.setTypeface(tf);

            return viewRow;
        }


        @Override
        public int getGroupCount() {
            return Winery.WineType.values().length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return winery.getWineCount(getGroup(groupPosition));
        }

        @Override
        public Winery.WineType getGroup(int groupPosition) {
            return Winery.WineType.values()[groupPosition];
        }

        @Override
        public Wine getChild(int groupPosition, int childPosition) {
            return winery.getWine(getGroup(groupPosition), childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View wineHeader = inflater.inflate(R.layout.list_item_wine_header, parent, false);
            TextView headerText = (TextView) wineHeader.findViewById(R.id.tvHeaderWineType);
            headerText.setTypeface(tf);

            if (getGroup(groupPosition) == Winery.WineType.RED) {
                headerText.setText(R.string.red);
            } else if (getGroup(groupPosition) == Winery.WineType.WHITE) {
                headerText.setText(R.string.white);
            } else if (getGroup(groupPosition) == Winery.WineType.ROSE) {
                headerText.setText(R.string.rose);
            } else {
                headerText.setText(R.string.other);
            }

            return wineHeader;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
