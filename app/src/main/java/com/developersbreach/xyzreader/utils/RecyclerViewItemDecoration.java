package com.developersbreach.xyzreader.utils;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.view.favorite.ArticleFavoritesFragment;
import com.developersbreach.xyzreader.view.list.ArticleListFragment;


/**
 * Item decoration for cardViews for equal spacing inside recyclerView.
 */
public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    // Variable of type int which gives equal spacing for all sides.
    private final int mItemOffset;

    private RecyclerViewItemDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    /**
     * @param outRect which has 4 edges for view to set equal spacing. Pass the same variable for
     *                all 4 sides of the outRect for the result.
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
    }

    /**
     * @param resources provides access for getting resources.
     * @param recyclerView which needs itemDecoration with spacing.
     * @see ArticleFavoritesFragment#onCreateView(LayoutInflater, ViewGroup, Bundle)
     * @see ArticleListFragment#onCreateView(LayoutInflater, ViewGroup, Bundle) for implementations.
     */
    public static void setItemSpacing(Resources resources, RecyclerView recyclerView) {
        int spacingInPixels = resources.getDimensionPixelSize(R.dimen.recycler_view_spacing_dimen);
        recyclerView.addItemDecoration(new RecyclerViewItemDecoration(spacingInPixels));
    }
}
