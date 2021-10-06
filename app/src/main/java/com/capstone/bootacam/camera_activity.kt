package com.capstone.bootacam

import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import android.widget.ToggleButton

class camera_activity : base_sidemenu_activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        super.setToolBar()

        //움직임 감지 스위치 및 카메라 onoff 토글 버튼
        val moveSwitch: Switch = findViewById(R.id.MoveSwitch)
        val cameraOnOff: ToggleButton = findViewById(R.id.CameraOnOff)


        //움직임 감지 스위치 작동 메소드
        moveSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "움직임 감지 작동", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "움직임 감지 해제", Toast.LENGTH_SHORT).show()
            }
        }

        //카메라 onoff 토글버튼 작동 메소드
        cameraOnOff.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "카메라 ON", Toast.LENGTH_SHORT).show()
                cameraOnOff.setBackgroundResource(R.drawable.ic_launcher_background)
            } else {
                Toast.makeText(this, "카메라 OFF", Toast.LENGTH_SHORT).show()
                cameraOnOff.setBackgroundResource(R.drawable.common_google_signin_btn_icon_dark)
            }
        }

    }

}