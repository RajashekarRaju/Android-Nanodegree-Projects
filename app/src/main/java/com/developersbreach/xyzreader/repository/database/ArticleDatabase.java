package com.developersbreach.xyzreader.repository.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.developersbreach.xyzreader.repository.AppExecutors;
import com.developersbreach.xyzreader.repository.network.JsonUtils;
import com.developersbreach.xyzreader.repository.network.ResponseBuilder;

import java.io.IOException;
import java.util.List;

@Database(entities = ArticleEntity.class, version = 1, exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Book_Database";

    public abstract ArticleDao articleDao();

    private static ArticleDatabase sINSTANCE;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static ArticleDatabase getDatabaseInstance(final Context context, final AppExecutors executors) {
        if (sINSTANCE == null) {
            synchronized (ArticleDatabase.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = buildDatabase(context.getApplicationContext(), executors);
                    sINSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sINSTANCE;
    }

    private static ArticleDatabase buildDatabase(final Context context, final AppExecutors executors) {

        return Room.databaseBuilder(context, ArticleDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.databaseThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                //addDelay();

                                try {
                                    ArticleDatabase database = ArticleDatabase.getDatabaseInstance(context, executors);
                                    String responseString = ResponseBuilder.startResponse();
                                    List<ArticleEntity> articleEntityList = JsonUtils.fetchArticleJsonData(responseString);
                                    insertData(database, articleEntityList);
                                    // notify that the database was created and it's ready to be used
                                    database.setDatabaseCreated();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).build();
    }


    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertData(final ArticleDatabase database, final List<ArticleEntity> articleEntityList) {
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                database.articleDao().insertAllArticles(articleEntityList);
            }
        });
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }
}
