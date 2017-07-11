package com.hpixel.dreamusicplayer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

        val label = findViewById(R.id.label) as TextView
        val files = Files()
        label.text = files.getSongs(this.applicationContext)[0]
	}
}
