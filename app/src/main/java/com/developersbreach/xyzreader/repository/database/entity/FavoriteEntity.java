package com.developersbreach.xyzreader.repository.database.entity;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.developersbreach.xyzreader.repository.database.ArticleDatabase;
import com.developersbreach.xyzreader.repository.database.dao.FavoriteDao;

import java.util.Objects;


/**
 * This class will have a mapping SQLite "favorites_table" in the {@link ArticleDatabase}.
 * This class fields are persisted once it is annotated with {@link Entity} and used as columns
 * in databases based on type of annotation they are assigned with. We pass this class entity into
 * database class with name.
 *
 * @see FavoriteDao which uses this class table for queries.
 */
@Entity(tableName = "favorites_table")
public class FavoriteEntity extends ArticleEntity {

    /**
     * Each entity class requires at-least one primary key.
     * Fields need to be annotated as {@link ColumnInfo} with a name to generate rows and columns
     * inside the database properly.
     * <p>
     * Fields can be annotated as {@link Ignore} for not being used by room.
     * <p>
     * This entity class table column names are differed by {@link ArticleEntity} with replacing
     * "column_article_id" to "column_favorite_id".
     */
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

    @Override
    public int getArticleId() {
        return mArticleId;
    }

    @Override
    public String getArticleTitle() {
        return mArticleTitle;
    }

    @Override
    public String getArticleAuthorName() {
        return mArticleAuthorName;
    }

    @Override
    public String getArticleBody() {
        return mArticleBody;
    }

    @Override
    public String getArticleThumbnail() {
        return mArticleThumbnail;
    }

    @Override
    public String getArticlePublishedDate() {
        return mArticlePublishedDate;
    }

    ////////////////////// Setters ///////////////////////////////////////

    @Override
    public void setArticleId(int id) {
        this.mArticleId = id;
    }

    @Override
    public void setArticleTitle(String articleTitle) {
        this.mArticleTitle = articleTitle;
    }

    @Override
    public void setArticleAuthorName(String authorName) {
        this.mArticleAuthorName = authorName;
    }

    @Override
    public void setArticleBody(String articleBody) {
        this.mArticleBody = articleBody;
    }

    @Override
    public void setArticleThumbnail(String articleThumbnail) {
        this.mArticleThumbnail = articleThumbnail;
    }

    @Override
    public void setArticlePublishedDate(String articlePublishedDate) {
        this.mArticlePublishedDate = articlePublishedDate;
    }

    ///////////////////////////////////////////////////////////////////////////////////

    /**
     * Each entity must either have a no-arg public constructor.
     */
    public FavoriteEntity() {
    }

    /**
     * Let room know not to use this constructor by annotating with ignore.
     */
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

    public FavoriteEntity(Parcel in) {
        mArticleId = in.readInt();
        mArticleTitle = in.readString();
        mArticleAuthorName = in.readString();
        mArticleBody = in.readString();
        mArticleThumbnail = in.readString();
        mArticlePublishedDate = in.readString();
    }

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

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FavoriteEntity favoriteEntity = (FavoriteEntity) obj;
        return Objects.equals(mArticleId, favoriteEntity.mArticleId)
                && Objects.equals(mArticleTitle, favoriteEntity.mArticleTitle)
                && Objects.equals(mArticleAuthorName, favoriteEntity.mArticleAuthorName)
                && Objects.equals(mArticleBody, favoriteEntity.mArticleBody)
                && Objects.equals(mArticleThumbnail, favoriteEntity.mArticleThumbnail)
                && Objects.equals(mArticlePublishedDate, favoriteEntity.mArticlePublishedDate);
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the list of
     * {@link FavoriteEntity} has been updated.
     */
    public static final DiffUtil.ItemCallback<FavoriteEntity> DIFF_ITEM_CALLBACK =
            new DiffUtil.ItemCallback<FavoriteEntity>() {

                @Override
                public boolean areItemsTheSame(
                        @NonNull FavoriteEntity oldItem, @NonNull FavoriteEntity newItem) {
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull FavoriteEntity oldItem, @NonNull FavoriteEntity newItem) {
                    return oldItem.getArticleId() == newItem.getArticleId();
                }
            };
}
