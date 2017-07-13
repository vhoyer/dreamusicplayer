package com.hpixel.dreamusicplayer.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.hpixel.dreamusicplayer.R
import com.hpixel.dreamusicplayer.controller.SongListProvider
import com.hpixel.dreamusicplayer.controller.SongsArrayAdaptor

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

        val context = this.applicationContext

        //filters out
        val listProvider = SongListProvider()
        val listFilter = SongListProvider.WHATSAPP_AUDIO_FILTER
        val songList = listProvider.songList(context, listFilter)

        val songListView = findViewById(R.id.main_songList) as ListView
        val songListArrayAdapter = SongsArrayAdaptor(context, songList)
        songListView.adapter = songListArrayAdapter
	}
}
