package com.velorn.loaderJSon;

import android.util.Log;

/**
 * Created by IEM on 03/11/2014.
 */
public abstract class LoaderJSonRunnable implements Runnable {
    public String s = "";

    public void errorNetwork(){Log.e(getClass().getName(), "No network detected on LoaderJSon");};

    public void errorParsing(){Log.e(getClass().getName(), "Error when try to read String object on LoaderJSon");};

    public void errorEncoding(){Log.e(getClass().getName(), "Encoding error on LoaderJSon");};

    public void errorClientProtocol(){Log.e(getClass().getName(), "Client Protocol error on LoaderJSon");};

    public void errorIO(){Log.e(getClass().getName(), "IO error on LoaderJSon");};
}
