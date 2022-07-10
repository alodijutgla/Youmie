package com.example.youmie.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPrefUtils {

    public static String KEY_USERNAME = "username";
    public static String KEY_PASSWORD = "password";

    public static boolean saveUsername(String username, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = preferences.edit();
        prefEditor.putString(KEY_USERNAME, username);
        prefEditor.apply();
        return true;
    }

    public static String getUsername(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(KEY_USERNAME, null);
    }

    public static boolean savePassword(String password, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefEditor = preferences.edit();
        prefEditor.putString(KEY_PASSWORD, password);
        prefEditor.apply();
        return true;
    }

    public static String getPassword(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(KEY_PASSWORD, null);
    }
}
