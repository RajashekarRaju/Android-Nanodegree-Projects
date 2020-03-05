package com.developersbreach.xyzreader.repository.database;

import android.content.Context;

import androidx.annotation.NonNull;
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

    public static ArticleDatabase getDatabaseInstance(final Context context) {
        if (sINSTANCE == null) {
            synchronized (ArticleDatabase.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = buildDatabase(context.getApplicationContext());
                }
            }
        }
        return sINSTANCE;
    }

    private static ArticleDatabase buildDatabase(final Context context) {

        return Room.databaseBuilder(context, ArticleDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        AppExecutors.getInstance().databaseThread().execute(() -> {
                            try {
                                ArticleDatabase database = ArticleDatabase.getDatabaseInstance(context);
                                String responseString = ResponseBuilder.startResponse();
                                List<ArticleEntity> articleEntityList = JsonUtils.fetchArticleJsonData(responseString);
                                insertData(database, articleEntityList);
                                // notify that the database was created and it's ready to be used
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }).build();
    }


    private static void insertData(final ArticleDatabase database, final List<ArticleEntity> articleEntityList) {
        database.runInTransaction(() -> database.articleDao().insertAllArticles(articleEntityList));
    }
}
