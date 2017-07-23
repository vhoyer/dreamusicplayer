package com.hpixel.dreamusicplayer.view

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.hpixel.dreamusicplayer.R
import com.hpixel.dreamusicplayer.controller.GlideApp
import com.hpixel.dreamusicplayer.model.Song


/**
* Created by vhoyer on 12/07/17.
*/
class SongsArrayAdaptor(val listContext: Context, val songList: ArrayList<Song>) : ArrayAdapter<Song>(listContext, 0, songList){

	//called when rendering the list
	override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
		//get the song we are displaying
		val song = songList[position]
        val album = song.album
		//inflate the XML layout
		val inflater = listContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val view = inflater.inflate(R.layout.song_listitem_layout, null)

		val layout_songTitle = view.findViewById(R.id.songListItem_songTitle) as TextView
		val layout_artistName = view.findViewById(R.id.songListItem_artistName) as TextView
		val layout_albumName = view.findViewById(R.id.songListItem_albumName) as TextView
        val layout_cover = view.findViewById(R.id.songListItem_cover) as ImageView

		//set values
		layout_songTitle.text = song.title
		layout_artistName.text = song.artist    
		layout_albumName.text = song.albumName
        GlideApp
                .with(listContext)
                .load(album.artworkPath)
                .placeholder(R.drawable.null_artwork)
                .into(layout_cover)

		return view
	}
}
