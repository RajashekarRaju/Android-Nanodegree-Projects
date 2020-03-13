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

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.FragmentSearchArticleBinding;
import com.developersbreach.xyzreader.viewModel.SearchArticleViewModel;

import java.util.Locale;


public class SearchArticleFragment extends Fragment {

    private FragmentSearchArticleBinding mBinding;
    private SearchArticleViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_article, container,
                false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SearchArticleViewModel.class);

        mViewModel.getArticleList().observe(getViewLifecycleOwner(), articleList -> {
            SearchAdapter adapter = new SearchAdapter();
            adapter.submitList(articleList);
            mBinding.searchRecyclerView.setAdapter(adapter);
        });

        mBinding.toolbarContentSearchHeader.articleSearchEditText.addTextChangedListener(new SearchTextListener());
    }

    private void filterWithViewModel(String query) {
        mViewModel.onFilterChanged(query).observe(getViewLifecycleOwner(), articleList -> {
            if (!query.isEmpty()) {
                SearchAdapter adapter = new SearchAdapter();
                adapter.submitList(articleList);
                mBinding.searchRecyclerView.setAdapter(adapter);
                if (articleList.size() == 0) {
                    mBinding.searchRecyclerView.setVisibility(View.INVISIBLE);
                    mBinding.noSearchResultsFoundText.setVisibility(View.VISIBLE);
                } else {
                    mBinding.searchRecyclerView.setVisibility(View.VISIBLE);
                    mBinding.noSearchResultsFoundText.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private class SearchTextListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence query, int start, int before, int count) {
            filterWithViewModel(query.toString().toLowerCase(Locale.getDefault()));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
