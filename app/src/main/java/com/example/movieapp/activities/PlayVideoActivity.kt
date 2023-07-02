package com.example.movieapp.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movieapp.R
import com.example.movieapp.databinding.ActivityPlayVideoBinding
import com.example.movieapp.utils.Constants
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import fr.bmartel.youtubetv.YoutubeTvView


class PlayVideoActivity : AppCompatActivity() {

    lateinit var _binding: ActivityPlayVideoBinding
    private val binding get() = _binding!!
    lateinit var mQuery: String
    lateinit var playerView:PlayerView
    var player: SimpleExoPlayer ?= null
    var playWhenReady = true
    var currentWindow: Int = 0
    var playBackPosition : Long = 0
    lateinit var youtubeTvView: YoutubeTvView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)
        youtubeTvView = findViewById(R.id.player)
        val receivedIntent = intent
        mQuery = receivedIntent.getStringExtra(Constants.VIDEO_URI).toString()
        youtubeTvView.playVideo(mQuery)
    }


    /*override fun onStart() {
        super.onStart()
        if(Util.SDK_INT>=24){
            initPlayer()
        }
    }

    override fun onStop() {
        if(Util.SDK_INT < 24){
            releasePlayer()
        }
        super.onStop()
    }

    override fun onResume() {
        super.onResume()

        if(Util.SDK_INT < 24 || player == null ){
            initPlayer()
            hideSystemUI()
        }
    }

    override fun onPause() {
        if(Util.SDK_INT <24)
            releasePlayer()
        super.onPause()
    }

    private fun hideSystemUI() {
        playerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE or
        View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPlayVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerView = binding.videoView
        val receivedIntent = intent
        mQuery = receivedIntent.getStringExtra(Constants.VIDEO_URI).toString()
        Log.d("mquery",mQuery)

        if (mQuery == null || mQuery.trim().isEmpty()) finish()
        initPlayer()
    }

    private fun initPlayer(){
        player = SimpleExoPlayer.Builder(this).build()
        playerView.player = player


        playYoutubeVideo(mQuery)
    }

    private fun playYoutubeVideo(youtubeUrl: String?) {
        @SuppressLint("StaticFieldLeak")
        val mExtractor: YouTubeExtractor =
            object : YouTubeExtractor(this) {
                override fun onExtractionComplete(
                    sparseArray: SparseArray<YtFile>?,
                    videoMeta: VideoMeta?
                ) {
                    if (sparseArray != null) {
                        var videoTag = 137
                        var audioTag = 140
                        var audioSource : MediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(sparseArray[audioTag].url))

                        var videoSource : MediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
                            .createMediaSource(MediaItem.fromUri(sparseArray[videoTag].url))

                        player!!.setMediaSource(MergingMediaSource(true,
                        videoSource,
                        audioSource),
                        true)

                        player!!.prepare()
                        player!!.playWhenReady=playWhenReady
                        player!!.seekTo(currentWindow,playBackPosition)

                    }
                }
            }
        mExtractor.extract(youtubeUrl, true, true)
    }

    fun releasePlayer(){
        if(player != null){
            playWhenReady = player!!.playWhenReady
            playBackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.release()
            player = null
        }
    }
*/
}