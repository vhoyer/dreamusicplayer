package com.hpixel.dreamusicplayer.controller.mediaplayer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hpixel.dreamusicplayer.controller.mediaplayer.MediaPlayerService

/**
 * Created by vhoyer on 18/07/17.
 */
class BecomingNoisyReceiver(val host: MediaPlayerService) : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        //pause audio on ACTION_AUDIO_BECOMING_NOISY
        host.pauseMedia()
        //TODO: host.buildNotification(PlaybackStatus.PAUSED)
    }
}