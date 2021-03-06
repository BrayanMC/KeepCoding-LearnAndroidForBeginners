package com.bmc.baccus.controller.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bmc.baccus.R;
import com.bmc.baccus.model.Wine;
import com.bmc.baccus.utils.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebFragment extends Fragment {

    private static final String STATE_URL = "CURRENT_URL";

    // Modelo
    private Wine oWine = null;

    // Vistas
    @BindView(R.id.wvBrowser)
    WebView wbBrowser;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_web, container, false);
        ButterKnife.bind(this, rootView);

        if (argumentsContainsKey(AppConstants.ARG_OBJECT_WINE)) {
            getWineObjectFromArguments();
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

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_URL, wbBrowser.getUrl());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_web, menu);
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

    private void getWineObjectFromArguments() {
        oWine = (Wine) getArguments().getSerializable(AppConstants.ARG_OBJECT_WINE);
    }

    private boolean argumentsContainsKey(String key) {
        return getArguments().containsKey(key);
    }
}
