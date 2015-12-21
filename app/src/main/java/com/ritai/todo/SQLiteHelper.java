package com.ritai.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by ritai on 12/21/15.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ItemDB";

    private static final String TABLE_ITEMS = "items";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DATE = "dueDate";
    private static final String KEY_PRIORITY = "priority";
    private static final String KEY_NOTES = "notes";
    private static final String KEY_STATUS = "status";
    private static final String[] COLUMNS = {KEY_ID, KEY_TITLE, KEY_DATE, KEY_PRIORITY, KEY_NOTES, KEY_STATUS};


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "CREATE TABLE items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "dueDate DATE, " +
                "priority INTEGER, " +
                "notes TEXT, " +
                "status INTEGER )";
        db.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS items");
        this.onCreate(db);;
    }

    public void addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = createContentValuesFromItem(item);

        db.insert(TABLE_ITEMS, null, values);
        db.close();
    }

    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                    db.query(TABLE_ITEMS,   // table
                            COLUMNS,        // column names
                            " id = ?",      // selection
                            new String[]{String.valueOf(id)}, // selection id
                            null,           // group by
                            null,           // having
                            null,           // order by
                            null);          // limit
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursorToItem(cursor);
    }

    public List<Item> getAllItems() {
        List<Item> items = new LinkedList<Item>();

        String query = "SELECT * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Item item = null;
        if (cursor.moveToFirst()) {
            do {
                item = cursorToItem(cursor);
                items.add(item);
            } while (cursor.moveToNext());
        }
        return items;
    }

    public int updateItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = createContentValuesFromItem(item);
        int numRowsAffected = db.update(TABLE_ITEMS,
                values,
                KEY_ID + " = ?",
                new String[]{String.valueOf(item.getId())}
        );
        db.close();
        return numRowsAffected;
    }

    public void deleteItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS,
                KEY_ID + " = ?",
                new String[] { String.valueOf(item.getId()) }
                );
        db.close();
    }

    private ContentValues createContentValuesFromItem(Item item) {
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, item.getTitle());
        values.put(KEY_DATE, item.getDueDate().toString());
        values.put(KEY_PRIORITY, item.getPriority());
        values.put(KEY_NOTES, item.getNotes());
        values.put(KEY_STATUS, item.getStatus());
        return values;
    }

    private Item cursorToItem(Cursor c) {
        int id = Integer.parseInt(c.getString(0));
        String title = c.getString(1);
        String dueDate = c.getString(2);
        int priority = Integer.parseInt(c.getString(3));
        String notes = c.getString(4);
        int status = Integer.parseInt(c.getString(5));
        // Come back and fix me, lol
        Item item = new Item(title, dueDate, priority, notes, status);
        item.setId(id);

        return item;
    }

}
