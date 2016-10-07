package moviles.isaacs.com.isaacs.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteCursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "isaacs.db";

    public static final String TABLE_CONTENTS = "contents";
    public static final String COLUMN_C_ID = "_id";
    public static final String COLUMN_C_DATA = "data";
    public static final String COLUMN_C_DATECREATED = "datecreated";
    public static final String COLUMN_C_TYPE = "type";

    public static final String TABLE_STORIES = "stories";
    public static final String COLUMN_S_ID = "_id";
    public static final String COLUMN_S_TITLE = "title";
    public static final String COLUMN_S_BRIEF = "brief";
    public static final String COLUMN_S_DATECREATED = "datecreated";
    public static final String COLUMN_S_LASTMODIFIED = "lastmodified";

    public static final String TABLE_JOIN_CONTENTS_STORIES = "join_contents_stories";
    public static final String COLUMN_JCS_ID = "_id";
    public static final String COLUMN_JCS_IDCONTENT = "idcontent";
    public static final String COLUMN_JCS_IDSTORY = "idstory";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    // What to do when creating the database for the FIRST time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryContents = "CREATE TABLE " + TABLE_CONTENTS + "(" +
                COLUMN_C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_C_DATA + " TEXT " +
                ");";
        db.execSQL(queryContents);

        String queryStories = "CREATE TABLE " + TABLE_STORIES + "(" +
                COLUMN_S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_S_TITLE + " TEXT " +
                COLUMN_S_BRIEF + " TEXT " +
                COLUMN_S_DATECREATED + " TEXT " +
                COLUMN_S_LASTMODIFIED + " TEXT " +
                ");";
        db.execSQL(queryStories);

        String queryJoinTable = "CREATE TABLE " + TABLE_CONTENTS + "(" +
                COLUMN_JCS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_JCS_IDCONTENT + " INTEGER " +
                COLUMN_JCS_IDSTORY + " INTEGER " +
                ");";
        db.execSQL(queryJoinTable);
    }

    // What to do when upgrading (changing) the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOIN_CONTENTS_STORIES);
        onCreate(db);
    }



     /*
    CRUD: Create; Retrieve; Update; Delete
     */

    // C: Create

    // Add new content
    public void createContent(Content content) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_C_DATA, content.getData());
        values.put(COLUMN_C_DATECREATED, content.getDate_created());
        values.put(COLUMN_C_TYPE, content.getType());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CONTENTS, null, values);
        db.close();
    }

    public Story createStory() {
        return null;
    }



    // R: Retrieve

    public Content getContentById(int idContent) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTENTS + " WHERE " + COLUMN_C_ID + " = \"" + idContent + "\"";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_C_DATA)) != null) {
                // Extract values and create content
            }
        }

        db.close();

        return null;
    }

    public List<Content> getContents() {
        return null;
    }

    public List<Content> getStory(int idStory) {
        return null;
    }

    public List<Story> getStories(){
        return null;
    }



    // U: Update

    public Content updateContent(Content modifiedContent) {
        return null;
    }

    public Story updateStory(Story modifiedStory) {
        return null;
    }



    // D: Delete

    public void deleteContentById(int idContent) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CONTENTS + " WHERE " + COLUMN_C_ID + " =\"" + idContent + "\"");
        db.close();
    }

    public Story removeStory(Story story) {
        return null;
    }

    // Add new relationship content-story

    // Remove new relationship content-story


}
