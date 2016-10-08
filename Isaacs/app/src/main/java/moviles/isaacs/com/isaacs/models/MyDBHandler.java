package moviles.isaacs.com.isaacs.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteCursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;
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

    public void createStory(Story story) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_S_TITLE, story.getTitle());
        values.put(COLUMN_S_BRIEF, story.getBrief());
        values.put(COLUMN_S_DATECREATED, story.getDateCreated());
        values.put(COLUMN_S_LASTMODIFIED, story.getLastModified());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_STORIES, null, values);
        db.close();
    }



    // R: Retrieve

    public Content getContentById(int idContent) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTENTS + " WHERE " + COLUMN_C_ID + " = \"" + idContent + "\"";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        Content content = new Content();

        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_C_DATA)) != null) {
                content.set_id(c.getInt(c.getColumnIndex(COLUMN_C_ID)));
                content.setType(c.getString(c.getColumnIndex(COLUMN_C_TYPE)));
                content.setDateCreated(c.getString(c.getColumnIndex(COLUMN_C_DATECREATED)));
                content.setData(c.getString(c.getColumnIndex(COLUMN_C_DATA))); // Here is necessary to parse JSON first
                content.setType(c.getString(c.getColumnIndex(COLUMN_C_TYPE)));
                content.setStories(null); // Retrieve content's stories
            }
        }

        db.close();

        return content;
    }

    public List<Content> getContents() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTENTS;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        List<Content> contents = new ArrayList<>();

        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_C_DATA)) != null) {
                Content content = new Content();
                content.set_id(c.getInt(c.getColumnIndex(COLUMN_C_ID)));
                content.setType(c.getString(c.getColumnIndex(COLUMN_C_TYPE)));
                content.setDateCreated(c.getString(c.getColumnIndex(COLUMN_C_DATECREATED)));
                content.setData(c.getString(c.getColumnIndex(COLUMN_C_DATA))); // Here is necessary to parse JSON first
                content.setType(c.getString(c.getColumnIndex(COLUMN_C_TYPE)));
                content.setStories(null); // Retrieve content's stories
                contents.add(content);
            }
        }

        db.close();

        return contents;
    }

    public Story getStory(int idStory) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_STORIES + " WHERE " + COLUMN_S_ID + " = \"" + idStory + "\"";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        Story story = new Story();

        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_S_TITLE)) != null) {
                story.set_id(c.getInt(c.getColumnIndex(COLUMN_S_ID)));
                story.setBrief(c.getString(c.getColumnIndex(COLUMN_S_BRIEF)));
                story.setTitle(c.getString(c.getColumnIndex(COLUMN_S_TITLE)));
                story.setDateCreated(c.getString(c.getColumnIndex(COLUMN_S_DATECREATED)));
                story.setLastModified(c.getString(c.getColumnIndex(COLUMN_S_LASTMODIFIED)));
                story.setContents(null);
            }
        }

        db.close();

        return story;
    }

    public List<Story> getStories(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_STORIES;

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        List<Story> stories = new ArrayList<Story>();

        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_S_TITLE)) != null) {
                Story story = new Story();
                story.set_id(c.getInt(c.getColumnIndex(COLUMN_S_ID)));
                story.setBrief(c.getString(c.getColumnIndex(COLUMN_S_BRIEF)));
                story.setTitle(c.getString(c.getColumnIndex(COLUMN_S_TITLE)));
                story.setDateCreated(c.getString(c.getColumnIndex(COLUMN_S_DATECREATED)));
                story.setLastModified(c.getString(c.getColumnIndex(COLUMN_S_LASTMODIFIED)));
                story.setContents(null);
                stories.add(story);
            }
        }

        db.close();

        return stories;
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
