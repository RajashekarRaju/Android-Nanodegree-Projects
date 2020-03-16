package com.developersbreach.xyzreader.view.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.FragmentArticleListBinding;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.utils.ArticleAnimations;
import com.developersbreach.xyzreader.utils.RecyclerViewItemDecoration;
import com.developersbreach.xyzreader.utils.ThemePreferencesManager;
import com.developersbreach.xyzreader.viewModel.ArticleListViewModel;

import java.util.List;


/**
 * First destination in default NavGraph, also first menu item in BottomNavigationView in activity.
 * <p>
 * This fragment observes data changes from {@link ArticleListViewModel} class. Also helps
 * to keep this class clean and no operations are handled here. Helps to preserve data of the
 * fragment data without loosing it's state when user navigates between different destinations or
 * changing orientation of the device.
 * <p>
 * Utilises adapter class {@link ArticleAdapter} to show a {@link List} of type {@link Article}.
 * <p>
 * Layout and views are bind using {@link DataBindingUtil}
 */
public class ArticleListFragment extends Fragment {

    // Get this class fragment associated ViewModel.
    private ArticleListViewModel mViewModel;
    // Get binding reference for this fragment.
    private FragmentArticleListBinding mBinding;
    // To manage day/night theme preference in the fragment.
    private ThemePreferencesManager mPreferenceManager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Get reference to binding and inflate this class layout.
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_article_list, container, false);
        // Set this class toolbar.
        setToolbar(getContext(), mBinding.toolbarContentArticlesHeader.headerThemeSwitcherImageView);
        // Set item decoration for items inside recycler view.
        RecyclerViewItemDecoration.setItemSpacing(getResources(), mBinding.articlesRecyclerView);
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment.
        mBinding.setLifecycleOwner(this);
        // Return root binding view.
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize this class ViewModel inside onCreate.
        // Get reference to ArticleListViewModel class with ViewModelProvider by class owner.
        mViewModel = new ViewModelProvider(this).get(ArticleListViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // With variable viewModel start observing LiveData to this fragment by calling articles()
        // and observer changes for list of article data.
        // articles() is externally exposed in ViewModel class to observe changes.
        mViewModel.articles().observe(getViewLifecycleOwner(), this::onArticlesChanged);
    }

    /**
     * @param articleList contains list for articles data.
     */
    private void onArticlesChanged(List<Article> articleList) {
        // Call associated adapter and pass required args.
        // For second argument, we pass this class as reference to get into DetailsFragment for
        // custom behaviour od viewPager.
        ArticleAdapter adapter = new ArticleAdapter(mViewModel, this);
        // Pass list to adapter calling submitList since our adapter class extends to ListAdapter<>.
        adapter.submitList(articleList);
        // Set adapter with recyclerView.
        mBinding.articlesRecyclerView.setAdapter(adapter);
    }

    /**
     * @param context                to pass context for preferenceManager.
     * @param themeSwitcherImageView view which implements click listener to show popUp menu for
     *                               selecting theme. By default we set theme to use dark mode.
     * @see ThemePreferencesManager#showThemeSwitcherPopUp(View)
     */
    public void setToolbar(Context context, ImageView themeSwitcherImageView) {
        mPreferenceManager = new ThemePreferencesManager(context);
        mPreferenceManager.applyDayNightTheme();
        themeSwitcherImageView.setOnClickListener(view ->
                mPreferenceManager.showThemeSwitcherPopUp(themeSwitcherImageView));
    }

    /**
     * Call animation from class {@link ArticleAnimations} and pass view which needs to animate.
     * In our case we animate items of cardView inside RecyclerView.
     */
    @Override
    public void onResume() {
        super.onResume();
        ArticleAnimations.startLinearAnimation(mBinding.articlesRecyclerView);
    }
}
