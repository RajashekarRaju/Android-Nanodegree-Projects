package com.developersbreach.xyzreader.view.favorite;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.ItemFavoriteBinding;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;

public class FavoriteAdapter extends ListAdapter<FavoriteEntity, FavoriteAdapter.FavoriteViewHolder> {

    private FavoriteDeleteAdapterListener mDeleteListener;
    private FavoriteDetailAdapterListener mDetailListener;

    FavoriteAdapter(FavoriteDeleteAdapterListener deleteListener, FavoriteDetailAdapterListener detailListener) {
        super(DIFF_ITEM_CALLBACK);
        this.mDeleteListener = deleteListener;
        this.mDetailListener = detailListener;
    }

    public interface FavoriteDeleteAdapterListener {
        void onFavoriteClickedDelete(FavoriteEntity favoriteEntity);
    }

    public interface FavoriteDetailAdapterListener {
        void onFavoriteClickedDetail(FavoriteEntity favoriteEntity, View view);
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

        void bind(final FavoriteEntity article) {
            mBinding.setFavoriteArticle(article);
            // Force DataBinding to execute binding views immediately.
            mBinding.executePendingBindings();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final FavoriteViewHolder holder, final int position) {
        final FavoriteEntity favoriteEntity = getItem(position);
        holder.bind(favoriteEntity);

        holder.mBinding.removeFavoriteImageView.setOnClickListener(
                view -> mDeleteListener.onFavoriteClickedDelete(favoriteEntity));

        holder.itemView.setOnClickListener(
                view -> mDetailListener.onFavoriteClickedDetail(favoriteEntity, view));
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Allow DataBinding to inflate the layout.
        ItemFavoriteBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_favorite, parent,
                false);
        return new FavoriteViewHolder(binding);
    }

    private static final DiffUtil.ItemCallback<FavoriteEntity> DIFF_ITEM_CALLBACK = new DiffUtil.ItemCallback<FavoriteEntity>() {

        @Override
        public boolean areItemsTheSame(@NonNull FavoriteEntity oldItem, @NonNull FavoriteEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull FavoriteEntity oldItem, @NonNull FavoriteEntity newItem) {
            return oldItem.getArticleId() == newItem.getArticleId();
        }
    };
}
