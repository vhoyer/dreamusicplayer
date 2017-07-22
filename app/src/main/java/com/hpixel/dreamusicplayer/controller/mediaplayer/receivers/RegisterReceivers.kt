package com.hpixel.dreamusicplayer.controller.mediaplayer.receivers

import android.content.IntentFilter
import android.media.AudioManager
import com.hpixel.dreamusicplayer.controller.mediaplayer.MediaPlayerService


/**
 * Created by vhoyer on 22/07/17.
 */
class RegisterReceivers(val host : MediaPlayerService) {

    val becomingNoisyReceiver = BecomingNoisyReceiver(host)

    init {
        registerBecomingNoisyReceiver()
    }

    private fun registerBecomingNoisyReceiver() {
        //register after getting audio focus
        val intentFilter = IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
        host.registerReceiver(becomingNoisyReceiver, intentFilter)
    }
}