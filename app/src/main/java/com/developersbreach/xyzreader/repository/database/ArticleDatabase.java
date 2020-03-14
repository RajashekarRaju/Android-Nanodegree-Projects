package com.developersbreach.xyzreader.repository.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.developersbreach.xyzreader.repository.database.dao.ArticleDao;
import com.developersbreach.xyzreader.repository.database.dao.FavoriteDao;
import com.developersbreach.xyzreader.repository.database.entity.ArticleEntity;
import com.developersbreach.xyzreader.repository.database.entity.FavoriteEntity;


/**
 * Let room know this is class for database by annotating with {@link Database} and class should be
 * an abstract class and extend to {@link RoomDatabase}.
 * <p>
 * Pass our two entity classes into database, version set 1 and exportSchema to false to not create
 * a separate space folder for our app database.
 */
@Database(entities = {ArticleEntity.class, FavoriteEntity.class}, version = 1, exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {

    // Name for the app database.
    private static final String DATABASE_NAME = "Article_Database";
    // Helps to create single instance for this class.
    private static ArticleDatabase sINSTANCE;

    /**
     * @return get access to all article objects mapped to database.
     */
    public abstract ArticleDao articleDao();

    /**
     * @return get access to all favorite article objects mapped to database.
     */
    public abstract FavoriteDao favoriteDao();

    /**
     * @param context needs context to build database.
     * @return a new instance for database. Perform null checks and do not perform asynchronous,
     * finally return instance for database without creating multiple.
     */
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
