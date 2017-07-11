package com.hpixel.dreamusicplayer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val files = Files()
		val separator = "||"
		val songArray = files.getSongs(this.applicationContext, separator)
		val song = Song(songArray[0], separator)

		val label = findViewById(R.id.label) as TextView
		label.text = song.title + "\n" + song.duration.toString() + " = " + song.getHDuration()
	}
}
