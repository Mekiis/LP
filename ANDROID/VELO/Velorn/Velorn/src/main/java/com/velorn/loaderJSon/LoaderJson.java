package com.velorn.loaderJSon;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import com.velorn.container.Station;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by IEM on 05/11/2014.
 */
public class LoaderJson extends AsyncTask<LoaderJsonParams, Void, Void> {
    private InputStream is = null;
    private JSONObject jObj = null;
    private String json = "";
    private String url = "";
    private LoaderJSonRunnable runnable = null;
    private Context ac = null;

    @Override
    protected Void doInBackground(LoaderJsonParams... obj) {
        url = obj[0].getUrl();
        runnable = obj[0].getRunnable();
        ac = obj[0].getAc();

        ArrayList<Station> stations = null;

        if(!checkInternetConnection())
            runnable.errorNetwork();

        // Making HTTP request
        try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet HttpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(HttpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            runnable.errorEncoding();
        } catch (ClientProtocolException e) {
            runnable.errorClientProtocol();
        } catch (IOException e) {
            runnable.errorIO();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();

            runnable.s = json;
        } catch (Exception e) {
            runnable.errorParsing();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void a_obj) {
        super.onPostExecute(a_obj);


        runnable.run();

    }

    private boolean checkInternetConnection() {
        ConnectivityManager conMgr = (ConnectivityManager) ac.getSystemService (Context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET
        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {

            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
