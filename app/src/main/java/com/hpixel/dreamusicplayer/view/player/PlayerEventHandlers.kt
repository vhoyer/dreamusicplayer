package com.hpixel.dreamusicplayer.view.player

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageButton
import com.hpixel.dreamusicplayer.R
import com.hpixel.dreamusicplayer.model.Settings

/**
* Created by vhoyer on 23/07/17.
*/
class PlayerEventHandlers(val parent: PlayerActivity): View.OnClickListener {

	init{
        setListener(R.id.player_playButton)
        setListener(R.id.player_skipNext)
        setListener(R.id.player_skipPrevious)
	}

    fun setListener(id: Int){
        val nextAudioButton = parent.findViewById( id ) as ImageButton
        nextAudioButton.setOnClickListener(this)
    }

	override fun onClick(view: View?) {
		when (view?.id){
			R.id.player_playButton -> playPauseAudio()
			R.id.player_skipNext -> nextSong()
			R.id.player_skipPrevious -> previousSong()
			else -> return
		}
	}

    fun previousSong(){
        broadcastBase(Settings.Broadcast_PREVIOUS_AUDIO_IN_PLAYLIST)
    }

    fun nextSong() {
        broadcastBase(Settings.Broadcast_NEXT_AUDIO_IN_PLAYLIST)
    }

    fun playPauseAudio(){
		broadcastBase(Settings.Broadcast_PLAY_PAUSE_AUDIO)

		parent.updatePlayButton()
	}

	fun broadcastBase(broadcastMessage: String){
		val intent = Intent()
		intent.action = broadcastMessage
		parent.sendBroadcast(intent)
	}
}
