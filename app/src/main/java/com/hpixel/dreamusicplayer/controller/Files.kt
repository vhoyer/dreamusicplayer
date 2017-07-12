package com.hpixel.dreamusicplayer.controller

import android.content.Context
import android.provider.MediaStore



class Files {

    fun getSongs(parent: Context, separator : String, songQuery : Array<String>): ArrayList<String>{
		//Retrieve a list of Music files currently listed in the Media store DB via URI.

		//Some audio may be explicitly marked as not being music
		val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

		val cursor = parent.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                songQuery,
				selection,
				null, null
		)

		val songs = ArrayList<String>()
		while (cursor.moveToNext()) {
            var songString = cursor.getString(0)
            for (i in 1..cursor.columnCount-1){
                songString += separator + cursor.getString(i)
            }
			songs.add(songString)
		}

		return songs
	}
}
