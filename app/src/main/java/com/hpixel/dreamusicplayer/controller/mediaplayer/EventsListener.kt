package com.hpixel.dreamusicplayer.controller.mediaplayer

import android.media.AudioManager
import android.media.MediaPlayer
import android.util.Log

/**
 * Created by vhoyer on 14/07/17.
 */
class EventsListener(val host: MediaPlayerService) :
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener {

    override fun onError(player: MediaPlayer?, what: Int, extra: Int): Boolean {
        when (what) {
            MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK ->
                    Log.d("MediaPlayer Error", "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra)
            MediaPlayer.MEDIA_ERROR_SERVER_DIED ->
                    Log.d("MediaPlayer Error", "MEDIA ERROR SERVER DIED " + extra)
            MediaPlayer.MEDIA_ERROR_UNKNOWN ->
                    Log.d("MediaPlayer Error", "MEDIA ERROR UNKNOWN " + extra)
        }
        return false
    }

    override fun onSeekComplete(p0: MediaPlayer?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onInfo(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        return false
    }

    override fun onBufferingUpdate(p0: MediaPlayer?, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPrepared(p0: MediaPlayer?) {
        host.playMedia();
    }

    override fun onCompletion(p0: MediaPlayer?) {
        //Invoked when playback of a media source has completed.
        host.stopMedia();
        //stop the service
        host.stopSelf();
    }
}