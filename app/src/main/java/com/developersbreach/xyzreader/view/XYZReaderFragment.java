package com.developersbreach.xyzreader.view;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.developersbreach.xyzreader.R;
import com.developersbreach.xyzreader.databinding.FragmentXyzReaderBinding;

import java.util.Objects;

import static com.developersbreach.xyzreader.view.MainActivity.LOTTIE_PREFERENCE_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class XYZReaderFragment extends Fragment {

    private FragmentXyzReaderBinding mBinding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_xyz_reader, container,
                false);
        mBinding.lottieAnimation.setImageAssetsFolder("assets");
        return mBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBinding.lottieAnimation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Navigation.findNavController(mBinding.getRoot())
                        .navigate(R.id.action_XYZReaderFragment_to_articleListFragment);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Navigation.findNavController(mBinding.getRoot())
                        .navigate(R.id.action_XYZReaderFragment_to_articleListFragment);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        applyLottieWithPreferences(Objects.requireNonNull(getActivity()));
    }

    private void applyLottieWithPreferences(Activity activity) {
        SharedPreferences preferences = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(LOTTIE_PREFERENCE_KEY, true);
        editor.apply();
    }
}
