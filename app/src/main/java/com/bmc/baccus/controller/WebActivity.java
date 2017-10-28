package com.bmc.baccus.controller;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bmc.baccus.R;
import com.bmc.baccus.model.Wine;
import com.bmc.baccus.util.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity {

    private static final String STATE_URL = "CURRENT_URL";

    // Modelo
    private Wine oWine = null;

    // Vistas
    @BindView(R.id.wvBrowser)
    WebView wbBrowser;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        if (intentHasExtra(AppConstants.EXTRA_OBJECT_WINE)) {
            oWine = (Wine) getIntent().getSerializableExtra(AppConstants.EXTRA_OBJECT_WINE);
        }

        // Configurar views
        wbBrowser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pbLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pbLoading.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                pbLoading.setVisibility(View.GONE);
            }
        });

        wbBrowser.getSettings().setJavaScriptEnabled(true);
        wbBrowser.getSettings().setBuiltInZoomControls(true);

        // Cargar la web page
        if (savedInstanceState == null || !savedInstanceState.containsKey(STATE_URL)) {
            wbBrowser.loadUrl(oWine.getCompanyWeb());
        } else {
            wbBrowser.loadUrl(savedInstanceState.getString(STATE_URL));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fileList();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_URL, wbBrowser.getUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_web, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_reload:
                wbBrowser.reload();
                return true;
        }

        return false;
    }

    private boolean intentHasExtra(String key) {
        return getIntent().hasExtra(key);
    }
}
