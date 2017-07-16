package com.hpixel.dreamusicplayer.model

import android.provider.MediaStore

/**
* Created by vhoyer on 11/07/17.
*/
data class Song(
        var songID : Int = 0,
        var artist : String = "unknown",
        var albumName : String = "unknown album",
        var title : String = "no title",
        var filePath : String = "/",
        var displayName : String = "no display",
        var duration : Int = 0,
        var albumID : Int = 0
)
{
    companion object {
        val QUERY: Array<String> = arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID
        )
    }

	constructor(string : String, separator : String): this(){
		val data = string.split(separator)
		songID = data[0].toInt()
		artist = data[1]
		title = data[2]
		filePath = data[3]
		displayName = data[4]
		duration = data[5].toInt()
        albumName = data[6]
        albumID = data[7].toInt()
	}

	//get Human Format Duration
	fun getHDuration() : String {
		val min = duration / 60000
		val sec = "${duration % 60000 / 1000}".format("%02d")
		return  "$min:$sec"
	}
}
