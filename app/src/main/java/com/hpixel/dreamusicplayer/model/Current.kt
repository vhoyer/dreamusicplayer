package com.hpixel.dreamusicplayer.model

/**
 * Created by vhoyer on 16/07/17.
 */
class Current {
    companion object {
        val REPEAT_ALL = 2
        val REPEAT_ONE = 1
        val REPEAT_NOT = 0

        var albumsInfo: ArrayList<Album> = ArrayList<Album>()
        var playlist: ArrayList<Song> = ArrayList<Song>()
        val playlistLenght : Int
            get() = playlist.size

        var shuffling = false
        var repeating: Int = this.REPEAT_NOT
        var playing = false

        private var currentPlaylistPosition: Int = 0
        val playlistPosition : Int
            get() = currentPlaylistPosition

        private var currentAlbum : Album = Album()
        val album : Album
            get() = currentAlbum

        private var currentSong : Song = Song()
        val song : Song
            get() = currentSong

        fun changeSong(song: Song){
            currentSong = song
            currentPlaylistPosition = playlist.indexOf(song)

            changeAlbum()
        }

        fun changeSong(index: Int){
            currentSong = playlist[index]
            currentPlaylistPosition = index

            changeAlbum()
        }

        private fun changeAlbum(){
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