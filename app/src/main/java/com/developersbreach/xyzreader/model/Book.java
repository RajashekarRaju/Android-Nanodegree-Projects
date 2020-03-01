package com.developersbreach.xyzreader.model;

import com.developersbreach.xyzreader.repository.database.BookEntity;

public class Book extends BookEntity {

    private int mBookId;
    private String mAuthorName;

    public Book(int bookId, String authorName) {
        super(bookId, authorName);
        this.mBookId = bookId;
        this.mAuthorName = authorName;
    }

    @Override
    public int getBookId() {
        return mBookId;
    }

    @Override
    public String getAuthorName() {
        return mAuthorName;
    }
}
