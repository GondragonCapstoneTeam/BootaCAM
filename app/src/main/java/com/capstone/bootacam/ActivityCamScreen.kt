package com.capstone.bootacam

import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory


class ActivityCamScreen : AppCompatActivity() {
    private var playerView: PlayerView? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L
    private var player: SimpleExoPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camscreen)

        val videoView: com.google.android.exoplayer2.ui.PlayerView =
            findViewById(R.id.playerview_exoplayer)

        playerView = findViewById(R.id.playerview_exoplayer)
        initializePlayer()

        //내부저장소 접근 권한이 있는지 확인
        val storagePermissionCheck = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (storagePermissionCheck != PackageManager.PERMISSION_GRANTED) {
            //권한이 없는 경우
            Log.d("testttt", "권한이 없음")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1000
            )

        } else {
            Log.d("testttt", "권한이 이미 있음")
        }

        val fullScreenButton: ImageView = findViewById(R.id.imageview_fullscreen)
        var fullScreen = true
//        val playerView: com.google.android.exoplayer2.ui.PlayerView =
//            findViewById(R.id.playerview_exoplayer)

        //전체화면 버튼을 누르면  가로모드로 바뀌게
        fullScreenButton.setOnClickListener(View.OnClickListener {
            if (fullScreen) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//                val params = playerView.getLayoutParams() as RelativeLayout.LayoutParams
//                params.width = ViewGroup.LayoutParams.MATCH_PARENT
//                params.height = (200 * applicationContext.resources.displayMetrics.density).toInt()
//                playerView.setLayoutParams(params)
                videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT)

                fullScreen = false
            } else {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

//                val params = playerView.layoutParams as RelativeLayout.LayoutParams
//                params.width = ViewGroup.LayoutParams.MATCH_PARENT
//                params.height = ViewGroup.LayoutParams.MATCH_PARENT
//                playerView.layoutParams = params
                videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                fullScreen = true
            }


        })

    }

    //requestcode를 확인해서 권한 승낙 거부 메세지창을 띄우게
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //승낙
                Log.d("testttt", "승낙")
            } else {
                //거부
                Log.d("testttt", "거부")

            }
        }
    }

    //exoplayer 초기설정해주는 함수
    private fun initializePlayer(): Unit {
        var bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
        var trackSelector: TrackSelector =
            DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
        if (player == null)
            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        var uri: Uri = Uri.parse("file:///sdcard/Documents/today222.mp4")

        var mediaSource: ExtractorMediaSource = ExtractorMediaSource(
            uri,
            DefaultDataSourceFactory(this, "btVideoPlayer"),
            DefaultExtractorsFactory(), null, null
        )

        player?.prepare(mediaSource)
        playerView?.player = player
        playerView?.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT)
        player?.playWhenReady = true
    }

    //플레이어 릴리즈
    fun releasePlayer() {
        if (player != null) {
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            playWhenReady = player!!.playWhenReady
            playerView!!.setPlayer(null)
            player!!.release()
            player = null
        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }


    //화면 모드 전환시 비디오 크기 조정
    override fun onConfigurationChanged(newConfig: Configuration) {
        val videoView: com.google.android.exoplayer2.ui.PlayerView =
            findViewById(R.id.playerview_exoplayer)
        super.onConfigurationChanged(newConfig)


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//가로
            videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM)

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {//세로
            videoView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT)
        }
    }


}