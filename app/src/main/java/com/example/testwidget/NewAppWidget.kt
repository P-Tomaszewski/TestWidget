package com.example.testwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.media.MediaParser
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.RemoteViews
import androidx.annotation.RequiresApi

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {


   var flag: Boolean = false

    companion object{
        lateinit var music: MediaPlayer
        val playList = mutableListOf(R.raw.mus, R.raw.mus2)
        var musicFlag: Boolean = false
    }


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

        music = MediaPlayer.create(context, playList[0])
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onReceive(context: Context?, intent: Intent?) {

        super.onReceive(context, intent)
        val appWidgetManager = AppWidgetManager
            .getInstance(context)
        if(intent?.action == "nextImg"){
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

        if(intent?.action == "previousImg"){
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

        if(intent?.action == "playMusic"){
            Log.d("musik", "play")
            music.start()
        }

        if(intent?.action == "pauseMusic"){
            Log.d("musik", "pause")
            music.pause()
        }

        if(intent?.action == "closeMusic"){
            Log.d("musik", "close")
            music.stop()
        }
        if(intent?.action == "nextMusic"){
            Log.d("musik", "next")
            music.reset()
            if(!musicFlag) {
                music = MediaPlayer.create(context, playList[1])
                musicFlag = true
            } else {
                music = MediaPlayer.create(context, playList[0])
                musicFlag = false
            }
            music.start()
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
    imageIntent.setAction("nextImg")
    val pendingtoastIntent = PendingIntent.getBroadcast(
        context,
        1,
        imageIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setImageViewResource(R.id.imageView, R.drawable.example_appwidget_preview)
    views.setOnClickPendingIntent(R.id.btAction, pendingtoastIntent)

    val imageIntent2 = Intent(context, NewAppWidget::class.java)
    imageIntent2.setAction("previousImg")
    val pendingtoastIntent2 = PendingIntent.getBroadcast(
        context,
        1,
        imageIntent2,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.previous, pendingtoastIntent2)

    val musicIntent = Intent(context, NewAppWidget::class.java)
    musicIntent.setAction("playMusic")
    val musicPendingIntent = PendingIntent.getBroadcast(
        context,
        1,
        musicIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.playBtt, musicPendingIntent)

    val musicPauseIntent = Intent(context, NewAppWidget::class.java)
    musicPauseIntent.setAction("pauseMusic")
    val musicPausePendingIntent = PendingIntent.getBroadcast(
        context,
        1,
        musicPauseIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.pauseBtt, musicPausePendingIntent)

    val musicCloseIntent = Intent(context, NewAppWidget::class.java)
    musicCloseIntent.setAction("closeMusic")
    val musicClosePendingIntent = PendingIntent.getBroadcast(
        context,
        1,
        musicPauseIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.stopBtt, musicClosePendingIntent)

    val musicNextIntent = Intent(context, NewAppWidget::class.java)
    musicNextIntent.setAction("nextMusic")
    val musicNextPendingIntent = PendingIntent.getBroadcast(
        context,
        1,
        musicNextIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
    views.setOnClickPendingIntent(R.id.nextBtt, musicNextPendingIntent)



    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}