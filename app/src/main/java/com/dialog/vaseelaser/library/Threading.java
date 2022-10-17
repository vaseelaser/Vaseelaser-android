package com.dialog.vaseelaser.library;


import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.dialog.vaseelaser.R;
import com.google.android.material.snackbar.Snackbar;

@SuppressWarnings({"Convert2Lambda"})
public class Threading {

    // reminder: Always clean static vars of protected scope.
    //private static AsyncLayoutInflater inflater;

    public static void runDelayed(Runnable runnable, long millis) {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(runnable, millis);
    }

    public static void popSnackbar(final AppCompatActivity act, final DialogFragment dialogParent, final String msg, final boolean isLong) {
        if (act != null && msg != null && dialogParent != null) {
            if (act.getBaseContext() == null && dialogParent.getContext() == null) return;

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {

                    Dialog thisDialog = dialogParent.getDialog();
                    if (thisDialog != null) {
                        Window w = thisDialog.getWindow();
                        if (w != null) {
                            View parentLayout = w.findViewById(android.R.id.content);
                            Snackbar bar = Snackbar.make(parentLayout, msg, isLong ? 6000 : 3000);
                                /*.setAction("CLOSE", new OnUniqueClickListener() {
                                    @Override
                                    public void onSingleClick(View view) {
                                    }
                                })
                                .setActionTextColor(act.getResources().getColor(android.R.color.holo_red_light ))*/
                            try {
                                // muehehe
                                TextView tv = bar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                                Drawable d = AppCompatResources.getDrawable(act, R.mipmap.ic_launcher_round);


                                tv.setCompoundDrawablesRelativeWithIntrinsicBounds(d, null, null, null);
                                tv.setCompoundDrawablePadding(10);
                                tv.setMinLines(1);
                                tv.setMaxLines(10);
                                tv.setGravity(Gravity.CENTER);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            bar.setBackgroundTint(ContextCompat.getColor(act, R.color.snackbar_bg));
                            bar.setTextColor(ContextCompat.getColor(act, R.color.snackbar_text));
                            bar.show();
                        }
                    }
                }
            });
        }
        // }

    }

    public static void popSnackbar(final AppCompatActivity act, final String msg, final boolean isLong) {
        if (act != null && msg != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {

                    View parentLayout = act.findViewById(android.R.id.content);
                    Snackbar bar = Snackbar.make(parentLayout, msg, isLong ? 6000 : 3000);
                                /*.setAction("CLOSE", new OnUniqueClickListener() {
                                    @Override
                                    public void onSingleClick(View view) {
                                    }
                                })
                                .setActionTextColor(act.getResources().getColor(android.R.color.holo_red_light ))*/
                    try {
                        // muehehe
                        TextView tv = bar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                        Drawable d = AppCompatResources.getDrawable(act, R.mipmap.ic_launcher_round);


                        tv.setCompoundDrawablesRelativeWithIntrinsicBounds(d, null, null, null);
                        tv.setCompoundDrawablePadding(10);
                        tv.setMinLines(1);
                        tv.setMaxLines(10);
                        tv.setGravity(Gravity.CENTER);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    bar.setBackgroundTint(ContextCompat.getColor(act, R.color.snackbar_bg));
                    bar.setTextColor(ContextCompat.getColor(act, R.color.snackbar_text));
                    bar.show();
                }
            });
        }
        // }

    }




    public static void popSnackbar(final AppCompatActivity act, final View parent, final String msg, final boolean isLong) {
        if (act != null && msg != null && parent != null) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {


                    Snackbar bar = Snackbar.make(parent, msg, isLong ? 6000 : 3000);
                                /*.setAction("CLOSE", new OnUniqueClickListener() {
                                    @Override
                                    public void onSingleClick(View view) {
                                    }
                                })
                                .setActionTextColor(act.getResources().getColor(android.R.color.holo_red_light ))*/
                    try {
                        // muehehe
                        TextView tv = bar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                        Drawable d = AppCompatResources.getDrawable(act, R.mipmap.ic_launcher_round);


                        tv.setCompoundDrawablesRelativeWithIntrinsicBounds(d, null, null, null);
                        tv.setCompoundDrawablePadding(10);
                        tv.setMinLines(1);
                        tv.setMaxLines(10);
                        tv.setGravity(Gravity.CENTER);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    bar.setBackgroundTint(ContextCompat.getColor(act, R.color.snackbar_bg));
                    bar.setTextColor(ContextCompat.getColor(act, R.color.snackbar_text));
                    bar.show();
                }
            });
        }
// }

    }

}