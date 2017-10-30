package com.bmc.baccus.controller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.bmc.baccus.utils.AppConstants;
import com.bmc.baccus.utils.PreferencesConstants;
import com.bmc.baccus.utils.navigation.Navigation;
import com.bmc.baccus.utils.navigation.NavigationUI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WineFragment extends Fragment {

    private static final int SETTINGS_REQUEST = 1;
    private static final String STAGE_IMAGE_SCALE_TYPE = "CURRENT_SCALE_TYPE";

    private static final String TAG = "WineFragment";

    // Modelo
    private Wine oWine = null;

    // Vistas
    @BindView(R.id.ivWine)
    ImageView ivWine = null;

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
    TextView tvWineNotes = null;

    @BindView(R.id.llGrapesContainer)
    ViewGroup vgWineGrapesContainer = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_wine, container, false);
        ButterKnife.bind(this, rootView);

        if (argumentsContainsKey(AppConstants.ARG_OBJECT_WINE)) {
            getWineObjectFromArguments();
        }

        // Damos valor a las vistas con el modelo
        setViewsFromModel();

        // Actualizar lista de Grapes
        updateGrapesList();

        if (savedInstanceState != null && savedInstanceState.containsKey(STAGE_IMAGE_SCALE_TYPE)) {
            setRadioGroupCheck(savedInstanceState.getString(STAGE_IMAGE_SCALE_TYPE));
        }

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                SettingsFragment settingsFragment = new SettingsFragment();
                Bundle arguments = new Bundle();
                arguments.putSerializable(AppConstants.EXTRA_WINE_IMAGE_SCALE_TYPE, ivWine.getScaleType() == ImageView.ScaleType.FIT_XY ? AppConstants.SCALE_TYPE_FIT_XY : AppConstants.SCALE_TYPE_FIT_CENTER);
                settingsFragment.setArguments(arguments);
                settingsFragment.setTargetFragment(this, SETTINGS_REQUEST);
                settingsFragment.show(getFragmentManager(), null);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SETTINGS_REQUEST:
                    setRadioGroupCheck(data.getStringExtra(AppConstants.EXTRA_WINE_IMAGE_SCALE_TYPE));
                    break;
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STAGE_IMAGE_SCALE_TYPE, ivWine.getScaleType() == ImageView.ScaleType.FIT_XY ? AppConstants.SCALE_TYPE_FIT_XY : AppConstants.SCALE_TYPE_FIT_CENTER);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).contains(PreferencesConstants.PREF_IMAGE_SCALE_TYPE)) {
            setRadioGroupCheck(PreferenceManager.getDefaultSharedPreferences(getActivity()).getString(PreferencesConstants.PREF_IMAGE_SCALE_TYPE, "0"));
        }
    }

    private void getWineObjectFromArguments() {
        oWine = (Wine) getArguments().getSerializable(AppConstants.ARG_OBJECT_WINE);
    }

    @OnClick(R.id.ibtnGoToWeb)
    public void goToDetails(View view) {
        Navigation.getInstance().startActivityForResultWithExtras(getActivity()
                , new Intent().putExtra(AppConstants.EXTRA_OBJECT_WINE, oWine)
                , NavigationUI.Activity.WEB
                , false
                , SETTINGS_REQUEST);
    }

    private boolean argumentsContainsKey(String key) {
        return getArguments().containsKey(key);
    }

    private void setViewsFromModel() {
        ivWine.setImageResource(oWine.getPhoto());
        tvWineName.setText(oWine.getName());
        tvWineType.setText(oWine.getType());
        tvWineOrigin.setText(oWine.getOrigin());
        rbWine.setRating(oWine.getRating());
        tvWineCompany.setText(oWine.getCompanyName());
        tvWineNotes.setText(oWine.getNotes());
        rbWine.setRating(oWine.getRating());
    }

    private void updateGrapesList() {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, oWine.toString());
        }

        for (int i = 0; i < oWine.getGrapeCount(); i++) {
            TextView grapeText = new TextView(getActivity());
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
