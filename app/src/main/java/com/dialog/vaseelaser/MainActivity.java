package com.dialog.vaseelaser;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dialog.vaseelaser.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding __binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        __binder = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(__binder.getRoot());


        __binder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                One one = new One();
                one.show(getSupportFragmentManager(),"e t h e r");
            }
        });
    }
}