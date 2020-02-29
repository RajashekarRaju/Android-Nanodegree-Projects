package com.developersbreach.xyzreader.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.developersbreach.xyzreader.XYZReaderApp;
import com.developersbreach.xyzreader.model.Book;
import com.developersbreach.xyzreader.repository.BooksRepository;
import com.developersbreach.xyzreader.repository.database.BookEntity;

import java.util.List;

public class BookListViewModel extends AndroidViewModel {

    private final LiveData<List<Book>> mBookList;

    public BookListViewModel(@NonNull Application application) {
        super(application);
        final BooksRepository repository = ((XYZReaderApp) application).getRepository();

        LiveData<List<BookEntity>> bookEntityList = repository.getBooks();

        mBookList = Transformations.map(bookEntityList, new Function<List<BookEntity>, List<Book>>() {
            @Override
            public List<Book> apply(List<BookEntity> input) {
                return null;
            }
        });
    }

    public LiveData<List<Book>> getBookList() {
        return mBookList;
    }
}
