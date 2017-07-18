package com.hpixel.dreamusicplayer.view

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hpixel.dreamusicplayer.R
import com.hpixel.dreamusicplayer.controller.ListProvider
import com.hpixel.dreamusicplayer.model.Current

class splashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val loadAll = Load()
        loadAll.execute()
    }

    inner class Load : AsyncTask<Void, Void, Void>() {
        private val context = this@splashActivity.applicationContext

        override fun doInBackground(vararg p0: Void?): Void? {
            //filters out whatsapp audio file to create playlist
            val listProvider = ListProvider(context)
            val listFilter = ListProvider.WHATSAPP_AUDIO_FILTER
            val songList = listProvider.songList(listFilter)
            val albumList = listProvider.albumList()

            Current.playlist = songList
            Current.albumsInfo = albumList

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            val mainActivity = Intent(context, MainActivity::class.java)
            val flags = Intent.FLAG_ACTIVITY_NEW_TASK

            mainActivity.setFlags(flags)
            context.startActivity(mainActivity)
            this@splashActivity.finish()
        }
    }
}
