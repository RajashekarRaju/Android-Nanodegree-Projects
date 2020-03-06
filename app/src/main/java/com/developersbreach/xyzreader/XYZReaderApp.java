package com.developersbreach.xyzreader;

import android.app.Application;

import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.ArticleDatabase;

public class XYZReaderApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private ArticleDatabase getDatabase() {
        return ArticleDatabase.getDatabaseInstance(this);
    }

    public ArticleRepository getRepository() {
        return ArticleRepository.getRepositoryInstance(getDatabase());
    }
}
