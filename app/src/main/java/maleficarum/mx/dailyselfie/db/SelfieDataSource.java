package maleficarum.mx.dailyselfie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.w3c.dom.Comment;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import maleficarum.mx.dailyselfie.ListViewItem;


/**
 * @author  maleficarum [ github.com/maleficarum ]
 */
public class SelfieDataSource {

    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] allColumns = { DbHelper.COLUMN_ID,
            DbHelper.COLUMN_FILE,
            DbHelper.COLUMN_DATE,
            DbHelper.COLUMN_HOUR};

    public SelfieDataSource(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ListViewItem createItem(ListViewItem item) {
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        item.image.compress(Bitmap.CompressFormat.PNG, 100, bos);

        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_DATE, item.description);
        values.put(DbHelper.COLUMN_HOUR, item.hours);
        values.put(DbHelper.COLUMN_FILE, bos.toByteArray());

        long insertId = database.insert(DbHelper.TABLE_NAME, null,values);

        Log.i("Maleficarum", "Created item " + insertId);

        return item;
    }

    public List<ListViewItem> getAllItems() {
        List<ListViewItem> items = new ArrayList<ListViewItem>();

        Cursor cursor = database.query(DbHelper.TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            items.add(cursorToItem(cursor));
            cursor.moveToNext();
        }

        Log.i("Maleficarum", "Fetched " + items);

        cursor.close();
        return items;
    }

    private ListViewItem cursorToItem(Cursor cursor) {
        byte[] img = cursor.getBlob(cursor.getColumnIndex(DbHelper.COLUMN_FILE));
        Bitmap bm= BitmapFactory.decodeByteArray(img, 0, img.length);
        ListViewItem item = new ListViewItem("Daily Selfie", cursor.getString(2), cursor.getString(3), bm);
        return item;
    }
}
