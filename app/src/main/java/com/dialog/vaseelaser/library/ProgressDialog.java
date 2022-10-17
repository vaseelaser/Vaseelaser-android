package com.dialog.vaseelaser.library;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dialog.vaseelaser.R;
import com.dialog.vaseelaser.databinding.CustomBottomProgressBinding;


public class ProgressDialog extends DialogFragment {


    private final AppCompatActivity mActivity;
    private CustomBottomProgressBinding __binder;

    public ProgressDialog(final AppCompatActivity act) {
        mActivity = act;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        __binder = CustomBottomProgressBinding.inflate(inflater);
        return __binder.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.setCancelable(false);
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT; // thats the important thing
            if (dialog.getWindow() != null) {
                Window thisWindow = dialog.getWindow();
                thisWindow.setLayout(width, height);
                thisWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                WindowManager.LayoutParams windowParams = thisWindow.getAttributes();
                windowParams.dimAmount = 0.0f;
                windowParams.windowAnimations = R.style.BottomDialogAnim;

                thisWindow.setAttributes(windowParams);

            } else {
                Log.e("BottomProgress", "Could not attach window");
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Animator.Slide.fromTopToVisible(__binder.progressContainer, 10, mActivity, false);
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, "bottomprogress");
        ft.commitAllowingStateLoss();

    }

    public void dismiss() {
        Log.e("ProgressDialog", "Killing progress dialog");
        try {
            this.dismissAllowingStateLoss();
        } catch (Exception e) {
            //if this blows, that means that our fragment(bottom dialog) is
            // not attached to a fragment manager;
            e.printStackTrace();
        }
    }

    public void show() {

        FragmentTransaction fragmentTransaction = mActivity.getSupportFragmentManager().beginTransaction();
        Fragment prev = mActivity.getSupportFragmentManager().findFragmentByTag("bottomprogress");
        if (prev != null) {
            fragmentTransaction.remove(prev);
        }
        fragmentTransaction.addToBackStack(null);
        this.show(fragmentTransaction, "bottomprogress");
    }

}