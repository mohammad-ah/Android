package com.mhassan.expenses.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    public static final String EXPENSES_PREFERENCES = "ExpensesPreferences";

    public static final String EXPENSES = "expenses";

    public static void save(Context context, String key, String val) {
        SharedPreferences prefs = context.getSharedPreferences(EXPENSES_PREFERENCES, Context.MODE_PRIVATE);
        prefs.edit().putString(key, val).apply();
    }

    public static String get(Context context, String key, String defaultVal) {
        SharedPreferences prefs = context.getSharedPreferences(EXPENSES_PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getString(key, defaultVal);
    }


    public static void save(Context context, String key, int val) {
        SharedPreferences prefs = context.getSharedPreferences(EXPENSES_PREFERENCES, Context.MODE_PRIVATE);
        prefs.edit().putInt(key, val).apply();
    }

    public static int get(Context context, String key, int defaultVal) {
        SharedPreferences prefs = context.getSharedPreferences(EXPENSES_PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getInt(key, defaultVal);
    }

    public static void save(Context context, String key, boolean val) {
        SharedPreferences prefs = context.getSharedPreferences(EXPENSES_PREFERENCES, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(key, val).apply();
    }

    public static boolean get(Context context, String key, boolean defaultVal) {
        SharedPreferences prefs = context.getSharedPreferences(EXPENSES_PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getBoolean(key, defaultVal);
    }

    public static void save(Context context, String key, long val) {
        SharedPreferences prefs = context.getSharedPreferences(EXPENSES_PREFERENCES, Context.MODE_PRIVATE);
        prefs.edit().putLong(key, val).apply();
    }

    public static long get(Context context, String key, long defaultVal) {
        SharedPreferences prefs = context.getSharedPreferences(EXPENSES_PREFERENCES, Context.MODE_PRIVATE);
        return prefs.getLong(key, defaultVal);
    }

    public static void deleteSavedData(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(EXPENSES_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(EXPENSES);
        editor.apply();
    }

    public static void cleanSP(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(EXPENSES_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
}
