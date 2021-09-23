package com.capstone.bootacam

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class activity_side_menu : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    lateinit var navigationView: NavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_side_menu)

        toolbar=findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.side_menu)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this@activity_side_menu)

        // 만든 툴바를 액션바로 설정 및 액션바 기본 셋팅
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // xml 파일에서 넣어놨던 header 선언
        val headerview: View = navigationView.getHeaderView(0);

        // header에 있는 리소스 가져오기
        val user_name: TextView = headerview.findViewById(R.id.user_name);
        val user_account: TextView = headerview.findViewById(R.id.user_account);
        user_name.setText("xxxxx");
        user_account.setText("ff");

        // 터지 영역 지정
        val touch_area: DrawerLayout=findViewById(R.id.side_menu);

        //사이드 메뉴가 열려있는 상태일 때
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            touch_area.setOnTouchListener { _: View, event: MotionEvent ->
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
    }


    //툴바 메뉴 설정 메소드
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //툴바 메뉴 객체화
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }


    //툴바의 메뉴 선택 메소드
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //특정 메뉴가 선택 될 때
        when(item.itemId) {
            // menu_tab 선택 시 사이드 메뉴 열림
            R.id.menu_tab -> {
               drawerLayout.openDrawer(GravityCompat.END)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    //네비게이션 속 아이템 선택 메소드
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.account->Toast.makeText(this,"account 실행",Toast.LENGTH_SHORT).show()
            R.id.setting->Toast.makeText(this,"setting 실행",Toast.LENGTH_SHORT).show()
            R.id.logout->Toast.makeText(this,"logout 실행",Toast.LENGTH_SHORT).show()
        }
        return true
    }
}
