package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLiteOpenHelper for notes. Creates table `notes` with columns:
 * id (INTEGER PRIMARY KEY AUTOINCREMENT), title (TEXT), content (TEXT), date
 * (TEXT)
 *
 * Provides simple add/update/delete/getAll methods that work with the `Note`
 * model.
 */
public class NotesDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mynotes.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NOTES = "notes";
    public static final String COL_ID = "id";
    public static final String COL_TITLE = "title";
    public static final String COL_CONTENT = "content";
    public static final String COL_DATE = "date";

    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TITLE + " TEXT, "
            + COL_CONTENT + " TEXT, "
            + COL_DATE + " TEXT"
            + ");";

    public NotesDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_NOTES);

        // Optionally prepopulate with sample notes
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, "Welcome");
        cv.put(COL_CONTENT, "This is a sample note. Tap + to add another.");
        cv.put(COL_DATE, "");
        db.insert(TABLE_NOTES, null, cv);

        cv.clear();
        cv.put(COL_TITLE, "Second note");
        cv.put(COL_CONTENT, "You can edit or delete notes. Long-press to delete from the list.");
        cv.put(COL_DATE, "");
        db.insert(TABLE_NOTES, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    /**
     * Add a new note. Returns the inserted row id (long).
     */
    public long addNote(String title, String content, String date) {
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, title);
        cv.put(COL_CONTENT, content);
        cv.put(COL_DATE, date);
        return getWritableDatabase().insert(TABLE_NOTES, null, cv);
    }

    /**
     * Update an existing note by id. Returns number of rows affected.
     */
    public int updateNote(int id, String title, String content, String date) {
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, title);
        cv.put(COL_CONTENT, content);
        cv.put(COL_DATE, date);
        return getWritableDatabase().update(TABLE_NOTES, cv, COL_ID + " = ?", new String[] { String.valueOf(id) });
    }

    /**
     * Delete a note by id. Returns number of rows deleted.
     */
    public int deleteNote(int id) {
        return getWritableDatabase().delete(TABLE_NOTES, COL_ID + " = ?", new String[] { String.valueOf(id) });
    }

    /**
     * Fetch all notes ordered by id desc.
     */
    public List<Note> getAllNotes() {
        List<Note> out = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;
        try {
            c = db.query(TABLE_NOTES,
                    new String[] { COL_ID, COL_TITLE, COL_CONTENT, COL_DATE },
                    null, null, null, null,
                    COL_ID + " DESC");
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String title = c.getString(1);
                String content = c.getString(2);
                String date = c.getString(3);
                out.add(new Note(id, title, content, date));
            }
        } finally {
            if (c != null)
                c.close();
        }
        return out;
    }

    /**
     * Optional: fetch a single note by id.
     */
    public Note getNoteById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;
        try {
            c = db.query(TABLE_NOTES,
                    new String[] { COL_ID, COL_TITLE, COL_CONTENT, COL_DATE },
                    COL_ID + " = ?", new String[] { String.valueOf(id) },
                    null, null, null);
            if (c.moveToFirst()) {
                return new Note(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
            }
        } finally {
            if (c != null)
                c.close();
        }
        return null;
    }

    /**
     * Fetch all notes sorted by date. If the date column is empty the order
     * may be undefined for those rows.
     */
    public List<Note> getAllNotesSortedByDate(boolean asc) {
        List<Note> out = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;
        String order = COL_DATE + (asc ? " ASC" : " DESC");
        try {
            c = db.query(TABLE_NOTES,
                    new String[] { COL_ID, COL_TITLE, COL_CONTENT, COL_DATE },
                    null, null, null, null,
                    order);
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String title = c.getString(1);
                String content = c.getString(2);
                String date = c.getString(3);
                out.add(new Note(id, title, content, date));
            }
        } finally {
            if (c != null)
                c.close();
        }
        return out;
    }

    /**
     * Delete all notes from the table.
     */
    public int deleteAllNotes() {
        return getWritableDatabase().delete(TABLE_NOTES, null, null);
    }
}
