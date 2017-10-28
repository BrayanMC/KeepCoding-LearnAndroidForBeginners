package com.bmc.baccus.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bmc.baccus.BuildConfig;
import com.bmc.baccus.R;
import com.bmc.baccus.model.Wine;
import com.bmc.baccus.util.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WineActivity extends AppCompatActivity {

    private static final int SETTINGS_REQUEST = 1;
    private static final String STAGE_IMAGE_SCALE_TYPE = "CURRENT_SCALE_TYPE";

    private static final String TAG = "WineActivity";

    // Modelo
    private Wine oWine = null;

    // Vistas
    @BindView(R.id.ivWine)
    ImageView ivWine;

    @BindView(R.id.tvWineName)
    TextView tvWineName = null;

    @BindView(R.id.tvWineType)
    TextView tvWineType = null;

    @BindView(R.id.tvWineOrigin)
    TextView tvWineOrigin = null;

    @BindView(R.id.rbWine)
    RatingBar rbWine = null;

    @BindView(R.id.tvWineCompany)
    TextView tvWineCompany = null;

    @BindView(R.id.tvWineNotes)
    TextView tvWineNotes;

    @BindView(R.id.llGrapesContainer)
    ViewGroup vgWineGrapesContainer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine);
        ButterKnife.bind(this);

        // Creamos el modelo
        createWineObject();

        // Accedemos a las vistas desde el modelo
        // findViewById()

        // Damos valor a las vistas con el modelo
        setViewsFromModel();

        // Actualizar lista de Grapes
        updateGrapesList();

        if (savedInstanceState != null && savedInstanceState.containsKey(STAGE_IMAGE_SCALE_TYPE)) {
            setRadioGroupCheck(savedInstanceState.getString(STAGE_IMAGE_SCALE_TYPE));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivityForResult(new Intent(this, SettingsActivity.class).putExtra(AppConstants.EXTRA_WINE_IMAGE_SCALE_TYPE, ivWine.getScaleType() == ImageView.ScaleType.FIT_XY ? AppConstants.SCALE_TYPE_FIT_XY : AppConstants.SCALE_TYPE_FIT_CENTER), SETTINGS_REQUEST);
                return true;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SETTINGS_REQUEST:
                    setRadioGroupCheck(data.getStringExtra(AppConstants.EXTRA_WINE_IMAGE_SCALE_TYPE));
                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STAGE_IMAGE_SCALE_TYPE, ivWine.getScaleType() == ImageView.ScaleType.FIT_XY ? AppConstants.SCALE_TYPE_FIT_XY : AppConstants.SCALE_TYPE_FIT_CENTER);
    }

    private void createWineObject() {
        oWine = new Wine("Bembibre"
                , "Tinto"
                , R.drawable.bembibre
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

    @OnClick(R.id.ibtnGoToWeb)
    public void goToDetails(View view) {
        startActivity(new Intent(this, WebActivity.class).putExtra(AppConstants.EXTRA_OBJECT_WINE, oWine));
    }

    private void setViewsFromModel() {
        ivWine.setImageResource(oWine.getPhoto());
        tvWineName.setText(oWine.getName());
        tvWineType.setText(oWine.getType());
        tvWineOrigin.setText(oWine.getOrigin());
        rbWine.setRating(oWine.getRating());
        tvWineCompany.setText(oWine.getCompanyName());
        tvWineNotes.setText(oWine.getNotes());
    }

    private void updateGrapesList() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, oWine.toString());
        }

        for (int i = 0; i < oWine.getGrapeCount(); i++) {
            TextView grapeText = new TextView(this);
            grapeText.setText(oWine.getGrape(i));

            grapeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            vgWineGrapesContainer.addView(grapeText);
        }
    }

    private void setRadioGroupCheck(String scaleType) {
        if (scaleType.equals(AppConstants.SCALE_TYPE_FIT_XY)) {
            ivWine.setScaleType(ImageView.ScaleType.FIT_XY);
        } else if (scaleType.equals(AppConstants.SCALE_TYPE_FIT_CENTER)) {
            ivWine.setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
    }
}
