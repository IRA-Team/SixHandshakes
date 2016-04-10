package com.irateam.sixhandshakes.ui;

import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

public class AnimatedRoundedDisplayer extends RoundedBitmapDisplayer {
    public AnimatedRoundedDisplayer(int cornerRadiusPixels) {
        super(cornerRadiusPixels);
    }

    public AnimatedRoundedDisplayer(int cornerRadiusPixels, int marginPixels) {
        super(cornerRadiusPixels, marginPixels);
    }


    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        super.display(bitmap, imageAware, loadedFrom);
        animate(imageAware.getWrappedView(), 500);
    }


    public static void animate(View imageView, int durationMillis) {
        AlphaAnimation fadeImage = new AlphaAnimation(0, 1);
        fadeImage.setDuration(durationMillis);
        fadeImage.setInterpolator(new DecelerateInterpolator());
        imageView.startAnimation(fadeImage);
    }
}
