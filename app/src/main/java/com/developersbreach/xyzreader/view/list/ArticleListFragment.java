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


public class ArticleListFragment extends Fragment {

    private ArticleListViewModel mViewModel;
    private FragmentArticleListBinding mBinding;
    private ThemePreferencesManager mPreferenceManager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_article_list, container, false);

        setToolbar(getContext(), mBinding.toolbarContentArticlesHeader.headerThemeSwitcherImageView);
        RecyclerViewItemDecoration.setItemSpacing(getResources(), mBinding.articlesRecyclerView);
        mBinding.setLifecycleOwner(this);
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ArticleListViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.articles().observe(getViewLifecycleOwner(), this::onArticlesChanged);
    }

    private void onArticlesChanged(List<Article> articleList) {
        ArticleAdapter adapter = new ArticleAdapter(mViewModel, this);
        adapter.submitList(articleList);
        mBinding.articlesRecyclerView.setAdapter(adapter);
    }

    private void setToolbar(Context context, ImageView themeSwitcherImageView) {
        mPreferenceManager = new ThemePreferencesManager(context);
        mPreferenceManager.applyDayNightTheme();
        themeSwitcherImageView.setOnClickListener(view ->
                mPreferenceManager.showThemeSwitcherPopUp(themeSwitcherImageView));
    }

    @Override
    public void onResume() {
        super.onResume();
        ArticleAnimations.startLinearAnimation(mBinding.articlesRecyclerView);
    }
}
