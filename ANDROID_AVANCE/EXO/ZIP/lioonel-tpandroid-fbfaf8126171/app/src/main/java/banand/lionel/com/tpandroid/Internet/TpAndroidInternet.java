package banand.lionel.com.tpandroid.Internet;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import banand.lionel.com.tpandroid.DAO.Objects.MediaObject;
import banand.lionel.com.tpandroid.Utilities.Global;

/**
 * Created by lionel on 06/10/14.
 */
public class TpAndroidInternet {

    // Container Activity must implement this interface
    public interface TpAndroidInternetInterface {
        public void onMediaDownloaded(MediaObject mediaObject);
    }

    private static final String TAG = "TpAndroidInternet";

    // used to download the package XML file
    public static String downloadXMLFile(String url) {
        InputStream stream;
        StringBuilder stringFromStream;

        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.connect();
            stream = conn.getInputStream();

            byte[] bytes = new byte[1000];

            stringFromStream = new StringBuilder();

            //convert input stream to string
            int numRead = 0;
            while ((numRead = stream.read(bytes)) >= 0) {
                stringFromStream.append(new String(bytes, 0, numRead));
            }
            conn.disconnect();

        } catch (FileNotFoundException e) {

            return null; // swallow a 404
        } catch (IOException e) {
            return null; // swallow a 404
        }
        if (stringFromStream.length() > 0) {
            return stringFromStream.toString();
        } else {
            return null;
        }
    }

    public void downloadMediaFile(MediaObject mediaObject, TpAndroidInternetInterface listener) {

        new DownloadMediaTask(mediaObject, listener).execute();
    }

    private class DownloadMediaTask extends AsyncTask<Void, Integer, Boolean> {

        String mURL;
        File mOutputFile;
        TpAndroidInternetInterface mListener;
        MediaObject mMediaObject;

        public DownloadMediaTask(MediaObject mediaObject, TpAndroidInternetInterface listener) {
            mMediaObject = mediaObject;
            mURL = Global.REMOTE_URL + mediaObject.path;
            mOutputFile = new File(Global.MEDIA_INTERNAL_PATH + mediaObject.name + mediaObject.getFileExtensionFromPath());
            mListener = listener;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HttpURLConnection conn = null;
            try {
                URL u = new URL(mURL);
                conn = (HttpURLConnection) u.openConnection();
                conn.connect();
                int filelength = conn.getContentLength();

                if (mOutputFile.exists()) {
                    mOutputFile.delete();
                }

                InputStream stream = conn.getInputStream();
                if (stream == null) {
                    return false;
                }
                FileOutputStream fos = new FileOutputStream(mOutputFile);

                byte data[] = new byte[2048];
                int count;
                long total = 0;

                while ((count = stream.read(data, 0, 2048)) != -1) {
                    if (filelength > 0) {
                        int progress = (int) (total * 100 / (filelength));
                        publishProgress(progress);
                    }
                    fos.write(data, 0, count);
                }
                if (stream != null)
                    stream.close();
                if (fos != null)
                    fos.close();
                conn.disconnect();

            } catch (FileNotFoundException e) {
                Log.w(TAG, e);
                if (conn != null) {
                    conn.disconnect();
                }
                return false; // swallow a 404
            } catch (IOException e) {
                Log.w(TAG, e);
                if (conn != null) {
                    conn.disconnect();
                }
                return false; // swallow a 404
            }

            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            mMediaObject.localPath = mOutputFile.getAbsolutePath();
            mListener.onMediaDownloaded(mMediaObject);
            super.onPostExecute(aBoolean);
        }
    }
}
