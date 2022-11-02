package com.dialog.vaseelaser;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dialog.vaseelaser.databinding.OneBinding;
import com.dialog.vaseelaser.library.EtherListener;
import com.dialog.vaseelaser.library.PopUp;
import com.dialog.vaseelaser.library.ProgressDialog;
import com.dialog.vaseelaser.library.Vaporizer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class One extends DialogFragmentHelper {

    private OneBinding __binder;
    String s = "";
    private ProgressDialog mDialog;

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
        __binder.view3.setOnClickListener(l);


        __binder.viewGenerator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog d = new ProgressDialog(_pActivity);
                d.show();
                Vaporizer.translate(_pActivity, "welcome", new EtherListener() {
                    @Override
                    public void onEtherReceived(List<String> response, int code, String calledURL) {
                        d.dismiss();

                        for (String z : response) {
                            s += z + "\n\n";
                        }

                        One.this.dismiss();

                        PopUp.Custom z = new PopUp.Custom(_pActivity, R.layout.dialog_generated_ether);
                        z.onRenderDone(new Runnable() {
                            @Override
                            public void run() {
                                ((TextView) z.grabView(R.id.container)).setText(s);
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

        __binder.viewBend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialog = new ProgressDialog(_pActivity);
                mDialog.show();
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(i);
            }
        });
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {

                        Uri selectedImageUri = result.getData().getData();
                        if (null != selectedImageUri) {
                            Vaporizer.__i_m_a_g_e_b_e_n_d_power_fulLlLly(_pActivity, selectedImageUri, new IImageSelectedListener() {
                                @Override
                                public void onImageSelected(String benturl) {

                                    if (mDialog != null) {
                                        mDialog.dismiss();
                                    }


                                    PopUp.Custom c = new PopUp.Custom(_pActivity, R.layout.dialog_fixed_img);
                                    c.onRenderDone(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.e("sdsdsds", benturl);
                                            ImageView v = c.grabView(R.id.imgcontainer);
                                            if (Objects.equals(benturl, "__dead__")) {

                                                Picasso.get().load(R.drawable.dead)
                                                        .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                                                        .into(v, new Callback() {
                                                            @Override
                                                            public void onSuccess() {

                                                            }

                                                            @Override
                                                            public void onError(Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        });
                                            } else {


                                                Picasso.get().load("http://"+benturl)
                                                        .placeholder(R.drawable.loading)
                                                        .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                                                        .into(v, new Callback() {
                                                            @Override
                                                            public void onSuccess() {

                                                            }

                                                            @Override
                                                            public void onError(Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        });
                                            }

                                            c.setViewAction(R.id.dialog_default_ok, c::dismiss);
                                            c.setViewAction(R.id.x,c::dismiss);

                                        }
                                    });
                                    c.pop();
                                }
                            });
                        }

                    }
                }
            });

}
