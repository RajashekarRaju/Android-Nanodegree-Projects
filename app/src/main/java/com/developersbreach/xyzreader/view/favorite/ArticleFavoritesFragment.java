package com.developersbreach.xyzreader.view.favorite;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.FragmentArticleFavoritesBinding;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;
import com.developersbreach.xyzreader.utils.ArticleAnimations;
import com.developersbreach.xyzreader.utils.RecyclerViewItemDecoration;
import com.developersbreach.xyzreader.viewModel.ArticleFavoritesViewModel;
import com.developersbreach.xyzreader.viewModel.factory.FavoriteViewModelFactory;

import java.util.List;
import java.util.Objects;


/**
 * Third menu item in BottomNavigationView in activity.
 * <p>
 * This fragment observes data changes from {@link ArticleFavoritesViewModel} class. Also helps
 * to keep this class clean and no operations are handled here. Helps to preserve data of the
 * fragment data without loosing it's state when user navigates between different destinations or
 * changing orientation of the device.
 * <p>
 * Utilises adapter class {@link FavoriteAdapter} to show a {@link List} of type
 * {@link FavoriteEntity} which are user favorites.
 * <p>
 * Layout and views are bind using {@link DataBindingUtil}
 */
public class ArticleFavoritesFragment extends Fragment {

    // Get binding reference for this fragment.
    private FragmentArticleFavoritesBinding mBinding;
    // Get this class fragment associated ViewModel.
    private ArticleFavoritesViewModel mViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Get reference to binding and inflate this class layout.
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_article_favorites, container, false);
        // Set item decoration for items inside recycler view.
        RecyclerViewItemDecoration.setItemSpacing(getResources(), mBinding.favoritesRecyclerView);
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment.
        mBinding.setLifecycleOwner(this);
        // Return root binding view.
        return mBinding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get this activity reference as non-null.
        Activity activity = Objects.requireNonNull(getActivity());
        // Get application context for this class for factory to create instance of ViewModel.
        Application application = activity.getApplication();
        // Call factory for creating new instance of ViewModel for this fragment to observe data.
        // Pass application context to the factory.
        FavoriteViewModelFactory factory = new FavoriteViewModelFactory(application);
        // Assign and get class ViewModel and pass fragment owner and factory to create instance
        // by calling ViewModelProviders.
        mViewModel = new ViewModelProvider(this, factory).get(ArticleFavoritesViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // With variable viewModel start observing LiveData to this fragment by calling favorites()
        // and observe changes for list of favorite article data.
        // favorites() is externally exposed in ViewModel class to observe changes.
        mViewModel.favorites().observe(getViewLifecycleOwner(), this::onFavoritesChanged);
    }

    /**
     * @param favoriteList contains list for favorite articles data.
     */
    private void onFavoritesChanged(List<FavoriteEntity> favoriteList) {
        // Call associated adapter and pass required args.
        // For second argument, we pass this class as reference to get into DetailsFragment for
        // custom behaviour od viewPager.
        FavoriteAdapter adapter = new FavoriteAdapter(mViewModel, this);
        // Pass list to adapter calling submitList since our adapter class extends to ListAdapter<>.
        adapter.submitList(favoriteList);
        // Set adapter with recyclerView.
        mBinding.favoritesRecyclerView.setAdapter(adapter);
        // Change behaviour of recyclerView based on data available.
        toggleRecyclerView(favoriteList);
    }

    /**
     * @param favoriteList contains list data of favorites.
     */
    private void toggleRecyclerView(List<FavoriteEntity> favoriteList) {
        // If no items are available, hide the recyclerView and show not found text error.
        if (favoriteList.size() == 0) {
            mBinding.favoritesRecyclerView.setVisibility(View.INVISIBLE);
            mBinding.noFavoritesFoundText.setVisibility(View.VISIBLE);
            // If items available, show recyclerView hide error textView.
        } else {
            mBinding.favoritesRecyclerView.setVisibility(View.VISIBLE);
            mBinding.noFavoritesFoundText.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Call animation from class {@link ArticleAnimations} and pass view which needs to animate.
     * In our case we animate items of cardView inside RecyclerView.
     */
    @Override
    public void onResume() {
        super.onResume();
        ArticleAnimations.startLinearAnimation(mBinding.favoritesRecyclerView);
    }
}
