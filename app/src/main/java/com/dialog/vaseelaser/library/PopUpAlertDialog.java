package com.dialog.vaseelaser.library;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.DialogFragment;


import com.dialog.vaseelaser.R;

import java.util.ArrayList;

@SuppressWarnings("Convert2Lambda")
public class PopUpAlertDialog extends DialogFragment {

    private final static int ANIM_DURATION_MS = 0;
    private final CompatAlertBundle mBundle;
    private View mRootView;
    private final boolean mIsNative;
    private ProgressDialog mProgress;

    private int ANIM_IN_MS;
    private int ANIM_OUT_MS;
    private View mNonNativeDeadArea;

    /* package privileges */
    PopUpAlertDialog(CompatAlertBundle bundle) {
        mBundle = bundle;
        mIsNative = (mBundle.getLayoutID() == R.layout.misc_popupalert_native);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            View v = mBundle.getActivity().findViewById(R.id.project_root_view);
            int width = v.getWidth();
            int height = v.getHeight();

            if (dialog.getWindow() != null) {
                Window thisWindow = dialog.getWindow();
                thisWindow.setLayout(width, height);
                WindowManager.LayoutParams windowParams = thisWindow.getAttributes();
                windowParams.dimAmount = 0.0f;
                windowParams.windowAnimations = R.style.CompatPopupAlert;
                thisWindow.setAttributes(windowParams);
                thisWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                //add start end padding to the whole dialog
                thisWindow.getDecorView().setPadding(0, 0, 0, 0);

            } else {
                Log.e("DialogFragmentHelper", "Could not attach window");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(mBundle.getLayoutID(), container, false);

        if (!mIsNative) mNonNativeDeadArea = mRootView.findViewById(R.id.dead_area);
        ANIM_IN_MS = 0;
        ANIM_OUT_MS = 0;

        if (mBundle.getInitWithDialog()) {
            mProgress = new ProgressDialog(mBundle.getActivity());
            mProgress.show();
        }

        if (mBundle.getInitWithAlphaZero())
            mRootView.setAlpha(0);

        if (!mIsNative) {
            mNonNativeDeadArea.setAlpha(0);

            mRootView.findViewById(R.id.x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rootDismiss();
                }
            });
        } else {
            mRootView.findViewById(R.id.anim_helper).setAlpha(0);
            mRootView.findViewById(R.id.anim_helper).animate().alpha(1)
                    .setStartDelay(ANIM_IN_MS)
                    .setDuration(ANIM_IN_MS).start();
        }

