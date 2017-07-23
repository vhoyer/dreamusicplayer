package com.hpixel.dreamusicplayer.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.hpixel.dreamusicplayer.R
import com.hpixel.dreamusicplayer.controller.ListProvider
import com.hpixel.dreamusicplayer.model.Current
import com.hpixel.dreamusicplayer.model.Settings
import com.hpixel.dreamusicplayer.model.Song

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

        val context = this.applicationContext

        //filters out whatsapp audio file to create playlist
        val listProvider = ListProvider(context)
        val listFilter = chooseFilter()
        val songList = listProvider.songList(listFilter)
        val albumList = listProvider.albumList()

        Current.playlist = songList
        Current.albumsInfo = albumList

        val songListView = findViewById(R.id.main_songList) as ListView
        val songListArrayAdapter = SongsArrayAdaptor(context, songList)
        songListView.adapter = songListArrayAdapter
        songListView.onItemClickListener = this
	}

    fun chooseFilter() : (Song) -> Boolean {
        if (Settings.EXCLUDE_WHATSAPP_AUDIO_IN_MAIN_LIST){
            return ListProvider.WHATSAPP_AUDIO_FILTER
        }

        return ListProvider.ALL_AUDIO_FILES
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val myIntent = Intent(parent.context, PlayerActivity::class.java)
        val playlist = Current.playlist
        Current.changeSong(playlist[position])

        parent.context.startActivity(myIntent)
    }
}
