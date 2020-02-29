package com.developersbreach.xyzreader.view.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.FragmentBookListBinding;
import com.developersbreach.xyzreader.model.Book;
import com.developersbreach.xyzreader.repository.database.BookEntity;
import com.developersbreach.xyzreader.viewModel.BookListViewModel;

import java.util.List;


public class BookListFragment extends Fragment {

    private RecyclerView mBookRecyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentBookListBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_book_list, container, false);

        mBookRecyclerView = binding.booksRecyclerView;
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BookListViewModel viewModel = new ViewModelProvider(this).get(BookListViewModel.class);

        viewModel.getBookList().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> bookList) {
                BookAdapter adapter = new BookAdapter(new BoolItemListener());
                adapter.setBookList(bookList);
                mBookRecyclerView.setAdapter(adapter);
            }
        });
    }

    private static class BoolItemListener implements BookAdapter.BookAdapterListener {
        /**
         * @param book get recipes from selected list of recipes.
         * @param view used to create navigation with controller, which needs view.
         */
        @Override
        public void onBookSelected(Book book, View view) {
            NavDirections direction = BookListFragmentDirections
                    .actionBookListFragmentToBookDetailFragment(book);
            // Find NavController with view and navigate to destination using directions.
            Navigation.findNavController(view).navigate(direction);
        }
    }
}
