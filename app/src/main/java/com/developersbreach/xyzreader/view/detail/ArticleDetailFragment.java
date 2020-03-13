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

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.FragmentArticleDetailBinding;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.viewModel.ArticleDetailViewModel;
import com.developersbreach.xyzreader.viewModel.factory.ArticleDetailViewModelFactory;

import java.util.List;
import java.util.Objects;

public class ArticleDetailFragment extends Fragment {

    private FragmentArticleDetailBinding mBinding;
    private ArticleDetailViewModel mViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_article_detail, container, false);
        mBinding.setLifecycleOwner(this);
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        String fragmentNameArgs = ArticleDetailFragmentArgs.fromBundle(args).getFragmentNameArgs();
        // Call factory for creating new instance of ViewModel for this fragment to observe data.
        // Pass application context and recipe object to the factory.
        ArticleDetailViewModelFactory factory =
                new ArticleDetailViewModelFactory(application, articleArgs, fragmentNameArgs);
        // Assign and get class ViewModel and pass fragment owner and factory to create instance
        // by calling ViewModelProviders.
        mViewModel = new ViewModelProvider(this, factory).get(ArticleDetailViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.articles().observe(getViewLifecycleOwner(), this::onListChanged);
        mViewModel.fragmentName().observe(getViewLifecycleOwner(), this::onFragmentChanged);
    }

    private void onListChanged(List<Article> articleList) {
        DetailViewPagerAdapter adapter = new DetailViewPagerAdapter();
        adapter.submitList(articleList);
        mBinding.detailViewPager.setAdapter(adapter);
        mViewModel.selectedArticle().observe(getViewLifecycleOwner(), this::onArticleChanged);
    }

    private void onArticleChanged(Article article) {
        int resetPosition = article.getArticleId() - 1;
        mBinding.detailViewPager.setCurrentItem(resetPosition, false);
    }

    private void onFragmentChanged(Boolean fragmentValue) {
        mBinding.detailViewPager.setUserInputEnabled(fragmentValue);
    }
}
