package com.capstone.bootacam


import android.content.Intent
import android.widget.Button
import android.os.Bundle


class basic_activity : base_sidemenu_activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basics)
        super.setToolBar()

        val cameraBtn: Button = findViewById(R.id.IntoCameraBtn)
        val storedVideoBtn: Button = findViewById(R.id.StoredVideoBtn)

        // 각 버튼의 화면전환
        cameraBtn.setOnClickListener {
            val cameraIntent = Intent(this, camera_activity::class.java)
            startActivity(cameraIntent)
        }
        storedVideoBtn.setOnClickListener {
            val storedVideoIntent = Intent(this, storedvideo_activity::class.java)
            startActivity(storedVideoIntent)
        }

    }


}

