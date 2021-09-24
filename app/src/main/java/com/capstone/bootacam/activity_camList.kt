package com.capstone.bootacam

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class activity_camList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_camlist)

        val add_fab: com.google.android.material.floatingactionbutton.FloatingActionButton =
            findViewById(R.id.add_fab)


        var cnt = 0//이거 왜 썼는지 기억 안나는데 일단 냅둠

        //FAB버튼 누르면 다이얼로그 창 뜨게하는 거
        add_fab.setOnClickListener {
            val addDialog = AlertDialog.Builder(this)
            addDialog.setIcon(R.drawable.fab_add) //다이얼로그 아이콘 사진 설정
            addDialog.setTitle("CAM 등록")
            addDialog.setMessage("등록할 CAM의 이름을 입력해주세요")

            var addCamName = EditText(this) //다이얼로그에 텍스트 입력하는 칸 넣기
            addDialog.setView(addCamName) //edittext뷰를 이용해서 입력한 이름 저장해주기


            //저장 버튼을 누르면 내가 입력한 이름의 캠이 저장되게
            addDialog.setPositiveButton(
                "저장",
                DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                    var result =
                        addCamName.getText().toString() //result에 addCamname에서 내가 입력한 이름 문자열로 저장
                    dialogInterface.dismiss() //다이얼로그창 종료

                    cnt++//이거 왜 썼는지 몰겠는데 일단 냅둠 나중에 필요없으면 지울거임

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
    fun createFakeCamList(res_name: String, mycam: MyCam = MyCam()): MyCam {

        mycam.addCam(
            Cam(name = res_name)
        )

        return mycam
    }

    //여기서 이제 그 등록한걸걸 진짜로 item뷰를 list뷰에 하나하나씩 넣는거
    fun createCamList(mycam: MyCam) {
        val layoutInflater = LayoutInflater.from(this)
        val container = findViewById<LinearLayout>(R.id.mycam_list_container)
        for (i in 0 until mycam.camList.size) {
            val view = layoutInflater.inflate(R.layout.cam_item, null)
            val camNameView = view.findViewById<TextView>(R.id.cam_name)
            camNameView.setText(mycam.camList.get(i).name)
            addSetOnClickListener(view,container,camNameView)
            container.addView(view)

        }

    }




    //뷰 하나하나 클릭할때마다 녹화 버튼 on/off되게 하는거였는데 지금 잠깐 주석처리하고 화면 넘어가는거로 바꿈
    fun addSetOnClickListener(view: View,container:LinearLayout,camNameView:TextView) {

        val setting=view.findViewById<ImageView>(R.id.cam_setting)
        setting.setOnClickListener{
            Log.d("setting","이거누르면뭐나오ㅏ야댐")
            val items = arrayOf("삭제","수정")

            val settingDialog = AlertDialog.Builder(this)
            settingDialog.setTitle("삭제/수정")
            settingDialog.setItems(items,
                DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                    Log.d("msg",items[i])
                    if(items[i]=="삭제"){
                        container.removeView(view)
                    }

                    else if(items[i]=="수정"){
                        Log.d("edit","수정되었습니다앙아옹아")
                        val editNameDialog = AlertDialog.Builder(this)
                        editNameDialog.setMessage("수정할 이름을 입력해주세요")

                        var editCamName = EditText(this) //다이얼로그에 텍스트 입력하는 칸 넣기
                        editNameDialog.setView(editCamName) //edittext뷰를 이용해서 입력한 이름 저장해주기


                        //저장 버튼을 누르면 내가 입력한 이름의 캠이 저장되게
                        editNameDialog.setPositiveButton(
                            "저장",
                            DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                                camNameView.setText(editCamName.getText().toString())
                                dialogInterface.dismiss()
                            })


                        //취소 버튼을 누르면 다이얼로그 창이 꺼지게
                        editNameDialog.setNegativeButton(
                            "취소",
                            DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                                dialogInterface.dismiss()

                            })

                        editNameDialog.show();



                    }
                })
            //AlertDialog alertDialog = builder.create();



//            addDialog.setPositiveButton(
////                "삭제",
////                DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
////                    dialogInterface.dismiss()
////                    Log.d("msg","삭제되었습니당")
////                })
////
////
////            addDialog.setNegativeButton(
////                "수정",
////                DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
////                    dialogInterface.dismiss()
////                    Log.d("msg","수정되었습니당")
////
////
////                })

            settingDialog.show();
        }


//        var rec_val = false //지금 꺼져있는상태(캠 생성시 기본)
        view.setOnClickListener {
            val camScreen= Intent(this,CamScreenActivity::class.java)
            startActivity(camScreen)

//            val recStatus = view.findViewById<ImageView>(R.id.recState)
//
//            if (rec_val == false) {
//                rec_val = true
//                recStatus.setImageResource(R.drawable.recstart) //꺼져있을때 누르면 초록색으로 바뀌게
//            } else {
//                rec_val = false
//                recStatus.setImageResource(R.drawable.recstop) //초록색으로 바뀐 아이콘 누르면 빨강되게
//            }


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