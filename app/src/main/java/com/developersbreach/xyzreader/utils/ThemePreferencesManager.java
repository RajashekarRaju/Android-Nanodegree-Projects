package com.developersbreach.xyzreader.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.PopupMenu;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.view.list.ArticleListFragment;


/**
 * This class and methods are taken as reference from android example app Material Components Catalog.
 * <p>
 * Class which builds logic for changing app Day/Night theme using PopUpMenu.
 *
 * @see ArticleListFragment#setToolbar(Context, ImageView) how this class method is used to switch
 * the themes using menu in the toolBar.
 */
public class ThemePreferencesManager {

    // Get access to resources to this class.
    private Context mContext;
    // For saving the theme, by default we launch theme with night mode.
    private static final String PREFERENCES_NAME = "night_mode_preferences";
    // Key for night mode preference.
    private static final String KEY_NIGHT_MODE = "night_mode";
    // Creates array of integers for each mode using id.
    private static final SparseIntArray THEME_NIGHT_MODE_MAP = new SparseIntArray();

    static {
        // To launch light theme which takes id and type of mode.
        THEME_NIGHT_MODE_MAP.append(R.id.theme_light, AppCompatDelegate.MODE_NIGHT_NO);
        // To launch dark theme which takes id and type of mode.
        THEME_NIGHT_MODE_MAP.append(R.id.theme_dark, AppCompatDelegate.MODE_NIGHT_YES);
        // To launch theme based on system selected mode which takes id and type of mode.
        THEME_NIGHT_MODE_MAP.append(R.id.theme_default, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    public ThemePreferencesManager(Context context) {
        this.mContext = context;
    }

    /**
     * @param view requires with view to create {@link PopupMenu} with {@link Context}
     */
    @SuppressWarnings("SameReturnValue")
    public void showThemeSwitcherPopUp(View view) {
        // Create a new popUp menu.
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        // Inflate the menu from menu resources.
        popupMenu.inflate(R.menu.theme_menu);
        // Set listener for menu items and apply theme of selected.
        popupMenu.setOnMenuItemClickListener(item -> {
            // This saves the preference value for selected menu item with id.
            saveAndApplyTheme(item.getItemId());
            return false;
        });
        // Show the menu.
        popupMenu.show();
    }

    /**
     * @param itemId menu itemId selected by the user.
     */
    private void saveAndApplyTheme(int itemId) {
        int nightMode = convertToNightMode(itemId);
        saveNightMode(nightMode);
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }

    /**
     * @param id selected menu item id.
     * @return returns theme as night mode with id and type.
     */
    private int convertToNightMode(@IdRes int id) {
        return THEME_NIGHT_MODE_MAP.get(id, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }

    /**
     * @param nightMode save key preference for night mode using sharedPreferences.
     */
    private void saveNightMode(int nightMode) {
        getSharedPreferences().edit().putInt(KEY_NIGHT_MODE, nightMode).apply();
    }

    /**
     * @return returns preference with name and changes it value whenever mode changed by user.
     */
    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Set the initial theme to night mode calling method with it's key and system mode.
     */
    public void applyDayNightTheme() {
        AppCompatDelegate.setDefaultNightMode(getNightMode());
    }

    /**
     * @return return preference night mode on with id also determine whether system is night or not.
     */
    private int getNightMode() {
        return getSharedPreferences().getInt(KEY_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}
