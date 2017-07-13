package com.hpixel.dreamusicplayer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.hpixel.dreamusicplayer.controller.Files
import com.hpixel.dreamusicplayer.controller.Song
import com.hpixel.dreamusicplayer.controller.SongsArrayAdaptor

class MainActivity : AppCompatActivity() {

    val context = this.applicationContext

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

        val songs = songList()
        val songListView = findViewById(R.id.main_songList) as ListView
        val songListArrayAdapter = SongsArrayAdaptor(context, songs)
        songListView.adapter = songListArrayAdapter
	}

    fun songList(): ArrayList<Song> {
        val files = Files()

        val separator = "||"
        val songQuery = Song.query

        val songArray = files.getSongs(context, separator, songQuery)

        val songList = arrayListOf<Song>()

        for (song in songArray){
            val newSong = Song(song, separator)
            songList.add(newSong)
        }

        return songList
    }
}
