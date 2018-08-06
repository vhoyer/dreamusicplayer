package com.hpixel.dreamusicplayer.controller.mediaplayer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hpixel.dreamusicplayer.controller.mediaplayer.MediaPlayerService
import com.hpixel.dreamusicplayer.model.Current
import com.hpixel.dreamusicplayer.model.Settings

class LastOrRewindSongReceiver(val host: MediaPlayerService) : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {

        if (Current.player.position <= Settings.ms_of_tolerance_in_skipping_back){
            skipBack()
        }
        else {
            rewind()
        }
    }

    private fun rewind(){
        Current.player.restartSong()
    }

    private val canSkipBack: Boolean
        get() = Current.playlistPosition -1 >= 0

    private fun skipBack(){
        changeAudio()
        updateUI()

        host.updateMedia()
    }

    private fun changeAudio(){
        if (canSkipBack){
            Current.changeSong( Current.playlistPosition -1 )
        }
        else {
            Current.changeSong( Current.playlistLenght -1 )
        }
    }

    private fun updateUI(){
        //with the new song information
        val intent = Intent()
        intent.action = Settings.Broadcast_NEW_AUDIO
        host.sendBroadcast(intent)
    }
}