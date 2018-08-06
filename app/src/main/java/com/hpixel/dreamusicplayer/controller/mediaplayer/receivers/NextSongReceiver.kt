package com.hpixel.dreamusicplayer.controller.mediaplayer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.hpixel.dreamusicplayer.controller.mediaplayer.MediaPlayerService
import com.hpixel.dreamusicplayer.model.Current
import com.hpixel.dreamusicplayer.model.Settings

/**
 * Created by vhoyer on 19/11/17.
 */
 class NextSongReceiver(val host: MediaPlayerService): BroadcastReceiver() {
    private val canIncrementPlaylistIndex : Boolean
        get() = Current.playlistPosition+1 < Current.playlistLenght

    override fun onReceive(p0: Context?, p1: Intent?) {

        Current.changeSong(newIndex())
        updateUI()
        host.updateMedia()
	}

    private fun newIndex() =
            if (canIncrementPlaylistIndex) Current.playlistPosition+1
            else 0

    private fun updateUI(){
        //with the new song information
        val intent = Intent()
        intent.action = Settings.Broadcast_NEW_AUDIO
        host.sendBroadcast(intent)
    }
 }