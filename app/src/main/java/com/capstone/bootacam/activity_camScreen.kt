package com.capstone.bootacam

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util

class activity_camScreen : AppCompatActivity() {
    private var playerView: PlayerView? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L
    private var player: SimpleExoPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camscreen)
        playerView = findViewById(R.id.video_view)
        initializePlayer()

        val storagePermissionCheck= ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if(storagePermissionCheck!=PackageManager.PERMISSION_GRANTED){
            //권한이 없는 경우
            Log.d("testttt","권한이 없음")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                1000
            )

        }else{
            Log.d("testttt","권한이 이미 있음")
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==1000){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //승낙
                Log.d("testttt","승낙")
            }else{
                //거부
                Log.d("testttt","거부")

            }
        }
    }

    private fun initializePlayer(): Unit {
        var bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
        var trackSelector: TrackSelector =
            DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandwidthMeter))
        if (player == null)
            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        var uri: Uri = Uri.parse("file:///sdcard/Documents/today.mp4")

        var mediaSource: ExtractorMediaSource = ExtractorMediaSource(
            uri,
            DefaultDataSourceFactory(this, "btVideoPlayer"),
            DefaultExtractorsFactory(), null, null
        )

        player?.prepare(mediaSource)
        playerView?.player = player
        playerView?.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM)
        player?.playWhenReady = true
    }

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
}