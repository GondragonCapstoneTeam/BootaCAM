package com.capstone.bootacam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch

class camera_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        
        val moveSwitch: Switch =findViewById(R.id.MoveSwitch)
        
        moveSwitch.setOnCheckedChangeListener { buttonView, isChecked ->  }
    }
}