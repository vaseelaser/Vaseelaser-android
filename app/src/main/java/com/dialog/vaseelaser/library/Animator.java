package com.dialog.vaseelaser.library;


import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;


@SuppressWarnings({"unused"})
public class Animator {

    //private final static int ROTATE_ANIMATION_DURATION = 500;

    // arrow constants
    private final static float ARROW_ANIMATION_DEGREES = 180f;

    private final static float FAB_ANIMATION_DEGREES = -90f;
    private final static float FAB_ANIMATION_DEGREES_BOTTOM = 90f;
    private final static float FAB_TOOLBOX_ANIMATION_DEGREES = 180f;
    // common constants
    private final static int INITIAL_ROTATION = 0;

    // slide constants

    public static float TRANSITION_ANIMATION_SCALE = 1.0f;
    public final static int ANIMATION_DELAY_FADE_MS = 0;
    public final static int ANIMATION_DELAY_SLIDE_MS = 0;
    public final static int ANIM_MARKERS_SHOW_HIDE_MS = 0;

    private final static int _INTERNAL_MS = 0;

    private static boolean mIsAnimating = false;

    public interface HideListener {
        void onHidingEnd();
        void onAnimationStart();
    }

    public interface RevealListener {
        void onRevealEnd();
        void onAnimationStart();
    }

    public static class Fab {
        public static void rotateFromBottomToToggled(View view, boolean isExpanded) {
            view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).rotation(isExpanded ? FAB_ANIMATION_DEGREES_BOTTOM : INITIAL_ROTATION);
        }

