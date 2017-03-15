package com.bnation.progress;

import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by ahmedy on 12/4/16.
 */

public class UIUtils {

    public static void animateProgress(View progressBar, float from, float to, float max, int color){
        if(progressBar instanceof ProgressBar) {
            ((ProgressBar)progressBar).setMax((int) max);
        } else if(progressBar instanceof ProgressView) {
            ((ProgressView)progressBar).setProgressMax(max);
            ((ProgressView)progressBar).setProgressColor(color);
        }

        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar,from,to);
        animation.setDuration(1500);
        progressBar.startAnimation(animation);
    }

    public static void animateProgress(View progressBar, float from, float to, float max){
        if(progressBar instanceof ProgressBar) {
            ((ProgressBar)progressBar).setMax((int) max);
        } else if(progressBar instanceof ProgressView) {
            ((ProgressView)progressBar).setProgressMax(max);
        }

        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar,from,to);
        animation.setDuration(1500);
        progressBar.startAnimation(animation);
    }

    public static void animateProgress(View progressBar, float from, float to){
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar,from,to);
        animation.setDuration(1500);
        progressBar.startAnimation(animation);
    }

}
