package com.dialog.vaseelaser.library;


import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.dialog.vaseelaser.R;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"Convert2Lambda"})
public class PopUp {


    public static class Native {
        private final AppCompatActivity mActivity;
        private final String mTitle, mBody;
        private boolean mUseYesNo = false;
        private Runnable mPosRun;
        private Runnable mNegRun;
        private PopUpAlertDialog mPoppedFragment;
        private boolean mAllowCancel = true;
        private boolean mHideNegative = false;
        private boolean mHidePositive = false;
        private boolean mIsAlertShowing = false;
        private String mRenamePositive = null;
        private String mRenameNegative = null;

        private boolean mInitWithAlphaZero = false;
        private boolean mInitAlsoWithDialog = false;


        public Native(AppCompatActivity act, String title, String body) {
            mActivity = act;
            mTitle = title;
            mBody = body;
        }

        public void animateAppearNow() {
            if (!mInitWithAlphaZero)
                throw new RuntimeException("Are you sure? You never set 'initWithAlphaZero' ");
            mPoppedFragment.animateReappearNow();
        }

        public PopUp.Native initWithAlphaZero(boolean alsoWithDialog) {
            mInitWithAlphaZero = true;
            mInitAlsoWithDialog = alsoWithDialog;
            return this;
        }

        public PopUp.Native renamePositiveOption(String renamePositive) {
            mRenamePositive = renamePositive;
            return this;
        }

        public PopUp.Native renameNegativeOption(String renameNegative) {
            mRenameNegative = renameNegative;
            return this;
        }

        public PopUp.Native hideNegativeOption() {
            mHideNegative = true;
            return this;
        }

        public PopUp.Native hidePositiveOption() {
            mHidePositive = true;
            return this;
        }

        public PopUp.Native allowCancel(boolean allow) {
            mAllowCancel = allow;
            return this;
        }

        public PopUp.Native useYesNo() {
            mUseYesNo = true;
            return this;
        }

        public PopUp.Native setPositiveAction(Runnable runnable) {
            mPosRun = runnable;
            return this;
        }

        public PopUp.Native setNegativeAction(Runnable runnable) {
            mNegRun = runnable;
            return this;
        }

        public void dismiss() {
            mPoppedFragment.rootDismiss();
        }

        public boolean isShowing() {
            return mIsAlertShowing;
        }

        public void pop() {
            CompatAlertBundle bundle = new CompatAlertBundle(null, new PopUpAlertDialog.onShownListener() {
                @Override
                public void onShown() {
                    mIsAlertShowing = true;
                }
            }, R.layout.misc_popupalert_native, null, mActivity, new Pair<>(mTitle, mBody), mUseYesNo, mPosRun, mNegRun, mAllowCancel, mHideNegative, mHidePositive, new Pair<>(mRenamePositive, mRenameNegative), mInitWithAlphaZero, mInitAlsoWithDialog);

            try {
                mPoppedFragment = new PopUpAlertDialog(bundle);
                mPoppedFragment.show(mActivity.getSupportFragmentManager(), "popupalert");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public static class Custom {
        private final AppCompatActivity mActivity;

        @LayoutRes
        private final int mLayoutID;
        private boolean mUseYesNo = false;
        private PopUpAlertDialog mPoppedFragment;
        private boolean mAllowCancel = true;
        private boolean mIsAlertShowing = false;
        private Runnable mOnShownRunnable = null;

        private final List<Pair<Integer, Runnable>> mRunnables = new ArrayList<>();

        private boolean mInitWithAlphaZero = false;
        private boolean mInitAlsoWithDialog = false;

        public Custom(AppCompatActivity act, @LayoutRes int id) {
            mActivity = act;
            mLayoutID = id;
        }

        public void animateAppearNow() {
            if (!mInitWithAlphaZero)
                throw new RuntimeException("Are you sure? You never set 'initWithAlphaZero' ");
            mPoppedFragment.animateReappearNow();
        }

        public PopUp.Custom initWithAlphaZero(boolean alsoWithDialog) {
            mInitWithAlphaZero = true;
            mInitAlsoWithDialog = alsoWithDialog;
            return this;
        }

        public PopUp.Custom setViewAction(@IdRes int id, Runnable runnable) {
            mRunnables.add(new Pair<>(id, runnable));
            return this;
        }

        public PopUp.Custom allowCancel(boolean allow) {
            mAllowCancel = allow;
            return this;
        }

        public PopUp.Custom useYesNo() {
            mUseYesNo = true;
            return this;
        }

        public void dismiss() {
            mPoppedFragment.rootDismiss();
        }

        public View getRootView() {
            return mPoppedFragment.getRootView();
        }

        /**
         * Should be the very first runnable after constructor call
         *
         * @param runnable The code to be ran after render is done
         * @return this instance.
         */
        public PopUp.Custom onRenderDone(Runnable runnable) {
            mOnShownRunnable = runnable;
            return this;
        }

        public <T extends View> T grabView(@IdRes int id) {
            if (mIsAlertShowing)
                return mPoppedFragment.grabViewById(id);
            else
                throw new RuntimeException("You cannot get a view before it is even rendered. You can call that function only inside $onShownRunnable()");
        }

        public void pop() {

            CompatAlertBundle bundle = new CompatAlertBundle(mOnShownRunnable, new PopUpAlertDialog.onShownListener() {
                @Override
                public void onShown() {
                    mIsAlertShowing = true;
                }
            }, mLayoutID, mRunnables, mActivity, new Pair<>("", ""), mUseYesNo, null, null, mAllowCancel, false, false, new Pair<>("", ""), mInitWithAlphaZero, mInitAlsoWithDialog);

            mPoppedFragment = new PopUpAlertDialog(bundle);
            mPoppedFragment.show(mActivity.getSupportFragmentManager(), "popupalert");

        }


    }


}
