package ca.nait.bsebagabo1.saveanddisplaysql;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String TAG = "DatabaseHelper";
    private  static  final String TABLE_NAME = "people_table";
    private static final  String COL1 = "ID";
    private static final String COL2 = "name";
    private static final String CREATED_AT = "created_at";

    public DatabaseHelper(@Nullable Context context)
    {
        super(context,TABLE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL2 + " TEXT)" ;
        db.execSQL(createTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Add data
    public boolean addData(String item){
        Application application;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        Log.d(TAG, "Add Data: Adding "+ item + " to "+ TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    /**
     * Getting all labels
     *
     */
    public List<String> getAllPeoples(){
        List<String> people = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                people.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning people
        return people;
    }

}
