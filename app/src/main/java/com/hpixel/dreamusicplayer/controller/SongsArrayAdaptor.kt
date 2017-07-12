package com.hpixel.dreamusicplayer.controller

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.hpixel.dreamusicplayer.R

/**
* Created by vhoyer on 12/07/17.
*/
class SongsArrayAdaptor(val listContext: Context, resource : Int, val songList: ArrayList<Song>) : ArrayAdapter<Song>(listContext, resource, songList){

	//called when rendering the list
	override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
		//get the song we are displaying
		val song = songList[position]
		//inflate the XML layout
		val inflater = listContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val view = inflater.inflate(R.layout.song_listitem_layout, null)

		val layout_duration = view.findViewById(R.id.songListItem_duration) as TextView
		val layout_songTitle = view.findViewById(R.id.songListItem_songTitle) as TextView
		val layout_artistName = view.findViewById(R.id.songListItem_artistName) as TextView
		val layout_albumName = view.findViewById(R.id.songListItem_albumName) as TextView

		//set values
		layout_duration.text = song.getHDuration()
		layout_songTitle.text = song.title
		layout_artistName.text = song.artist
		layout_albumName.text = song.albumName

		return view
	}
}
