package com.velorn;

import android.app.Activity;
import android.os.Bundle;

import com.velorn.loaderJSon.LoaderJSonRunnable;
import com.velorn.loaderJSon.LoaderJson;
import com.velorn.loaderJSon.LoaderJsonParams;
import com.velorn.parser.PathParser;

/**
 * Created by IEM on 05/11/2014.
 */
public class ChosePath extends Activity{
    // WEB SERVICE GOOGLE MAP : Directions
    // Sert à tracer des parcours
    // http://javapapers.com/android/draw-path-on-google-maps-android-api/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new LoaderJson().execute(new LoaderJsonParams(
        "",
        new LoaderJSonRunnable() {
            @Override
            public void run() {
                PathParser parser = new PathParser();
                parser.parse(s);
            }
        },
        getApplicationContext()));
    }
}
