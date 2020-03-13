package com.developersbreach.xyzreader.view.list;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.FragmentArticleListBinding;
import com.developersbreach.xyzreader.utils.SpaceItemDecoration;
import com.developersbreach.xyzreader.utils.ThemePreferencesManager;
import com.developersbreach.xyzreader.viewModel.ArticleListViewModel;

import java.util.Objects;


public class ArticleListFragment extends Fragment {

    private RecyclerView mArticleRecyclerView;
    private ArticleListViewModel mViewModel;
    private FragmentArticleListBinding mBinding;
    private ThemePreferencesManager mPreferenceManager;

    private static final String COMPLETED_ANIMATION_PREF_NAME = "ANIMATION_LINEAR_OUT";

    /**
     * Using preferences with editor decide whether to change the default value in string
     * COMPLETED_ANIMATION_PREF_NAME to show or hide the animation.
     */
    private SharedPreferences mSharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_article_list, container, false);

        mArticleRecyclerView = mBinding.articlesRecyclerView;
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_spacing_dimen);
        mArticleRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        setFragmentToolbar(getContext());
        mBinding.setLifecycleOwner(this);
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        animateForFirstTimeOnly();
    }

    // Until value in sharedPreference won't effect, we continue showing animation to user.
    private void animateForFirstTimeOnly() {
        // Getting our shared preference in primate mode.
        mSharedPreferences = Objects.requireNonNull(getActivity()).getPreferences(Context.MODE_PRIVATE);
        // Check for change in default value, if false returned then no effect happens.
        if (!mSharedPreferences.getBoolean(COMPLETED_ANIMATION_PREF_NAME, false)) {
            // Starts th animation with the properties we set.
            startLinearAnimation();
        }
    }

    private void startLinearAnimation() {
        // Occurs transition from bottom to top at 800dp.
        mBinding.fragmentListParent.setTranslationY(800);
        // Animate with duration, height.
        mBinding.fragmentListParent.animate().translationY(0f).setDuration(1000L).start();
        // After finishing the animation,change boolean value inside String with editor.
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(COMPLETED_ANIMATION_PREF_NAME, true);
        mEditor.apply();
    }

    private void setFragmentToolbar(Context context) {
        mBinding.toolbarContentArticlesHeader.headerThemeSwitcherImageView.setVisibility(View.VISIBLE);
        mPreferenceManager = new ThemePreferencesManager(context);
        mPreferenceManager.applyTheme();
        mBinding.toolbarContentArticlesHeader.headerThemeSwitcherImageView.setOnClickListener(view ->
                mPreferenceManager.showThemePopUp(mBinding.toolbarContentArticlesHeader.headerThemeSwitcherImageView));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ArticleListViewModel.class);

        mViewModel.getArticleList().observe(getViewLifecycleOwner(), articles -> {
            ArticleAdapter adapter = new ArticleAdapter(mViewModel, this);
            adapter.submitList(articles);
            mArticleRecyclerView.setAdapter(adapter);
        });
    }
}
