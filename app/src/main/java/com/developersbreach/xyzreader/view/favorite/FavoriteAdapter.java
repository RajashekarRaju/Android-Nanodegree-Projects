package com.developersbreach.xyzreader.view.favorite;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.ItemFavoriteBinding;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.viewModel.ArticleFavoritesViewModel;

import static com.developersbreach.xyzreader.view.favorite.FavoriteAdapter.*;

public class FavoriteAdapter extends ListAdapter<FavoriteEntity, FavoriteViewHolder> {

    private final ArticleFavoritesViewModel mViewModel;
    private final ArticleFavoritesFragment mFragment;


    FavoriteAdapter(ArticleFavoritesViewModel viewModel, ArticleFavoritesFragment fragment) {
        super(DIFF_ITEM_CALLBACK);
        this.mViewModel = viewModel;
        this.mFragment = fragment;
    }

    /**
     * RecipeViewHolder class creates child view Recipe properties.
     */
    static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        // Get access to binding the views in layout
        private final ItemFavoriteBinding mBinding;

        private FavoriteViewHolder(final ItemFavoriteBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        void bind(final FavoriteEntity article, ArticleFavoritesViewModel viewModel,
                  ArticleFavoritesFragment fragment) {
            mBinding.setFavoriteArticle(article);
            mBinding.setFavoriteViewModel(viewModel);
            mBinding.setActivity(fragment.getActivity());
            // Force DataBinding to execute binding views immediately.
            mBinding.executePendingBindings();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteViewHolder holder, final int position) {
        final FavoriteEntity favoriteEntity = getItem(position);
        holder.bind(favoriteEntity, mViewModel, mFragment);
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Allow DataBinding to inflate the layout.
        ItemFavoriteBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_favorite,
                parent, false);
        return new FavoriteViewHolder(binding);
    }

    private static final DiffUtil.ItemCallback<FavoriteEntity> DIFF_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<FavoriteEntity>() {

        @Override
        public boolean areItemsTheSame(@NonNull FavoriteEntity oldItem,
                                       @NonNull FavoriteEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavoriteEntity oldItem,
                                          @NonNull FavoriteEntity newItem) {
            return oldItem.getArticleId() == newItem.getArticleId();
        }
    };
}
