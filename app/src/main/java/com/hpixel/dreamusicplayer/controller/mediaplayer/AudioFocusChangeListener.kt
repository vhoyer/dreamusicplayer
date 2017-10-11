package com.hpixel.dreamusicplayer.controller.mediaplayer

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer

/**
 * Created by vhoyer on 18/07/17.
 */
class AudioFocusChangeListener(host: MediaPlayerService) : AudioManager.OnAudioFocusChangeListener {

    val mediaPlayer : MediaPlayer?
    val context : Context

    init {
        mediaPlayer = host.mediaPlayer
        context = host.applicationContext
    }

    override fun onAudioFocusChange(focusState: Int) {
        when (focusState) {
            AudioManager.AUDIOFOCUS_GAIN -> audioFocus_gain()
            AudioManager.AUDIOFOCUS_LOSS -> audioFocus_loss()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> audioFocus_transient()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> audioFocus_canDuck()
        }
    }

    private fun audioFocus_canDuck() {
        // Lost focus for a short time, but it's ok to keep playing
        // at an attenuated level
        if (mediaPlayer == null) return

        if (mediaPlayer.isPlaying) {
            mediaPlayer.setVolume(0.5f, 0.5f)
        }
    }

    private fun audioFocus_transient() {
        // Lost focus for a short time, but we have to stop
        // playback. We don't release the media player because playback
        // is likely to resume
        if (mediaPlayer == null) return

        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    private fun audioFocus_loss(){
        // Lost focus for an unbounded amount of time: stop playback and release media player
        if (mediaPlayer == null) return

        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }

    private fun audioFocus_gain(){
        // resume playback
        if (mediaPlayer == null) return

        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
        mediaPlayer.setVolume(1.0f, 1.0f)
    }

    fun requestAudioFocus(): Boolean {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val result = audioManager.requestAudioFocus(
                this,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN
        )
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //Focus gained
            return true
        }
        //Could not gain focus
        return false
    }

    fun removeAudioFocus(): Boolean {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == audioManager.abandonAudioFocus(this)
    }
}