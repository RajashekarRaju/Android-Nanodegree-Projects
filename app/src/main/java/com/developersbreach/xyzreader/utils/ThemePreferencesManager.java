package com.developersbreach.xyzreader.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseIntArray;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.PopupMenu;

import com.developersbreach.xyzreader.R;

public class ThemePreferencesManager {

    private Context mContext;

    private static final String PREFERENCES_NAME = "night_mode_preferences";
    private static final String KEY_NIGHT_MODE = "night_mode";
    private static final SparseIntArray THEME_NIGHT_MODE_MAP = new SparseIntArray();

    static {
        THEME_NIGHT_MODE_MAP.append(R.id.theme_light, AppCompatDelegate.MODE_NIGHT_NO);
        THEME_NIGHT_MODE_MAP.append(R.id.theme_dark, AppCompatDelegate.MODE_NIGHT_YES);
        THEME_NIGHT_MODE_MAP.append(R.id.theme_default, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public ThemePreferencesManager(Context context) {
        this.mContext = context;
    }

    @SuppressWarnings("SameReturnValue")
    public void showThemeSwitcherPopUp(View view) {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.inflate(R.menu.theme_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            saveAndApplyTheme(item.getItemId());
            return false;
        });
        popupMenu.show();
    }

    private void saveAndApplyTheme(int itemId) {
        int nightMode = convertToNightMode(itemId);
        saveNightMode(nightMode);
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }

    private int convertToNightMode(@IdRes int id) {
        return THEME_NIGHT_MODE_MAP.get(id, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    private void saveNightMode(int nightMode) {
        getSharedPreferences().edit().putInt(KEY_NIGHT_MODE, nightMode).apply();
    }

    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void applyDayNightTheme() {
        AppCompatDelegate.setDefaultNightMode(getNightMode());
    }

    private int getNightMode() {
        return getSharedPreferences().getInt(KEY_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}
