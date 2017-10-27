package com.bmc.baccus.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bmc.baccus.BuildConfig;
import com.bmc.baccus.R;
import com.bmc.baccus.model.Wine;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WineActivity extends AppCompatActivity {

    //    private static final String TAG = WineActivity.class.getSimpleName();
    private static final String TAG = "WineActivity";

    // Modelo
    private Wine oWine = null;

    // Vistas
    @BindView(R.id.ivWine)
    ImageView mIvWine;

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

    private void setViewsFromModel() {
        mIvWine.setImageResource(oWine.getPhoto());
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
}
