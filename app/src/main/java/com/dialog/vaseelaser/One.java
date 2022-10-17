package com.dialog.vaseelaser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dialog.vaseelaser.databinding.OneBinding;

public class One extends DialogFragmentHelper
{

    private OneBinding __binder;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        __binder = OneBinding.inflate(getLayoutInflater());
        return __binder.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                One.this.dismiss();
            }
        };

        __binder.view1.setOnClickListener(l);
        __binder.view2.setOnClickListener(l);


    }
}
