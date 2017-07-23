package com.hpixel.dreamusicplayer.controller.mediaplayer.receivers

import android.content.IntentFilter
import android.media.AudioManager
import com.hpixel.dreamusicplayer.controller.mediaplayer.MediaPlayerService
import com.hpixel.dreamusicplayer.model.Settings


/**
 * Created by vhoyer on 22/07/17.
 */
class ReceiversRegistry(val host : MediaPlayerService) {

    private val becomingNoisyReceiver = BecomingNoisyReceiver(host)
    private val playPauseReceiver = PlayPauseReceiver(host)

    fun register() {
        registerBecomingNoisyReceiver()
        registerPlayPauseReceiver()
    }

    fun unregister(){
        host.unregisterReceiver(becomingNoisyReceiver)
    }

    private fun registerBecomingNoisyReceiver() {
        //register after getting audio focus
        val intentFilter = IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
        host.registerReceiver(becomingNoisyReceiver, intentFilter)
    }

    private fun registerPlayPauseReceiver() {
        val intentFilter = IntentFilter(Settings.Broadcast_PLAY_PAUSE_AUDIO)
        host.registerReceiver(playPauseReceiver, intentFilter)
    }
}