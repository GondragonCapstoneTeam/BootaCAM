package com.capstone.bootacam

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast

class camera_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        
        val moveSwitch: Switch =findViewById(R.id.MoveSwitch)
        val cameraOnOff: ImageView =findViewById(R.id.CameraOnOff)
        
        moveSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){ Toast.makeText(this,"움직임 감지 작동",Toast.LENGTH_SHORT).show()}
            else{ Toast.makeText(this,"움직임 감지 해제",Toast.LENGTH_SHORT).show()}
        }


    }
}