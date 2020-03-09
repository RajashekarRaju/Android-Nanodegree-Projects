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
import androidx.viewpager2.widget.ViewPager2;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.FragmentArticleDetailBinding;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.viewModel.ArticleDetailViewModel;
import com.developersbreach.xyzreader.viewModel.factory.ArticleDetailViewModelFactory;

import java.util.Objects;

public class ArticleDetailFragment extends Fragment {

    private ViewPager2 mDetailViewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        FragmentArticleDetailBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_article_detail, container, false);
        mDetailViewPager = binding.detailViewPager;

        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

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

        ArticleDetailViewModel viewModel = new ViewModelProvider(this, factory).get(ArticleDetailViewModel.class);
        viewModel.getMutableArticleList().observe(getViewLifecycleOwner(), articleList -> {
            DetailViewPagerAdapter adapter = new DetailViewPagerAdapter();
            adapter.submitList(articleList);
            mDetailViewPager.setAdapter(adapter);
            viewModel.selectedArticle().observe(getViewLifecycleOwner(), article ->
                    mDetailViewPager.setCurrentItem(article.getArticleId() - 1, false));
        });

        viewModel.getFragmentValue().observe(getViewLifecycleOwner(), fragmentValue ->
                mDetailViewPager.setUserInputEnabled(fragmentValue));
    }

}
