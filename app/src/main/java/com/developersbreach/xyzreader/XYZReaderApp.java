package com.developersbreach.xyzreader;

import android.app.Application;

import com.developersbreach.xyzreader.repository.AppExecutors;
import com.developersbreach.xyzreader.repository.BooksRepository;
import com.developersbreach.xyzreader.repository.database.BookDatabase;

public class XYZReaderApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public BookDatabase getDatabase() {
        return BookDatabase.getDatabaseInstance(this, mAppExecutors);
    }

    public BooksRepository getRepository() {
        return BooksRepository.getRepositoryInstance(getDatabase());
    }
}
