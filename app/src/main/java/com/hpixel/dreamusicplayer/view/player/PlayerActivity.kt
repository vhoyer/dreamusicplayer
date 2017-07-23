package com.hpixel.dreamusicplayer.view.player

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.hpixel.dreamusicplayer.R
import com.hpixel.dreamusicplayer.controller.GlideApp
import com.hpixel.dreamusicplayer.controller.mediaplayer.MediaPlayerService
import com.hpixel.dreamusicplayer.model.Current
import com.hpixel.dreamusicplayer.model.Settings


class PlayerActivity : AppCompatActivity() {
    private var player : MediaPlayerService? = null
    private var serviceBound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        updateLabels()

        playAudio()

        registerReceivers()

        //set oncliks and everything
        PlayerEventHandlers(this)
    }

    private fun playAudio() {
        if (!serviceBound) {
            //service is not active
            val playerIntent = Intent(this, MediaPlayerService::class.java)
            startService(playerIntent)

            bindService(playerIntent, serviceConnection, Context.BIND_ABOVE_CLIENT)
            return
        }
        //Service is active
        //Send media with BroadcastReceiver
        //Send a broadcast to the service -> PLAY_NEW_AUDIO
        val broadcastIntent = Intent(Settings.Broadcast_PLAYING_NEW_AUDIO)
        sendBroadcast(broadcastIntent)
    }

    fun updateLabels() {
        val songToPlay = Current.song
        val playingAlbum = Current.album

        val title = songToPlay.title
        val artist = songToPlay.artist
        val album = songToPlay.albumName
        val artwork = playingAlbum.artworkPath

        val txtTitle = findViewById(R.id.player_title) as TextView
        val txtArtist = findViewById(R.id.player_artist) as TextView
        val txtAlbum = findViewById(R.id.player_album) as TextView
        val imgCover = findViewById(R.id.player_cover) as ImageView

        txtTitle.text = title
        txtArtist.text = artist
        txtAlbum.text = album
        GlideApp.with(this)
                .load(artwork)
                .into(imgCover)

        updatePlayButton()
    }

    fun updatePlayButton() {
        val resources = this.resources
        val playPauseButton = findViewById(R.id.player_playButton) as ImageButton

        val drawable_id = if(Current.playing) R.drawable.ic_play_arrow_black else R.drawable.ic_pause_black

        val ic = resources.getDrawable(drawable_id, null)
        playPauseButton.setImageDrawable(ic)
    }

    private val PlayingNewAudioReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            //on .PlayingNewAudioReceiver
            updateLabels()
        }
    }

    private fun registerReceivers() {
        val playNewAudio = IntentFilter(Settings.Broadcast_PLAYING_NEW_AUDIO)
        registerReceiver(PlayingNewAudioReceiver, playNewAudio)
    }

    //Binding this Client to the AudioPlayer Service
    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as MediaPlayerService.LocalBinder
            player = binder.service
            serviceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            serviceBound = false
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putBoolean("ServiceState", serviceBound)
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        serviceBound = savedInstanceState.getBoolean("ServiceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (serviceBound) {
            unbindService(serviceConnection)
            //service is active
            player!!.stopSelf()
        }
    }
}



