package ca.nait.bsebagabo1.threaddemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ChatService extends Service
{
    public static final String TAG =  "ChatService";
    static final int DELAY = 1000;
    public boolean bRun = false;
    private ChatThread  thread =

    @Override
    public void onCreate()
    {
        super.onCreate();
        thread = new chatThread();
    }

    public ChatService()
    {
        Log.d(TAG," in constructor");
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private class ChatThread extends Thread{
        public chatThread(){
            super("ChatServiceThread");

        }
    }
}
