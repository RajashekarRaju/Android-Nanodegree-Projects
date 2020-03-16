package com.developersbreach.xyzreader.view.search;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.R.layout;
import com.developersbreach.xyzreader.bindingAdapter.SearchListBindingAdapter;
import com.developersbreach.xyzreader.databinding.ItemSearchArticleBinding;
import com.developersbreach.xyzreader.model.Article;


/**
 * This class implements a {@link RecyclerView} extends to {@link ListAdapter} which uses Data
 * Binding to present list data, including computing diffs between lists.
 * <p>
 * {@link Article} type of list this adapter will receive.
 * {@link SearchAdapter.SearchViewHolder} class that extends ViewHolder that will be used by the adapter.
 * This adapter is associated with {@link SearchArticleFragment} class.
 * Views inside recyclerView items are bind in class {@link SearchListBindingAdapter} with layout
 * reference to {@link R.layout#item_search_article}.
 */
public class SearchAdapter extends ListAdapter<Article, SearchAdapter.SearchViewHolder> {

    // Empty constructor with DIFF_ITEM_CALLBACK.
    SearchAdapter() {
        super(DIFF_ITEM_CALLBACK);
    }

    /**
     * SearchViewHolder class creates child view Article properties.
     * Class also calls setters for binding objects from inflated layout.
     */
    static class SearchViewHolder extends RecyclerView.ViewHolder {

        // Get access to binding the views in layout
        private final ItemSearchArticleBinding mBinding;

        /**
         * @param binding binds each properties in {@link Article} list
         */
        private SearchViewHolder(final ItemSearchArticleBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        /**
         * @param article pass object to set article for binding. This binding is accessed from layout
         *                xml {@link layout#item_article}
         */
        void bind(final Article article) {
            mBinding.setArticle(article);
            // Force DataBinding to execute binding views immediately.
            mBinding.executePendingBindings();
        }
    }

    /**
     * Called by RecyclerView to display the data at the specified position. DataBinding should
     * update the contents of the {@link SearchAdapter.SearchViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull final SearchViewHolder holder, final int position) {
        final Article article = getItem(position);
        holder.bind(article);
    }

    /**
     * Called when RecyclerView needs a new {@link SearchViewHolder} of the given type to represent
     * an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Allow DataBinding to inflate the layout.
        ItemSearchArticleBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_search_article, parent,
                false);
        return new SearchViewHolder(binding);
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the list of {@link Article}
     * has been updated.
     */
    private static final DiffUtil.ItemCallback<Article> DIFF_ITEM_CALLBACK = new DiffUtil.ItemCallback<Article>() {

        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.getArticleId() == newItem.getArticleId();
        }
    };
}
