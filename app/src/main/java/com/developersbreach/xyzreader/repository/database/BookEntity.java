package com.developersbreach.xyzreader.repository.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.developersbreach.xyzreader.model.Book;


@Entity(tableName = "books_table")
public class BookEntity extends Book {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "column_author")
    private String mName;

    public BookEntity(@NonNull String name) {
        this.mName = name;
    }

    public String getName() {
        return mName;
    }
}
