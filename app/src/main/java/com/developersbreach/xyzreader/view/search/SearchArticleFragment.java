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
import com.developersbreach.xyzreader.model.Article;
import com.developersbreach.xyzreader.utils.ArticleAnimations;
import com.developersbreach.xyzreader.viewModel.SearchArticleViewModel;

import java.util.List;
import java.util.Locale;


/**
 * Second menu item in BottomNavigationView in activity.
 * <p>
 * This fragment observes data changes from {@link SearchArticleViewModel} class. Also helps
 * to keep this class clean and no operations are handled here. Helps to preserve data of the
 * fragment data without loosing it's state when user navigates between different destinations or
 * changing orientation of the device.
 * <p>
 * Utilises adapter class {@link SearchAdapter} to show a {@link List} of type {@link Article}
 * which are results appeared after performed search by user.
 * <p>
 * Layout and views are bind using {@link DataBindingUtil}
 */
public class SearchArticleFragment extends Fragment {

    // Get binding reference for this fragment.
    private FragmentSearchArticleBinding mBinding;
    // Get this class fragment associated ViewModel.
    private SearchArticleViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Get reference to binding and inflate this class layout.
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search_article, container,
                false);
        // Return root binding view.
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Assign and get class ViewModel and pass fragment owner and factory to create instance
        // by calling ViewModelProviders.
        mViewModel = new ViewModelProvider(this).get(SearchArticleViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // With variable viewModel start observing LiveData to this fragment by calling articles()
        // and observe changes for list of searchable article data.
        // articles() is externally exposed in ViewModel class to observe changes.
        mViewModel.articles().observe(getViewLifecycleOwner(), this::onSearchChanged);
        // Get reference to editText and attach textChangeListener to listen for text changes using
        // query string.
        mBinding.toolbarContentSearchHeader.articleSearchEditText.addTextChangedListener(
                new SearchTextListener());
    }

    /**
     * @param articleList contains list for searchable articles data.
     */
    private void onSearchChanged(List<Article> articleList) {
        // Call associated adapter, this adapter won't take any arguments to the constructor.
        SearchAdapter adapter = new SearchAdapter();
        // Pass list to adapter calling submitList since our adapter class extends to ListAdapter<>.
        adapter.submitList(articleList);
        // Set adapter with recyclerView.
        mBinding.searchRecyclerView.setAdapter(adapter);
    }

    /**
     * Listener which implements TextWatcher which notifies query has been changed for request of
     * the new data.
     */
    private class SearchTextListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        /**
         * @param query contains search query entered by user in editText.
         *              We pass this query to filter results by converting to string query.
         */
        @Override
        public void onTextChanged(CharSequence query, int start, int before, int count) {
            filterWithViewModel(query.toString().toLowerCase(Locale.getDefault()));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    /**
     * With viewModel call filter method with passing query and observe the liveData changes to this
     * fragment.
     *
     * @param query contains user submitted query.
     * @see SearchArticleViewModel#filter(String) is externally exposed in class to observe changes.
     */
    private void filterWithViewModel(String query) {
        mViewModel.filter(query).observe(getViewLifecycleOwner(), articleList -> {
            // Only perform this operation is query is valid.
            if (!query.isEmpty()) {
                // Get our adapter
                SearchAdapter adapter = new SearchAdapter();
                // Set adapter to recyclerView and call submitList with articles fragment observes.
                adapter.submitList(articleList);
                mBinding.searchRecyclerView.setAdapter(adapter);
                // Change behaviour of recyclerView based on data available.
                toggleRecyclerView(articleList);
            }
        });
    }

    /**
     * @param articleList contains list data of searchable articles.
     */
    private void toggleRecyclerView(List<Article> articleList) {
        // If no items are available, hide the recyclerView and show not found text error.
        if (articleList.size() == 0) {
            mBinding.searchRecyclerView.setVisibility(View.INVISIBLE);
            mBinding.noSearchResultsFoundText.setVisibility(View.VISIBLE);
            // If items available, show recyclerView hide error textView.
        } else {
            mBinding.searchRecyclerView.setVisibility(View.VISIBLE);
            mBinding.noSearchResultsFoundText.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Call animation from class {@link ArticleAnimations} and pass view which needs to animate.
     * In our case we animate items of cardView inside RecyclerView.
     */
    @Override
    public void onResume() {
        super.onResume();
        ArticleAnimations.startLinearAnimation(mBinding.searchRecyclerView);
    }
}
