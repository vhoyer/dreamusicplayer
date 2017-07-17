package com.hpixel.dreamusicplayer.model

import android.content.Context
import android.graphics.drawable.Drawable
import com.hpixel.dreamusicplayer.R

/**
 * Created by vhoyer on 16/07/17.
 */
data class Album(
        val id: Int = 0,
        val artworkPath: String = ""
)
{
    fun getArtwork(context: Context)  : Drawable{
        var drawable = Drawable.createFromPath(artworkPath)

        if (drawable == null) {
            val resources = context.getResources()
            val null_artworkID = R.drawable.null_artwork
            return resources.getDrawable(null_artworkID, null)
        }

        return drawable
    }
}