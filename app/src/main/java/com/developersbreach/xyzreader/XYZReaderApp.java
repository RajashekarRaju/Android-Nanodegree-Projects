package com.developersbreach.xyzreader;

import android.app.Application;

import com.developersbreach.xyzreader.repository.AppExecutors;
import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.ArticleDatabase;

public class XYZReaderApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = new AppExecutors();
    }

    private ArticleDatabase getDatabase() {
        return ArticleDatabase.getDatabaseInstance(this, mAppExecutors);
    }

    public ArticleRepository getRepository() {
        return ArticleRepository.getRepositoryInstance(getDatabase());
    }
}
