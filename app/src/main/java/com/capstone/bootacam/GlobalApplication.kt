package com.capstone.bootacam

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "0aea004f8054f823aa2223a37f2de4f9")
    }
}