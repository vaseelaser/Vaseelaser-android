package com.dialog.vaseelaser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dialog.vaseelaser.databinding.OneBinding;
import com.dialog.vaseelaser.library.EtherListener;
import com.dialog.vaseelaser.library.PopUp;
import com.dialog.vaseelaser.library.ProgressDialog;
import com.dialog.vaseelaser.library.Vaporizer;

import java.util.List;

public class One extends DialogFragmentHelper
{

    private OneBinding __binder;
    String s = "";

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


        __binder.viewGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog d = new ProgressDialog(_pActivity);
                d.show();
                Vaporizer.translate(_pActivity, "welcome", new EtherListener() {
                    @Override
                    public void onEtherReceived(List<String> response, int code, String calledURL) {
                        d.dismiss();

                        for (String z : response){
                            s += z + "\n\n";
                        }

                        One.this.dismiss();

                        PopUp.Custom z = new PopUp.Custom(_pActivity,R.layout.dialog_generated_ether);
                        z.onRenderDone(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView)z.grabView(R.id.container)).setText(s);
                                z.grabView(R.id.x).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        z.dismiss();
                                    }
                                });
                            }
                        });
                        z.setViewAction(R.id.dialog_default_ok, new Runnable() {
                            @Override
                            public void run() {
                                z.dismiss();
                            }
                        });


                        z.pop();
                    }
                });
            }
        });
    }
}
