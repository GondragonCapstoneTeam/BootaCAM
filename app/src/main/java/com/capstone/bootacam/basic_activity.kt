package com.capstone.bootacam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class basic_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basics)


        val cameraBtn: Button = findViewById(R.id.IntoCameraBtn)
        val storedVideoBtn: Button = findViewById(R.id.StoredVideoBtn)

        val cameraIntent = Intent(this,camera_activity::class.java)
        val storedVideoIntent = Intent(this,storedvideo_activity::class.java)

        cameraBtn.setOnClickListener { startActivity(cameraIntent) }
        storedVideoBtn.setOnClickListener { startActivity(storedVideoIntent) }

    }
}