package com.bmc.baccus.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioGroup;

import com.bmc.baccus.R;
import com.bmc.baccus.util.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends Activity {

    // Vistas
    @BindView(R.id.rgScaleType)
    RadioGroup rgScaleType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        if (intentHasExtra(AppConstants.EXTRA_WINE_IMAGE_SCALE_TYPE)) {
            setRadioGroupCheck(getIntent().getStringExtra(AppConstants.EXTRA_WINE_IMAGE_SCALE_TYPE));
        }
    }

    @OnClick(R.id.btnCancel)
    public void cancelConfiguration(View view) {
        cancelSettings();
    }

    @OnClick(R.id.btnSave)
    public void saveConfiguration(View view) {
        saveSettings();
    }

    private void setRadioGroupCheck(String scaleType) {
        if (scaleType.equals(AppConstants.SCALE_TYPE_FIT_XY)) {
            rgScaleType.check(R.id.rbFitScale);
        } else if (scaleType.equals(AppConstants.SCALE_TYPE_FIT_CENTER)) {
            rgScaleType.check(R.id.rbCenterScale);
        }
    }

    private void cancelSettings() {
        setResult(RESULT_CANCELED);
        backToWineActivity();
    }

    private void setExtraWithScaleType(AppConstants.ScaleType type) {
        setResult(RESULT_OK, new Intent(this, WineActivity.class).putExtra(AppConstants.EXTRA_WINE_IMAGE_SCALE_TYPE, type.getType() == 0 ? AppConstants.SCALE_TYPE_FIT_XY : AppConstants.SCALE_TYPE_FIT_CENTER));
        backToWineActivity();
    }

    private void saveSettings() {
        switch (rgScaleType.getCheckedRadioButtonId()) {
            case R.id.rbFitScale:
                setExtraWithScaleType(AppConstants.ScaleType.SCALE_TYPE_FIT_XY);
                break;

            case R.id.rbCenterScale:
                setExtraWithScaleType(AppConstants.ScaleType.SCALE_TYPE_FIT_CENTER);
                break;
        }
    }

    private void backToWineActivity() {
        finish();
    }

    private boolean intentHasExtra(String key) {
        return getIntent().hasExtra(key);
    }
}
