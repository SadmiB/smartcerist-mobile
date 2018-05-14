package com.smartcerist.mobile.app;


import android.app.Application;

import com.smartcerist.mobile.R;
import com.smartcerist.mobile.util.FontsOverride;


/**
 * Created by s on 07/02/18.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "DEFAULT",
                "fonts/Dosis-Regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE",
                "fonts/Dosis-Regular.ttf");
        FontsOverride.setDefaultFont(this, "SERIF",
                "fonts/Dosis-Regular.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF",
                "fonts/Dosis-Regular.ttf");
    }
}
