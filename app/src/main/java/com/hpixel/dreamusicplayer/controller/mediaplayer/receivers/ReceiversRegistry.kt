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
    private val nextAudioInPlaylist = NextSongReceiver(host)

    fun register() {
        registerBecomingNoisyReceiver()
        registerPlayPauseReceiver()
        registerNextAudioInPlaylist()
    }

    private fun registerNextAudioInPlaylist() {
        val intentFilter = IntentFilter(Settings.Broadcast_NEXT_AUDIO_IN_PLAYLIST)
        host.registerReceiver(nextAudioInPlaylist, intentFilter)
    }

    private fun registerPlayPauseReceiver() {
        val intentFilter = IntentFilter(Settings.Broadcast_PLAY_PAUSE_AUDIO)
        host.registerReceiver(playPauseReceiver, intentFilter)
    }

    private fun registerBecomingNoisyReceiver() {
        //register after getting audio focus
        val intentFilter = IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
        host.registerReceiver(becomingNoisyReceiver, intentFilter)
    }

    fun unregister(){
        host.unregisterReceiver(becomingNoisyReceiver)
    }
}