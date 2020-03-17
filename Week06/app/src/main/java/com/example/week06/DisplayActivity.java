package com.example.week06;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayActivity extends BaseActivity
{
    static final String TAG = "DisplayActivity";
    DBManager dbManager;
    SQLiteDatabase database;
    Cursor cursor;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        textView = (TextView)this.findViewById(R.id.text_view_display_chatter);
        dbManager = new DBManager(this);
        database = dbManager.getReadableDatabase();
    }

    @Override
    protected void onDestroy()
    {
        database.close();
        super.onDestroy();
    }

    @Override
    protected void onResume()
    {
        cursor = database.query(DBManager.TABLE_NAME,
                null, null, null, null, null, DBManager.C_ID + " DESC");
        startManagingCursor(cursor);
        String sender, date, message, output;
        while(cursor.moveToNext())
        {
            sender = cursor.getString(cursor.getColumnIndex(DBManager.C_SENDER));
            date = cursor.getString(cursor.getColumnIndex(DBManager.C_DATE));
            message = cursor.getString(cursor.getColumnIndex(DBManager.C_MESSAGE));

            output = String.format("%s: from %s - %s\n",date, sender, message );
            textView.append(output);
        }
        Log.d(TAG,"onResume()");
        super.onResume();
    }
}
