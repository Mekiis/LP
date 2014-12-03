package com.velorn.parser;

import android.view.View;
import android.widget.ProgressBar;

/**
 * Created by Simon on 03/12/2014.
 */
public class LoadingManager {
    private static LoadingManager mInstance = null;

    private static ProgressBar UILoaderBar = null;
    private static int count = 0;

    public static LoadingManager getInstance(){
        if(mInstance == null)
            mInstance = new LoadingManager();

        return mInstance;
    }

    public void setProgressBar(ProgressBar bar){
        this.UILoaderBar = bar;
    }

    public void addLoading(){
        if(this.UILoaderBar == null)
            return;

        this.count++;
        this.UILoaderBar.setVisibility(View.VISIBLE);
    }

    public void removeLoading(){
        if(this.UILoaderBar == null)
            return;

        this.count--;
        this.count = this.count < 0 ? 0 : this.count;

        if(this.count == 0)
            this.UILoaderBar.setVisibility(View.GONE);
    }
}
