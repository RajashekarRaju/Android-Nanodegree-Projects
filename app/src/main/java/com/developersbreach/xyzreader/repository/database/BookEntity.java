package com.developersbreach.xyzreader.repository.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "books_table")
public class BookEntity implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "column_id")
    private int mBookId;

    @ColumnInfo(name = "column_authorName")
    private String mAuthorName;

    public int getBookId() {
        return mBookId;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    void setBookId(int mBookId) {
        this.mBookId = mBookId;
    }

    void setAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }

    BookEntity() {}

    @Ignore
    public BookEntity(int id, String authorName) {
        this.mBookId = id;
        this.mAuthorName = authorName;
    }

    private BookEntity(Parcel in) {
        mBookId = in.readInt();
        mAuthorName = in.readString();
    }

    public static final Creator<BookEntity> CREATOR = new Creator<BookEntity>() {
        @Override
        public BookEntity createFromParcel(Parcel in) {
            return new BookEntity(in);
        }

        @Override
        public BookEntity[] newArray(int size) {
            return new BookEntity[size];
        }
    };

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mBookId);
        dest.writeString(mAuthorName);
    }
}
