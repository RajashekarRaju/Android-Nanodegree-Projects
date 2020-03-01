package com.developersbreach.xyzreader.repository.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "articles_table")
public class ArticleEntity implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "column_article_id")
    private int mArticleId;

    @ColumnInfo(name = "column_article_author_name")
    private String mArticleAuthorName;

    public int getArticleId() {
        return mArticleId;
    }

    public String getArticleAuthorName() {
        return mArticleAuthorName;
    }

    void setArticleId(int id) {
        this.mArticleId = id;
    }

    void setArticleAuthorName(String authorName) {
        this.mArticleAuthorName = authorName;
    }

    ArticleEntity() {}

    @Ignore
    public ArticleEntity(int id, String authorName) {
        this.mArticleId = id;
        this.mArticleAuthorName = authorName;
    }

    private ArticleEntity(Parcel in) {
        mArticleId = in.readInt();
        mArticleAuthorName = in.readString();
    }

    public static final Creator<ArticleEntity> CREATOR = new Creator<ArticleEntity>() {
        @Override
        public ArticleEntity createFromParcel(Parcel in) {
            return new ArticleEntity(in);
        }

        @Override
        public ArticleEntity[] newArray(int size) {
            return new ArticleEntity[size];
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
        dest.writeInt(mArticleId);
        dest.writeString(mArticleAuthorName);
    }
}