        return mRootView;
    }

    /* override */
    public void rootDismiss() {

        if (!mIsNative) {
            mNonNativeDeadArea.setAlpha(1);
            mNonNativeDeadArea.animate().alpha(0).setDuration(ANIM_OUT_MS).start();
            Threading.runDelayed(new Runnable() {
                @Override
                public void run() {
                    mNonNativeDeadArea.setVisibility(View.GONE);
                    ObjectAnimator anim = ObjectAnimator.ofFloat(mRootView, "translationY", mRootView.getHeight());
                    anim.setDuration(ANIM_OUT_MS);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mRootView.setVisibility(View.GONE);
                            try {
                                PopUpAlertDialog.this.dismissAllowingStateLoss();
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                    anim.start();
                }
            }, ANIM_OUT_MS);


        } else {
            ArrayList<ObjectAnimator> arrayListObjectAnimators = new ArrayList<>(); //ArrayList of ObjectAnimators

            ObjectAnimator fadeout = ObjectAnimator.ofPropertyValuesHolder(mRootView.findViewById(R.id.anim_helper),
                    PropertyValuesHolder.ofFloat(View.ALPHA, 1.0f, 0.0f));

            ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(mRootView.findViewById(R.id.dialog_default_container),
                    PropertyValuesHolder.ofFloat("translationY", mRootView.findViewById(R.id.dialog_default_container).getHeight()));
            ObjectAnimator fadeoutwindow = ObjectAnimator.ofPropertyValuesHolder(mRootView.findViewById(R.id.dialog_default_container),
                    PropertyValuesHolder.ofFloat(View.ALPHA, 1.0f, 0.0f));

            arrayListObjectAnimators.add(fadeout);
            arrayListObjectAnimators.add(scaleDown);
            arrayListObjectAnimators.add(fadeoutwindow);
            ObjectAnimator[] animators = arrayListObjectAnimators.toArray(new ObjectAnimator[3]);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(animators);
            set.setDuration(ANIM_OUT_MS);
            set.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    try {
                        PopUpAlertDialog.this.dismissAllowingStateLoss();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            set.start();
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mIsNative) {
            ((TextView) mRootView.findViewById(R.id.dialog_default_title)).setText(mBundle.getTitle());
            ((TextView) mRootView.findViewById(R.id.dialog_default_body)).setText(mBundle.getBody());

            if (mBundle.getYesNo()) {
                ((Button) mRootView.findViewById(R.id.dialog_default_ok)).setText("ｙｅｓ　ン媛映");
                ((Button) mRootView.findViewById(R.id.dialog_default_cancel)).setText("ｎｏ　疫院ぜ :(");
            }

            mRootView.findViewById(R.id.dialog_default_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mBundle.getPositiveRunnable() != null) {
                        mBundle.getPositiveRunnable().run();
                        try {
                            PopUpAlertDialog.this.dismissAllowingStateLoss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            // it may fail due to wrong developer code
                            // (dismissing from OK click is wrong since its automatically dismisses)
                        }
                    } else dismiss();
                }
            });

            mRootView.findViewById(R.id.dialog_default_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mBundle.getNegativeRunnable() != null) mBundle.getNegativeRunnable().run();
                    else dismiss();
                }
            });

            if (mBundle.getAllowCancel()) {
                mRootView.findViewById(R.id.dialog_default_bg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
            } else
                this.setCancelable(false);

            if (mBundle.getHidePositive())
                mRootView.findViewById(R.id.dialog_default_ok).setVisibility(View.GONE);
            if (mBundle.getHideNegative())
                mRootView.findViewById(R.id.dialog_default_cancel).setVisibility(View.GONE);

            if (mBundle.getRenamePositive() != null)
                ((Button) mRootView.findViewById(R.id.dialog_default_ok)).setText(mBundle.getRenamePositive());
            if (mBundle.getRenameNegative() != null)
                ((Button) mRootView.findViewById(R.id.dialog_default_cancel)).setText(mBundle.getRenameNegative());

            if (mBundle.getTitle().equals(""))
                mRootView.findViewById(R.id.dialog_default_title).setVisibility(View.GONE);

            if (mBundle.getOnShownListener() != null) {
                mBundle.getOnShownListener().onShown();
            }


            mRootView.getRootView().animate().setDuration(ANIM_IN_MS).alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            }).start();

            mRootView.findViewById(R.id.dialog_default_bg).animate().setDuration(ANIM_IN_MS).alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            }).start();
            mRootView.findViewById(R.id.dialog_default_container).animate().setDuration(ANIM_IN_MS).alpha(1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                }
            }).start();
        } else {
            mNonNativeDeadArea.animate().alpha(1)
                    .setStartDelay(ANIM_IN_MS)
                    .setDuration(ANIM_IN_MS).start();

            // first, trigger the UI threads from caller
            if (mBundle.getOnShownListener() != null) {
                mBundle.getOnShownListener().onShown();
                if (mBundle.getOnShownRunnable() != null)
                    mBundle.getOnShownRunnable().run();
            }

            if (mBundle.getAllowCancel()) {
                mRootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
            } else
                this.setCancelable(false);

            if (mBundle.getRunnables() != null) {
                for (Pair<Integer, Runnable> p : mBundle.getRunnables()) {
                    mRootView.findViewById(p.first).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            p.second.run();
                        }
                    });
                }
            }

            mRootView.findViewById(R.id.dead_area).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rootDismiss();
                }
            });

        }


    }

    /* package */ void animateReappearNow() {
        mRootView.animate().alpha(1).setDuration(ANIM_DURATION_MS).start();
        if (mBundle.getInitWithDialog()) {
            mProgress.dismiss();
        }
    }

    /* package */ View getRootView() {
        return mRootView;
    }

    /* package */  <T extends View> T grabViewById(@IdRes int id) {
        return mRootView.findViewById(id);
    }

    /* package */ interface onShownListener {
        void onShown();
    }

}