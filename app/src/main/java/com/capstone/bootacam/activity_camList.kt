package com.capstone.bootacam

import android.content.DialogInterface
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
import androidx.core.view.isInvisible

class activity_camList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_camlist)

        val add_fab: com.google.android.material.floatingactionbutton.FloatingActionButton =
            findViewById(R.id.add_fab)
        //val mycam_list_container:LinearLayout=findViewById(R.id.mycam_list_container)


        var cnt = 0
        add_fab.setOnClickListener {
            val addDialog = AlertDialog.Builder(this)
            addDialog.setIcon(R.drawable.fab_add)
            addDialog.setTitle("CAM 등록")
            addDialog.setMessage("등록할 CAM의 이름을 입력해주세요")

            var addCamName = EditText(this)
            addDialog.setView(addCamName)

            addDialog.setPositiveButton(
                "저장",
                DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                    var result = addCamName.getText().toString()
                    dialogInterface.dismiss()
                    cnt++
                    val mycam = createFakeCamLsit(result)//이제 여기다가 숫자를 인자로 주면 그만큼 만들어지는거임
                    createCamList(mycam)


                })

            addDialog.setNegativeButton(
                "취소",
                DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()

                })

            addDialog.show();

        }


    }

    fun createFakeCamLsit(res_name: String, mycam: MyCam = MyCam()): MyCam {

        mycam.addCam(
            Cam(name = res_name)
        )

        return mycam
    }

    fun createCamList(mycam: MyCam) {
        val layoutInflater = LayoutInflater.from(this)
        val container = findViewById<LinearLayout>(R.id.mycam_list_container)
        for (i in 0 until mycam.camList.size) {
            val view = layoutInflater.inflate(R.layout.cam_item, null)
            val camNameView = view.findViewById<TextView>(R.id.cam_name)
            camNameView.setText(mycam.camList.get(i).name)
            addSetOnClickListener(view)
            container.addView(view)

        }
    }

    fun addSetOnClickListener(view: View) {


        var rec_val = false //지금 꺼져있는상태

        view.setOnClickListener {
            if(rec_val==false){
                Log.d("click","꺼져있는거 클릭햇다.."+rec_val)
                rec_val=true
            }else{
                Log.d("click","켜져있는거 클릭햇다.."+rec_val)
                rec_val=false
            }
            Log.d("click",""+rec_val)


//            if (rec_val == false) {
//                recStatus.setImageResource(R.drawable.recstart)
//                rec_val = true
//            }else{
//                recStatus.setImageResource(R.drawable.recstop)
//                rec_val = false
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