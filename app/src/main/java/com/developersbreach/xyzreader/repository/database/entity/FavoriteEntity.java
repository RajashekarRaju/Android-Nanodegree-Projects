package com.developersbreach.xyzreader.repository.database.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "favourite_table")
public class FavoriteEntity implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "column_favorite_id")
    private int mArticleId;

    @ColumnInfo(name = "column_favorite_title")
    private String mArticleTitle;

    @ColumnInfo(name = "column_favorite_author_name")
    private String mArticleAuthorName;

    @ColumnInfo(name = "column_favorite_body")
    private String mArticleBody;

    @ColumnInfo(name = "column_favorite_thumbnail")
    private String mArticleThumbnail;

    @ColumnInfo(name = "column_favorite_published_date")
    private String mArticlePublishedDate;

    /////////////////// Getters /////////////////////

    public int getArticleId() {
        return mArticleId;
    }

    public String getArticleTitle() {
        return mArticleTitle;
    }

    public String getArticleAuthorName() {
        return mArticleAuthorName;
    }

    public String getArticleBody() {
        return mArticleBody;
    }

    public String getArticleThumbnail() {
        return mArticleThumbnail;
    }

    public String getArticlePublishedDate() {
        return mArticlePublishedDate;
    }

    ////////////////////// Setters ///////////////////////////////////////

    public void setArticleId(int id) {
        this.mArticleId = id;
    }

    public void setArticleTitle(String articleTitle) {
        this.mArticleTitle = articleTitle;
    }

    public void setArticleAuthorName(String authorName) {
        this.mArticleAuthorName = authorName;
    }

    public void setArticleBody(String articleBody) {
        this.mArticleBody = articleBody;
    }

    public void setArticleThumbnail(String articleThumbnail) {
        this.mArticleThumbnail = articleThumbnail;
    }

    public void setArticlePublishedDate(String articlePublishedDate) {
        this.mArticlePublishedDate = articlePublishedDate;
    }

    ///////////////////////////////////////////////////////////////////////////////////

    public FavoriteEntity() {
    }

    @Ignore
    public FavoriteEntity(int id, String title, String authorName, String body, String thumbnail,
                          String publishedDate) {
        this.mArticleId = id;
        this.mArticleTitle = title;
        this.mArticleAuthorName = authorName;
        this.mArticleBody = body;
        this.mArticleThumbnail = thumbnail;
        this.mArticlePublishedDate = publishedDate;
    }

    ////////////////////////////////////////////////////////////////////////////

    public FavoriteEntity(Parcel in) {
        mArticleId = in.readInt();
        mArticleTitle = in.readString();
        mArticleAuthorName = in.readString();
        mArticleBody = in.readString();
        mArticleThumbnail = in.readString();
        mArticlePublishedDate = in.readString();
    }

    public static final Creator<FavoriteEntity> CREATOR = new Creator<FavoriteEntity>() {
        @Override
        public FavoriteEntity createFromParcel(Parcel in) {
            return new FavoriteEntity(in);
        }

        @Override
        public FavoriteEntity[] newArray(int size) {
            return new FavoriteEntity[size];
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
        dest.writeString(mArticleTitle);
        dest.writeString(mArticleAuthorName);
        dest.writeString(mArticleBody);
        dest.writeString(mArticleThumbnail);
        dest.writeString(mArticlePublishedDate);
    }
}
