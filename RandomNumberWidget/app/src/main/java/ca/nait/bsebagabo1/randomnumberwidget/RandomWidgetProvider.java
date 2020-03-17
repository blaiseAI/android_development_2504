package ca.nait.bsebagabo1.randomnumberwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Random;

public class RandomWidgetProvider extends AppWidgetProvider
{
    RemoteViews views;
    public static int currentColor = 1;
    private static final String TAG = "RandomWidgetProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        ComponentName thisWidget = new ComponentName(context, RandomWidgetProvider.class);
        int[] widgetArray = appWidgetManager.getAppWidgetIds(thisWidget);
        for(int widgetId : widgetArray)
        {
            int number = new Random().nextInt(100);
            switch (currentColor){
                case 1:{
                    views = new RemoteViews(context.getPackageName(),R.layout.blue_layout);
                    currentColor = 2;
                    break;
                }
                case 2: {
                    views = new RemoteViews(context.getPackageName(),R.layout.red_layout_widget);
                    currentColor = 3;
                    break;
                }
                case 3: {
                    views = new RemoteViews(context.getPackageName(),R.layout.green_layout_widget);
                    currentColor = 1;
                    break;
                }
            }//eof of switch
            Intent intent = new Intent(context, RandomWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.text_view_random_number, pendingIntent);
            views.setTextViewText(R.id.text_view_random_number, String.valueOf(number));
            appWidgetManager.updateAppWidget(thisWidget, views);
        }//eof for loop
    }
}
