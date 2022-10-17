package com.dialog.vaseelaser.library;


import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import java.util.List;

public class CompatAlertBundle {

    private final AppCompatActivity mActivity;
    private final String mTitle, mBody;
    private final boolean mUseYesNo;
    private final Runnable mPositiveRunnable;
    private final Runnable mNegativeRunnable;
    private final String mRenamePositive;
    private final String mRenameNegative;
    private final boolean mAllowCancel;
    private final boolean mHideNegative;
    private final boolean mHidePositive;
    private final PopUpAlertDialog.onShownListener mOnShownListener;
    private final Runnable mOnShownRunnable;
    private final List<Pair<Integer,Runnable>> mRunnables;

    private final boolean mInitWithAlphaZero;
    private final boolean mInitWithDialog;


    // needed for custom
    @LayoutRes
    private final int mLayoutID;

    public CompatAlertBundle (Runnable onshownrunnable,PopUpAlertDialog.onShownListener onshown, @LayoutRes int id,List<Pair<Integer,Runnable>> runnables, AppCompatActivity act,Pair<String,String> strings,boolean useyesno,Runnable posRun,Runnable negRun,boolean allowcancel,boolean hideneg,boolean hidepos,Pair<String,String> renames,boolean initwithalpha, boolean alsodialog) {
        mActivity = act;
        mTitle = strings.first;
        mBody = strings.second;
        mUseYesNo = useyesno;
        mPositiveRunnable = posRun;
        mNegativeRunnable = negRun;
        mAllowCancel = allowcancel;
        mHideNegative = hideneg;
        mHidePositive = hidepos;

        mRenamePositive = renames.first;
        mRenameNegative = renames.second;

        mLayoutID = id;
        mRunnables = runnables;
        mOnShownRunnable = onshownrunnable;
        mOnShownListener = onshown;

        mInitWithAlphaZero = initwithalpha;
        mInitWithDialog = alsodialog;
    }

    public AppCompatActivity getActivity() {
        return mActivity;
    }

    public String getTitle() { return mTitle;}
    public String getBody() { return mBody;}
    public boolean getYesNo() { return mUseYesNo;}
    public Runnable getPositiveRunnable(){return mPositiveRunnable;}
    public Runnable getNegativeRunnable(){return mNegativeRunnable;}
    public boolean getAllowCancel(){return mAllowCancel; }
    public boolean getHideNegative(){return mHideNegative;}
    public boolean getHidePositive(){return mHidePositive;}
    public String getRenamePositive(){return mRenamePositive;}
    public String getRenameNegative(){return mRenameNegative;}

    public @LayoutRes int getLayoutID(){return mLayoutID;}
    public List<Pair<Integer,Runnable>> getRunnables() { return mRunnables;}
    public PopUpAlertDialog.onShownListener getOnShownListener() { return mOnShownListener;}
    public Runnable getOnShownRunnable() { return mOnShownRunnable;}
    public boolean getInitWithAlphaZero() { return mInitWithAlphaZero; }
    public boolean getInitWithDialog() { return mInitWithDialog;}


}

