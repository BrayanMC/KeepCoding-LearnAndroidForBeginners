package com.bmc.baccus.controller.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.bmc.baccus.R;
import com.bmc.baccus.utils.AppConstants;
import com.bmc.baccus.utils.PreferencesConstants;
import com.bmc.baccus.utils.navigation.Navigation;
import com.bmc.baccus.utils.navigation.NavigationUI;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsFragment extends DialogFragment {

    // Vistas
    @BindView(R.id.rgScaleType)
    RadioGroup rgScaleType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragmnet_settings, container, false);
        ButterKnife.bind(this, rootView);

        if (argumentsContainsKey(AppConstants.ARG_WINE_IMAGE_SCALE_TYPE)) {
            setRadioGroupCheck(getArguments().getString(AppConstants.ARG_WINE_IMAGE_SCALE_TYPE));
        }

        return rootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(R.string.settings);

        return dialog;
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
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
            dismiss();
        } else {
            getActivity().setResult(Activity.RESULT_CANCELED);
            getActivity().finish();
        }
    }

    private void saveArgumentsWithScaleType(AppConstants.ScaleType type) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        editor.putString(PreferencesConstants.PREF_IMAGE_SCALE_TYPE, type.getType() == 0 ? AppConstants.SCALE_TYPE_FIT_XY : AppConstants.SCALE_TYPE_FIT_CENTER);
        editor.apply();

        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent().putExtra(AppConstants.EXTRA_WINE_IMAGE_SCALE_TYPE, type.getType() == 0 ? AppConstants.SCALE_TYPE_FIT_XY : AppConstants.SCALE_TYPE_FIT_CENTER));
            dismiss();
        } else {
            Navigation.getInstance().setResult(Activity.RESULT_OK
                    , getActivity()
                    , new Intent().putExtra(AppConstants.EXTRA_WINE_IMAGE_SCALE_TYPE, type.getType() == 0 ? AppConstants.SCALE_TYPE_FIT_XY : AppConstants.SCALE_TYPE_FIT_CENTER)
                    , NavigationUI.Activity.WINE
                    , true);
        }
    }

    private void saveSettings() {
        switch (rgScaleType.getCheckedRadioButtonId()) {
            case R.id.rbFitScale:
                saveArgumentsWithScaleType(AppConstants.ScaleType.SCALE_TYPE_FIT_XY);
                break;

            case R.id.rbCenterScale:
                saveArgumentsWithScaleType(AppConstants.ScaleType.SCALE_TYPE_FIT_CENTER);
                break;
        }
    }

    private boolean argumentsContainsKey(String key) {
        return getArguments().containsKey(key);
    }
}
