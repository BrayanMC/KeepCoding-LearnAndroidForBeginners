package com.bmc.baccus.utils;

public class AppConstants {

    public static final String ARG_OBJECT_WINE = "OBJECT_WINE";
    public static final String EXTRA_OBJECT_WINE = "OBJECT_WINE";
    public static final String ARG_WINE_IMAGE_SCALE_TYPE = "WINE_IMAGE_SCALE_TYPE";
    public static final String EXTRA_WINE_IMAGE_SCALE_TYPE = "WINE_IMAGE_SCALE_TYPE";
    public static final String ARG_WINE_INDEX = "WINE_INDEX";
    public static final String EXTRA_WINE_INDEX = "WINE_INDEX";

    public static final String SCALE_TYPE_FIT_XY = "0";
    public static final String SCALE_TYPE_FIT_CENTER = "1";

    public static final int ASYNCTASK_TIMEOUT = 30000;

    public enum ScaleType {
        SCALE_TYPE_FIT_XY(0),
        SCALE_TYPE_FIT_CENTER(1);

        private final int mType;

        ScaleType(int type) {
            mType = type;
        }

        public int getType() {
            return mType;
        }
    }
}
