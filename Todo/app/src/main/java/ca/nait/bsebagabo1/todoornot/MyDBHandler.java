package ca.nait.bsebagabo1.todoornot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyDBHandler extends SQLiteOpenHelper
{

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    /* information of Database */
    // Database version
    private static final int DATABASE_VERSION = 1;
    // Database name
    private static final String DATABASE_NAME = "Lab02DB.db";

    // Table Names
    private static final  String TABLE_TITLE = "listTitle";
    private static final String TABLE_ITEM= "listItem";
    private static final String TABLE_ITEM_TITLE="item_titles";

    // Common Column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT= "created_at";

    // List Item Table - column Names
    private static final String KEY_ITEM_CONTENT= "listItemContent";
    private static final String KEY_STATUS = "status";

    //  List Title Table - column Names
    private static final String KEY_LIST_NAME = "list_name";

    // Associative Table item_title table
    private static final String KEY_TITLE_ID= "title_id";
    private static final String KEY_ITEM_ID="item_id";

    // Table create statement
    // List Item Table create statement
    private static final String CREATE_TABLE_LIST_ITEM = "CREATE TABLE "
            + TABLE_ITEM + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_ITEM_CONTENT
            + " TEXT," + KEY_STATUS + " INTEGER," + KEY_CREATED_AT
            + " DATETIME" + ")";

    // Title List Table create statement for list Name tags
    private static final String CREATE_TABLE_LIST_TITLE= "CREATE TABLE " + TABLE_TITLE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LIST_NAME + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    // item_title Table create statement
    private static final String CREAT_TABLE_ITEM_TITLE= "CREATE TABLE "
            + TABLE_ITEM_TITLE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_ITEM_ID + " INTEGER," + KEY_TITLE_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";


    public MyDBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // creating required tables
        db.execSQL(CREATE_TABLE_LIST_ITEM);
        db.execSQL(CREATE_TABLE_LIST_TITLE);
        db.execSQL(CREAT_TABLE_ITEM_TITLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TITLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM_TITLE);

        // create new tables
        onCreate(db);
    }
    /**
     * CRUD METHODS
     */

    // Create
    public long createItem(ListItem todo, long[]title_ids){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_CONTENT, todo.getListItemContent());
        values.put(KEY_STATUS, todo.getStatus());
        values.put(KEY_CREATED_AT, getDateTime());
        // insert row
        long item_id = db.insert(TABLE_ITEM,null, values);
        // assigning Title tags to item
        for(long title_id : title_ids){
            createItemTile(item_id, title_id);
        }
        return item_id;
    }

    /**
     * Fetches an Item from ListItem table
     * get single Item
     * @param item_id
     * @return
     */
    public ListItem getItem(long item_id){
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ITEM + " WHERE "
                + KEY_ID + " = " + item_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        ListItem li = new ListItem();
        li.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        li.setListItemContent((c.getString(c.getColumnIndex(KEY_ITEM_CONTENT))));
        li.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
        return li;
    }

    /**
     *  Getting all list items
     * @return
     */
    public List<ListItem> getAllItems(){
        List<ListItem> items = new ArrayList<ListItem>();
        String selectQuery= "SELECT * FROM "+ TABLE_ITEM;
        Log.e(LOG, selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ListItem li = new ListItem();
                li.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                li.setListItemContent((c.getString(c.getColumnIndex(KEY_ITEM_CONTENT))));
                li.setCreated_at(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

                // adding to item list
                items.add(li);
            } while (c.moveToNext());
        }

        return items;
    }

    /**
     * Getting All items under single Title
     * @param title_name
     * @return
     */
    public List<ListItem> getAllItemByTitle(String title_name){

    }





    private void createItemTile(long item_id, long title_id)
    {
    }

    private String getDateTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
