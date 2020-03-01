package com.developersbreach.xyzreader.repository;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * AppExecutors class let us create Executors for completing tasks given by creating threads
 * on main thread, background, database.
 */
public class AppExecutors {

    // Create single instance
    private static final Object LOCK = new Object();
    // Create single instance
    private static AppExecutors sINSTANCE;
    // Create an object which executes any given task in main thread.
    private final Executor mMainThread;
    // Create an object which executes any given task in background thread.
    private final Executor mBackgroundThread;
    // Create an object which executes task to work with database in thread.
    private final Executor mDatabaseThread;

    public AppExecutors(Executor mainThread, Executor backgroundThread, Executor databaseThread) {
        this.mMainThread = mainThread;
        this.mBackgroundThread = backgroundThread;
        this.mDatabaseThread = databaseThread;
    }

    public AppExecutors() {
        this(new MainThreadExecutor(), Executors.newFixedThreadPool(3),
                Executors.newSingleThreadExecutor());
    }

    // This executor runs on main thread, we call this executor with handler
    private static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }

    // This executor runs on main thread.
    public Executor mainThread() {
        return mMainThread;
    }

    // This executor used to do background operations.
    public Executor backgroundThread() {
        return mBackgroundThread;
    }

    // This execute does database operations.
    public Executor databaseThread() {
        return mDatabaseThread;
    }
}
