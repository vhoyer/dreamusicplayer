package com.hpixel.dreamusicplayer.model

/**
 * Created by vhoyer on 22/07/17.
 */
class Settings {
    companion object {
        val PACKAGE_NAME = "com.hpixel.dreamusicplayer"
        val Broadcast_PLAY_NEW_AUDIO = "$PACKAGE_NAME.PlayNewAudio"


        var EXCLUDE_WHATSAPP_AUDIO_IN_MAIN_LIST = true
    }

    fun loadSettings() {}
}