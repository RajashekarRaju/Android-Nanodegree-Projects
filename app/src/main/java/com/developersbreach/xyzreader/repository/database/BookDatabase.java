package com.developersbreach.xyzreader.repository.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
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

@Database(entities = BookEntity.class, version = 1, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Book_Database";

    public abstract BookDao bookDao();

    private static BookDatabase sINSTANCE;

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static BookDatabase getDatabaseInstance(final Context context, final AppExecutors executors) {
        if (sINSTANCE == null) {
            synchronized (BookDatabase.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = buildDatabase(context.getApplicationContext(), executors);
                    sINSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sINSTANCE;
    }

    private static BookDatabase buildDatabase(final Context context, final AppExecutors executors) {

        return Room.databaseBuilder(context, BookDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.databaseThread().execute(new Runnable() {
                            @Override
                            public void run() {
                                addDelay();

                                try {
                                    BookDatabase database = BookDatabase.getDatabaseInstance(context, executors);
                                    String responseString = ResponseBuilder.startResponse();
                                    List<BookEntity> bookEntityList = JsonUtils.fetchBookJsonData(responseString);
                                    insertData(database, bookEntityList);
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

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    private LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    private static void insertData(final BookDatabase database, final List<BookEntity> bookEntityList) {
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                database.bookDao().insertAll(bookEntityList);
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
