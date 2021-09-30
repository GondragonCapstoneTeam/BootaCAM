package com.capstone.bootacam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat

class activity_qr : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        var createQr: Button = findViewById(R.id.createQr)
        var scanQr: Button = findViewById(R.id.scanQr)

        // QR 생성 버튼 만들어서 QR 생성 화면으로 넘어가게
        createQr.setOnClickListener ({
          val intent = Intent(this, activity_qrCodeCreate::class.java)
          startActivity(intent)
        })

        // QR 스캔 버튼 만들어서 QR 스캐너로 넘어가게
        scanQr.setOnClickListener ({
            val intent = Intent(this, activity_qrCodeScan::class.java)
            startActivity(intent)
        })

    }
}