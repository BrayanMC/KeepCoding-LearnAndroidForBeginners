package com.bmc.baccus.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bmc.baccus.R;
import com.bmc.baccus.model.Wine;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends Activity {

    private static final String STATE_URL = "url";

    // Modelo
    private Wine oWine = null;

    // Vistas
    @BindView(R.id.wvBrowser)
    WebView wbBrowser;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);

        // Creamos el modelo
        createWineObject();

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

    private void createWineObject() {
        oWine = new Wine("Bembibre"
                , "Tinto"
                , R.drawable.vegaval
                , "Dominio de Tares"
                , "http://www.dominiodetares.com/portfolio/bembibre/"
                , "Este vino muestra toda la complejidad y la elegencia de la variedad Mencía. En fase visual luce un color rojo picota muy cubierto con tonalidades violáceas en el menisco. En nariz aparecen frutales muy intensos de frutas rojos (frambuesa, cereza) y una potente ciruela negra, así como tonos florales de la gama de las rosas y violetas, vegetales muy elegantes y complementarios, hojarasca verde, tabaco y madres aromáticas (sándalo) que le brinda un toque ciertamente perfumado."
                , "El Bierzo"
                , 5);

        addGrape("Mencía");
    }

    private void addGrape(String newGrape) {
        oWine.addGrape(newGrape);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_URL, wbBrowser.getUrl());
    }
}
