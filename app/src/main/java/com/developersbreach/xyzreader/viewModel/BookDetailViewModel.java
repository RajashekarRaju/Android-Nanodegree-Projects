package com.developersbreach.xyzreader.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.developersbreach.xyzreader.model.Book;
import com.developersbreach.xyzreader.view.list.BookListFragment;

public class BookDetailViewModel extends AndroidViewModel {

    /**
     * This field is encapsulated, we used {@link MutableLiveData} because when the data is being
     * changed we will be updating StepsDetail with new values. And any externally exposed LiveData
     * can observe this changes.
     */
    private MutableLiveData<Book> _mMutableBook;

    /**
     * fragment to observe changes. Data is observed once changes will be done internally.
     */
    public LiveData<Book> selectedBook() {
        return _mMutableBook;
    }

    /**
     * @param application provides application context for ViewModel.
     * @param book      parcel Recipe object with data for user selected recipe from
     *                    {@link BookListFragment}
     */
    public BookDetailViewModel(@NonNull Application application, Book book) {
        super(application);
        getMutableBookDetailsData(book);
    }

    /**
     * Create a new {@link MutableLiveData} after checking if is is empty, otherwise no need make
     * changes by adding new values.
     *
     * @param book has data for user selected Recipe with id.
     */
    private void getMutableBookDetailsData(Book book) {
        if (_mMutableBook == null) {
            _mMutableBook = new MutableLiveData<>();
            // Add list to internally exposed data of RecipeDetails by calling postValue.
            _mMutableBook.postValue(book);
        }
    }
}
