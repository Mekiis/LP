package com.velorn.loaderJSon;

import android.content.Context;

/**
 * Created by IEM on 05/11/2014.
 */
public class LoaderJsonParams {
    private String url = "";
    private LoaderJSonRunnable runnable = null;
    private Context ac = null;

    /**
     * <i>Warning : Put on the manifest the authorizations to check Internet Status</i>
     *
     * @param url             The url of the web page to load
     * @param runnable        The LoaderJsonRunnable to launch when the loading is finished. It catch the error on "errorXXX" functions.
     * @param activityContext The context of the activity to check if the connection is activate
     */
    public LoaderJsonParams(String url, LoaderJSonRunnable runnable, Context activityContext) {
        this.url = url;
        this.runnable = runnable;
        this.ac = activityContext;
    }

    public String getUrl() {
        return url;
    }

    public LoaderJSonRunnable getRunnable() {
        return runnable;
    }

    public Context getAc() {
        return ac;
    }
}
