package com.developersbreach.xyzreader.view.favorite;

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
import com.developersbreach.xyzreader.databinding.FragmentArticleFavoritesBinding;
import com.developersbreach.xyzreader.utils.SpaceItemDecoration;
import com.developersbreach.xyzreader.viewModel.ArticleFavoritesViewModel;
import com.developersbreach.xyzreader.viewModel.factory.FavoriteViewModelFactory;

import java.util.Objects;


public class ArticleFavoritesFragment extends Fragment {

    private FragmentArticleFavoritesBinding mBinding;
    private ArticleFavoritesViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_article_favorites, container, false);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing_dimen);
        mBinding.favoritesRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        setFragmentToolbar();
        return mBinding.getRoot();
    }

    private void setFragmentToolbar() {
        mBinding.toolbarContentFavoritesHeader.headerAppTitle.setText(R.string.favorites_title);
        mBinding.toolbarContentFavoritesHeader.headerThemeSwitcherImageView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = Objects.requireNonNull(getActivity());
        Application application = activity.getApplication();

        FavoriteViewModelFactory factory = new FavoriteViewModelFactory(application);
        mViewModel = new ViewModelProvider(this, factory).get(ArticleFavoritesViewModel.class);

        mViewModel.getFavoriteList().observe(getViewLifecycleOwner(), favoriteList -> {
            FavoriteAdapter adapter = new FavoriteAdapter(mViewModel, this);
            adapter.submitList(favoriteList);
            mBinding.favoritesRecyclerView.setAdapter(adapter);
            if (favoriteList.size() == 0) {
                mBinding.favoritesRecyclerView.setVisibility(View.INVISIBLE);
                mBinding.noFavoritesFoundText.setVisibility(View.VISIBLE);
            } else {
                mBinding.favoritesRecyclerView.setVisibility(View.VISIBLE);
                mBinding.noFavoritesFoundText.setVisibility(View.INVISIBLE);
            }
        });
    }
}
