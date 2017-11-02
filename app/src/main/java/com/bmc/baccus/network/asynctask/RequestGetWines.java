package com.bmc.baccus.network.asynctask;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bmc.baccus.R;
import com.bmc.baccus.model.Winery;
import com.bmc.baccus.network.asynctask.interfaces.IRequestGetWines;

public class RequestGetWines extends AsyncTask<Void, Void, Winery> {

    private static final String TAG = "RequestGetWines";

    private ProgressDialog progressDialog = null;

    @SuppressLint("StaticFieldLeak")
    private Context context;

    public IRequestGetWines iRequestGetWines = null;

    public RequestGetWines(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        try {
            initProgressDialog();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        progressDialog.show();
    }

    @Override
    protected Winery doInBackground(Void... voids) {
        return Winery.getInstance();
    }

    @Override
    protected void onPostExecute(Winery winery) {
        super.onPostExecute(winery);

        if (winery != null) {
            iRequestGetWines.showData(winery);
        } else {
            iRequestGetWines.showData(null);
        }

        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (isCancelled()) {
            progressDialog.dismiss();
        }
    }

    private void initProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setTitle(R.string.loading);
        }
    }
}
