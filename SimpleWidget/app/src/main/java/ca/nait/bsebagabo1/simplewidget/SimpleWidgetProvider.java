package ca.nait.bsebagabo1.simplewidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.widget.RemoteViews;

public class SimpleWidgetProvider extends AppWidgetProvider
{
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        // every 30min they are updating
        ComponentName component = new ComponentName(context, SimpleWidgetProvider.class);
        RemoteViews views = new RemoteViews(context.getPackageName(),R.layout.widget_layout);
        appWidgetManager.updateAppWidget(component,views);
        // then add xml widget file in XML
    }
}
