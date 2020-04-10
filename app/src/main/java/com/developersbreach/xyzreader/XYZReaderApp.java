package com.developersbreach.xyzreader;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.ArticleDatabase;
import com.developersbreach.xyzreader.view.settings.ThemeHelper;
import com.developersbreach.xyzreader.worker.ArticleRefreshWorker;

import java.util.concurrent.TimeUnit;


/**
 * Base class for maintaining global application state. This Application class is instantiated
 * before any other class when the process for your application/package is created.
 */
public class XYZReaderApp extends Application {

    /**
     * When this class initiates we call worker to execute the task associated with worker class
     *
     * @see ArticleRefreshWorker#doWork()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        startArticleWorker();
        applyDayNightTheme(this);
    }

    /**
     * Create an instance for {@link WorkManager} with context and work properties which are
     * required by work manager do complete the task.
     */
    private void startArticleWorker() {
        WorkManager.getInstance(getApplicationContext()).enqueueUniquePeriodicWork(
                ArticleRefreshWorker.WORKER_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingWork());
    }

    /**
     * @return creates and executes the same work once a day.
     */
    private PeriodicWorkRequest repeatingWork() {
        return new PeriodicWorkRequest.Builder(
                ArticleRefreshWorker.class, 1, TimeUnit.DAYS).build();
    }

    /**
     * @return returns instance for the database.
     */
    private ArticleDatabase getDatabase() {
        return ArticleDatabase.getDatabaseInstance(this);
    }

    /**
     * @return returns instance for the repository.
     */
    public ArticleRepository getRepository() {
        return ArticleRepository.getRepositoryInstance(getDatabase());
    }

    private void applyDayNightTheme(Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        String themePref = sharedPreferences.getString("themePref", ThemeHelper.DARK_MODE);
        ThemeHelper.applyTheme(themePref);
    }
}
