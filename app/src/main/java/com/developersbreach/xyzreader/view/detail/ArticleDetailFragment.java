package com.developersbreach.xyzreader.view.detail;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.bindingAdapter.ArticleDetailBindingAdapter;
import com.developersbreach.xyzreader.databinding.FragmentArticleDetailBinding;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.view.favorite.ArticleFavoritesFragment;
import com.developersbreach.xyzreader.view.list.ArticleListFragment;
import com.developersbreach.xyzreader.view.search.SearchArticleFragment;
import com.developersbreach.xyzreader.viewModel.ArticleDetailViewModel;
import com.developersbreach.xyzreader.viewModel.factory.ArticleDetailViewModelFactory;

import java.util.List;
import java.util.Objects;


/**
 * This fragment is second destination in NavGraph opens when user selects a article from
 * {@link ArticleListFragment}, {@link ArticleFavoritesFragment}, {@link SearchArticleFragment}.
 * <p>
 * This fragment observes data changes from {@link ArticleDetailViewModel} class and it's
 * instance is created by factory {@link ArticleDetailViewModelFactory} class. Also helps
 * to keep this class clean and no operations are handled here. Also helps to preserve data of the
 * fragment without loosing it's data when user navigates between different destinations or changing
 * orientation of the device.
 * <p>
 * This fragment shows details of article data using a {@link ViewPager2} with swipeable items based
 * on position horizontally. Which needs {@link RecyclerView} adapter {@link DetailViewPagerAdapter}
 * class hosts one child at once.
 * <p>
 * Layout and views are bind using {@link DataBindingUtil}.
 *
 * @see ArticleDetailBindingAdapter for all binding values.
 */
public class ArticleDetailFragment extends Fragment {

    // Get binding reference for this fragment.
    private FragmentArticleDetailBinding mBinding;
    // Get this class fragment associated ViewModel.
    private ArticleDetailViewModel mViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Get reference to binding and inflate this class layout.
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_article_detail, container, false);
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment.
        mBinding.setLifecycleOwner(this);
        // Return root binding view.
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a new bundle to receive arguments.
        Bundle args = Objects.requireNonNull(getArguments());
        // Get this activity reference as non-null.
        Activity activity = Objects.requireNonNull(getActivity());
        // Get application context for this class for factory to create instance of ViewModel.
        Application application = activity.getApplication();
        // After passing arguments with name and type of value in NavGraph, it is necessary to
        // perform a gradle build for android studio to auto-generate all required classes and
        // objects for this class using NavigationComponent library.
        // get arguments with name and receive arguments from bundle.
        Article articleArgs = ArticleDetailFragmentArgs.fromBundle(args).getDetailFragmentArgs();
        // Same fragment takes one more SafeArgs argument which gets name of the fragment from
        // which this fragment has been opened, based on different fragment we will change the
        // behaviour of viewPager2.
        String fragmentNameArgs = ArticleDetailFragmentArgs.fromBundle(args).getFragmentNameArgs();
        // Call factory for creating new instance of ViewModel for this fragment to observe data.
        // Pass application context, article and fragment name of type string object to the factory.
        ArticleDetailViewModelFactory factory =
                new ArticleDetailViewModelFactory(application, articleArgs, fragmentNameArgs);
        // Assign and get class ViewModel and pass fragment owner and factory to create instance
        // by calling ViewModelProviders.
        mViewModel = new ViewModelProvider(this, factory).get(ArticleDetailViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // With variable viewModel start observing LiveData to this fragment by calling articles()
        // and observe changes for list of articles and current article data inside viewPager.
        // articles() is externally exposed in ViewModel class to observe changes.
        mViewModel.articles().observe(getViewLifecycleOwner(), this::onArticleChanged);
        // Also call fragmentName() to know which fragment is calling this fragment.
        mViewModel.fragmentName().observe(getViewLifecycleOwner(), this::onFragmentChanged);
    }

    /**
     * @param articleList contains list for articles data inside the viewPager RecyclerView.
     */
    private void onArticleChanged(List<Article> articleList) {
        // Call associated adapter and this adapter won't take any arguments.
        DetailViewPagerAdapter adapter = new DetailViewPagerAdapter();
        // Set adapter to viewPager and submit articles.
        adapter.submitList(articleList);
        mBinding.detailViewPager.setAdapter(adapter);
        // Shows data for single current article selected by user from previous fragment.
        mViewModel.selectedArticle().observe(getViewLifecycleOwner(), this::onArticleChanged);
    }

    /**
     * @param article contains data to be shown for current fragment article.
     */
    private void onArticleChanged(Article article) {
        // Initialize and set position to default position.
        int resetPosition = article.getArticleId() - 1;
        // Directly launch the detail fragment without showing horizontal scroll between articles
        // inside the viewPager.
        // Let the viewPager change it's behaviour to not scroll horizontally between articles.
        mBinding.detailViewPager.setCurrentItem(resetPosition, false);
    }

    /**
     * {@link ArticleListFragment} -> ItemClick -> {@link ArticleDetailFragment} ->
     * Enable viewPager horizontal scrolling between articles.
     * <p>
     * {@link ArticleFavoritesFragment} -> ItemClick -> {@link ArticleDetailFragment} ->
     * Disable viewPager horizontal scrolling between articles.
     * <p>
     * {@link SearchArticleFragment} -> ItemClick -> {@link ArticleDetailFragment} ->
     * Disable viewPager horizontal scrolling between articles.
     * <p>
     * This behaviour can be achieved by simple passing "false" as value to setUserInputEnabled()
     *
     * @param fragmentValue contains boolean value for passing it to below method.
     * @see ViewPager2#setUserInputEnabled(boolean). But we should assign boolean values to these
     * fragments which are trying to trigger this fragment.
     * @see ArticleDetailViewModel for clear appreach on this logic.
     */
    private void onFragmentChanged(Boolean fragmentValue) {
        mBinding.detailViewPager.setUserInputEnabled(fragmentValue);
    }
}
