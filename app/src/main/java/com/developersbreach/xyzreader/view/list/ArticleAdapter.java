package com.developersbreach.xyzreader.view.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.ItemArticleBinding;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.viewModel.ArticleListViewModel;

import static com.developersbreach.xyzreader.view.list.ArticleAdapter.ArticleViewHolder;


/**
 * This class implements a {@link RecyclerView} {@link ListAdapter} which uses Data Binding to
 * present list data, including computing diffs between lists.
 * <p>
 * {@link ArticleViewHolder} class that extends ViewHolder that will be used by the adapter.
 */
public class ArticleAdapter extends ListAdapter<Article, ArticleViewHolder> {

    private final ArticleListViewModel mViewModel;
    private final ArticleListFragment mFragment;


    ArticleAdapter(ArticleListViewModel viewModel, ArticleListFragment fragment) {
        super(DIFF_ITEM_CALLBACK);
        this.mViewModel = viewModel;
        this.mFragment = fragment;
    }

    /**
     * RecipeViewHolder class creates child view Recipe properties.
     */
    static class ArticleViewHolder extends RecyclerView.ViewHolder {

        // Get access to binding the views in layout
        private final ItemArticleBinding mBinding;

        private ArticleViewHolder(final ItemArticleBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        /**
         * @param article pass object to set recipe for binding. This binding is accessed from layout
         *               xml {@link R.layout#item_article}
         * @param viewModel model
         * @param fragment activity
         */
        void bind(final Article article, ArticleListViewModel viewModel, ArticleListFragment fragment) {
            mBinding.setArticle(article);
            mBinding.setArticleViewModel(viewModel);
            mBinding.setActivity(fragment.getActivity());
            // Force DataBinding to execute binding views immediately.
            mBinding.executePendingBindings();
        }
    }

    /**
     * Called by RecyclerView to display the data at the specified position. DataBinding should
     * update the contents of the {@link ArticleViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull final ArticleViewHolder holder, final int position) {
        final Article article = getItem(position);
        holder.bind(article, mViewModel, mFragment);
    }

    /**
     * Called when RecyclerView needs a new {@link ArticleViewHolder} of the given type to represent
     * an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Allow DataBinding to inflate the layout.
        ItemArticleBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_article, parent,
                false);
        return new ArticleViewHolder(binding);
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
