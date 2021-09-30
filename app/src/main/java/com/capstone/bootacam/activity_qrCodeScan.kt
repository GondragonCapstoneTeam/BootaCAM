package com.capstone.bootacam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult


class activity_qrCodeScan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcodescan)

        IntentIntegrator(this).initiateScan() //QR코드 스캐너 실행

        val integrator = IntentIntegrator(this)
        integrator.setPrompt("QR코드를 스캔해주세요.") //메세지를 띄울 것이다.
        integrator.setBeepEnabled(true) //큐알코드 인식하면 삡하는 알림음이 난다.
        integrator.initiateScan() //스캔시작

    }

    fun startBarcodeReaderCustom(view: View){  //스캔하기전 어플 커스텀
        val integrator = IntentIntegrator(this)
        integrator.setPrompt("QR코드를 스캔해주세요.") //메세지를 띄울 것이다.
        integrator.setBeepEnabled(true) //큐알코드 인식하면 삡하는 알림음이 난다.
        integrator.initiateScan() //스캔시작
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result : IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data) // result에 데이트들 저장
        if (result != null) {
            if (result.contents != null) { // qr코드의 값이 있다면
                Toast.makeText(this, "확인되었습니다.: ${result.contents} ${result.formatName}", Toast.LENGTH_LONG).show()
                Log.e("this", result.contents.toString())
            } else { // qr코드에 대한 값이 없을 때
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                Log.e("this", "잘못된 QR코드입니다.")
            }
        } else { // null이 아니라면 실행
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
}