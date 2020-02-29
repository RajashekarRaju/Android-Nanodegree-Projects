package com.developersbreach.xyzreader.view.detail;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.developersbreach.xyzreader.model.Book;

import java.util.List;

public class BookViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Book> mBookList;

    /**
     * Constructor for {@link FragmentStatePagerAdapter}.
     *
     * @param fm       fragment manager that will interact with this adapter
     * @param bookList list
     */
    public BookViewPagerAdapter(@NonNull FragmentManager fm, int behaviour, List<Book> bookList) {
        super(fm, behaviour);
        mBookList = bookList;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position position
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Book book = mBookList.get(position);
        return BookDetailFragment.newInstance(book);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mBookList.size();
    }
}
