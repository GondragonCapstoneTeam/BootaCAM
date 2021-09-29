package com.capstone.bootacam

import android.content.Intent
import android.widget.Button
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import android.widget.ToggleButton

class camera_activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        toolbar = findViewById(R.id.ToolbarInAppbar)
        drawerLayout = findViewById(R.id.SideMenu)
        navigationView = findViewById(R.id.NaviView)


        navigationView.setNavigationItemSelectedListener(this)

        // 만든 툴바를 액션바로 설정 및 액션바 기본 셋팅
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        // xml 파일에서 넣어놨던 header 선언
        val headerview: View = navigationView.getHeaderView(0);

        // header에 있는 리소스 가져오기
        val userName: TextView = headerview.findViewById(R.id.UserName);
        val userAccount: TextView = headerview.findViewById(R.id.UserAccount);
        userName.setText("xxxxx");
        userAccount.setText("ff");

        // 터지 영역 지정
        val touchArea: DrawerLayout = findViewById(R.id.SideMenu);


        //움직임 감지 스위치 및 카메라 onoff 토글 버튼
        val moveSwitch: Switch = findViewById(R.id.MoveSwitch)
        val cameraOnOff: ToggleButton = findViewById(R.id.CameraOnOff)


        //사이드 메뉴가 열려있는 상태일 때
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            touchArea.setOnTouchListener { _: View, event: MotionEvent ->
                when (event.action) {
                    //지정된 영역에 화면 터치가 감지되면
                    MotionEvent.ACTION_DOWN -> {
                        //사이드 메뉴를 닫음
                        drawerLayout.closeDrawers();
                    }
                }
                //리턴값은 return 없이 아래와 같이
                true // or false
            }
        }


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


    //툴바 메뉴 설정 메소드
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //툴바 메뉴 객체화
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }


    //툴바의 메뉴 선택 메소드
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //특정 메뉴가 선택 될 때
        when (item.itemId) {
            // menu_tab 선택 시 사이드 메뉴 열림
            R.id.MenuTab -> {
                drawerLayout.openDrawer(GravityCompat.END)
            }

        }
        return super.onOptionsItemSelected(item)
    }


    //네비게이션 속 아이템 선택 메소드
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        return true
    }
}