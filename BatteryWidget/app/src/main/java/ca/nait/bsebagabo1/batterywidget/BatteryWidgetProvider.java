package ca.nait.bsebagabo1.batterywidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.RemoteViews;

public class BatteryWidgetProvider extends AppWidgetProvider
{
    RemoteViews views;
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        context.getApplicationContext().registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        ComponentName cn = new ComponentName(context, BatteryWidgetProvider.class);
        appWidgetManager.updateAppWidget(cn,views);
    }
    // called when battery changes
    private BroadcastReceiver batteryReceiver = new BroadcastReceiver()
    {
        // call everytime the battery level changess
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // android uses "level" to track the level of the battery so it's always "level"
            Integer level = intent.getIntExtra("level", -1);
            views = new RemoteViews(context.getPackageName(), R.layout.widget_layout_green);
            views.setTextViewText(R.id.text_view_status, level.toString() + "%");
            ComponentName cn = new ComponentName(context, BatteryWidgetProvider.class);
            AppWidgetManager.getInstance(context).updateAppWidget(cn,views);
        }
    };
}







