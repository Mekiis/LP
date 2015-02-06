package banand.lionel.com.tpandroid.Manager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Stack;

import banand.lionel.com.tpandroid.DAO.Objects.ImageObject;
import banand.lionel.com.tpandroid.DAO.Objects.MediaObject;
import banand.lionel.com.tpandroid.Utilities.Global;
import banand.lionel.com.tpandroid.Utilities.TpAndroidXMLParser;

/**
 * Created by lionel on 06/10/14.
 */
public class MediasManager {
    private static MediasManager mInstance;

    private ArrayList<ImageObject> mImagesList = new ArrayList<ImageObject>();
    private ArrayList<MediaObject> mMediasList = new ArrayList<MediaObject>();

    public static MediasManager getInstance() {
        if (mInstance == null) {
            mInstance = new MediasManager();
        }

        return mInstance;
    }

    public ImageObject createImage(Bundle mediaInfo) {

        ImageObject img = new ImageObject();
        img.name = mediaInfo.getString(TpAndroidXMLParser.KEY_NAME);
        img.path = mediaInfo.getString(TpAndroidXMLParser.KEY_DOWNLOAD_PATH);
        img.versionCode = mediaInfo.getInt(TpAndroidXMLParser.KEY_VERSION_CODE);
        return img;
    }

    public MediaObject createMedia(Bundle mediaInfo) {
        MediaObject media = new MediaObject();
        media.name = mediaInfo.getString(TpAndroidXMLParser.KEY_NAME);
        media.path = mediaInfo.getString(TpAndroidXMLParser.KEY_DOWNLOAD_PATH);
        media.versionCode = mediaInfo.getInt(TpAndroidXMLParser.KEY_VERSION_CODE);
        media.type = mediaInfo.getString(TpAndroidXMLParser.KEY_TYPE);
        return media;
    }

    public void createMediasList(Stack<Bundle> mediasInfo) {

        for (Bundle media : mediasInfo) {

            if (media.getString(TpAndroidXMLParser.KEY_TYPE).equals(Global.MEDIA_TYPE_IMAGE)) {
                mImagesList.add(createImage(media));
            }

            mMediasList.add(createMedia(media));
        }
    }

    public ArrayList<MediaObject> getImages() {
        ArrayList<MediaObject> images = new ArrayList<MediaObject>();
        for (MediaObject mediaObject : mMediasList) {
            if (mediaObject.type.equals(Global.MEDIA_TYPE_IMAGE)) {
                images.add(mediaObject);
            }
        }

        return images;
    }

}
