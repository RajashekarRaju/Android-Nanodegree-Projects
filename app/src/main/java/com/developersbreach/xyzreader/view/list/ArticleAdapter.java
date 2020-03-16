package com.developersbreach.xyzreader.view.list;

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
import com.developersbreach.xyzreader.bindingAdapter.ArticleListBindingAdapter;
import com.developersbreach.xyzreader.databinding.ItemArticleBinding;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.viewModel.ArticleListViewModel;

import static com.developersbreach.xyzreader.view.list.ArticleAdapter.ArticleViewHolder;


/**
 * This class implements a {@link RecyclerView} extends to {@link ListAdapter} which uses Data
 * Binding to present list data, including computing diffs between lists.
 * <p>
 * {@link Article} type of list this adapter will receive.
 * {@link ArticleViewHolder} class that extends ViewHolder that will be used by the adapter.
 * This adapter is associated with {@link ArticleListFragment} class.
 * Views inside recyclerView items are bind in class {@link ArticleListBindingAdapter} with layout
 * reference to {@link R.layout#item_article}.
 */
public class ArticleAdapter extends ListAdapter<Article, ArticleViewHolder> {

    /**
     * This adapter class requires associated {@link ArticleListFragment} fragment with viewModel
     * {@link ArticleListViewModel} class to get access for {@link BindingAdapter} to access to
     * binding objects inside recyclerView items, by creating setters in layout.
     *
     * @see R.layout#item_article, declares setters and variables.
     */
    private final ArticleListViewModel mViewModel;

    /**
     * Get fragment and pass inside ViewHolder for binding.
     * Only purpose of getting fragment reference here is to get window for current fragment which
     * is shown in screen currently.
     *
     * @see ArticleListBindingAdapter#bindArticleFavoriteClickListener(ImageView, Article,
     * ArticleListViewModel, Activity).
     * In above method we are passing this fragment reference as activity from ViewHolder class.
     */
    private final ArticleListFragment mFragment;


    /**
     * @param viewModel takes associated viewModel.
     * @param fragment  takes associated fragment class.
     * @see ArticleAdapter#DIFF_ITEM_CALLBACK DiffUtil is a utility class that calculates the
     * difference between two lists and outputs a list of update operations that converts the first
     * list into the second one.
     */
    ArticleAdapter(ArticleListViewModel viewModel, ArticleListFragment fragment) {
        super(DIFF_ITEM_CALLBACK);
        this.mViewModel = viewModel;
        this.mFragment = fragment;
    }

    /**
     * ArticleViewHolder class creates child view Article properties.
     * Class also calls setters for binding objects from inflated layout.
     */
    static class ArticleViewHolder extends RecyclerView.ViewHolder {

        // Get access to binding the views in layout.
        private final ItemArticleBinding mBinding;

        /**
         * @param binding binds each properties in {@link Article} list
         */
        private ArticleViewHolder(final ItemArticleBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        /**
         * @param article   pass object to set article for binding. This binding is accessed from
         *                  layout xml {@link R.layout#item_article}
         * @param viewModel call setter for viewModel
         * @param fragment  call setter for fragment which is referenced as activity.
         */
        void bind(final Article article, ArticleListViewModel viewModel, ArticleListFragment fragment) {
            mBinding.setArticle(article);
            mBinding.setArticleViewModel(viewModel);
            // Don't pass fragment, instead get reference to activity window.
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

    /**
     * Allows the RecyclerView to determine which items have changed when the list of {@link Article}
     * has been updated.
     */
    private static final DiffUtil.ItemCallback<Article> DIFF_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<Article>() {

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
