package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "mynotes.db";
    private static final int DB_VER = 1;

    public DBHelper(Context ctx) {
        super(ctx, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE notes(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, content TEXT);");

        // Prepopulate sample notes
        ContentValues cv = new ContentValues();
        cv.put("title", "Welcome");
        cv.put("content", "This is a sample note. Tap + to add another.");
        db.insert("notes", null, cv);

        cv = new ContentValues();
        cv.put("title", "Second note");
        cv.put("content", "You can edit or delete notes. Long-press to delete from the list.");
        db.insert("notes", null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notes");
        onCreate(db);
    }

    public long insertNote(String title, String content) {
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("content", content);
        return getWritableDatabase().insert("notes", null, cv);
    }

    public int updateNote(int id, String title, String content) {
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("content", content);
        return getWritableDatabase().update("notes", cv, "id = ?", new String[] { String.valueOf(id) });
    }

    public int deleteNote(int id) {
        return getWritableDatabase().delete("notes", "id = ?", new String[] { String.valueOf(id) });
    }

    public Note getNote(int id) {
        Cursor c = getReadableDatabase().rawQuery("SELECT id, title, content FROM notes WHERE id = ?",
                new String[] { String.valueOf(id) });
        try {
            if (c.moveToFirst()) {
                return new Note(c.getInt(0), c.getString(1), c.getString(2), "");
            }
        } finally {
            c.close();
        }
        return null;
    }

    public List<Note> getAllNotes() {
        List<Note> out = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("SELECT id, title, content FROM notes ORDER BY id DESC", null);
        try {
            while (c.moveToNext()) {
                out.add(new Note(c.getInt(0), c.getString(1), c.getString(2), ""));
            }
        } finally {
            c.close();
        }
        return out;
    }
}
