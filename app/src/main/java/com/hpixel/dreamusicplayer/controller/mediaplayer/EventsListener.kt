package com.hpixel.dreamusicplayer.controller.mediaplayer

import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import com.hpixel.dreamusicplayer.model.Current
import com.hpixel.dreamusicplayer.model.Settings

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
        if (Current.playlistPosition < Current.playlistLenght){
            goToNextSongInPlaylist()
            sendBroadcasts(Settings.Broadcast_PLAYING_NEW_AUDIO)

            return
        }

        host.stopMedia();
        //stop the service
        host.stopSelf();
    }

    private fun goToNextSongInPlaylist(){
        val newPlaylistPosition = Current.playlistPosition + 1
        Current.changeSong(newPlaylistPosition)

        host.updateMedia()
    }

    private fun sendBroadcasts(action: String){
        val intent = Intent()
        intent.action = action

        host.sendBroadcast(intent)
    }
}