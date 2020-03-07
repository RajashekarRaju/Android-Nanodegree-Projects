package com.developersbreach.xyzreader.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.ActivityMainBinding;
import com.developersbreach.xyzreader.utils.ThemePreferencesManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private NavController mNavigationController;
    private ThemePreferencesManager mPreferenceManager;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mNavigationController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(new NavigationListener());
        mPreferenceManager = new ThemePreferencesManager(this);
        mPreferenceManager.applyTheme();

        mNavigationController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.articleDetailFragment) {
                mBinding.bottomNavigation.setVisibility(View.INVISIBLE);
            } else {
                mBinding.bottomNavigation.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return mNavigationController.navigateUp();
    }

    private class NavigationListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        /**
         * Called when an item in the bottom navigation menu is selected.
         *
         * @param item The selected item
         * @return true to display the item as the selected item and false if the item should not be
         * selected. Consider setting non-selectable items as disabled preemptively to make them
         * appear non-interactive.
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.articleFavoritesFragment:

                case R.id.searchArticleFragment:
                    NavigationUI.onNavDestinationSelected(item, mNavigationController);
                    break;

                case R.id.theme_bottom_menu_item:
                    mPreferenceManager.showThemePopUp(mBinding.bottomNavigation);
                    break;
            }
            return true;
        }
    }
}
