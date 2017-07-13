package com.hpixel.dreamusicplayer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.hpixel.dreamusicplayer.model.Files
import com.hpixel.dreamusicplayer.model.Song
import com.hpixel.dreamusicplayer.controller.SongsArrayAdaptor

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

        val context = this.applicationContext
        val songs = songList()
        val songListView = findViewById(R.id.main_songList) as ListView
        val songListArrayAdapter = SongsArrayAdaptor(context, songs)
        songListView.adapter = songListArrayAdapter
	}

    fun songList(): ArrayList<Song> {
        val files = Files()

        val context = this.applicationContext
        val separator = "||"
        val songQuery = Song.query

        val songArray = files.getSongs(context, separator, songQuery)

        val songList = arrayListOf<Song>()

        for (song in songArray){
            val newSong = Song(song, separator)
            songList.add(newSong)
        }

        //sort "alphabetically" by unicode value
        songList.sortBy { song -> song.title }

        //due to a possible "floating action button" that may be added later
        val anEmptySpaceInTheEnd = Song(0, "", "", "", "", "", 0)
        songList.add(anEmptySpaceInTheEnd)

        return songList
    }
}
