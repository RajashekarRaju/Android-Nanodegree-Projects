package com.developersbreach.xyzreader.utils;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.xyzreader.R;

public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    private final int mItemOffset;

    private RecyclerViewItemDecoration(int itemOffset) {
        mItemOffset = itemOffset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent,
                               @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
    }

    public static void setItemSpacing(Resources resources, RecyclerView recyclerView) {
        int spacingInPixels = resources.getDimensionPixelSize(R.dimen.recycler_view_spacing_dimen);
        recyclerView.addItemDecoration(new RecyclerViewItemDecoration(spacingInPixels));
    }
}
