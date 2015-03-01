package banand.lionel.com.tpandroid.DAO.Objects;

import banand.lionel.com.tpandroid.Utilities.Global;

/**
 * Created by lionel on 06/10/14.
 */
public class MediaObject {
    public String name;
    public int versionCode;
    public String path;
    public String type;
    public String localPath;
    public String getFileExtension() {
        if(type.equals(Global.MEDIA_TYPE_IMAGE)) {
            return ".png";
        }

        return null;
    }

    public String getFileExtensionFromPath() {
        String ext = path.substring(path.length() - 4);
        return ext;
    }
}
