package com.hpixel.dreamusicplayer.controller.mediaplayer

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.hpixel.dreamusicplayer.controller.mediaplayer.receivers.ReceiversRegistry
import com.hpixel.dreamusicplayer.model.Current
import com.hpixel.dreamusicplayer.model.Song
import java.io.IOException




/**
 * Class from www.sitepoint.com/a-step-by-step-guide-to-building-an-android-audio-player-app/ converted to kotlin by vhoyer on 13/07/17.
 */
class MediaPlayerService : Service() {

    var mediaPlayer: MediaPlayer? = null
    var resumePosition = 0

    private val iBinder = LocalBinder()

    override fun onBind(parentIntent: Intent?): IBinder {
        return iBinder
    }

    fun initMediaPlayer(){
        mediaPlayer = MediaPlayer()
        Current.player.source = mediaPlayer
        val eventsListener = EventsListener(this)
        //Set up MediaPlayer event listeners
        mediaPlayer?.apply {
            setOnCompletionListener(eventsListener)
            setOnErrorListener(eventsListener)
            setOnPreparedListener(eventsListener)
            setOnBufferingUpdateListener(eventsListener)
            setOnSeekCompleteListener(eventsListener)
            setOnInfoListener(eventsListener)
        }

        updateMedia()
    }

    fun playMedia() {
        mediaPlayer?.run {
            if (!isPlaying) start()
            Current.playing = true
        }
    }

    fun stopMedia() {
        mediaPlayer?.run {
            if (isPlaying) stop()
            Current.playing = false
        }
    }

    fun pauseMedia() {
        mediaPlayer?.run {
            if (isPlaying) {
                pause()
                resumePosition = currentPosition
            }
            Current.playing = false
        }
    }

    fun resumeMedia() {
        mediaPlayer?.run {
            if (!isPlaying) {
                seekTo(resumePosition)
                start()
            }
            Current.playing = true
        }
    }

    fun updateMedia() {
        val songSource = Current.song.filePath
        if (songSource == Song.DEFAULT_FILE_PATH){
            //stop self because there is no song to be played
            stopSelf()
        }

        mediaPlayer?.apply {
            //Reset so that the MediaPlayer is not pointing to another data source
            reset()

            setAudioStreamType(AudioManager.STREAM_MUSIC)
            try{
                setDataSource(songSource)
            }
            catch (e : IOException){
                e.printStackTrace()
                stopSelf()
            }

            prepareAsync()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val returnValue = super.onStartCommand(intent, flags, startId)

        val songSource = Current.song.filePath
        if (songSource == Song.DEFAULT_FILE_PATH){
            //stop self because there is no song to be played
            stopSelf()
        }

        //Request audio focus
        val audioFocusListener = AudioFocusChangeListener(this)
        if (!audioFocusListener.requestAudioFocus()) {
            //Could not gain focus
            stopSelf();
        }

        initMediaPlayer()
        ReceiversRegistry(this).register()

        return returnValue
    }

    override fun onDestroy() {
        super.onDestroy()

        stopMedia()
        mediaPlayer?.release()
        mediaPlayer = null
        //ReceiversRegistry(this).unregister()

        val audioFocusListener = AudioFocusChangeListener(this)
        audioFocusListener.removeAudioFocus()
    }

    fun seekTo(newPosition: Int) {
        mediaPlayer?.seekTo(newPosition)
    }

    inner class LocalBinder : Binder() {
        val service: MediaPlayerService
            get() = this@MediaPlayerService
    }
}
