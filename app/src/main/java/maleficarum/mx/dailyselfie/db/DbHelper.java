package maleficarum.mx.dailyselfie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by oscar on 11/27/14.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "selfies";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_FILE = "file";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_HOUR = "hour";

    private static final String DATABASE_NAME = "selfie.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_FILE + " text not null,"
            + COLUMN_DATE + " TEXT NOT NULL,"
            + COLUMN_HOUR + " TEXT NOT NULL);";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
