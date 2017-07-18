package com.hpixel.dreamusicplayer.controller.mediaplayer

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.hpixel.dreamusicplayer.model.Current
import com.hpixel.dreamusicplayer.model.Song
import java.io.IOException




/**
 * Class from www.sitepoint.com/a-step-by-step-guide-to-building-an-android-audio-player-app/ converted to kotlin by vhoyer on 13/07/17.
 */
class MediaPlayerService : Service(), AudioManager.OnAudioFocusChangeListener {

    private val mediaPlayer = MediaPlayer()
    private var resumePosition = 0

    private val iBinder = LocalBinder()

    private var audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

    override fun onBind(parentIntent: Intent?): IBinder {
        return iBinder
    }

    fun initMediaPlayer(songSource: String){
        val eventsListener = EventsListener(this)
        //Set up MediaPlayer event listeners
        mediaPlayer.setOnCompletionListener(eventsListener)
        mediaPlayer.setOnErrorListener(eventsListener)
        mediaPlayer.setOnPreparedListener(eventsListener)
        mediaPlayer.setOnBufferingUpdateListener(eventsListener)
        mediaPlayer.setOnSeekCompleteListener(eventsListener)
        mediaPlayer.setOnInfoListener(eventsListener)
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
        if (songSource == Song.DEFAULT_FILE_PATH){
            //stop self because there is no song to be played
            stopSelf()
        }

        //Request audio focus
        if (requestAudioFocus() == false) {
            //Could not gain focus
            stopSelf();
        }

        initMediaPlayer(songSource)

        return returnValue
    }

    override fun onDestroy() {
        super.onDestroy()

        stopMedia()
        mediaPlayer.release()

        removeAudioFocus()
    }

    inner class LocalBinder : Binder() {
        val service: MediaPlayerService
            get() = this@MediaPlayerService
    }

    //
    // AudioManager.OnAudioFocusChangeListener Implementations
    //

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
        if (mediaPlayer.isPlaying) {
            mediaPlayer.setVolume(0.1f, 0.1f)
        }
    }

    private fun audioFocus_transient() {
        // Lost focus for a short time, but we have to stop
        // playback. We don't release the media player because playback
        // is likely to resume
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    private fun audioFocus_loss(){
        // Lost focus for an unbounded amount of time: stop playback and release media player
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }

    private fun audioFocus_gain(){
        // resume playback
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
        mediaPlayer.setVolume(1.0f, 1.0f)
    }

    private fun requestAudioFocus(): Boolean {
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

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

    private fun removeAudioFocus(): Boolean {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED == audioManager.abandonAudioFocus(this)
    }
}
