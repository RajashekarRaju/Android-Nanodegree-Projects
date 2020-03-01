package com.developersbreach.xyzreader.view.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.ItemBookBinding;
import com.developersbreach.xyzreader.model.Book;

import java.util.List;

import static com.developersbreach.xyzreader.view.list.BookAdapter.*;


/**
 * This class implements a {@link RecyclerView} {@link ListAdapter} which uses Data Binding to
 * present list data, including computing diffs between lists.
 * <p>
 * {@link BookViewHolder} class that extends ViewHolder that will be used by the adapter.
 */
public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {

    /**
     * Declare a new list of recipes to return with data.
     */
    private List<? extends Book> mBookList;

    /**
     * The interface that receives onClick listener.
     */
    private final BookAdapterListener mListener;

    /**
     * @param listener   create click listener on itemView.
     */
    BookAdapter(BookAdapterListener listener) {
        this.mListener = listener;
        setHasStableIds(true);
    }

    /**
     * The interface that receives onClick listener.
     */
    public interface BookAdapterListener {
        /**
         * @param book get recipes from selected list of recipes.
         * @param view   used to create navigation with controller, which needs view.
         */
        void onBookSelected(Book book, View view);
    }

    /**
     * RecipeViewHolder class creates child view Recipe properties.
     */
    static class BookViewHolder extends RecyclerView.ViewHolder {

        // Get access to binding the views in layout
        private final ItemBookBinding mBinding;


        private BookViewHolder(final ItemBookBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        /**
         * @param book pass object to set recipe for binding. This binding is accessed from layout
         *               xml {@link com.developersbreach.xyzreader.R.layout#item_book}
         */
        void bind(final Book book) {
            mBinding.setBook(book);
            // Force DataBinding to execute binding views immediately.
            mBinding.executePendingBindings();
        }
    }

    /**
     * Called by RecyclerView to display the data at the specified position. DataBinding should
     * update the contents of the {@link BookViewHolder#itemView} to reflect the item at the given
     * position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull final BookViewHolder holder, final int position) {
        final Book book = mBookList.get(position);
        holder.bind(book);

        // Set click listener on itemView and pass arguments recipe, view for selected recipe.
        holder.itemView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onBookSelected(book, view);
                    }
                });
    }

    /**
     * Called when RecyclerView needs a new {@link BookViewHolder} of the given type to represent
     * an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Allow DataBinding to inflate the layout.
        ItemBookBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_book, parent,
                false);
        return new BookViewHolder(binding);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    void setBookList(final List<? extends Book> bookList) {
        if (mBookList == null) {
            mBookList = bookList;
            notifyItemRangeInserted(0, bookList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mBookList.size();
                }

                @Override
                public int getNewListSize() {
                    return bookList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mBookList.get(oldItemPosition).getBookId() == (bookList.get(newItemPosition).getBookId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Book newProduct = bookList.get(newItemPosition);
                    Book oldProduct = bookList.get(oldItemPosition);
                    return newProduct.getBookId() == oldProduct.getBookId();
                }
            });

            mBookList = bookList;
            result.dispatchUpdatesTo(this);
        }
    }
}
