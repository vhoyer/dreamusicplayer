package com.hpixel.dreamusicplayer.controller

import android.content.Context
import com.hpixel.dreamusicplayer.model.Album
import com.hpixel.dreamusicplayer.model.Files
import com.hpixel.dreamusicplayer.model.Song

/**
 * Created by vhoyer on 13/07/17.
 */
class ListProvider(val context: Context) {
    companion object {
        val ALL_AUDIO_FILES
            get() = { _ : Song -> true}
        val WHATSAPP_AUDIO_FILTER
            get() = { song : Song -> !Regex("WhatsApp Audio").containsMatchIn (song.albumName) }
    }


    private val files = Files()
    private val separator = "||"

    fun songList(withCondition: (Song) -> Boolean): ArrayList<Song> {
        val songQuery = Song.QUERY

        val songArray = files.getSongs(context, separator, songQuery)

        var songList = arrayListOf<Song>()

        for (songString in songArray){
            val newSong = Song(songString, separator)
            songList.add(newSong)
        }

        songList = songList.filter(withCondition) as ArrayList<Song>

        return songList
    }

    fun albumList() : ArrayList<Album> {
        val albumArray = files.getAlbumnInfo(context, separator)

        val albumList = arrayListOf<Album>()

        for (albumString in albumArray){
            val data = albumString.split(separator)
            val albumID = data[0].toInt()
            val artworkPath = data[1]

            val newSong = Album(albumID, artworkPath)

            albumList.add(newSong)
        }

        return albumList
    }
}
