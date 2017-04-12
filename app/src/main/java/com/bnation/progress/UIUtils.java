package com.bnation.progress;

import android.graphics.Color;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Random;

/**
 * Created by ahmed.bah7ini on Mar 15, 2017.
 */

public class UIUtils {

    public static void animateProgress(View progressBar, float from, float to, float max, int progressColor){
        if(progressBar instanceof ProgressBar) {
            ((ProgressBar)progressBar).setMax((int) max);
        } else if(progressBar instanceof ProgressView) {
            ((ProgressView)progressBar).setProgressMax(max);
            ((ProgressView)progressBar).setProgressColor(progressColor);
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

    public static int getRandomColor(){
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;

    }

}
