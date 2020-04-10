package com.developersbreach.xyzreader.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.ActivityMainBinding;
import com.developersbreach.xyzreader.utils.ArticleAnimations;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * This app uses {@link Navigation} library to show all destinations(fragments) which is build with
 * single activity and multiple fragments. This is made easy using NavigationComponent.
 * This activity hosts NavHostFragment and NavController has control over all app fragments.
 * <p>
 * When app launches, MainActivity is being called which then inflates layout using DataBinding
 * {@link R.layout#activity_main} which hosts fragment and uses graph to determine all destinations.
 *
 * @see R.navigation#navigation_graph, has virtual graph with actions and destinations.
 */
public class MainActivity extends AppCompatActivity {

    // NavController manages app navigation within a NavHost.
    private NavController mNavigationController;
    // Inflate activity layout using DataBinding library.
    private ActivityMainBinding mBinding;
    public static final String LOTTIE_PREFERENCE_KEY = "XYZ_NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set layout with DataBindingUtil.
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // NavigationController to set default NavHost as nav_host_fragment.
        mNavigationController = Navigation.findNavController(this, R.id.nav_host_fragment);

        launchFragmentWithPreference();

        // Set BottomNavigationView and attach a item selected navigation listener.
        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener(new NavigationListener());
        // Change behaviour of destination view or content based on type of destination is user at.
        mNavigationController.addOnDestinationChangedListener(this::onDestinationChanged);
    }

    private void launchFragmentWithPreference() {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        final boolean preferencesBoolean = preferences.getBoolean(LOTTIE_PREFERENCE_KEY, false);
        if (preferencesBoolean) {
            mNavigationController.navigate(R.id.articleListFragment);
        } else {
            mNavigationController.navigate(R.id.XYZReaderFragment);
        }
    }

    /**
     * @param controller  to manage navigation.
     * @param destination gets the destination type using fragment id.
     * @param arguments   to pass any arguments to those destinations, in our case we are not doing.
     */
    private void onDestinationChanged(NavController controller, NavDestination destination, Bundle arguments) {
        // Check for destination ArticleDetailFragment, if true hide the navigation view.
        if (destination.getId() == R.id.articleDetailFragment) {
            mBinding.bottomNavigationView.setVisibility(View.INVISIBLE);
        } else if (destination.getId() == R.id.XYZReaderFragment) {
            mBinding.bottomNavigationView.setVisibility(View.GONE);
        } else {
            // If not valid destination, don;t hide view.
            mBinding.bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Called when an item in the bottom navigation menu is selected.
     */
    private class NavigationListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        /**
         * @param item The selected item, each item in our case opens a new fragment.
         * @return true to display the item as the selected item and false if the item should not be
         * selected. Consider setting non-selectable items as disabled preemptively to make them
         * appear non-interactive.
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.articleListFragment:
                case R.id.searchArticleFragment:
                case R.id.articleFavoritesFragment:
                case R.id.settingsFragment:
                    // Call selected destination on object NavigationUI with selected item
                    // and controller to manage the navigation.
                    NavigationUI.onNavDestinationSelected(item, mNavigationController);
                    // Start a selected fragment with simple reveal animations every-time.
                    ArticleAnimations.startBottomCircularEffect(mBinding.mainRootView);
                    break;
            }
            return true;
        }
    }

    // Set up-button for app, triggers when user clicks up button.
    @Override
    public boolean onSupportNavigateUp() {
        return mNavigationController.navigateUp();
    }
}
