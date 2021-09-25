package com.example.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.univ_mem.R;

/**
 * Created by SunPeng.
 * USER : SunPeng.
 * DATE : 2021/9/2.
 * TIME : 20:58.
 */

/**
 * Implementation of App Widget functionality.
 */
public class UnivMemWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.app_name);
        CharSequence widgetText1="今天也要努力加油鸭！！！！";
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.univ_mem_widget);
        views.setTextViewText(R.id.tv_title, widgetText);
        Intent intent = new Intent(context, LoginActivity.class);

        PendingIntent Pintent = PendingIntent
                .getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ll_right, Pintent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}