package com.velorn.loaderJSon;

import android.util.Log;

/**
 * Created by IEM on 03/11/2014.
 */
public abstract class LoaderJSonRunnable implements Runnable {
    public String s = "";

    private Object thisO = (Object)this;

    public void errorNetwork() {
        Log.e(thisO.getClass().getName(), "No network detected on LoaderJSon");
    }

    ;

    public void errorParsing() {
        Log.e(thisO.getClass().getName(), "Error when try to read String object on LoaderJSon");
    }

    ;

    public void errorEncoding() {
        Log.e(thisO.getClass().getName(), "Encoding error on LoaderJSon");
    }

    ;

    public void errorClientProtocol() {
        Log.e(thisO.getClass().getName(), "Client Protocol error on LoaderJSon");
    }

    ;

    public void errorIO() {
        Log.e(thisO.getClass().getName(), "IO error on LoaderJSon");
    }

    ;
}