        public static void rotate(View view, boolean isExpanded) {
            view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).rotation(isExpanded ? FAB_ANIMATION_DEGREES : INITIAL_ROTATION);
        }

        public static void rotateToolbox(View view, boolean isExpanded) {
            view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).rotation(isExpanded ? FAB_TOOLBOX_ANIMATION_DEGREES : INITIAL_ROTATION);
        }
    }

    public static class Slide {
        public static void toRight(AppCompatActivity act,View view, int howmanyDP) {
            Utils.DisplayTools tools = new Utils.DisplayTools(act);
            int offsetPx = tools.convertDpToPixel(howmanyDP);
            view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).translationXBy(offsetPx).start();

        }

        public static void toLeft(AppCompatActivity act,View view, int howmanyDP) {
            Utils.DisplayTools tools = new Utils.DisplayTools(act);
            int offsetPx = tools.convertDpToPixel(howmanyDP);
            view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).translationXBy(-offsetPx).start();
        }

        // slide the view from below itself to the current position
        public static void fromBottomToTop(Context context, View view, boolean measureStatusBar, boolean alsoShowHide) {
            view.setVisibility(View.VISIBLE);

            Utils.DisplayTools tools = new Utils.DisplayTools((AppCompatActivity) context);
            int height = tools.getScreenRectangle().height();

            int statusBarHeight = 0;
            if (measureStatusBar) {

                int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
                }
            }
            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                 // toXDelta
                    height - statusBarHeight,                     // fromYDelta
                    0);                // toYDelta
            animate.setDuration(500 * (long) TRANSITION_ANIMATION_SCALE);

            if (alsoShowHide) {
                view.setAlpha(0f);
                view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).alpha(1).start();
            }
            view.startAnimation(animate);
        }

        // This functions requires ABSOLUTE coords (pixels). screen's top left corner = 0,0
        public static void fromTopToBottom(Context context, View view, boolean measureStatusBar, boolean alsoShowHide) {

            Utils.DisplayTools tools = new Utils.DisplayTools((AppCompatActivity) context);
            int height = tools.getScreenRectangle().height();

            int statusBarHeight = 0;
            if (measureStatusBar) {
                int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
                }
            }

            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                  // toXDelta
                    statusBarHeight,                 // fromYDelta
                    height);                    // toYDelta
            animate.setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE);
            if (alsoShowHide) {
                view.setAlpha(1f);
                view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).alpha(0).start();
            }
            view.startAnimation(animate);
            view.setVisibility(View.GONE);
        }

        // Slides
        public static void fromLeftToVisible(View view, boolean alsoShowHide) {
            view.setVisibility(View.VISIBLE);

            //view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int xx = view.getMeasuredWidth();

            TranslateAnimation animate = new TranslateAnimation(
                    -xx,  // fromXDelta
                    0,                  // toXDelta
                    0,                     // fromYDelta
                    0);                // toYDelta
            animate.setDuration(500 * (long) TRANSITION_ANIMATION_SCALE);
            animate.setFillAfter(false);
            animate.setFillEnabled(false);
            //animate.setFillAfter(true);
            if (alsoShowHide) {
                view.setAlpha(0f);
                view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).alpha(1).start();
            }
            view.startAnimation(animate);
        }

        public static int getAnimationDuration() {
            return (int)(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE);
        }

        public static void fromVisibleToLeft(View view, boolean alsoShowHide) {
            //view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int xx = view.getMeasuredWidth();

            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    -xx,                  // toXDelta
                    0,                 // fromYDelta
                    0);                    // toYDelta
            animate.setDuration(500 * (long) TRANSITION_ANIMATION_SCALE);
            animate.setFillAfter(false);
            animate.setFillEnabled(false);
            animate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            if (alsoShowHide) {
                view.setAlpha(1f);
                view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).alpha(0).start();
            }
            view.startAnimation(animate);

        }

        public static void fromVisibleToBottom(View view,Integer marginDP,AppCompatActivity act) {
            Utils.DisplayTools tools = new Utils.DisplayTools(act);
            view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int xx = view.getMeasuredHeight();
            int marginDPpx = (marginDP == null) ? 0 : tools.convertDpToPixel(marginDP);
            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                  // toXDelta
                    0,                 // fromYDelta
                    xx+marginDPpx);                    // toYDelta
            animate.setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE);
            animate.setFillAfter(false);
            animate.setFillEnabled(false);

            view.startAnimation(animate);
            view.setVisibility(View.GONE);
        }

        public static void fromBottomToVisible(View view,Integer marginDP,AppCompatActivity act) {
            view.setVisibility(View.VISIBLE);
            Utils.DisplayTools tools = new Utils.DisplayTools(act);
            view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int xx = view.getMeasuredHeight();
            int marginendPx = (marginDP == null) ? 0 : tools.convertDpToPixel(marginDP);

            TranslateAnimation animate = new TranslateAnimation(
                    0,  // fromXDelta
                    0,                  // toXDelta
                    xx,                     // fromYDelta
                    0);                // toYDelta
            animate.setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE);
            animate.setFillAfter(false);
            animate.setFillEnabled(false);
            //animate.setFillAfter(true);
            view.startAnimation(animate);
        }

        public static void fromVisibleToTop(View view,Integer marginDP,AppCompatActivity act,boolean alsoShowHide) {
            Utils.DisplayTools tools = new Utils.DisplayTools(act);
            view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int xx = view.getMeasuredHeight();
            int marginDPpx = (marginDP == null) ? 0 : tools.convertDpToPixel(marginDP);
            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    0,                  // toXDelta
                    0,                 // fromYDelta
                    -xx-marginDPpx);                    // toYDelta
            animate.setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE);
            animate.setFillAfter(false);
            animate.setFillEnabled(false);
            if (alsoShowHide) {
                view.setAlpha(1f);
                view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).alpha(0).start();
            }
            view.startAnimation(animate);
            view.setVisibility(View.GONE);
        }

        public static void fromTopToVisible(View view,Integer marginDP,AppCompatActivity act, boolean alsoShowHide) {
            view.setVisibility(View.VISIBLE);
            Utils.DisplayTools tools = new Utils.DisplayTools(act);
            view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int xx = view.getMeasuredHeight();

            int marginDPpx = (marginDP == null) ? 0 : tools.convertDpToPixel(marginDP);

            TranslateAnimation animate = new TranslateAnimation(
                    0,  // fromXDelta
                    0,                  // toXDelta
                    -xx-marginDPpx,                     // fromYDelta
                    0);                // toYDelta
            animate.setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE);
            animate.setFillAfter(false);
            animate.setFillEnabled(false);
            //animate.setFillAfter(true);

            if (alsoShowHide) {
                view.setAlpha(0f);
                view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).alpha(1).start();
            }
            view.startAnimation(animate);
        }

        public static void fromVisibleToRight(View view, boolean alsoShowHide) {

            //view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int xx = view.getMeasuredWidth();

            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    xx,                  // toXDelta
                    0,                 // fromYDelta
                    0);                    // toYDelta
            animate.setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE);
            animate.setFillAfter(false);
            animate.setFillEnabled(false);

            if (alsoShowHide) {
                view.setAlpha(1f);
                view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).alpha(0).start();
            }
            view.startAnimation(animate);
            view.setVisibility(View.GONE);
        }

        public static void fromRightToVisible(View view, boolean alsoShowHide) {
            view.setVisibility(View.VISIBLE);

            //view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int xx = view.getMeasuredWidth();

            TranslateAnimation animate = new TranslateAnimation(
                    xx,  // fromXDelta
                    0,                  // toXDelta
                    0,                     // fromYDelta
                    0);                // toYDelta
            animate.setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE);
            animate.setFillAfter(false);
            animate.setFillEnabled(false);
            //animate.setFillAfter(true);
            if (alsoShowHide) {
                view.setAlpha(0f);
                view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).alpha(1).start();
            }
            view.startAnimation(animate);
        }

        // overridable
        public static void fromVisibleToRight(View view, Integer marginEndDP,AppCompatActivity act,boolean alsoShowHide) {

            Utils.DisplayTools tools = new Utils.DisplayTools(act);
            int marginendPx = (marginEndDP == null) ? 0 : tools.convertDpToPixel(marginEndDP);

            view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int xx = view.getMeasuredWidth();

            TranslateAnimation animate = new TranslateAnimation(
                    0,                 // fromXDelta
                    xx+marginendPx,                  // toXDelta
                    0,                 // fromYDelta
                    0);                    // toYDelta
            animate.setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE);
            animate.setFillAfter(false);
            animate.setFillEnabled(false);

            if (alsoShowHide) {
                view.setAlpha(1f);
                view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).alpha(0).start();
            }
            view.startAnimation(animate);
            view.setVisibility(View.GONE);
        }

        public static void fromRightToVisible(View view, Integer marginEndDP,AppCompatActivity act,boolean alsoShowHide) {
            view.setVisibility(View.VISIBLE);

            Utils.DisplayTools tools = new Utils.DisplayTools(act);
            int marginendPx = (marginEndDP == null) ? 0 : tools.convertDpToPixel(marginEndDP);

            view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int xx = view.getMeasuredWidth();

            TranslateAnimation animate = new TranslateAnimation(
                    xx+marginendPx,  // fromXDelta
                    0,                  // toXDelta
                    0,                     // fromYDelta
                    0);                // toYDelta
            animate.setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE);
            animate.setFillAfter(false);
            animate.setFillEnabled(false);
            //animate.setFillAfter(true);
            if (alsoShowHide) {
                view.setAlpha(0f);
                view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).alpha(1).start();
            }
            view.startAnimation(animate);
        }

    }

    public static class Transitions {
        public static void CircularHide(HideListener listener, View root, float x, float y) {

            if (mIsAnimating) return;

            mIsAnimating = true;

            final long DURATION_MS = 1000 * (long) TRANSITION_ANIMATION_SCALE;
            root.setAlpha(1);
            int finalRadius = Math.max(root.getWidth(), root.getHeight());
            android.animation.Animator animator =
                    ViewAnimationUtils.createCircularReveal(root, (int) x, (int) y, finalRadius, 0);
            animator.setInterpolator(new FastOutSlowInInterpolator());
            animator.setDuration(DURATION_MS);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(android.animation.Animator animation) {
                    mIsAnimating = false;
                    if (listener != null )
                        listener.onHidingEnd();
                }
                @Override
                public void onAnimationStart(android.animation.Animator animation) {
                    mIsAnimating = false;
                    if (listener != null )
                        listener.onAnimationStart();
                }
            });
            animator.start();
            root.animate().setStartDelay(0)
                    .setDuration(DURATION_MS)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .alpha(0);
        }

        public static void CircularReveal(RevealListener listener, View root, float x, float y) {

            if (mIsAnimating) return;
            mIsAnimating = true;

            root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {

                    ViewTreeObserver.OnGlobalLayoutListener globalListener = this;
                    root.getViewTreeObserver().removeOnGlobalLayoutListener(globalListener);
                    final long DURATION_MS = 1000 * (long) TRANSITION_ANIMATION_SCALE;
                    root.setAlpha(0.5f);
                    int finalRadius = Math.max(root.getWidth(), root.getHeight());
                    android.animation.Animator animator =
                            ViewAnimationUtils.createCircularReveal(root, (int) x, (int) y, 0, finalRadius);
                    animator.setInterpolator(new FastOutSlowInInterpolator());
                    animator.setDuration(DURATION_MS);

                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(android.animation.Animator animation) {
                            //super.onAnimationEnd(animation);
                            mIsAnimating = false;
                            if (listener != null)
                                listener.onRevealEnd();
                        }
                        @Override
                        public void onAnimationStart(android.animation.Animator animation) {
                            mIsAnimating = false;
                            if (listener != null )
                                listener.onAnimationStart();
                        }
                    });

                    animator.start();
                    root.animate().setStartDelay(0)
                            .setDuration(DURATION_MS)
                            .setInterpolator(new FastOutSlowInInterpolator())
                            .alpha(1);
                }
            });
        }

    }

    public static class Arrows {
        public static void animate(View view, boolean isExpanded) {
            view.animate().setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE).rotation(isExpanded ? ARROW_ANIMATION_DEGREES : INITIAL_ROTATION);
        }

        public static void expand(final View view, boolean expand) {
            Animation animation;

            view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            final int actualHeight = view.getMeasuredHeight();
            if (expand) {
                view.getLayoutParams().height = 0;
                view.setVisibility(View.VISIBLE);

                animation = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        view.getLayoutParams().height = (int) (actualHeight * interpolatedTime);
                        view.requestLayout();
                    }
                };
            } else {

                animation = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {

                        if (interpolatedTime == 1) {
                            view.setVisibility(View.GONE);
                        } else {
                            view.getLayoutParams().height = actualHeight - (int) (actualHeight * interpolatedTime);
                            view.requestLayout();

                        }
                    }
                };
            }
            animation.setDuration(_INTERNAL_MS * (long) TRANSITION_ANIMATION_SCALE);
            view.startAnimation(animation);
        }

    }


}