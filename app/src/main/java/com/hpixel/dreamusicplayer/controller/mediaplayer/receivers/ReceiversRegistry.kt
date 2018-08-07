package com.hpixel.dreamusicplayer.controller.mediaplayer.receivers

import android.content.BroadcastReceiver
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
    private val lastAudioInPlaylist = LastOrRewindSongReceiver(host)
    private val updateSongPosition = UpdateSongPosition(host)

    fun register() {
        registerBecomingNoisyReceiver()
        registerPlayPauseReceiver()
        registerNextAudioInPlaylist()
        registerLastAudioInPlaylist()

        register(updateSongPosition, Settings.Broadcast_UPDATE_SONG_POSITION)
    }

    fun register(receiver: BroadcastReceiver, id: String){
        val intentFilter = IntentFilter(id)
        host.registerReceiver(receiver, intentFilter)
    }

    private fun registerLastAudioInPlaylist() {
        val intentFilter = IntentFilter(Settings.Broadcast_PREVIOUS_AUDIO_IN_PLAYLIST)
        host.registerReceiver(lastAudioInPlaylist, intentFilter)
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