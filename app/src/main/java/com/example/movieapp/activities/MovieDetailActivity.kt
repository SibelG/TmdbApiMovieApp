package com.example.movieapp.activities


import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

import com.example.movieapp.Network.ApiService
import com.example.movieapp.Network.Movies.*
import com.example.movieapp.Network.videos.Video
import com.example.movieapp.Network.videos.VideosResponse
import com.example.movieapp.R
import com.example.movieapp.adapters.MovieBriefsSmallAdapter
import com.example.movieapp.adapters.MovieCastsAdapter
import com.example.movieapp.adapters.VideoAdapter
import com.example.movieapp.databinding.ActivityMainBinding
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.Favourite.addMovieToFav
import com.example.movieapp.utils.Favourite.isMovieFav
import com.example.movieapp.utils.Favourite.removeMovieFromFav
import com.example.movieapp.utils.NetworkHelper
import com.example.movieapp.utils.Status
import com.example.movieapp.view_model.MainViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMovieDetailBinding

    private var mMovieId = 0
    lateinit var mCollapsingToolbarLayout: CollapsingToolbarLayout
    lateinit var mAppBarLayout: AppBarLayout
    lateinit var mToolbar: Toolbar
    lateinit var mMovieTabLayout: ConstraintLayout
    lateinit var mPosterImageView: ImageView
    private var mPosterHeight = 0
    private var mPosterWidth = 0
    //private var mPosterProgressBar: AVLoadingIndicatorView? = null
    private var mBackdropImageView: ImageView? = null
    private var mBackdropHeight = 0
    private var mBackdropWidth = 0
    //private var mBackdropProgressBar: AVLoadingIndicatorView? = null
    lateinit var mTitleTextView: TextView
    lateinit var mGenreTextView: TextView
    lateinit var mYearTextView: TextView
    lateinit var mBackImageButton: ImageButton
    lateinit var mFavImageButton: ImageButton
    lateinit var mShareImageButton: ImageButton
    lateinit var mRatingLayout: LinearLayout
    private var mRatingTextView: TextView? = null
    private var mOverviewTextView: TextView? = null
    lateinit var mOverviewReadMoreTextView: TextView
    lateinit var mDetailsLayout: LinearLayout
    lateinit var mDetailsTextView: TextView
    lateinit var mTrailerTextView: TextView
    lateinit var mTrailerRecyclerView: RecyclerView

    lateinit var mTrailerAdapter: VideoAdapter
    lateinit var mHorizontalLine: View
    private var mCastTextView: TextView? = null
    lateinit var mCastRecyclerView: RecyclerView

    lateinit var mCastAdapter: MovieCastsAdapter
    private var mSimilarMoviesTextView: TextView? = null
    lateinit var mSimilarMoviesRecyclerView: RecyclerView

    lateinit var mSimilarMoviesAdapter: MovieBriefsSmallAdapter
    private var mConnectivitySnackbar: Snackbar? = null


    lateinit var viewModel: MainViewModel
    @Inject
    lateinit var  networkHelper: NetworkHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mToolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(mToolbar)
        title = ""
        val receivedIntent = intent
        mMovieId = receivedIntent.getIntExtra(Constants.MOVIE_ID, -1)
        if (mMovieId == -1) finish()
        mCollapsingToolbarLayout =
            findViewById<View>(R.id.toolbar_layout) as CollapsingToolbarLayout
        mAppBarLayout = findViewById<View>(R.id.app_bar) as AppBarLayout
        mPosterWidth = (resources.displayMetrics.widthPixels * 0.25).toInt()
        mPosterHeight = (mPosterWidth / 0.66).toInt()
        mBackdropWidth = resources.displayMetrics.widthPixels
        mBackdropHeight = (mBackdropWidth / 1.77).toInt()
        mMovieTabLayout = findViewById<View>(R.id.layout_toolbar_movie) as ConstraintLayout
        mMovieTabLayout!!.layoutParams.height = mBackdropHeight + (mPosterHeight * 0.9).toInt()
        mPosterImageView = findViewById<View>(R.id.image_view_poster) as ImageView
        mPosterImageView!!.layoutParams.width = mPosterWidth
        mPosterImageView!!.layoutParams.height = mPosterHeight
       // mPosterProgressBar = findViewById<View>(R.id.progress_bar_poster) as AVLoadingIndicatorView
        //mPosterProgressBar.setVisibility(View.GONE)
        mBackdropImageView = findViewById<View>(R.id.image_view_backdrop) as ImageView
        mBackdropImageView!!.layoutParams.height = mBackdropHeight
        //mBackdropProgressBar =
            //findViewById<View>(R.id.progress_bar_backdrop) as AVLoadingIndicatorView
        //mBackdropProgressBar.setVisibility(View.GONE)
        mTitleTextView = findViewById<View>(R.id.text_view_title_movie_detail) as TextView
        mGenreTextView = findViewById<View>(R.id.text_view_genre_movie_detail) as TextView
        mYearTextView = findViewById<View>(R.id.text_view_year_movie_detail) as TextView
        mBackImageButton = findViewById<View>(R.id.image_button_back_movie_detail) as ImageButton
        mBackImageButton!!.setOnClickListener { onBackPressed() }
        mFavImageButton = findViewById<View>(R.id.image_button_fav_movie_detail) as ImageButton
        mShareImageButton = findViewById<View>(R.id.image_button_share_movie_detail) as ImageButton
        mRatingLayout = findViewById<View>(R.id.layout_rating_movie_detail) as LinearLayout
        mRatingTextView = findViewById<View>(R.id.text_view_rating_movie_detail) as TextView
        mOverviewTextView = findViewById<View>(R.id.text_view_overview_movie_detail) as TextView
        mOverviewReadMoreTextView =
            findViewById<View>(R.id.text_view_read_more_movie_detail) as TextView
        mDetailsLayout = findViewById<View>(R.id.layout_details_movie_detail) as LinearLayout
        mDetailsTextView = findViewById<View>(R.id.text_view_details_movie_detail) as TextView
        mTrailerTextView = findViewById<View>(R.id.text_view_trailer_movie_detail) as TextView
        mTrailerRecyclerView =
            findViewById<View>(R.id.recycler_view_trailers_movie_detail) as RecyclerView
        LinearSnapHelper().attachToRecyclerView(mTrailerRecyclerView)

        mTrailerAdapter = VideoAdapter(this@MovieDetailActivity)
        mTrailerRecyclerView!!.adapter = mTrailerAdapter
        mTrailerRecyclerView!!.layoutManager =
            LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        mHorizontalLine = findViewById(R.id.view_horizontal_line) as View
        mCastTextView = findViewById<View>(R.id.text_view_cast_movie_detail) as TextView
        mCastRecyclerView = findViewById<View>(R.id.recycler_view_cast_movie_detail) as RecyclerView

        mCastAdapter = MovieCastsAdapter(this@MovieDetailActivity)
        mCastRecyclerView!!.adapter = mCastAdapter
        mCastRecyclerView!!.layoutManager =
            LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        mSimilarMoviesTextView = findViewById<View>(R.id.text_view_similar_movie_detail) as TextView
        mSimilarMoviesRecyclerView =
            findViewById<View>(R.id.recycler_view_similar_movie_detail) as RecyclerView

        mSimilarMoviesAdapter = MovieBriefsSmallAdapter(this@MovieDetailActivity)
        mSimilarMoviesRecyclerView!!.adapter = mSimilarMoviesAdapter
        mSimilarMoviesRecyclerView!!.layoutManager =
            LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        if (networkHelper.isNetworkConnected()) {

            loadActivity()
        }
    }

    override fun onStart() {
        super.onStart()
        mSimilarMoviesAdapter!!.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        if (!networkHelper.isNetworkConnected()) {
            mConnectivitySnackbar = Snackbar.make(
                findViewById(R.id.main_activity_fragment_container),
                R.string.no_network,
                Snackbar.LENGTH_INDEFINITE
            )
        } else  {
            loadActivity()
        }
    }

    override fun onPause() {
        super.onPause()
       /* if (isBroadcastReceiverRegistered) {
            isBroadcastReceiverRegistered = false
            unregisterReceiver(mConnectivityBroadcastReceiver)
        }*/
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    private fun loadActivity() {
        viewModel.getMovieDetails(mMovieId)
        viewModel.resMovieDetails.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {

                    it.data.let {res->
                        res?.let {
                                it1 ->  mAppBarLayout!!.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
                            if (appBarLayout.totalScrollRange + verticalOffset == 0) {
                                if (it1!!.title != null) mCollapsingToolbarLayout!!.title =
                                    it1!!.title else mCollapsingToolbarLayout!!.title = ""
                                mToolbar.setVisibility(View.VISIBLE)
                            } else {
                                mCollapsingToolbarLayout!!.title = ""
                                mToolbar.setVisibility(View.INVISIBLE)
                            }
                        }
                            Glide.with(applicationContext).load(
                                Constants.IMAGE_LOADING_BASE_URL_1280 + it1!!.posterPath
                            )
                                .asBitmap()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(mPosterImageView)
                            Glide.with(applicationContext).load(
                                Constants.IMAGE_LOADING_BASE_URL_1280 + it1!!.backdropPath
                            )
                                .asBitmap()
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)

                                .into(mBackdropImageView)
                            if (it1.title != null) mTitleTextView.setText(
                                it1.title
                            ) else mTitleTextView.text =
                                ""
                            setGenres(it1.genres)
                            setYear(it1.releaseDate)
                            mFavImageButton!!.visibility = View.VISIBLE
                            mShareImageButton!!.visibility = View.VISIBLE
                            setImageButtons(
                                it1.id,
                                it1.posterPath,
                                it1.title,
                                it1.tagline,
                                it1.imdbId,
                                it1.homepage
                            )
                            if (it1.voteAverage != null && it1.voteAverage !== 0.0) {
                                mRatingLayout!!.visibility = View.VISIBLE
                                mRatingTextView!!.text =
                                    java.lang.String.format("%.1f", it1.voteAverage)
                            }
                            if (it1.overview != null && !it1.overview.trim()
                                    .isEmpty()
                            ) {
                                mOverviewReadMoreTextView!!.visibility = View.VISIBLE
                                mOverviewTextView!!.text = it1.overview
                                mOverviewReadMoreTextView!!.setOnClickListener {
                                    mOverviewTextView!!.maxLines = Int.MAX_VALUE
                                    mDetailsLayout!!.visibility = View.VISIBLE
                                    mOverviewReadMoreTextView!!.visibility = View.GONE
                                }
                            } else {
                                mOverviewTextView!!.text = ""
                            }
                            setDetails(it1.releaseDate, it1.runtime)
                            setTrailers()
                            mHorizontalLine!!.visibility = View.VISIBLE
                            setCasts()
                            setSimilarMovies()
                            Log.d("Tag", it1.toString())}

                    }
                }
                Status.LOADING -> {
                    /*mProgressBar!!.visibility = View.VISIBLE
                    mNowShowingLayout!!.visibility = View.GONE*/
                }
                Status.ERROR -> {
                    /*mProgressBar!!.visibility = View.GONE
                    mNowShowingLayout!!.visibility = View.VISIBLE*/
                    Snackbar.make(findViewById(R.id.main_activity_fragment_container), "Something went wrong",Snackbar.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun setGenres(genresList: List<Genre>) {
        var genres = ""
        if (genresList != null) {
            for (i in genresList.indices) {
                if (genresList[i] == null) continue
                genres = if (i == genresList.size - 1) {
                    genres + genresList[i].genreName
                } else {
                    genres + genresList[i].genreName.toString() + ", "
                }
            }
        }
        mGenreTextView!!.text = genres
    }

    private fun setYear(releaseDateString: String?) {
        if (releaseDateString != null && !releaseDateString.trim { it <= ' ' }.isEmpty()) {
            val sdf1 = SimpleDateFormat("yyyy-MM-dd")
            val sdf2 = SimpleDateFormat("yyyy")
            try {
                val releaseDate = sdf1.parse(releaseDateString)
                mYearTextView!!.text = sdf2.format(releaseDate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        } else {
            mYearTextView!!.text = ""
        }
    }

    private fun setImageButtons(
        movieId: Int?,
        posterPath: String,
        movieTitle: String?,
        movieTagline: String?,
        imdbId: String?,
        homepage: String?
    ) {
        if (movieId == null) return
        if (isMovieFav(this@MovieDetailActivity, movieId)) {
            mFavImageButton!!.tag = Constants.TAG_FAV
            mFavImageButton!!.setImageResource(R.mipmap.ic_favorite_white_24dp)
        } else {
            mFavImageButton!!.tag = Constants.TAG_NOT_FAV
            mFavImageButton!!.setImageResource(R.mipmap.ic_favorite_border_white_24dp)
        }
        mFavImageButton!!.setOnClickListener { view ->
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            if (isMovieFav(this@MovieDetailActivity, movieId)) {
                removeMovieFromFav(this@MovieDetailActivity, movieId)
                mFavImageButton!!.setImageResource(R.mipmap.ic_favorite_border_white_24dp)
            } else {
                addMovieToFav(this@MovieDetailActivity, movieId, posterPath, movieTitle)
                mFavImageButton!!.setImageResource(R.mipmap.ic_favorite_white_24dp)
            }
        }
        mShareImageButton!!.setOnClickListener { view ->
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            val movieShareIntent = Intent(Intent.ACTION_SEND)
            movieShareIntent.type = "text/plain"
            var extraText: String? = ""
            if (movieTitle != null) extraText += """
     $movieTitle
     
     """.trimIndent()
            if (movieTagline != null) extraText += """
     $movieTagline
     
     """.trimIndent()
            if (imdbId != null) extraText += Constants.IMDB_BASE_URL.toString() + imdbId + "\n"
            if (homepage != null) extraText += homepage
            movieShareIntent.putExtra(Intent.EXTRA_TEXT, extraText)
            startActivity(movieShareIntent)
        }
    }

    private fun setDetails(releaseString: String?, runtime: Int?) {
        var detailsString = ""
        if (releaseString != null && !releaseString.trim { it <= ' ' }.isEmpty()) {
            val sdf1 = SimpleDateFormat("yyyy-MM-dd")
            val sdf2 = SimpleDateFormat("MMM d, yyyy")
            try {
                val releaseDate = sdf1.parse(releaseString)
                detailsString += """
                    ${sdf2.format(releaseDate)}
                    
                    """.trimIndent()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        } else {
            detailsString = "-\n"
        }
        detailsString += if (runtime != null && runtime != 0) {
            if (runtime < 60) {
                "$runtime min(s)"
            } else {
                (runtime / 60).toString() + " hr " + runtime % 60 + " mins"
            }
        } else {
            "-"
        }
        mDetailsTextView!!.text = detailsString
    }

    private fun setTrailers() {
        viewModel.getMovieVideos(mMovieId)
        viewModel.resMovieVideos.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    it.data.let {res->
                        res?.videos?.let { it1 ->
                            for (video in it1) {
                                if (video != null && video.site != null && video.site
                                        .equals("YouTube") && video.type != null && video.type
                                        .equals("Trailer")) {
                                         mTrailerAdapter.submitList(it1)
                                        Log.d("Trailer", it1.toString())

                                }
                                if (!it1.isEmpty()) mTrailerTextView!!.visibility = View.VISIBLE
                                }
                            }
                    }
                }
                Status.LOADING -> {
                    mTrailerTextView!!.visibility = View.GONE
                }
                Status.ERROR -> {
                    mTrailerTextView!!.visibility = View.VISIBLE
                    Snackbar.make(findViewById(R.id.main_activity_fragment_container), "Something went wrong",Snackbar.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun setCasts() {
        viewModel.getMovieCredits(mMovieId)
        viewModel.resMovieCredidts.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {

                    it.data.let {res->
                        res?.casts.let { it1 ->
                            if (it1 != null) {
                                for (castBrief in it1) {
                                    if (castBrief != null && castBrief.name != null && castBrief.profilePath != null)
                                        mCastAdapter.submitList(it1)
                                }
                                if (!it1.isEmpty()) mCastTextView!!.visibility = View.VISIBLE
                            }

                        }

                    }
                }
                Status.LOADING -> {
                    mCastTextView!!.visibility = View.GONE
                }
                Status.ERROR -> {
                    mSimilarMoviesTextView!!.visibility = View.VISIBLE
                    Snackbar.make(findViewById(R.id.main_activity_fragment_container), "Something went wrong",Snackbar.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun setSimilarMovies() {
        viewModel.getSimilarMovies(mMovieId)
        viewModel.resSimilarMovies.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {

                    it.data.let {res->
                        res?.results.let { it1 ->
                            if (it1 != null) {
                                for (movieBrief in it1) {
                                    if (movieBrief != null && movieBrief.title != null && movieBrief.posterPath != null && !movieBrief.posterPath.isEmpty())
                                        mSimilarMoviesAdapter.submitList(it1)
                                }
                                if (!it1.isEmpty()) mSimilarMoviesTextView!!.visibility = View.VISIBLE
                            }

                        }

                    }
                }
                Status.LOADING -> {
                    mSimilarMoviesTextView!!.visibility = View.GONE
                }
                Status.ERROR -> {
                    mSimilarMoviesTextView!!.visibility = View.GONE
                    Snackbar.make(findViewById(R.id.main_activity_fragment_container), "Something went wrong",Snackbar.LENGTH_SHORT).show()
                }
            }
        })

    }
}