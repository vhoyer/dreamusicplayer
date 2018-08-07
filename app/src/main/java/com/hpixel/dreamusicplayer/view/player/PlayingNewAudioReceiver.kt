package com.hpixel.dreamusicplayer.view.player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PlayingNewAudioReceiver(val player: PlayerActivity) : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {

        player.updateLabels()
    }
}