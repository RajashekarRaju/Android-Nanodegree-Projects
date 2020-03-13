package com.developersbreach.xyzreader;

import android.app.Application;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.ArticleDatabase;
import com.developersbreach.xyzreader.worker.ArticleRefreshWorker;

import java.util.concurrent.TimeUnit;

public class XYZReaderApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        startArticleWorker();
    }

    private void startArticleWorker() {
        WorkManager.getInstance(getApplicationContext()).enqueueUniquePeriodicWork(
                ArticleRefreshWorker.WORKER_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingWork());
    }

    private PeriodicWorkRequest repeatingWork() {
        return new PeriodicWorkRequest.Builder(
                ArticleRefreshWorker.class,1, TimeUnit.DAYS).build();
    }

    private ArticleDatabase getDatabase() {
        return ArticleDatabase.getDatabaseInstance(this);
    }

    public ArticleRepository getRepository() {
        return ArticleRepository.getRepositoryInstance(getDatabase());
    }
}
