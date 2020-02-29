package com.developersbreach.xyzreader.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.developersbreach.xyzreader.repository.database.BookDatabase;
import com.developersbreach.xyzreader.repository.database.BookEntity;

import java.util.List;

public class BooksRepository {

    private static BooksRepository sINSTANCE;
    private final BookDatabase mDatabase;
    private MediatorLiveData<List<BookEntity>> mObservableBooks;

    public BooksRepository(BookDatabase database) {
        this.mDatabase = database;
        mObservableBooks = new MediatorLiveData<>();

        mObservableBooks.addSource(mDatabase.bookDao().loadAllBooks(), new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> bookEntityList) {
                mObservableBooks.postValue(bookEntityList);
            }
        });
    }

    public static BooksRepository getRepositoryInstance(final BookDatabase database) {
        if (sINSTANCE == null) {
            synchronized (BooksRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new BooksRepository(database);
                }
            }
        }
        return sINSTANCE;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<BookEntity>> getBooks() {
        return mObservableBooks;
    }
}
