package com.capstone.bootacam

import android.graphics.Bitmap
import android.graphics.Color
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.lang.Exception
import com.google.zxing.WriterException

import com.google.zxing.EncodeHintType
import java.util.*


class activity_qrCodeCreate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code_create)

        // ANDROID_ID에 단말기 고유 값을 저장하려고 만든 것
        var ANDROID_ID : String = Settings.Secure.getString(applicationContext.contentResolver, Settings.Secure.ANDROID_ID)
        var qrCodeCreate : ImageView = findViewById(R.id.qrCodeCreate)
        var textt : String = "Hello" // qr코드를 스캔했을 때 뜰 것
        val multiFormatWriter = MultiFormatWriter() //qr코드 만들 때 필요한 함수
        try { // 여기서 부터 qr 생성
            val bitMatrix = multiFormatWriter.encode(textt, BarcodeFormat.QR_CODE, 200, 200) // qr에 들어갈 값,생성하고자 하는 포맷(우리는 QR), 가로 세로 크기 지정
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            qrCodeCreate!!.setImageBitmap(bitmap)
        } catch(e:Exception){

        }

    }


}
