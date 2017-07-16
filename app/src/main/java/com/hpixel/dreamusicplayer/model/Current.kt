package com.hpixel.dreamusicplayer.model

/**
 * Created by vhoyer on 16/07/17.
 */
class Current {
    companion object {
        var albumsInfo: ArrayList<Album> = ArrayList<Album>()
        var playlist: ArrayList<Song> = ArrayList<Song>()
        var arrayPosition : Int = 0

        private var currentAlbum : Album = Album()
        val album : Album
            get() = currentAlbum

        private var currentSong : Song = Song()
        val song : Song
            get() = currentSong

        fun changeSong(song: Song){
            currentSong = song
            val albumFound = albumsInfo.find {
                album -> album.id == song.albumID
            }

            if (albumFound == null) {
                currentAlbum = Album()
                return
            }
            currentAlbum = albumFound
        }
    }
}