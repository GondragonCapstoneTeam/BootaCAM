package com.capstone.bootacam

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
//Kakao SDK를 사용하기 위해서 Native App Key로 초기화 꼭 해줘야해서 만든 것 Appication을 상속받고 Native appKey
class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "0aea004f8054f823aa2223a37f2de4f9")
    }
}