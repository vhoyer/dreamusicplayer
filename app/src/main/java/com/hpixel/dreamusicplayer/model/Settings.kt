package com.hpixel.dreamusicplayer.model

/**
 * Created by vhoyer on 22/07/17.
 */
class Settings {
    companion object {
        val PACKAGE_NAME = "com.hpixel.dreamusicplayer"
        val Broadcast_PLAYING_NEW_AUDIO = "$PACKAGE_NAME.PlayingNewAudio"
        val Broadcast_PLAY_PAUSE_AUDIO = "$PACKAGE_NAME.PlayPauseAudio"


        var exclude_whatsapp_audio_in_main_list = true
        var ms_of_tolerance_in_skipping_back = 5000
    }

    fun loadSettings() {}
}