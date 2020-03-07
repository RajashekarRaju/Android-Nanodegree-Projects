package com.developersbreach.xyzreader.viewModel.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.developersbreach.xyzreader.view.favorite.ArticleFavoritesViewModel;
import com.developersbreach.xyzreader.viewModel.ArticleDetailViewModel;

public class FavoriteViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    // Needs to to be passed as parameter for AndroidViewModel class.
    private final Application mApplication;
    // Parcel model class Recipe as argument.

    /**
     * Creates a {@link ViewModelProvider.AndroidViewModelFactory}
     *
     * @param application parameter to pass in {@link AndroidViewModel}
     */
    public FavoriteViewModelFactory(@NonNull Application application) {
        super(application);
        this.mApplication = application;
    }

    /**
     * @param modelClass to check if our {@link ArticleDetailViewModel} model class is assignable.
     * @param <T>        type of generic class
     * @return returns the ViewModel class with passing parameters if instance is created.
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ArticleFavoritesViewModel.class)) {
            //noinspection unchecked
            return (T) new ArticleFavoritesViewModel(mApplication);
        }
        throw new IllegalArgumentException("Cannot create Instance for FavoriteViewModel class");
    }
}
