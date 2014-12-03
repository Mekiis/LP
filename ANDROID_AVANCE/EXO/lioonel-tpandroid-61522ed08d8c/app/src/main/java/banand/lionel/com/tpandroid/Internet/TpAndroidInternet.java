package banand.lionel.com.tpandroid.Internet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lionel on 06/10/14.
 */
public class TpAndroidInternet {

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
}
