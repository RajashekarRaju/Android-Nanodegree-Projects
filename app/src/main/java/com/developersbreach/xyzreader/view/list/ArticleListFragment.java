package com.developersbreach.xyzreader.view.list;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.FragmentArticleListBinding;
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.utils.SpaceItemDecoration;
import com.developersbreach.xyzreader.utils.ThemePreferencesManager;
import com.developersbreach.xyzreader.viewModel.ArticleListViewModel;


public class ArticleListFragment extends Fragment {

    private RecyclerView mArticleRecyclerView;
    private ArticleListViewModel mViewModel;
    private FragmentArticleListBinding mBinding;
    private ThemePreferencesManager mPreferenceManager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_article_list, container, false);

        mArticleRecyclerView = mBinding.articlesRecyclerView;
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.rv_dimen);
        mArticleRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        setFragmentToolbar(getContext());
        return mBinding.getRoot();
    }

    private void setFragmentToolbar(Context context) {
        mBinding.articlesToolbarContent.themeImageView.setVisibility(View.VISIBLE);
        mPreferenceManager = new ThemePreferencesManager(context);
        mPreferenceManager.applyTheme();
        mBinding.articlesToolbarContent.themeImageView.setOnClickListener(view ->
                mPreferenceManager.showThemePopUp(mBinding.articlesToolbarContent.themeImageView));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ArticleListViewModel.class);

        mViewModel.getArticleList().observe(getViewLifecycleOwner(), articles -> {
            ArticleAdapter adapter = new ArticleAdapter(new ArticleItemListener(), new FavoriteItemListener());
            adapter.submitList(articles);
            mArticleRecyclerView.setAdapter(adapter);
        });
    }

    private static class ArticleItemListener implements ArticleAdapter.ArticleAdapterListener {
        /**
         * @param article get recipes from selected list of recipes.
         * @param view used to create navigation with controller, which needs view.
         */
        @Override
        public void onArticleSelected(Article article, View view) {
            NavDirections direction = ArticleListFragmentDirections
                    .actionArticleListFragmentToArticleDetailFragment(article, ArticleListFragment.class.getSimpleName());
            // Find NavController with view and navigate to destination using directions.
            Navigation.findNavController(view).navigate(direction);
        }
    }

    private class FavoriteItemListener implements ArticleAdapter.FavoriteListener {
        @Override
        public void onFavouriteSelected(Article article, View view) {
            mViewModel.insertFavoriteData(article);
        }
    }
}
