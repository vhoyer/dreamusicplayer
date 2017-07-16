package com.hpixel.dreamusicplayer.controller

import android.content.Context
import com.hpixel.dreamusicplayer.model.Files
import com.hpixel.dreamusicplayer.model.Song

/**
 * Created by vhoyer on 13/07/17.
 */
class SongListProvider(val context: Context) {
    companion object {
        val WHATSAPP_AUDIO_FILTER
            get() = { song : Song -> !Regex("WhatsApp Audio").containsMatchIn (song.albumName) }
    }

    val songList
        get() = songList { song -> true }

    fun songList(withCondition: (Song) -> Boolean): ArrayList<Song> {
        val files = Files()

        val separator = "||"
        val songQuery = Song.QUERY

        val songArray = files.getSongs(context, separator, songQuery)

        var songList = arrayListOf<Song>()

        for (song in songArray){
            val newSong = Song(song, separator)
            songList.add(newSong)
        }

        songList = songList.filter(withCondition) as ArrayList<Song>

        //due to a possible "floating action button" that may be added later
        val anEmptySpaceInTheEnd = Song(0, "", "", "", "", "", 0)
        songList.add(anEmptySpaceInTheEnd)

        return songList
    }

    fun getSong(byId : Int) : Song = songList { song -> song.songID == byId }[0]
}
