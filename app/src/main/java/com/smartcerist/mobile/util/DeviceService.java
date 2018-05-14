package com.smartcerist.mobile.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;

/**
 * Created by s on 28/05/17.
 */

public class DeviceService {
    private Context context;

    public DeviceService(Context context) {
        this.context = context;
    }

    // Get the screen densitiy from device configuration
    public String getScreenDensityConfiguration() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        String density ="";
        switch(metrics.densityDpi){
            case DisplayMetrics.DENSITY_LOW:
                density="ldpi";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                density= "mdpi";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                density="hdpi";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                density= "xhdpi";
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                density= "xxhdpi";
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                density= "xxxhdpi";
                break;
        }
        saveScreenDensity(density);
        return density;
    }
    // store the screen denstiy
    public boolean saveScreenDensity(String density) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("screen",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("density",density);
        return  editor.commit();
    }
    // get the stored screen density
    public String getScreenDensity(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("screen",Context.MODE_PRIVATE);
        return sharedPreferences.getString("density",getScreenDensityConfiguration());
    }


    public boolean isScreenDensitySaved(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("screen",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("screen",false);
    }
}
