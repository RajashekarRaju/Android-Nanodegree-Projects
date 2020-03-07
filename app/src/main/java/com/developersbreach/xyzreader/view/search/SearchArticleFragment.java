package com.developersbreach.xyzreader.view.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.developersbreach.xyzreader.databinding.FragmentSearchArticleBinding;
import com.developersbreach.xyzreader.model.Article;

import java.util.Locale;


public class SearchArticleFragment extends Fragment {

    private FragmentSearchArticleBinding mBinding;
    private SearchArticleViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_article, container,
                false);

        setHomeNav();
        return mBinding.getRoot();
    }

    private void setHomeNav() {
        mBinding.searchHeaderIncluded.homeButtonImageView.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.articleListFragment));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchArticleViewModel.class);

        mViewModel.getArticleList().observe(getViewLifecycleOwner(), articleList -> {
            SearchAdapter adapter = new SearchAdapter(new SearchItemListener());
            adapter.submitList(articleList);
            mBinding.searchRecyclerView.setAdapter(adapter);
        });

        mBinding.searchHeaderIncluded.articleSearchEditText.addTextChangedListener(new SearchTextListener());
    }

    private void filterWithViewModel(RecyclerView recyclerView, String query) {
        mViewModel.onFilterChanged(query).observe(getViewLifecycleOwner(), articleList -> {
            if (!query.isEmpty()) {
                SearchAdapter adapter = new SearchAdapter(new SearchItemListener());
                adapter.submitList(articleList);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private static class SearchItemListener implements SearchAdapter.SearchAdapterListener {
        /**
         * @param article get recipes from selected list of recipes.
         * @param view    used to create navigation with controller, which needs view.
         */
        @Override
        public void onSearchSelected(Article article, View view) {
            NavDirections direction = SearchArticleFragmentDirections
                    .actionSearchArticleFragmentToArticleDetailFragment(article, null);
            // Find NavController with view and navigate to destination using directions.
            Navigation.findNavController(view).navigate(direction);
        }
    }

    private class SearchTextListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            filterWithViewModel(mBinding.searchRecyclerView, s.toString().toLowerCase(Locale.getDefault()));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
