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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.FragmentArticleFavoritesBinding;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.repository.database.FavoriteEntity;
import com.developersbreach.xyzreader.utils.SpaceItemDecoration;
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

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.rv_dimen);
        mBinding.favoritesRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        setHomeNav();

        return mBinding.getRoot();
    }

    private void setHomeNav() {
        mBinding.favoritesHeaderIncluded.headerTitle.setText(R.string.favorites_title);
        mBinding.favoritesHeaderIncluded.favoritesHomeButtonImageView.setVisibility(View.VISIBLE);
        mBinding.favoritesHeaderIncluded.favoritesHomeButtonImageView.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.articleListFragment));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = Objects.requireNonNull(getActivity());
        Application application = activity.getApplication();
        FavoriteViewModelFactory factory = new FavoriteViewModelFactory(application);
        mViewModel = new ViewModelProvider(this, factory).get(ArticleFavoritesViewModel.class);
        mViewModel.getFavoriteList().observe(getViewLifecycleOwner(), favoriteEntities -> {
            FavoriteAdapter adapter = new FavoriteAdapter(new DeleteFavorite(), new DetailFavorite());
            adapter.submitList(favoriteEntities);
            mBinding.favoritesRecyclerView.setAdapter(adapter);
        });
    }

    private class DeleteFavorite implements FavoriteAdapter.FavoriteDeleteAdapterListener {
        @Override
        public void onFavoriteClickedDelete(FavoriteEntity favoriteEntity) {
            mViewModel.deleteFavoriteData(favoriteEntity);
        }
    }

    private static class DetailFavorite implements FavoriteAdapter.FavoriteDetailAdapterListener {
        @Override
        public void onFavoriteClickedDetail(FavoriteEntity favoriteEntity, View view) {

            Article article = new Article(
                    favoriteEntity.getArticleId(),
                    favoriteEntity.getArticleTitle(),
                    favoriteEntity.getArticleAuthorName(),
                    favoriteEntity.getArticleBody(),
                    favoriteEntity.getArticleThumbnail(),
                    favoriteEntity.getArticlePublishedDate());

            NavDirections direction = ArticleFavoritesFragmentDirections
                    .actionArticleFavoritesFragmentToArticleDetailFragment(article);
            // Find NavController with view and navigate to destination using directions.
            Navigation.findNavController(view).navigate(direction);
        }
    }
}
