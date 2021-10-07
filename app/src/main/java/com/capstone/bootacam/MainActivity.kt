package com.capstone.bootacam

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApi
import com.kakao.sdk.user.UserApiClient


class MainActivity : AppCompatActivity() {
    //firebase Auth
    private lateinit var firebaseAuth : FirebaseAuth

    //google client
    private lateinit var googleSignInClient: GoogleSignInClient

    //private const val TAG = "googleActivity"
    private val RC_SIGN_IN = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 구글 로그인 버튼
        val googleLoginButton : com.google.android.gms.common.SignInButton = findViewById(R.id.googleLoginButton)
        googleLoginButton.setOnClickListener{signIn()} // 구글 로그인 버튼을 클릭 했을 때 구글 계정 인증 Activity가 보여짐
        // 구글 로그인 옵션 구성 requestIdToken 및 Email 요청
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) // 구글 로그인을 통해 앱에서 사용자의 데이터 요청
            .requestIdToken("940911197771-8tpmllrgm1o0feb1ab74499acnkjas87.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso) //gso를 통해 클라이언트의 정보(GoogleSignInClient)를 보호

        firebaseAuth = FirebaseAuth.getInstance()


        // 카카오 개발자 등록위한 키 해쉬 확인하는 코드 2줄
        val keyHash = Utility.getKeyHash(this)
        Log.d("HASH", keyHash)

        // 카카오 로그인 정보 확인
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            }
            else if (tokenInfo != null) {
                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "토큰 정보 보기 성공" + "\n회원번호 : ${tokenInfo.id}" + "\n만료시간 : ${tokenInfo.expiresIn}초")
                val intent = Intent(this, activity_login2::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        //카카오 사용자 정보 가져오기
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.e(TAG, "사용자 정보 요청 성공" +
                "\n회원번호: ${user.id}" +
                        "\n이메일: ${user.kakaoAccount?.email}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
            }
        }



        // 사용자 정보 저장하기

        val properties = mapOf("nickname" to "${System.currentTimeMillis()}")

        UserApiClient.instance.updateProfile(properties) { error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 저장 실패", error)
            }
            else {
                Log.e(TAG, "사용자 정보 저장 성공")
            }
        }




        // 카카오 토큰 확인
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, activity_login2::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }


        // 카카오 로그인
        val kakaoLoginButton = findViewById<ImageButton>(R.id.kakaoLoginButton) // 로그인 버튼

        // isKakaoTaliLoginAvailable() API로 사용자 기기에 카카오톡이 설치외어 있음을 확인하고, 카카오톡 로그인 API인 loginWithKakaoTali() 호출
        kakaoLoginButton.setOnClickListener {
            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)

                // loginWithKakaoTalk() API 호출 시 Android SDK가 카카오톡 실행 후 사용자에게 앱 이용관련 동의 구하는 동의 화면 출력 context와 결과 처리를 위한 콜백 함수 전달해야함

                // 카카오톡이 설치되어 있으면 카카오톡으로 르그인, 아니면 카카오계정으로 로그인
            }else{
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }


    }




    // OnStart. 유저가 앱에 이미 구글 로그인 했는지 확인
    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account !== null ) {
            toMainActivity(firebaseAuth.currentUser)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) { // 로그인 한 user-에 대한 정보 활용을 위해 task 변수에 getSignedInAccountFromIntent로 가져와 담음음
           val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try { // 정상 로그인이 되면 Firebase에 등록
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!) // 응답이 정상이면 firebaseAuthWithGoogle 함수 호출

            } catch (e: ApiException) {
                Log.w("LoginActivity", "Google sign in failed", e)

            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle: " + acct.id!!)

        // Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if(task.isSuccessful) { // signInWithCredential에 대한 호출이 성공하면 getCurrentUser 메소드로 사용자의 계정 데이터를 가져옴
                Log.w("LoginActivity", "firebaseAuthWithGoogle 성공", task.exception)
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                toMainActivity(firebaseAuth?.currentUser)

            } else {
                Log.w("LoginActivity", "firebaseAuthWithGoogle 실패", task.exception)
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun toMainActivity(user: FirebaseUser?) {
        if(user != null) {
            startActivity(Intent(this, activity_login2::class.java))
            finish()
        }
    }

    //singIn 로그인 요청
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    // 구글 로그아웃
   // private fun signOut() {

   //     firebaseAuth.signOut()

   //     googleSignInClient.signOut().addOnCompleteListener(this) {


   //         }
   // }

    // 구글 로그인 탈퇴
  //  private fun revokeAccess() {
  //      firebaseAuth.signOut()
  //      googleSignInClient.revokeAccess().addOnCompleteListener(this) {

 //       }
 //   }

}


