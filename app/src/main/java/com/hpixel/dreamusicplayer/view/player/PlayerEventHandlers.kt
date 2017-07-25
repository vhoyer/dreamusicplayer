package com.hpixel.dreamusicplayer.view.player

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import com.hpixel.dreamusicplayer.R
import com.hpixel.dreamusicplayer.model.Settings

/**
 * Created by vhoyer on 23/07/17.
 */
class PlayerEventHandlers(val parent: PlayerActivity): View.OnClickListener {

    init{
        val playPauseButton = parent.findViewById(R.id.player_playButton) as ImageButton
        playPauseButton.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id){
            R.id.player_playButton -> playPauseAudio()
            else -> return
        }
    }

    fun playPauseAudio(){
        val intent = Intent()
        intent.action = Settings.Broadcast_PLAY_PAUSE_AUDIO
        parent.sendBroadcast(intent)

        parent.updatePlayButton()
    }
}