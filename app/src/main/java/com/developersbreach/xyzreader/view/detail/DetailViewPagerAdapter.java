package com.developersbreach.xyzreader.view.detail;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.ItemArticleAdapterBinding;
import com.developersbreach.xyzreader.model.Article;

import static com.developersbreach.xyzreader.view.detail.DetailViewPagerAdapter.ArticlePagerViewHolder;


public class DetailViewPagerAdapter extends ListAdapter<Article, ArticlePagerViewHolder> {

    DetailViewPagerAdapter() {
        super(DIFF_ITEM_CALLBACK);
    }

    static class ArticlePagerViewHolder extends RecyclerView.ViewHolder {

        // Get access to binding the views in layout
        private final ItemArticleAdapterBinding mBinding;

        private ArticlePagerViewHolder(final ItemArticleAdapterBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;

            mBinding.detailToolbar.setNavigationOnClickListener(
                    view -> Navigation.findNavController(view).navigateUp());
        }

        /**
         * @param article pass object to set recipe for binding. This binding is accessed from layout
         *                xml {@link com.developersbreach.xyzreader.R.layout#item_article}
         */
        void bind(final Article article) {
            mBinding.setArticleDetail(article);
            // Force DataBinding to execute binding views immediately.
            mBinding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public ArticlePagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Allow DataBinding to inflate the layout.
        ItemArticleAdapterBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_article_adapter, parent,
                false);
        return new ArticlePagerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlePagerViewHolder holder, int position) {
        final Article article = getItem(position);
        holder.bind(article);
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
