package dev.diviz.ipOnWidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.content.ComponentName




/**
 * Implementation of App Widget functionality.
 */
class AppWidget : AppWidgetProvider() {
    val networkHelper = NetworkHelper()

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {


            val widgetText = networkHelper.getStringInfo()
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.app_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            views.setOnClickPendingIntent(R.id.appwidget_text, createIntentForTextAction(context))

            views.setOnClickPendingIntent(R.id.btnUpdate, createIntentForUpdateAction(context, appWidgetId, appWidgetIds))

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun createIntentForTextAction(context: Context): PendingIntent  {
        return PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createIntentForUpdateAction(context: Context, appWidgetId: Int, appWidgetIds: IntArray): PendingIntent  {

        val intent = Intent(context, AppWidget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        //val ids = AppWidgetManager.getInstance(context.applicationContext).getAppWidgetIds(ComponentName(context.applicationContext,
        //    AppWidgetProvider::class.java!!))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)

        return PendingIntent.getBroadcast(
            context,
            appWidgetId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

