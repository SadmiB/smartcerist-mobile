package com.smartcerist.mobile.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by s on 14/05/17.
 */

public class UserPreferenceManager {

    private Context context;

    public UserPreferenceManager(Context context) {
        this.context = context;
    }

    public boolean saveConnectedUser(String email, String token){

        SharedPreferences pref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token);
        editor.putString("email", email);

        return editor.commit();
    }

    public String isConnected(){
        SharedPreferences pref = context.getSharedPreferences("email", Context.MODE_PRIVATE);
        return pref.getString("token",null);
    }

    public String getConnectedUser(){
        SharedPreferences pref = context.getSharedPreferences("email", Context.MODE_PRIVATE);
        return pref.getString("email",null);
    }

    public boolean disconnectUser()
    {
        SharedPreferences pref = context.getSharedPreferences("email", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", null);
        return editor.commit();
    }
}
