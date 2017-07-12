package com.hpixel.dreamusicplayer

/**
* Created by vhoyer on 11/07/17.
*/
data class Song(
		var songID : Int = 0,
		var artist : String = "unknown",
		var title : String = "no title",
		var filePath : String = "/",
		var displayName : String = "no display",
		var duration : Int = 0
)
{
	constructor(string : String, separator : String): this(){
		val data = string.split(separator)
		songID = data[0].toInt()
		artist = data[1]
		title = data[2]
		filePath = data[3]
		displayName = data[4]
		duration = data[5].toInt()
	}

	//get Human Format Duration
	fun getHDuration() : String {
		val min = duration / 60000
		val sec = duration % 60000 / 1000
		return  "$min:$sec"
	}
}
