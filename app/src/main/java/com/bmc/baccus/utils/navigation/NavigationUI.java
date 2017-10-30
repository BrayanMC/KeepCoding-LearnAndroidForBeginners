package com.bmc.baccus.utils.navigation;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.bmc.baccus.utils.navigation.NavigationUI.Activity.SETTINGS;
import static com.bmc.baccus.utils.navigation.NavigationUI.Activity.WEB;
import static com.bmc.baccus.utils.navigation.NavigationUI.Activity.WINE;

public class NavigationUI {

    @StringDef({WINE, SETTINGS, WEB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Activity {
        String WINE = "com.bmc.baccus.controller.activities.WineActivity";
        String SETTINGS = "com.bmc.baccus.controller.activities.SettingsActivity";
        String WEB = "com.bmc.baccus.controller.activities.WebActivity";
    }
}