package com.developersbreach.xyzreader.repository.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM books_table")
    LiveData<List<BookEntity>> loadAllBooks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BookEntity> bookEntityList);

    @Query("SELECT * FROM books_table WHERE column_id = :bookId")
    LiveData<BookEntity> getBookById(int bookId);
}
