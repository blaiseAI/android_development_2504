package com.example.week06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class CursorActivity extends BaseActivity
{
    static final String TAG = "CursorActivity";
    DBManager dbManager;
    SQLiteDatabase database;
    Cursor cursor;
    ListView listView;
    DBAdapter dbAdapter;
    ChatReceiver receiver;
    IntentFilter filter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursor);
        listView = findViewById(R.id.list_view_cursor);
        dbManager = new DBManager(this);
        database = dbManager.getReadableDatabase();

        receiver = new ChatReceiver();
        filter = new IntentFilter(ChatService.NEW_MESSAGE_RECEIVED);
    }

    @Override
    protected void onResume()
    {
        cursor = database.query(DBManager.TABLE_NAME, null, null, null, null, null, DBManager.C_ID + " DESC");
        startManagingCursor(cursor);

        dbAdapter = new DBAdapter(this, cursor);
        listView.setAdapter(dbAdapter);

        registerReceiver(receiver, filter);
        Log.d(TAG, "in onResume()");
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        unregisterReceiver(receiver);
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        database.close();
        super.onDestroy();
    }
    class ChatReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            cursor.requery();
            dbAdapter.notifyDataSetChanged();
            Log.d(TAG, "broadcast received");
        }
    }
}













