package com.developersbreach.xyzreader.view.detail;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.FragmentBookDetailBinding;
import com.developersbreach.xyzreader.model.Book;
import com.developersbreach.xyzreader.viewModel.BookDetailViewModel;
import com.developersbreach.xyzreader.viewModel.factory.BookDetailViewModelFactory;

import java.util.Objects;

public class BookDetailFragment extends Fragment {

    private FragmentBookDetailBinding mBinding;

    static BookDetailFragment newInstance(Book book) {

        Bundle args = new Bundle();

        BookDetailFragment fragment = new BookDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_book_detail, container, false);

        mBinding.setLifecycleOwner(this);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = Objects.requireNonNull(getArguments());
        // Get this activity reference as non-null.
        Activity activity = Objects.requireNonNull(getActivity());
        // Get application context for this class for factory to create instance of ViewModel.
        Application application = activity.getApplication();
        // After passing arguments with name and type of value in NavGraph, it is necessary to
        // perform a gradle build for android studio to auto-generate all required classes and
        // objects for this class using NavigationComponent library.
        // get arguments with name and receive arguments from bundle.
        Book bookArgs = BookDetailFragmentArgs.fromBundle(args).getDetailFragmentArgs();
        // Call factory for creating new instance of ViewModel for this fragment to observe data.
        // Pass application context and recipe object to the factory.
        BookDetailViewModelFactory factory =
                new BookDetailViewModelFactory(application, bookArgs);
        // Assign and get class ViewModel and pass fragment owner and factory to create instance
        // by calling ViewModelProviders.

        BookDetailViewModel viewModel = new ViewModelProvider(this, factory).get(BookDetailViewModel.class);
        viewModel.selectedBook().observe(getViewLifecycleOwner(), new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                mBinding.setBookDetail(book);
                // Force binding to execute immediately all views.
                mBinding.executePendingBindings();
            }
        });
    }

}
