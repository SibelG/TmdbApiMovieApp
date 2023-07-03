package com.example.movieapp.activities


import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityPlayVideoBinding
import com.example.movieapp.utils.Constants
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import fr.bmartel.youtubetv.YoutubeTvView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class PlayVideoActivity : AppCompatActivity() {

    lateinit var _binding: ActivityPlayVideoBinding
    private val binding get() = _binding!!
    lateinit var mQuery: String
    lateinit var playerView: PlayerView
    var player: SimpleExoPlayer? = null
    var playWhenReady = true
    var currentWindow: Int = 0
    var playBackPosition: Long = 0
    lateinit var youtubeTvView: YoutubeTvView

    // on the below line we are creating a variable
    // for our youtube player view.
    lateinit var youtubePlayerView: YouTubePlayerView

    // on below line we are creating a
    // string variable for our video id.
    var videoID = "vG2PNdI8axo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // on below line we are setting
        // screen orientation to landscape
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // on below line we are setting flags to
        // change our activity to full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_play_video)

        // on below line we are hiding our action bar
        actionBar?.hide()

        // on below line we are initializing
        // our youtube player view with its id.
        youtubePlayerView = findViewById(R.id.videoPlayer)

        // on below line we are setting full
        // screen for our youtube player view.
        /*youtubePlayerView.enterFullScreen()
        youtubePlayerView.toggleFullScreen()*/

        // on below line we are getting observer
        // for our youtube player view.
        lifecycle.addObserver(youtubePlayerView)

        val receivedIntent = intent
        mQuery = receivedIntent.getStringExtra(Constants.VIDEO_URI).toString()

        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                // loading the selected video
                // into the YouTube Player
                youTubePlayer.loadVideo(mQuery, 0f)
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                // this method is called if video has ended,
                super.onStateChange(youTubePlayer, state)
            }
        })
    }
}
