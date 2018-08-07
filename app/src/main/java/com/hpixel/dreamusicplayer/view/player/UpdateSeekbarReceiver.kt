package com.hpixel.dreamusicplayer.view.player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.SeekBar
import android.widget.TextView
import com.hpixel.dreamusicplayer.R
import com.hpixel.dreamusicplayer.model.Current
import com.hpixel.dreamusicplayer.model.Song

class UpdateSeekBarReceiver(val player: PlayerActivity) : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        val seekBar = player.findViewById(R.id.player_seekBar) as SeekBar
        val currentTime = player.findViewById(R.id.player_elapsedTime) as TextView

        seekBar.progress = Current.player.position
        currentTime.text = Song.convertToHumanFormat( Current.player.position )
    }
}