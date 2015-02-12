package banand.lionel.com.tpandroid.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;

/**
 * Created by lionel on 06/10/14.
 */
public class TpAndroidSqlHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + "MediasDB.db";
    public static final String TABLE_MEDIAS = "medias";
    public static final String MEDIAS_ID = "id";
    public static final String MEDIAS_NAME = "name";
    public static final String MEDIAS_VERSION_CODE = "version_code";
    public static final String MEDIAS_TYPE = "type";
    public static final String MEDIAS_LOCAL_PATH = "local_path";


    public TpAndroidSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
// SQL statement to create media table
        String CREATE_MEDIA_TABLE = "CREATE TABLE " + TABLE_MEDIAS +" ( " +
                MEDIAS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MEDIAS_NAME + " TEXT, " +
                MEDIAS_VERSION_CODE + " TEXT, " +
                MEDIAS_TYPE + " TEXT, " +
                MEDIAS_LOCAL_PATH + " TEXT )";

        // create medias table
        sqLiteDatabase.execSQL(CREATE_MEDIA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop older medias table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS books");

        // create fresh medias table
        onCreate(sqLiteDatabase);

        //here u can manage the db upgrade with alter table for example
    }
}
