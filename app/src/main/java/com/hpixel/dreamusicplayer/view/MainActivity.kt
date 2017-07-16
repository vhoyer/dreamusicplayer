package com.hpixel.dreamusicplayer.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.hpixel.dreamusicplayer.R
import com.hpixel.dreamusicplayer.controller.SongListProvider
import com.hpixel.dreamusicplayer.controller.SongsArrayAdaptor
import com.hpixel.dreamusicplayer.model.Current

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

        val context = this.applicationContext

        //filters out whatsapp audio file to create playlist
        val listProvider = SongListProvider(context)
        val listFilter = SongListProvider.WHATSAPP_AUDIO_FILTER
        val songList = listProvider.songList(listFilter)

        Current.playlist = songList

        val songListView = findViewById(R.id.main_songList) as ListView
        val songListArrayAdapter = SongsArrayAdaptor(context, songList)
        songListView.adapter = songListArrayAdapter
        songListView.onItemClickListener = this
	}



    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val myIntent = Intent(parent.context, PlayerActivity::class.java)
        val playlist = Current.playlist
        Current.arrayPosition = position
        Current.song = playlist[position]

        parent.context.startActivity(myIntent)
    }
}
