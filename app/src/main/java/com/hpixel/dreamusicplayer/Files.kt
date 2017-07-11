package com.hpixel.dreamusicplayer

import android.content.Context
import android.provider.MediaStore



class Files {

    fun getSongs(parent: Context): ArrayList<String>{
		//Retrieve a list of Music files currently listed in the Media store DB via URI.

		//Some audio may be explicitly marked as not being music
		val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

		val projection = arrayOf(
				MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.TITLE,
				MediaStore.Audio.Media.DATA,
				MediaStore.Audio.Media.DISPLAY_NAME,
				MediaStore.Audio.Media.DURATION
		)

		val cursor = parent.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				projection,
				selection,
				null, null
		)

		val songs = ArrayList<String>()
		while (cursor.moveToNext()) {
			songs.add(
					cursor.getString(0) + "||" +
					cursor.getString(1) + "||" +
					cursor.getString(2) + "||" +
					cursor.getString(3) + "||" +
					cursor.getString(4) + "||" +
					cursor.getString(5)
			)
		}

		return songs
	}
}
