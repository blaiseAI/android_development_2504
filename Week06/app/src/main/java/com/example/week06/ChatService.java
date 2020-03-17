package com.example.week06;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

public class ChatService extends Service
{
    static final String TAG = "ChatService";
    public static final String NEW_MESSAGE_RECEIVED = "new_message_received";
    static final int DELAY = 10000;
    public static boolean bRun = false;
    private ChatThread theThread = null;

    DBManager dbManager;
    SQLiteDatabase database;


    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        dbManager = new DBManager(this);

        theThread = new ChatThread("ChatServiceThread");
        Log.d(TAG,"onCreate() instantiate a new Thread");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        bRun = true;
        theThread.start();
        Log.d(TAG,"service started");
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        bRun = false;
        theThread.interrupt();
        theThread = null;
        Log.d(TAG,"onDestroy() - stopping the Thread");

        super.onDestroy();
    }

    class ChatThread extends Thread
    {
        public ChatThread(String name)
        {
            super(name);
        }

        @Override
        public void run()
        {
            while(bRun == true)
            {
                try
                {
                    Log.d(TAG, "reader executed one cycle");
                    if (getFromServer() == true)
                    {
                        Intent broadcast = new Intent(NEW_MESSAGE_RECEIVED);
                        sendBroadcast(broadcast);
                        Log.d(TAG, "broadcast sent");
                    }
                    Thread.sleep(DELAY);
                }
                catch(InterruptedException e)
                {
                    bRun = false;
                }
            }
        }
    }
    public boolean getFromServer()
    {
        boolean bNewMessage = false;
        BufferedReader in;
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI("http://www.youcode.ca/Week05Servlet"));
            HttpResponse response = client.execute(request);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";

            database = dbManager.getWritableDatabase();
            ContentValues values = new ContentValues();

            while((line = in.readLine()) != null)
            {
                values.clear();

                values.put(DBManager.C_ID, Integer.parseInt(line));

                line = in.readLine();
                values.put(DBManager.C_SENDER, line);

                line = in.readLine();
                values.put(DBManager.C_MESSAGE, line);

                line = in.readLine();
                values.put(DBManager.C_DATE, line);

                try
                {
                    database.insertOrThrow(DBManager.TABLE_NAME, null, values);
                    bNewMessage = true;
                    Log.d(TAG, "record added to database ");
                }
                catch(SQLException sqle)
                {
                    //Log.d(TAG, "duplicate record ");
                }
            }// closes while loop
            in.close();
            database.close();
        }
        catch(Exception e)
        {
            Log.d(TAG, "read failed " + e);
        }
        return bNewMessage;
    }
}

















