package com.developersbreach.xyzreader.view.favorite;

import android.app.Activity;
import android.app.Application;
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
import com.developersbreach.xyzreader.databinding.FragmentArticleFavoritesBinding;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.utils.ArticleAnimations;
import com.developersbreach.xyzreader.utils.RecyclerViewItemDecoration;
import com.developersbreach.xyzreader.utils.ThemePreferencesManager;
import com.developersbreach.xyzreader.viewModel.ArticleFavoritesViewModel;
import com.developersbreach.xyzreader.viewModel.factory.FavoriteViewModelFactory;
import com.google.android.material.textview.MaterialTextView;

import java.util.List;
import java.util.Objects;


public class ArticleFavoritesFragment extends Fragment {

    private FragmentArticleFavoritesBinding mBinding;
    private ArticleFavoritesViewModel mViewModel;
    private ThemePreferencesManager mPreferenceManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_article_favorites, container, false);

        setToolbar(getContext(), mBinding.toolbarContentFavoritesHeader.headerAppTitle,
                mBinding.toolbarContentFavoritesHeader.headerThemeSwitcherImageView);
        RecyclerViewItemDecoration.setItemSpacing(getResources(), mBinding.favoritesRecyclerView);
        mBinding.setLifecycleOwner(this);
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = Objects.requireNonNull(getActivity());
        Application application = activity.getApplication();
        FavoriteViewModelFactory factory = new FavoriteViewModelFactory(application);
        mViewModel = new ViewModelProvider(this, factory).get(ArticleFavoritesViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.favorites().observe(getViewLifecycleOwner(), this::onFavoritesChanged);
    }

    private void onFavoritesChanged(List<FavoriteEntity> favoriteList) {
        FavoriteAdapter adapter = new FavoriteAdapter(mViewModel, this);
        adapter.submitList(favoriteList);
        mBinding.favoritesRecyclerView.setAdapter(adapter);
        toggleRecyclerView(favoriteList);
    }

    private void setToolbar(Context context, MaterialTextView title, ImageView themeSwitcherImageView) {
        title.setText(R.string.favorites_title);
        mPreferenceManager = new ThemePreferencesManager(context);
        mPreferenceManager.applyDayNightTheme();
        themeSwitcherImageView.setOnClickListener(view ->
                mPreferenceManager.showThemeSwitcherPopUp(themeSwitcherImageView));
    }

    private void toggleRecyclerView(List<FavoriteEntity> favoriteList) {
        if (favoriteList.size() == 0) {
            mBinding.favoritesRecyclerView.setVisibility(View.INVISIBLE);
            mBinding.noFavoritesFoundText.setVisibility(View.VISIBLE);
        } else {
            mBinding.favoritesRecyclerView.setVisibility(View.VISIBLE);
            mBinding.noFavoritesFoundText.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ArticleAnimations.startLinearAnimation(mBinding.favoritesRecyclerView);
    }
}
