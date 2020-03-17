package ca.nait.bsebagabo1.buttonwidget;

import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class ButtonWidgetProvider extends AppWidgetProvider
{
    public static String BUTTON_ACTION_LAUNCH = "launch_button";
    public static String BUTTON_ACTION_NOTIFY = "notify_button";
    public static String TAG = "ButtonWidgetProvider";
    public static String MESSAGE_KEY = "message";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        Log.d(TAG, "In OnUpdate()");
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widgetlayout);
        Intent launchIntent = new Intent(context, MainActivity.class);
        launchIntent.setAction(BUTTON_ACTION_LAUNCH);

        Intent notifyIntent = new Intent(context, ButtonWidgetProvider.class);
        notifyIntent.setAction(BUTTON_ACTION_NOTIFY);
        notifyIntent.putExtra(MESSAGE_KEY, "Notification: Message posted");

        // made to be executed later Creating a pending Intent
        PendingIntent launchPendingIntent = PendingIntent.getActivity(context,0 , launchIntent,0 );
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(context, 0, notifyIntent, 0);

        // attaching pending intents to the buttons
        views.setOnClickPendingIntent(R.id.button_one, launchPendingIntent);
        views.setOnClickPendingIntent(R.id.button_two, notifyPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetIds,views);

        // leave the super
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getAction().equals(BUTTON_ACTION_NOTIFY)){
            // RUN THIS FOR NOTIFICATION
            String msg = null;
            try{
                msg = intent.getStringExtra(MESSAGE_KEY);

            }catch (NullPointerException npe){
                Log.d(TAG, "Message is null");
            }
            PendingIntent notifyIntent = PendingIntent.getActivity(context, 0, intent, 0);
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setAutoCancel(false);
            builder.setTicker("this is the ticker text");
            builder.setContentTitle("Content title");
            builder.setContentText(msg);
            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setContentIntent(notifyIntent);
            builder.setOngoing(true);
            builder.setNumber(100);
            builder.build();

            Notification notification = builder.getNotification();
            nm.notify(1, notification);


        }
        super.onReceive(context, intent);
    }
}



