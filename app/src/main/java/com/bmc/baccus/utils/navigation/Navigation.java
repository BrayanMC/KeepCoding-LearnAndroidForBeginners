package com.bmc.baccus.utils.navigation;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Navigation {

    private static Navigation instance;

    private synchronized static void createInstance() {
        if (instance == null) {
            instance = new Navigation();
        }
    }

    public static Navigation getInstance() {
        if (instance == null) createInstance();
        return instance;
    }

    public void startActivity(Context context, String activity, boolean destroy) {
        try {
            Intent intent = new Intent();
            intent.setClassName(context, activity);
            context.startActivity(intent);
            if (destroy) {
                ((Activity) (context)).finish();
            }
        } catch (ActivityNotFoundException e) {
            Log.e("", e.getMessage());
        }
    }

    public void startActivityWithExtras(Context context, Intent extras, String activity, boolean destroy) {
        try {
            Intent intent = new Intent();
            intent.putExtras(extras);
            intent.setClassName(context, activity);
            context.startActivity(intent);
            if (destroy) {
                ((Activity) (context)).finish();
            }
        } catch (ActivityNotFoundException e) {
            Log.e("", e.getMessage());
        }
    }

    public void startActivityForResult(Context context, String activity, boolean destroy, int requestCode) {
        try {
            Intent intent = new Intent();
            intent.setClassName(context, activity);
            ((Activity) (context)).startActivityForResult(intent, requestCode);
            if (destroy) {
                ((Activity) (context)).finish();
            }
        } catch (ActivityNotFoundException e) {
            Log.e("", e.getMessage());
        }
    }

    public void startActivityForResultWithExtras(Context context, Intent extras, String activity, boolean destroy, int requestCode) {
        try {
            Intent intent = new Intent();
            intent.putExtras(extras);
            intent.setClassName(context, activity);
            ((Activity) (context)).startActivityForResult(intent, requestCode);
            if (destroy) {
                ((Activity) (context)).finish();
            }
        } catch (ActivityNotFoundException e) {
            Log.e("", e.getMessage());
        }
    }

    public void setResult(int resultCode, Context context, Intent extras, String activity, boolean destroy) {
        try {
            Intent intent = new Intent();
            intent.putExtras(extras);
            intent.setClassName(context, activity);
            ((Activity) (context)).setResult(resultCode, intent);
            if (destroy) {
                ((Activity) (context)).finish();
            }
        } catch (ActivityNotFoundException e) {
            Log.e("", e.getMessage());
        }
    }
}