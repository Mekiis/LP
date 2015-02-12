package banand.lionel.com.tpandroid.Utilities;

import android.os.Environment;

import java.io.File;

/**
 * Created by lionel on 06/10/14.
 */
public class Global {

    public static final String MEDIA_URL = "http://lionel.banand.free.fr/lp_iem/updaterLPIEM.php?serial=AAA&type=medias";
    public static final String REMOTE_URL = "http://lionel.banand.free.fr/lp_iem/";

    //    public static final String MEDIA_INTERNAL_PATH = Environment.getExternalStoragePublicDirectory("medias") + File.separator;
    public static final String MEDIA_INTERNAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    public static final String MEDIA_FILE_NAME = "medias.xml";
    public static final String NEED_TO_START_SERVICE = "banand.lionel.com.tpandroid.NEED_TO_START_SERVICE";
    public static final String MEDIA_TYPE_IMAGE = "image";
    public static final String MEDIA_TYPE_VIDEO = "video";
    public static final String MEDIA_TYPE_TEXT = "texte";

}
