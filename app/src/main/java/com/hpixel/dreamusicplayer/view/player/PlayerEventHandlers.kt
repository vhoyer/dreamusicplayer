package com.hpixel.dreamusicplayer.view.player

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
import com.hpixel.dreamusicplayer.R
import com.hpixel.dreamusicplayer.model.Settings

/**
* Created by vhoyer on 23/07/17.
*/
class PlayerEventHandlers(val parent: PlayerActivity):
		SeekBar.OnSeekBarChangeListener,
		View.OnClickListener {

	init{
        setOnClickListener(R.id.player_playButton)
        setOnClickListener(R.id.player_skipNext)
        setOnClickListener(R.id.player_skipPrevious)

		val seekBar = parent.findViewById(R.id.player_seekBar) as SeekBar
		seekBar.setOnSeekBarChangeListener( this )
	}

    private fun setOnClickListener(id: Int){
        val element = parent.findViewById( id ) as ImageButton
        element.setOnClickListener(this)
    }

	override fun onClick(view: View?) {
		when (view?.id){
			R.id.player_playButton -> playPauseAudio()
			R.id.player_skipNext -> nextSong()
			R.id.player_skipPrevious -> previousSong()
			else -> return
		}
	}

	override fun onProgressChanged(seekBar: SeekBar?, progress: Int, isFromUser: Boolean) {
		if (!isFromUser){
			return
		}

		val intent = Intent( Settings.Broadcast_UPDATE_SONG_POSITION )
		intent.putExtra(Settings.Intent_SongPosition , progress)
		parent.sendBroadcast( intent )
	}

	override fun onStartTrackingTouch(p0: SeekBar?) {
	}

	override fun onStopTrackingTouch(p0: SeekBar?) {
	}

    private fun previousSong(){
        broadcastBase(Settings.Broadcast_PREVIOUS_AUDIO_IN_PLAYLIST)
    }

    private fun nextSong() {
        broadcastBase(Settings.Broadcast_NEXT_AUDIO_IN_PLAYLIST)
    }

    private fun playPauseAudio(){
		broadcastBase(Settings.Broadcast_PLAY_PAUSE_AUDIO)

		parent.updatePlayButton()
	}

	private fun broadcastBase(broadcastMessage: String){
		val intent = Intent()
		intent.action = broadcastMessage
		parent.sendBroadcast(intent)
	}
}
