package com.hpixel.dreamusicplayer.controller

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.hpixel.dreamusicplayer.controller.mediaPlayerHandlers.OnEventListener
import com.hpixel.dreamusicplayer.model.Current
import java.io.IOException


/**
 * Class from www.sitepoint.com/a-step-by-step-guide-to-building-an-android-audio-player-app/ converted to kotlin by vhoyer on 13/07/17.
 */
class MediaPlayerService : Service() {

    private val mediaPlayer = MediaPlayer()
    private var resumePosition = 0

    private val iBinder = LocalBinder()
    override fun onBind(parentIntent: Intent?): IBinder {
        return iBinder
    }

    fun initMediaPlayer(songSource: String){
        val onEventListener = OnEventListener(this)
        //Set up MediaPlayer event listeners
        mediaPlayer.setOnCompletionListener(onEventListener)
        mediaPlayer.setOnErrorListener(onEventListener)
        mediaPlayer.setOnPreparedListener(onEventListener)
        mediaPlayer.setOnBufferingUpdateListener(onEventListener)
        mediaPlayer.setOnSeekCompleteListener(onEventListener)
        mediaPlayer.setOnInfoListener(onEventListener)
        //Reset so that the MediaPlayer is not pointing to another data source
        mediaPlayer.reset()

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try{
            mediaPlayer.setDataSource(songSource)
        }
        catch (e : IOException){
            e.printStackTrace()
            stopSelf()
        }
        mediaPlayer.prepareAsync()
    }

    fun playMedia() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    fun stopMedia() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
    }

    fun pauseMedia() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            resumePosition = mediaPlayer.currentPosition
        }
    }

    fun resumeMedia() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.seekTo(resumePosition)
            mediaPlayer.start()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val returnValue = super.onStartCommand(intent, flags, startId)

        val songSource = Current.song.filePath
        initMediaPlayer(songSource)

        return returnValue
    }

    inner class LocalBinder : Binder() {
        val service: MediaPlayerService
            get() = this@MediaPlayerService
    }
}
