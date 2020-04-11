package com.developersbreach.xyzreader.view.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.repository.network.NetworkManager;
import com.developersbreach.xyzreader.utils.SnackbarBuilder;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;


public class SettingsCompatFragment extends PreferenceFragmentCompat {

    private static SettingsViewModel mViewModel;
    private static SettingsFragment mFragmentView;
    private Preference mDeletePreference;

    static SettingsCompatFragment newInstance(SettingsViewModel viewModel, SettingsFragment fragment) {
        mViewModel = viewModel;
        mFragmentView = fragment;
        return new SettingsCompatFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final boolean beforeDelete = mViewModel.checkFavoritesBeforeDelete();
        if (beforeDelete) {
            mDeletePreference.setEnabled(true);
        } else {
            mDeletePreference.setEnabled(false);
            mDeletePreference.getIcon().setAlpha(50);
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        ListPreference themePreference = findPreference("DayNightThemeKey");
        if (themePreference != null) {
            themePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String themeOption = (String) newValue;
                ThemeHelper.applyTheme(themeOption);
                return true;
            });
        }

        Preference refreshPreference = findPreference("RefreshArticlesKey");
        if (refreshPreference != null) {
            refreshPreference.setOnPreferenceClickListener(preference -> {
                showArticleRefreshDialog(getContext());
                return true;
            });
        }

        mDeletePreference = findPreference("DeleteAllFavoritesKey");
        if (mDeletePreference != null) {
            mDeletePreference.setOnPreferenceClickListener(preference -> {
                showArticleDeleteDialog(getContext());
                return true;
            });
        }
    }

    private void showArticleRefreshDialog(Context context) {
        final MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context, R.style.MaterialDialog);
        dialog.setTitle(getString(R.string.refresh_article_dialog_title));
        dialog.setIcon(R.drawable.ic_refresh);
        dialog.setMessage(getString(R.string.refresh_article_dialog_message));
        dialog.setPositiveButton(R.string.refresh_article_dialog_positive_button, new RefreshArticlesButton());
        dialog.setNegativeButton(R.string.refresh_article_dialog_negative_button, new DismissDialog());
        dialog.show();
    }

    private void showArticleDeleteDialog(Context context) {
        final MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context, R.style.MaterialDialog);
        dialog.setTitle(getString(R.string.delete_article_dialog_title));
        dialog.setIcon(R.drawable.ic_delete_all);
        dialog.setMessage(getString(R.string.delete_article_dialog_message));
        dialog.setPositiveButton(getString(R.string.delete_article_dialog_positive_button), new DeleteArticlesButton());
        dialog.setNegativeButton(getString(R.string.delete_article_dialog_negative_button), new DismissDialog());
        dialog.show();
    }

    private class RefreshArticlesButton implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (NetworkManager.checkNetwork(Objects.requireNonNull(getContext()))) {
                mViewModel.refreshData();
                final FragmentActivity activity = Objects.requireNonNull(mFragmentView.getActivity());
                SnackbarBuilder.showSnackBar(getString(R.string.snackbar_refresh_articles_message), activity);
            } else {
                final FragmentActivity activity = Objects.requireNonNull(mFragmentView.getActivity());
                SnackbarBuilder.showSnackBar("No Internet", activity);
            }
        }
    }

    private class DeleteArticlesButton implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            mViewModel.deleteFavoriteArticleData();
            mDeletePreference.setEnabled(false);
            mDeletePreference.getIcon().setAlpha(50);
            final FragmentActivity activity = Objects.requireNonNull(mFragmentView.getActivity());
            SnackbarBuilder.showSnackBar(getString(R.string.snackbar_delete_articles_message), activity);
        }
    }

    private static class DismissDialog implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }
}
