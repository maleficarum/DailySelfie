package maleficarum.mx.dailyselfie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.w3c.dom.Comment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import maleficarum.mx.dailyselfie.ListViewItem;

/**
 * Created by oscar on 11/27/14.
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
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_DATE, item.description);
        values.put(DbHelper.COLUMN_HOUR, item.hours);

        long insertId = database.insert(DbHelper.TABLE_NAME, null,values);

        return item;
    }

    public List<ListViewItem> getAllItems() {
        List<ListViewItem> items = new ArrayList<ListViewItem>();

        Cursor cursor = database.query(DbHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            ListViewItem item = cursorToItem(cursor);
            //comments.add(comment);
            cursor.moveToNext();
        }

        cursor.close();
        return items;
    }

    private ListViewItem cursorToItem(Cursor cursor) {
        /*Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;*/
        return null;
    }
}