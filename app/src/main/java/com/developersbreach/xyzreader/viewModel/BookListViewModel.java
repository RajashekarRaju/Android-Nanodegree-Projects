package com.developersbreach.xyzreader.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.developersbreach.xyzreader.XYZReaderApp;
import com.developersbreach.xyzreader.model.Book;
import com.developersbreach.xyzreader.repository.BooksRepository;
import com.developersbreach.xyzreader.repository.database.BookEntity;

import java.util.ArrayList;
import java.util.List;

public class BookListViewModel extends AndroidViewModel {

    private LiveData<List<Book>> mBookList;

    public BookListViewModel(@NonNull Application application) {
        super(application);
        final BooksRepository repository = ((XYZReaderApp) application).getRepository();
        LiveData<List<BookEntity>> liveBookEntityData = repository.getBooks();

        mBookList = Transformations.switchMap(liveBookEntityData, new Function<List<BookEntity>,
                LiveData<List<Book>>>() {
            @Override
            public LiveData<List<Book>> apply(List<BookEntity> input) {
                MutableLiveData<List<Book>> listLiveData = new MutableLiveData<>();
                List<Book> bookList = new ArrayList<>();
                for (BookEntity bookEntity : input) {
                    bookList.add(new Book(bookEntity.getBookId(), bookEntity.getAuthorName()));
                }
                listLiveData.postValue(bookList);
                return listLiveData;
            }
        });
    }

    public LiveData<List<Book>> getBookList() {
        return mBookList;
    }
}
