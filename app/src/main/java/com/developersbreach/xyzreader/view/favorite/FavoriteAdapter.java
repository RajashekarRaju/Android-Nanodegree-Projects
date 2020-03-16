package com.developersbreach.xyzreader.view.favorite;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.bindingAdapter.FavoriteListBindingAdapter;
import com.developersbreach.xyzreader.databinding.ItemFavoriteBinding;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.viewModel.ArticleFavoritesViewModel;
import com.google.android.material.card.MaterialCardView;

import static com.developersbreach.xyzreader.view.favorite.FavoriteAdapter.FavoriteViewHolder;


/**
 * This class implements a {@link RecyclerView} extends to {@link ListAdapter} which uses Data
 * Binding to present list data, including computing diffs between lists.
 * <p>
 * {@link FavoriteEntity} type of list this adapter will receive.
 * {@link FavoriteAdapter.FavoriteViewHolder} class that extends ViewHolder that will be used by the
 * adapter.
 * <p>
 * This adapter is associated with {@link ArticleFavoritesFragment} class.
 * Views inside recyclerView items are bind in class {@link FavoriteListBindingAdapter} with layout
 * reference to {@link R.layout#item_favorite}.
 */
public class FavoriteAdapter extends ListAdapter<FavoriteEntity, FavoriteViewHolder> {

    /**
     * This adapter class requires associated {@link ArticleFavoritesFragment} fragment with viewModel
     * {@link ArticleFavoritesViewModel} class to get access for {@link BindingAdapter} to access to
     * binding objects inside recyclerView items, by creating setters in layout.
     *
     * @see R.layout#item_favorite, declares setters and variables.
     */
    private final ArticleFavoritesViewModel mViewModel;

    /**
     * Get fragment and pass inside ViewHolder for binding.
     * Only purpose of getting fragment reference here is to get window for current fragment which
     * is shown in screen currently.
     *
     * @see FavoriteListBindingAdapter#bindDeleteFavoriteClickListener(ImageView, FavoriteEntity,
     * ArticleFavoritesViewModel, Activity, MaterialCardView)
     * In above method we are passing this fragment reference as activity from ViewHolder class.
     */
    private final ArticleFavoritesFragment mFragment;


    /**
     * @param viewModel takes associated viewModel.
     * @param fragment  takes associated fragment class.
     * @see FavoriteAdapter#DIFF_ITEM_CALLBACK DiffUtil is a utility class that calculates the
     * difference between two lists and outputs a list of update operations that converts the first
     * list into the second one.
     */
    FavoriteAdapter(ArticleFavoritesViewModel viewModel, ArticleFavoritesFragment fragment) {
        super(DIFF_ITEM_CALLBACK);
        this.mViewModel = viewModel;
        this.mFragment = fragment;
    }

    /**
     * FavoriteViewHolder class creates child view FavoriteEntity properties.
     * Class also calls setters for binding objects from inflated layout.
     */
    static class FavoriteViewHolder extends RecyclerView.ViewHolder {

        // Get access to binding the views in layout
        private final ItemFavoriteBinding mBinding;

        /**
         * @param binding binds each properties in {@link FavoriteEntity} list
         */
        private FavoriteViewHolder(final ItemFavoriteBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        /**
         * @param favoriteEntity pass object to set article for binding. This binding is accessed
         *                       from layout xml {@link R.layout#item_favorite}
         * @param viewModel      call setter for viewModel
         * @param fragment       call setter for fragment which is referenced as activity.
         */
        void bind(final FavoriteEntity favoriteEntity, ArticleFavoritesViewModel viewModel,
                  ArticleFavoritesFragment fragment) {
            mBinding.setFavoriteArticle(favoriteEntity);
            mBinding.setFavoriteViewModel(viewModel);
            // Don't pass fragment, instead get reference to activity window.
            mBinding.setActivity(fragment.getActivity());
            // Force DataBinding to execute binding views immediately.
            mBinding.executePendingBindings();
        }
    }

    /**
     * Called by RecyclerView to display the data at the specified position. DataBinding should
     * update the contents of the {@link FavoriteAdapter.FavoriteViewHolder#itemView} to reflect the
     * item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull final FavoriteViewHolder holder, final int position) {
        final FavoriteEntity favoriteEntity = getItem(position);
        holder.bind(favoriteEntity, mViewModel, mFragment);
    }

    /**
     * Called when RecyclerView needs a new {@link FavoriteAdapter.FavoriteViewHolder} of the given
     * type to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Allow DataBinding to inflate the layout.
        ItemFavoriteBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_favorite,
                parent, false);
        return new FavoriteViewHolder(binding);
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the list of
     * {@link FavoriteEntity} has been updated.
     */
    private static final DiffUtil.ItemCallback<FavoriteEntity> DIFF_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<FavoriteEntity>() {

                @Override
                public boolean areItemsTheSame(
                        @NonNull FavoriteEntity oldItem, @NonNull FavoriteEntity newItem) {
                    return oldItem == newItem;
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull FavoriteEntity oldItem, @NonNull FavoriteEntity newItem) {
                    return oldItem.getArticleId() == newItem.getArticleId();
                }
            };
}
