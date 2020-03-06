package com.developersbreach.xyzreader.view.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.ItemSearchArticleBinding;
import com.developersbreach.xyzreader.model.Article;

public class SearchAdapter extends ListAdapter<Article, SearchAdapter.SearchViewHolder> {

    /**
     * The interface that receives onClick listener.
     */
    private final SearchAdapter.SearchAdapterListener mListener;

    /**
     * @param listener   create click listener on itemView.
     */
    SearchAdapter(SearchAdapter.SearchAdapterListener listener) {
        super(DIFF_ITEM_CALLBACK);
        this.mListener = listener;
    }

    /**
     * The interface that receives onClick listener.
     */
    public interface SearchAdapterListener {
        /**
         * @param article get recipes from selected list of recipes.
         * @param view   used to create navigation with controller, which needs view.
         */
        void onSearchSelected(Article article, View view);
    }

    /**
     * RecipeViewHolder class creates child view Recipe properties.
     */
    static class SearchViewHolder extends RecyclerView.ViewHolder {

        // Get access to binding the views in layout
        private final ItemSearchArticleBinding mBinding;

        private SearchViewHolder(final ItemSearchArticleBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        /**
         * @param article pass object to set recipe for binding. This binding is accessed from layout
         *               xml {@link com.developersbreach.xyzreader.R.layout#item_article}
         */
        void bind(final Article article) {
            mBinding.setSearchArticle(article);
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

        // Set click listener on itemView and pass arguments recipe, view for selected recipe.
        holder.itemView.setOnClickListener(
                view -> mListener.onSearchSelected(article, view));
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
