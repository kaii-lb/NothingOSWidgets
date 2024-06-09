package com.kaii.customwidgets.music_widget.longboi_ui

import android.widget.RemoteViews
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.clickable
import androidx.glance.appwidget.AndroidRemoteViews
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.kaii.customwidgets.R
import com.kaii.customwidgets.music_widget.MusicWidgetUIState
import com.kaii.customwidgets.music_widget.NotificationListenerCustomService
import com.kaii.customwidgets.music_widget.extension_functions.LaunchMediaPlayer

@Composable
fun ImageAndTitle(musicWidgetUIState: MusicWidgetUIState) {
    Row (
        GlanceModifier
            .fillMaxSize()
    )
    {
//        println("ALBUM ART URI IS ${musicWidgetUIState.albumArt}")

        Column (
            modifier = GlanceModifier
                .defaultWeight()
                .fillMaxHeight()
                .width(110.dp)
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column (
                modifier = GlanceModifier
                    .background(GlanceTheme.colors.primary)
                    .cornerRadius(1000.dp)
                    .size(100.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column (
                    modifier = GlanceModifier
                        .background(ImageProvider(musicWidgetUIState.albumArt))
                        .cornerRadius(1000.dp)
                        .clickable(actionRunCallback(LaunchMediaPlayer::class.java))
                        .size(96.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {}
            }
        }

        Column (
            modifier = GlanceModifier
                .defaultWeight()
                .fillMaxHeight()
                .background(GlanceTheme.colors.widgetBackground)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = musicWidgetUIState.songTitle ?: "Unknown Title",
                maxLines = 1,
                style = TextStyle(color = GlanceTheme.colors.onBackground, fontSize = TextUnit(16.0f, TextUnitType.Sp))
            )

            val glanceColor = GlanceTheme.colors.onBackground.getColor(LocalContext.current).toArgb()
            val lightColor = Color(glanceColor.red, glanceColor.green, glanceColor.blue, 176)
            Text(
                text = musicWidgetUIState.artist ?: "Unknown Artist",
                maxLines = 1,
                style = TextStyle(
                    color = ColorProvider(lightColor),
                    fontSize = TextUnit(16.0f, TextUnitType.Sp)
                )
            )
        }

        Column (
            modifier = GlanceModifier
                .fillMaxHeight()
                .width(40.dp)
                .padding((-15).dp)
                .background(GlanceTheme.colors.widgetBackground)
                .clickable(actionRunCallback(LaunchMediaPlayer::class.java)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val packageName = LocalContext.current.packageName
            val remoteViews = RemoteViews(packageName, R.layout.music_player_vertical_text_layout)

            val mediaPlayerPackageName = NotificationListenerCustomService.getMediaPlayer()
            val mediaPlayerTitle: String
            val pm = LocalContext.current.packageManager

            mediaPlayerTitle = if (mediaPlayerPackageName != null) {
                val appInfo = pm.getApplicationInfo(mediaPlayerPackageName, 0)
                pm.getApplicationLabel(appInfo).toString()
            } else {
                ""
            }

            remoteViews.setTextViewText(
                R.id.music_player_text,
                mediaPlayerTitle.uppercase()
            )

            remoteViews.setTextColor(
                R.id.music_player_text,
                GlanceTheme.colors.primary.getColor(LocalContext.current).toArgb()
            )

            AndroidRemoteViews(remoteViews)
        }
    }
}
