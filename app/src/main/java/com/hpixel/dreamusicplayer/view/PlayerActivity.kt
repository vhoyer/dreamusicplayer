package com.hpixel.dreamusicplayer.view

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.hpixel.dreamusicplayer.R
import com.hpixel.dreamusicplayer.controller.MediaPlayerService
import com.hpixel.dreamusicplayer.controller.SongListProvider
import com.hpixel.dreamusicplayer.model.Current


class PlayerActivity : AppCompatActivity() {

    var player : MediaPlayerService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        val context = this.applicationContext
        val songProvider = SongListProvider(context)

        val songToPlay = Current.song

        val txtTitle = findViewById(R.id.player_title) as TextView
        val txtArtist = findViewById(R.id.player_artist) as TextView
        val txtAlbum = findViewById(R.id.player_album) as TextView

        txtTitle.text = songToPlay.title
        txtArtist.text = songToPlay.artist
        txtAlbum.text = songToPlay.albumName

        val playerService = Intent(context, MediaPlayerService::class.java)
        startService(playerService)
        bindService(playerService, serviceConnection, Context.BIND_ABOVE_CLIENT)
    }

    //Binding this Client to the AudioPlayer Service
    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as MediaPlayerService.LocalBinder
            player = binder.service
            //serviceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            //serviceBound = false
        }
    }
}



