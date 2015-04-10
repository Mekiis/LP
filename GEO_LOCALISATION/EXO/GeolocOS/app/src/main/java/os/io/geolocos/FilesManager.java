package os.io.geolocos;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by iem on 04/03/15.
 */
public class FilesManager {

    public static void writeToPrivateFile(Context context, String data, String fileName) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data); outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public static String readFromPrivateFile(Context context, String fileName) {

        String ret = "";

        try { InputStream inputStream = context.openFileInput(fileName);

            if ( inputStream != null )
            {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = ""; StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null )
                {
                    stringBuilder.append(receiveString);
                }

                inputStream.close(); ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public static String writeInFile(Context context, String contentFile, String fileName) throws IOException {
        File file = buildFile(context, fileName);

        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(contentFile.getBytes());
        outputStream.close();

        Toast.makeText(context, "File saved !", Toast.LENGTH_LONG).show();

        return file.getAbsolutePath();
    }

    private static File buildFile(Context context, String fileName) throws IOException {
        String tempDir = Environment.getExternalStorageDirectory() + "/AppSIG/";
        prepareDirectory(context, tempDir);
        File mFileSVG = new File(tempDir, fileName);

        try {
            mFileSVG.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mFileSVG;
    }

    private static boolean prepareDirectory(Context context, String fileName) {
        try {
            if (makeDirs(fileName)) { return true; }
            else { return false; }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Could not initiate File System.. Is Sdcard mounted properly?", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private static boolean makeDirs(String dirName) {
        File tempDir = new File(dirName);

        if (!tempDir.exists())
            tempDir.mkdirs();

        return tempDir.isDirectory();
    }
}
