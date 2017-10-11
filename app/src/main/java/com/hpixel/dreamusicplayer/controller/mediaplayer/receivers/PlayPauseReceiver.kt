package com.hpixel.dreamusicplayer.controller.mediaplayer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hpixel.dreamusicplayer.controller.mediaplayer.MediaPlayerService
import com.hpixel.dreamusicplayer.model.Current

/**
 * Created by vhoyer on 23/07/17.
 */
class PlayPauseReceiver(val host: MediaPlayerService): BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        //when .PlayPauseAudio

        if(Current.playing){
            host.pauseMedia()
            return
        }

        host.resumeMedia()
    }
}