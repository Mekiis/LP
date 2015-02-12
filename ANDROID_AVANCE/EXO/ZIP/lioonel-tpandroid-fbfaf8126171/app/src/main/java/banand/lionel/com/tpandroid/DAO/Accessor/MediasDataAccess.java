package banand.lionel.com.tpandroid.DAO.Accessor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import banand.lionel.com.tpandroid.DAO.Objects.MediaObject;
import banand.lionel.com.tpandroid.DAO.TpAndroidSqlHelper;

/**
 * Created by lionel on 07/10/14.
 */
public class MediasDataAccess {

    private TpAndroidSqlHelper mTpAndroidSqlHelper;
    SQLiteDatabase db;

    public MediasDataAccess(Context context) {
        mTpAndroidSqlHelper = new TpAndroidSqlHelper(context);
    }

    public void insertMedia(MediaObject media) {
        db = mTpAndroidSqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TpAndroidSqlHelper.MEDIAS_NAME, media.name);
        values.put(TpAndroidSqlHelper.MEDIAS_VERSION_CODE, media.versionCode);
        values.put(TpAndroidSqlHelper.MEDIAS_TYPE, media.type);
        values.put(TpAndroidSqlHelper.MEDIAS_LOCAL_PATH, media.localPath);

        db.insert(TpAndroidSqlHelper.TABLE_MEDIAS, null, values);
        db.close();
    }

    public ArrayList<MediaObject> getMediasForType(String mediaType) {
        ArrayList<MediaObject> mediaObjects = new ArrayList<MediaObject>();
        db = mTpAndroidSqlHelper.getWritableDatabase();
        String query = "select * from " + TpAndroidSqlHelper.TABLE_MEDIAS +
                " where " + TpAndroidSqlHelper.MEDIAS_TYPE + " = '" + mediaType + "'";

        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null && cursor.moveToFirst()) {
            while(!cursor.isAfterLast()) {
                MediaObject mediaObject = new MediaObject();
                mediaObject.name = cursor.getString(cursor.getColumnIndex(TpAndroidSqlHelper.MEDIAS_NAME));
                mediaObject.versionCode = cursor.getInt(cursor.getColumnIndex(TpAndroidSqlHelper.MEDIAS_VERSION_CODE));
                mediaObject.localPath = cursor.getString(cursor.getColumnIndex(TpAndroidSqlHelper.MEDIAS_LOCAL_PATH));
                mediaObject.type = mediaType;

                mediaObjects.add(mediaObject);
                cursor.moveToNext();
            }
        }

        if(cursor != null) {
            cursor.close();
        }

        db.close();

        return mediaObjects;
    }
}
