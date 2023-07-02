package com.example.movieapp.activities


import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.Network.ApiService
import com.example.movieapp.Network.Movies.*
import com.example.movieapp.R
import com.example.movieapp.adapters.MovieBriefsSmallAdapter
import com.example.movieapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class ViewAllMoviesActivity : AppCompatActivity() {

    lateinit var  mRecyclerView: RecyclerView
    lateinit var  mMovies: ArrayList<MovieBrief>
    lateinit var  mMoviesAdapter: MovieBriefsSmallAdapter

    private var mMovieType :Int = 0

    private var pagesOver = false
    private var presentPage = 1
    private var loading = true
    private var previousTotal = 0
    private val visibleThreshold = 5

    lateinit var mNowShowingMoviesCall: Call<NowShowingMoviesResponse>
    lateinit var mPopularMoviesCall: Call<PopularMoviesResponse>
    lateinit var mUpcomingMoviesCall: Call<UpcomingMoviesResponse>
    lateinit var mTopRatedMoviesCall: Call<TopRatedMoviesResponse>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.movieapp.R.layout.activity_view_all_movies)
        val toolbar: Toolbar = findViewById<View>(com.example.movieapp.R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val receivedIntent = intent
        mMovieType = receivedIntent.getIntExtra(Constants.VIEW_ALL_MOVIES_TYPE, -1)

        if (mMovieType === -1) finish()

        when (mMovieType) {
            Constants.NOW_SHOWING_MOVIES_TYPE -> setTitle(com.example.movieapp.R.string.now_showing_movies)
            Constants.POPULAR_MOVIES_TYPE -> setTitle(com.example.movieapp.R.string.popular_movies)
            Constants.UPCOMING_MOVIES_TYPE -> setTitle(com.example.movieapp.R.string.upcoming_movies)
            Constants.TOP_RATED_MOVIES_TYPE -> setTitle(com.example.movieapp.R.string.top_rated_movies)
        }
        mRecyclerView = findViewById<View>(R.id.recycler_view_view_all) as RecyclerView
        mMovies = ArrayList()
        mMoviesAdapter = MovieBriefsSmallAdapter(this@ViewAllMoviesActivity)
        mRecyclerView.adapter = mMoviesAdapter
        val gridLayoutManager = GridLayoutManager(this@ViewAllMoviesActivity, 3)
        mRecyclerView.layoutManager = gridLayoutManager

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = gridLayoutManager.childCount
                val totalItemCount = gridLayoutManager.itemCount
                val firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition()
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }
                if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
                    loadMovies(mMovieType)
                    loading = true
                }
            }
        })
        loadMovies(mMovieType)
    }
    override fun onStart() {
        super.onStart()
        mMoviesAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        mNowShowingMoviesCall.cancel()
        mPopularMoviesCall.cancel()
        mUpcomingMoviesCall.cancel()
        mTopRatedMoviesCall.cancel()
    }
    private fun loadMovies(movieType: Int) {
        if (pagesOver) return
        /*val apiService: ApiService =
            ApiClient.getClient()!!.create<ApiService>(ApiService::class.java)
        when (movieType) {
            Constants.NOW_SHOWING_MOVIES_TYPE -> {
                mNowShowingMoviesCall = apiService.getNowShowingMovies(
                    resources.getString(com.example.movieapp.R.string.MOVIE_DB_API_KEY),
                    "en-US",
                    presentPage
                )
                mNowShowingMoviesCall.enqueue(object : Callback<NowShowingMoviesResponse> {
                    override fun onResponse(
                        call: Call<NowShowingMoviesResponse>,
                        response: Response<NowShowingMoviesResponse>
                    ) {
                        if (!response.isSuccessful()) {
                            mNowShowingMoviesCall = call.clone()
                            mNowShowingMoviesCall.enqueue(this)
                            return
                        }

                        if (response.body() == null) return
                        if (response.body()!!.results == null) return

//                        mSmoothProgressBar.progressiveStop();
                        for (movieBrief in response.body()!!.results) {
                            if (movieBrief != null && movieBrief.title != null && movieBrief.posterPath != null)
                                mMovies.add(
                                movieBrief
                            )
                        }
                        mMoviesAdapter.notifyDataSetChanged()
                        if (response.body()!!.page === response.body()!!
                                .totalPages

                        ) pagesOver = true else presentPage++
                    }

                    override fun onFailure(call: Call<NowShowingMoviesResponse>, t: Throwable?) {}
                })
            }
            Constants.POPULAR_MOVIES_TYPE -> {
                mPopularMoviesCall = apiService.getPopularMovies(
                    resources.getString(com.example.movieapp.R.string.MOVIE_DB_API_KEY),
                    "en-US",
                    presentPage
                )
                mPopularMoviesCall.enqueue(object : Callback<PopularMoviesResponse> {
                    override fun onResponse(
                        call: Call<PopularMoviesResponse>,
                        response: Response<PopularMoviesResponse>
                    ) {
                        if (!response.isSuccessful()) {
                            mPopularMoviesCall = call.clone()
                            mPopularMoviesCall.enqueue(this)
                            return
                        }
                        if (response.body() == null) return
                        if (response.body()!!.results == null) return

//                        mSmoothProgressBar.progressiveStop();
                        for (movieBrief in response.body()!!.results) {
                            if (movieBrief != null && movieBrief.title != null && movieBrief.posterPath != null)
                                mMovies.add(
                                movieBrief
                            )
                        }
                        mMoviesAdapter.notifyDataSetChanged()
                        if (response.body()!!.page === response.body()!!
                                .totalPages
                        ) pagesOver = true else presentPage++
                    }

                    override fun onFailure(call: Call<PopularMoviesResponse>, t: Throwable?) {}
                })
            }
            Constants.UPCOMING_MOVIES_TYPE -> {
                mUpcomingMoviesCall = apiService.getUpcomingMovies(
                    resources.getString(com.example.movieapp.R.string.MOVIE_DB_API_KEY),
                    "en-US",
                    presentPage
                )
                mUpcomingMoviesCall.enqueue(object : Callback<UpcomingMoviesResponse> {
                    override fun onResponse(
                        call: Call<UpcomingMoviesResponse>,
                        response: Response<UpcomingMoviesResponse>
                    ) {
                        if (!response.isSuccessful()) {
                            mUpcomingMoviesCall = call.clone()
                            mUpcomingMoviesCall.enqueue(this)
                            return
                        }
                        if (response.body() == null) return
                        if (response.body()!!.results == null) return

//                        mSmoothProgressBar.progressiveStop();
                        for (movieBrief in response.body()!!.results) {
                            if (movieBrief != null && movieBrief.title != null && movieBrief.posterPath != null) mMovies.add(
                                movieBrief
                            )
                        }
                        mMoviesAdapter.notifyDataSetChanged()
                        if (response.body()!!.page === response.body()!!
                                .totalPages
                        ) pagesOver = true else presentPage++
                    }

                    override fun onFailure(call: Call<UpcomingMoviesResponse>, t: Throwable?) {}
                })
            }
            Constants.TOP_RATED_MOVIES_TYPE -> {
                mTopRatedMoviesCall = apiService.getTopRatedMovies(
                    resources.getString(com.example.movieapp.R.string.MOVIE_DB_API_KEY),
                    "en-US",
                    presentPage
                )
                mTopRatedMoviesCall!!.enqueue(object : Callback<TopRatedMoviesResponse> {
                    override fun onResponse(
                        call: Call<TopRatedMoviesResponse>,
                        response: Response<TopRatedMoviesResponse>
                    ) {
                        if (!response.isSuccessful()) {
                            mTopRatedMoviesCall = call.clone()
                            mTopRatedMoviesCall.enqueue(this)
                            return
                        }
                        if (response.body() == null) return
                        if (response.body()!!.results == null) return

//                        mSmoothProgressBar.progressiveStop();
                        for (movieBrief in response.body()!!.results) {
                            if (movieBrief != null && movieBrief.title != null && movieBrief.posterPath != null) mMovies.add(
                                movieBrief
                            )
                        }
                        mMoviesAdapter.notifyDataSetChanged()
                        if (response.body()!!.page === response.body()!!
                                .totalPages
                        ) pagesOver = true else presentPage++
                    }

                    override fun onFailure(call: Call<TopRatedMoviesResponse?>?, t: Throwable?) {}
                })
            }
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === androidx.appcompat.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}