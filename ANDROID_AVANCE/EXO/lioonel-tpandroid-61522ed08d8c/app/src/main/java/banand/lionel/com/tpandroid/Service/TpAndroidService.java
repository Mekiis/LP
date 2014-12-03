package banand.lionel.com.tpandroid.Service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.Stack;

import banand.lionel.com.tpandroid.Internet.TpAndroidInternet;
import banand.lionel.com.tpandroid.Manager.MediasManager;
import banand.lionel.com.tpandroid.Utilities.Global;
import banand.lionel.com.tpandroid.Utilities.TpAndroidXMLParser;

public class TpAndroidService extends Service {

    private Stack<Bundle> mMediasList = new Stack<Bundle>();

    public TpAndroidService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                downloadMediasInfo();
                return null;
            }
        }.execute();

        return super.onStartCommand(intent, flags, startId);
    }


    private void downloadMediasInfo() {

        String xmlFile = TpAndroidInternet.downloadXMLFile(Global.MEDIA_URL);
        if (xmlFile != null && !xmlFile.equals("Not Found")) {

            TpAndroidXMLParser updaterXmlParser = new TpAndroidXMLParser();
            mMediasList = updaterXmlParser.loadUpdaterXml(xmlFile);

            MediasManager.getInstance().createMediasList(mMediasList);
        }
    }


}
