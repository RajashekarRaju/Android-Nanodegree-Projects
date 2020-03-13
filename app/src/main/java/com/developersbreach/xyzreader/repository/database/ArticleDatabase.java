package com.developersbreach.xyzreader.repository.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.developersbreach.xyzreader.repository.database.dao.ArticleDao;
import com.developersbreach.xyzreader.repository.database.dao.FavoriteDao;
import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;

@Database(entities = {ArticleEntity.class, FavoriteEntity.class}, version = 1, exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Article_Database";
    private static ArticleDatabase sINSTANCE;

    public abstract ArticleDao articleDao();
    public abstract FavoriteDao favoriteDao();

    public static ArticleDatabase getDatabaseInstance(final Context context) {
        if (sINSTANCE == null) {
            synchronized (ArticleDatabase.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = Room.databaseBuilder(context, ArticleDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return sINSTANCE;
    }
}
