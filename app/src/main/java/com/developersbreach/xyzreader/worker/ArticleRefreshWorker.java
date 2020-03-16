package com.developersbreach.xyzreader.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.developersbreach.xyzreader.repository.ArticleRepository;
import com.developersbreach.xyzreader.repository.database.ArticleDatabase;


/**
 * A class that performs work synchronously on a background thread provided by {@link WorkManager}.
 */
public class ArticleRefreshWorker extends Worker {

    // Name for our worker.
    public static final String WORKER_NAME = "ArticleRefreshWorker";
    // Get reference to app repository to complete that task here by calling.
    private final ArticleRepository mRepository;

    /**
     * @param context   The application {@link Context}
     * @param workerParams Parameters to setup the internal state of this worker
     */
    public ArticleRefreshWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        // Get instance for database class.
        ArticleDatabase database = ArticleDatabase.getDatabaseInstance(context);
        // Create repository instance with database.
        mRepository = ArticleRepository.getRepositoryInstance(database);
    }

    /**
     * Override this method to do your actual background processing.  This method is called on a
     * background thread - you are required to <b>synchronously</b> do your work and return the
     * {@link Result} from this method.  Once you return from this
     * method, the Worker is considered to have finished what its doing and will be destroyed.
     * <p>
     * A Worker is given a maximum of ten minutes to finish its execution and return a
     * {@link Result}.  After this time has expired, the Worker will
     * be signalled to stop.
     */
    @NonNull
    @Override
    public Result doWork() {
        try {
            // This task gets data from internet and adds them to database.
            // Also deletes any initial data found.
            mRepository.refreshData();
            // Indicate whether the task finished successfully with the Result.
            return Result.success();
        } catch (Exception e) {
            // Return failure as result and print exception.
            e.printStackTrace();
            return Result.failure();
        }
    }
}
