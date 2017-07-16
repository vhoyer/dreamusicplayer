package com.hpixel.dreamusicplayer.model

/**
 * Created by vhoyer on 16/07/17.
 */
class Current {
    companion object {
        var playlist: ArrayList<Song> = ArrayList<Song>()
        var arrayPosition : Int = 0

        var song : Song = Song()
    }
}