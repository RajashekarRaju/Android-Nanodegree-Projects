package com.developersbreach.xyzreader.viewModel.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.developersbreach.xyzreader.model.Book;
import com.developersbreach.xyzreader.view.detail.BookDetailFragment;
import com.developersbreach.xyzreader.viewModel.BookDetailViewModel;

/**
 * A AndroidViewModelFactory for creating a instance of {@link BookDetailViewModel}
 * AndroidViewModel for fragment class {@link BookDetailFragment} with a constructor.
 */
public class BookDetailViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    // Needs to to be passed as parameter for AndroidViewModel class.
    private final Application mApplication;
    // Parcel model class Recipe as argument.
    private final Book mBook;

    /**
     * Creates a {@link ViewModelProvider.AndroidViewModelFactory}
     *
     * @param application parameter to pass in {@link AndroidViewModel}
     * @param book        a user selected Recipe object to pass in {@link AndroidViewModel}
     */
    public BookDetailViewModelFactory(@NonNull Application application, Book book) {
        super(application);
        this.mApplication = application;
        this.mBook = book;
    }

    /**
     * @param modelClass to check if our {@link BookDetailViewModel} model class is assignable.
     * @param <T>        type of generic class
     * @return returns the ViewModel class with passing parameters if instance is created.
     */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BookDetailViewModel.class)) {
            //noinspection unchecked
            return (T) new BookDetailViewModel(mApplication, mBook);
        }
        throw new IllegalArgumentException("Cannot create Instance for BookDetailViewModel class");
    }
}
