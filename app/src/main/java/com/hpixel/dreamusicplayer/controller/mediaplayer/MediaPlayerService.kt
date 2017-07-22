package com.hpixel.dreamusicplayer.controller.mediaplayer

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.hpixel.dreamusicplayer.controller.mediaplayer.receivers.RegisterReceivers
import com.hpixel.dreamusicplayer.model.Current
import com.hpixel.dreamusicplayer.model.Song
import java.io.IOException




/**
 * Class from www.sitepoint.com/a-step-by-step-guide-to-building-an-android-audio-player-app/ converted to kotlin by vhoyer on 13/07/17.
 */
class MediaPlayerService : Service() {

    val mediaPlayer = MediaPlayer()
    var resumePosition = 0

    private val iBinder = LocalBinder()

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
        val audioFocusListener = AudioFocusChangeListener(this)
        if (!audioFocusListener.requestAudioFocus()) {
            //Could not gain focus
            stopSelf();
        }

        initMediaPlayer(songSource)
        RegisterReceivers(this)

        return returnValue
    }

    override fun onDestroy() {
        super.onDestroy()

        stopMedia()
        mediaPlayer.release()

        val audioFocusListener = AudioFocusChangeListener(this)
        audioFocusListener.removeAudioFocus()
    }

    inner class LocalBinder : Binder() {
        val service: MediaPlayerService
            get() = this@MediaPlayerService
    }
}
