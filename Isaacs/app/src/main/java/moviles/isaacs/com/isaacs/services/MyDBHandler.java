package moviles.isaacs.com.isaacs.services;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteCursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import moviles.isaacs.com.isaacs.models.Content;
import moviles.isaacs.com.isaacs.models.Story;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "isaacs.db";

    public static final String TABLE_CONTENTS = "contents";
    public static final String COLUMN_C_ID = "_id";
    public static final String COLUMN_C_DATA = "data";
    public static final String COLUMN_C_DATECREATED = "datecreated";
    public static final String COLUMN_C_LASTUPDATED = "lastupdated";
    public static final String COLUMN_C_TYPE = "type";

    public static final String TABLE_STORIES = "stories";
    public static final String COLUMN_S_ID = "_id";
    public static final String COLUMN_S_TITLE = "title";
    public static final String COLUMN_S_BRIEF = "brief";
    public static final String COLUMN_S_DATECREATED = "datecreated";
    public static final String COLUMN_S_LASTUPDATED = "lastupdated";

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
                COLUMN_C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_C_TYPE + " INTEGER ," +
                COLUMN_C_DATA + " TEXT ," +
                COLUMN_C_DATECREATED + " INTEGER ," +
                COLUMN_C_LASTUPDATED + " INTEGER " +
                ");";
        db.execSQL(queryContents);

        String queryStories = "CREATE TABLE " + TABLE_STORIES + "(" +
                COLUMN_S_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_S_TITLE + " TEXT ," +
                COLUMN_S_BRIEF + " TEXT ," +
                COLUMN_S_DATECREATED + " INTEGER ," +
                COLUMN_S_LASTUPDATED + " INTEGER " +
                ");";
        db.execSQL(queryStories);

        String queryJoinTable = "CREATE TABLE " + TABLE_CONTENTS + "(" +
                COLUMN_JCS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_JCS_IDCONTENT + " INTEGER ," +
                COLUMN_JCS_IDSTORY + " INTEGER ," +
                "UNIQUE ("+COLUMN_JCS_IDCONTENT+","+COLUMN_JCS_IDSTORY+") ON CONFLICT REPLACE" +
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
    public boolean createContent(Content content) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_C_TYPE, content.getType());
        values.put(COLUMN_C_DATA, content.getData());
        values.put(COLUMN_C_DATECREATED, convertDateToLong(content.getDateCreated()));
        values.put(COLUMN_C_LASTUPDATED, convertDateToLong(content.getLastUpdated()));

        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_CONTENTS, null, values);
        db.close();

        return result >= 0;
    }

    public boolean createStory(Story story) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_S_TITLE, story.getTitle());
        values.put(COLUMN_S_BRIEF, story.getBrief());
        values.put(COLUMN_S_DATECREATED, convertDateToLong(story.getDateCreated()));
        values.put(COLUMN_S_LASTUPDATED, convertDateToLong(story.getLastUpdated()));

        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_STORIES, null, values);
        db.close();

        return result >= 0;
    }



    // R: Retrieve

    public Content getContent(int idContent) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTENTS + " WHERE " + COLUMN_C_ID + " = \"" + idContent + "\"";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        Content content = new Content();

        while(!c.isAfterLast()) {
            if(c.getString(c.getColumnIndex(COLUMN_C_DATA)) != null) {
                /*content.set_id(c.getInt(c.getColumnIndex(COLUMN_C_ID)));
                content.setType(c.getString(c.getColumnIndex(COLUMN_C_TYPE)));
                content.setDateCreated(c.getString(c.getColumnIndex(COLUMN_C_DATECREATED)));
                content.setData(c.getString(c.getColumnIndex(COLUMN_C_DATA))); // Here is necessary to parse JSON first
                content.setType(c.getString(c.getColumnIndex(COLUMN_C_TYPE)));
                content.setStories(null); // Retrieve content's stories*/
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
                /*Content content = new Content();
                content.set_id(c.getInt(c.getColumnIndex(COLUMN_C_ID)));
                content.setType(c.getString(c.getColumnIndex(COLUMN_C_TYPE)));
                content.setDateCreated(c.getString(c.getColumnIndex(COLUMN_C_DATECREATED)));
                content.setData(c.getString(c.getColumnIndex(COLUMN_C_DATA))); // Here is necessary to parse JSON first
                content.setType(c.getString(c.getColumnIndex(COLUMN_C_TYPE)));
                content.setStories(null); // Retrieve content's stories
                contents.add(content);*/
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
                /*story.set_id(c.getInt(c.getColumnIndex(COLUMN_S_ID)));
                story.setBrief(c.getString(c.getColumnIndex(COLUMN_S_BRIEF)));
                story.setTitle(c.getString(c.getColumnIndex(COLUMN_S_TITLE)));
                story.setDateCreated(c.getString(c.getColumnIndex(COLUMN_S_DATECREATED)));
                story.setLastModified(c.getString(c.getColumnIndex(COLUMN_S_LASTMODIFIED)));
                story.setContents(null);*/
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
                /*Story story = new Story();
                story.set_id(c.getInt(c.getColumnIndex(COLUMN_S_ID)));
                story.setBrief(c.getString(c.getColumnIndex(COLUMN_S_BRIEF)));
                story.setTitle(c.getString(c.getColumnIndex(COLUMN_S_TITLE)));
                story.setDateCreated(c.getString(c.getColumnIndex(COLUMN_S_DATECREATED)));
                story.setLastModified(c.getString(c.getColumnIndex(COLUMN_S_LASTMODIFIED)));
                story.setContents(null);
                stories.add(story);*/
            }
        }

        db.close();

        return stories;
    }



    // U: Update

    public boolean updateContent(Content modifiedContent) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_C_TYPE, modifiedContent.getType());
        values.put(COLUMN_C_DATA, modifiedContent.getData());
        values.put(COLUMN_C_DATECREATED, convertDateToLong(modifiedContent.getDateCreated()));
        values.put(COLUMN_C_LASTUPDATED, convertDateToLong(modifiedContent.getLastUpdated()));

        SQLiteDatabase db = getWritableDatabase();
        int result = db.update(TABLE_CONTENTS, values, COLUMN_C_ID + "=" + modifiedContent.get_id(), null);
        db.close();

        return result > 0;
    }

    public boolean updateStory(Story modifiedStory) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_S_TITLE, modifiedStory.getTitle());
        values.put(COLUMN_S_BRIEF, modifiedStory.getBrief());
        values.put(COLUMN_S_DATECREATED, convertDateToLong(modifiedStory.getDateCreated()));
        values.put(COLUMN_S_LASTUPDATED, convertDateToLong(modifiedStory.getLastUpdated()));

        SQLiteDatabase db = getWritableDatabase();
        int result = db.update(TABLE_STORIES, values, COLUMN_S_ID + "=" + modifiedStory.get_id(), null);
        db.close();

        return result > 0;
    }



    // D: Delete

    public boolean deleteContentById(Content content) {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(TABLE_CONTENTS, COLUMN_C_ID + "=" + content.get_id(), null);
        db.close();

        return result > 0;
    }

    public boolean removeStory(Story story) {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(TABLE_STORIES, COLUMN_S_ID + "=" + story.get_id(), null);
        db.close();
        
        return result > 0;
    }

    // Add new relationship content-story

    public boolean addContentToStory(Content content, Story story) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_JCS_IDCONTENT, content.get_id());
        values.put(COLUMN_JCS_IDSTORY, story.get_id());

        SQLiteDatabase db = getWritableDatabase();
        long result = db.insert(TABLE_JOIN_CONTENTS_STORIES, null, values);
        db.close();

        return result >= 0;
    }

    // Remove new relationship content-story

    public boolean removeContentFromStory(Content content, Story story) {
        SQLiteDatabase db = getWritableDatabase();
        int result = db.delete(TABLE_JOIN_CONTENTS_STORIES, COLUMN_JCS_IDCONTENT + " = " + content.get_id() + " AND " + COLUMN_JCS_IDSTORY + " = " + story.get_id(), null);
        db.close();

        return result > 0;
    }


    // Utils

    public long convertDateToLong(Date date) {
        return date.getTime();
    }

    public Date convertLongToDate(Long numberDate) {
        return new Date(numberDate);
    }

}