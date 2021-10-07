package com.capstone.bootacam

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class ActivityCamList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camlist)

        val addFab: com.google.android.material.floatingactionbutton.FloatingActionButton =
            findViewById(R.id.button_addfab)

        //FAB버튼 누르면 다이얼로그 창이 뜸
        addFab.setOnClickListener {
            val addDialog = AlertDialog.Builder(this)
            addDialog.setIcon(R.drawable.camlist_add_40dp) //다이얼로그 아이콘 사진 설정
            addDialog.setTitle("CAM 등록") //다이얼로그 제목
            addDialog.setMessage("등록할 CAM의 이름을 입력해주세요") //다이얼로그 내용

            var addCamName = EditText(this) //다이얼로그에 텍스트 입력하는 칸 넣기
            addDialog.setView(addCamName) //edittext뷰를 이용해서 입력한 이름 저장해주기

            //저장 버튼을 누르면 내가 입력한 이름의 캠이 저장되게
            addDialog.setPositiveButton(
                "저장",
                DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                    var result =
                        addCamName.getText().toString() //result에 addCamname에서 내가 입력한 이름 문자열로 저장
                    dialogInterface.dismiss() //다이얼로그창 종료

                    val mycam = createFakeCamList(result)//이제 여기다가 입력한 이름을 인자로 넣어서 그 이름으로 생성되게
                    createCamList(mycam)//캠 하나 생성
                })

            //취소 버튼을 누르면 다이얼로그 창이 꺼지게
            addDialog.setNegativeButton(
                "취소",
                DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                })
            addDialog.show();
        }
    }

    //캠리스트 틀..?을 만들어서 이제 여기서는 아마 캠 등록하고 이름 정해주려고 이렇게 한듯..??
    fun createFakeCamList(resName: String, mycam: MyCam = MyCam()): MyCam {

        mycam.addCam(
            Cam(name = resName)
        )

        return mycam
    }

    //여기서 이제 그 등록한걸걸 진짜로 item뷰를 list뷰에 하나하나씩 넣는거
    fun createCamList(mycam: MyCam) {
        val layoutInflater = LayoutInflater.from(this)
        val container = findViewById<LinearLayout>(R.id.linear_camcontainer)
        for (i in 0 until mycam.camList.size) {
            val view = layoutInflater.inflate(R.layout.item_camlist, null)
            val camNameView = view.findViewById<TextView>(R.id.textview_camname)
            camNameView.setText(mycam.camList.get(i).name)
            addSetOnClickListener(view, container, camNameView)
            container.addView(view)
        }
    }

    //뷰 하나하나 클릭할때마다 카메라 화면으로 넘어가는 함수
    fun addSetOnClickListener(view: View, container: LinearLayout, camNameView: TextView) {

        //톱니바퀴 아이콘을 누르면 삭제/이름 수정 할 수 있는 리스트 다이얼로그창 생성
        val setting = view.findViewById<ImageView>(R.id.imageview_setting)
        setting.setOnClickListener {
            val items = arrayOf("삭제", "수정")

            val settingDialog = AlertDialog.Builder(this)
            settingDialog.setTitle("삭제/수정")
            settingDialog.setItems(items,
                DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                    Log.d("msg", items[i])

                    //삭제버튼을 누르면 정말로 삭제할까요?라는 창을 하나 더 띄우기
                    if (items[i] == "삭제") {
                        val deleteDialog = AlertDialog.Builder(this)
                        deleteDialog.setMessage("정말로 삭제할까요?")
                        deleteDialog.setPositiveButton(
                            "삭제",
                            DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                                container.removeView(view) //삭제버튼을 최종적으로 누르면 해당 뷰(내가 선택한 캠)를 remove
                                dialogInterface.dismiss()
                            })

                        //취소 버튼을 누르면 다이얼로그 창이 꺼지게
                        deleteDialog.setNegativeButton(
                            "취소",
                            DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                                dialogInterface.dismiss()
                                Toast.makeText(this, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                            })

                        deleteDialog.show();
                    }

                    //수정버튼을 누르면 수정할 이름을 입력하는 다이얼로그창 띄우기
                    else if (items[i] == "수정") {
                        val editNameDialog = AlertDialog.Builder(this)
                        editNameDialog.setMessage("수정할 이름을 입력해주세요")

                        var editCamName = EditText(this)
                        editNameDialog.setView(editCamName)

                        editNameDialog.setPositiveButton(
                            "저장",
                            DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                                camNameView.setText(editCamName.getText().toString())
                                dialogInterface.dismiss()
                            })

                        editNameDialog.setNegativeButton(
                            "취소",
                            DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                                dialogInterface.dismiss()
                            })

                        editNameDialog.show();
                    }
                })
            settingDialog.show();
        }


        //뷰를 클릭하면 카메라 화면으로 전환하는 클릭리스너
        view.setOnClickListener {
            val camScreen = Intent(this, ActivityCamScreen::class.java)


            startActivity(camScreen)
        }
    }
}

class MyCam() {
    //등록한 캠 리스트
    val camList = ArrayList<Cam>()

    fun addCam(cam: Cam) {
        camList.add(cam)
    }
}

class Cam(val name: String) {

}