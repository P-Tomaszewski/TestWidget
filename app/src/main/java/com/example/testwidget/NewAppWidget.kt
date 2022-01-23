package com.example.testwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.Log.INFO
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.ImageViewCompat
import com.example.testwidget.databinding.NewAppWidgetBinding

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {


   var flag: Boolean = false

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val appWidgetManager = AppWidgetManager
            .getInstance(context)
        if(intent?.action == "Action1"){
            Log.d("pacname", context?.packageName.toString())
//            Toast.makeText(context, "Action1", Toast.LENGTH_SHORT).show()
            val views = RemoteViews(context?.packageName, R.layout.new_app_widget)
                Log.d("flaga p false", flag.toString())
                views.setImageViewResource(R.id.imageView, R.drawable.img1)
            val thisAppWidget = context?.let {
                ComponentName(
                    it, NewAppWidget::class.java
                )
            }
           var appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget)
            appWidgetManager.updateAppWidget(appWidgetIds[0], views)
        }

        if(intent?.action == "Action2"){
            val views = RemoteViews(context?.packageName, R.layout.new_app_widget)
            views.setImageViewResource(R.id.imageView, R.drawable.example_appwidget_preview)
            val thisAppWidget = context?.let {
                ComponentName(
                    it, NewAppWidget::class.java
                )
            }
            var appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget)
            appWidgetManager.updateAppWidget(appWidgetIds[0], views)
        }
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {

    val widgetText = context.getString(R.string.appwidget_text)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.new_app_widget)
    val wwwIntent = Intent(Intent.ACTION_VIEW)
    wwwIntent.data = Uri.parse("https://www.pja.edu.pl")
    val pendingwwwIntent = PendingIntent.getActivity(
        context,
        1,
        wwwIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.btWWW, pendingwwwIntent)




//    val toastIntent = Intent("com.example.testwidget.action1")
//    toastIntent.component = ComponentName(context, NewAppWidget::class.java)
    val imageIntent = Intent(context, NewAppWidget::class.java)
    imageIntent.setAction("Action1")
    val pendingtoastIntent = PendingIntent.getBroadcast(
        context,
        1,
        imageIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setImageViewResource(R.id.imageView, R.drawable.example_appwidget_preview)
    views.setOnClickPendingIntent(R.id.btAction, pendingtoastIntent)

    val imageIntent2 = Intent(context, NewAppWidget::class.java)
    imageIntent2.setAction("Action2")
    val pendingtoastIntent2 = PendingIntent.getBroadcast(
        context,
        1,
        imageIntent2,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.previous, pendingtoastIntent2)



    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}