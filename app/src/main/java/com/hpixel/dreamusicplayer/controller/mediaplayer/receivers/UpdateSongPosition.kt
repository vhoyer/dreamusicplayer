package com.hpixel.dreamusicplayer.controller.mediaplayer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hpixel.dreamusicplayer.controller.mediaplayer.MediaPlayerService
import com.hpixel.dreamusicplayer.model.Settings

class UpdateSongPosition(val host: MediaPlayerService) : BroadcastReceiver() {
    override fun onReceive(p0: Context?, intent: Intent?) {

        val newPosition = intent?.getIntExtra( Settings.Intent_SongPosition, 0) ?: 0

        host.seekTo(newPosition)
    }

}
