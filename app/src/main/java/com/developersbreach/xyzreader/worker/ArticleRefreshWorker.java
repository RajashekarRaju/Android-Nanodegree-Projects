package com.developersbreach.xyzreader.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.ArticleDatabase;

public class ArticleRefreshWorker extends Worker {

    public static final String WORKER_NAME = "ArticleRefreshWorker";
    private final ArticleRepository mRepository;

    public ArticleRefreshWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        ArticleDatabase database = ArticleDatabase.getDatabaseInstance(context);
        mRepository = ArticleRepository.getRepositoryInstance(database);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            mRepository.refreshData();
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }
}
