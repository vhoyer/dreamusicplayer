package com.hpixel.dreamusicplayer.model

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore


class Files {

    fun getSongs(parent: Context, separator : String, songQuery : Array<String>): ArrayList<String>{
		//Retrieve a list of Music files currently listed in the Media store DB via URI.

		//Some audio may be explicitly marked as not being music
		val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"

		val cursor = parent.getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                songQuery,
				selection,
				null,
                sortOrder
		)

        return cursorIter(cursor, separator)
	}

    fun getAlbumnInfo(parent: Context, separator: String) : ArrayList<String> {
        val query = arrayOf(
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM_ART
        )

        val cursor = parent.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                query,
                null,null,null
        )

        return cursorIter(cursor, separator)
    }

    private fun cursorIter (cursor : Cursor, separator: String): ArrayList<String> {
        val list = ArrayList<String>()

        while (cursor.moveToNext()) {
            var string = cursor.getString(0)

            for (i in 1..cursor.columnCount-1){
                string += separator + cursor.getString(i)
            }

            list.add(string)
        }

        return list
    }
}
