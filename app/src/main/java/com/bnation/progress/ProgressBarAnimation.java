package com.bnation.progress;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;

/**
 * Created by ahmed.bah7ini on Mar 15, 2017.
 */

public class ProgressBarAnimation extends Animation {

    private View mView;
    private float from;
    private float  to;

    public ProgressBarAnimation(View progressBar, float from, float to) {
        super();
        this.mView = progressBar;
        this.from = from;
        this.to = to;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float value = from + (to - from) * interpolatedTime;
        if(mView instanceof ProgressBar) {
            ((ProgressBar) mView).setProgress((int) value);
        } else if(mView instanceof ProgressView) {
            // set progress color
            ((ProgressView) mView).setProgress(value);
        }
    }

}
