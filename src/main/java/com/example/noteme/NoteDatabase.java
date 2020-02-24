package com.example.noteme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "notesDbs1";
    private static final String DATABASE_TABLE = "notesTable1";

    //columns name for database table
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";


    NoteDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    //this method is called when an instance
    //of the NoteDatabase is created
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE tablename(id INT PRIMARY KEY, title TEXT, content TEXT, date TEXT, time TEXT)
        String query = "CREATE TABLE " + DATABASE_TABLE + "(" + KEY_ID + "INT PRIMARY KEY," +
                KEY_TITLE + " TEXT," +
                KEY_CONTENT + " TEXT,"+
                KEY_DATE + " TEXT,"+
                KEY_TIME + " TEXT"+ ")";
        //execute query
        db.execSQL(query);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion >= newVersion)
            return;
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    //add new note to the database from the AddNote Activity
    public long addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        //collect the data
        ContentValues c = new ContentValues();
        c.put(KEY_TITLE, note.getTitle());
        c.put(KEY_CONTENT, note.getContent());
        c.put(KEY_DATE, note.getDate());
        c.put(KEY_TIME, note.getTime());

        long ID = db.insert(DATABASE_TABLE, null,c);
        Log.d("Inserted", "ID =>" + ID);
        note.setID(ID);
        return ID;
    }

    public long saveNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        //collect the data
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited title: ->"+ note.getTitle() + "\n ID-> "+ note.getID());
        c.put(KEY_TITLE, note.getTitle());
        c.put(KEY_CONTENT, note.getContent());
        return db.update(DATABASE_TABLE,c,KEY_ID+"=" + note.getID(),new String[]{String.valueOf(note.getID())});
    }

    public Note getNote(long id){
        // SELECT * FROM databaseName WHERE id=1
        SQLiteDatabase db = this.getReadableDatabase();
        //pointer that points to the specific database column
        Cursor cursor = db.query(DATABASE_TABLE,new String[]{KEY_ID, KEY_TITLE, KEY_CONTENT, KEY_DATE, KEY_TIME}, KEY_ID+"=" + id,
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        return new Note(cursor.getLong(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4));
    }

    //get all the notes from the database and display them in the MainActivity
    public List<Note> getNotes(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Note> allNotes = new ArrayList<>();

        //SELECT * FROM databaseName
        String query = "SELECT * FROM " + DATABASE_TABLE;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setID(cursor.getLong(0));
                note.setTitle(cursor.getString(1));
                note.setContent(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));

                allNotes.add(note);
            }while (cursor.moveToNext());
        }
        return allNotes;
    }
}
