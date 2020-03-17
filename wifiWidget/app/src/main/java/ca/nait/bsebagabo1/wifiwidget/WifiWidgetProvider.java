package ca.nait.bsebagabo1.wifiwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.widget.RemoteViews;

public class WifiWidgetProvider extends AppWidgetProvider
{
    // remote views
    RemoteViews views;
    public static final String TAG = "WifiWidgetProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
     ComponentName thisWidget = new ComponentName(context, WifiWidgetProvider.class);
     int[] widgetIdArray = appWidgetManager.getAppWidgetIds(thisWidget);
     for ( int widgetId : widgetIdArray){
         // both intent
         Intent intent = new Intent(context, WifiWidgetProvider.class);
         // set action for intent
         intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
         intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
         // Pending Intent
         // broadcast
         PendingIntent pendingIntent =
         PendingIntent.getBroadcast(context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
         // use the wifi manager check if the status is on or off
         // GETTING ACCESS ON THE WIFI
         WifiManager wifiManager =
                 (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
         if (wifiManager.isWifiEnabled()){
             wifiManager.setWifiEnabled(false);
             views = new RemoteViews(context.getPackageName(), R.layout.widget_layout_red);
         }
         else{
             wifiManager.setWifiEnabled(true);
             views = new RemoteViews(context.getPackageName(), R.layout.widget_layout_green);
         }

         views.setOnClickPendingIntent(R.id.text_view_status, pendingIntent);
         appWidgetManager.updateAppWidget(thisWidget, views);
     }
    }
}
