package com.example.movieapp.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.Network.ApiService
import com.example.movieapp.Network.tvshows.*
import com.example.movieapp.R
import com.example.movieapp.adapters.TVShowBriefsSmallAdapter
import com.example.movieapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class ViewAllTVShowsActivity : AppCompatActivity() {
//    private SmoothProgressBar mSmoothProgressBar;

    //    private SmoothProgressBar mSmoothProgressBar;
    lateinit var mRecyclerView: RecyclerView
    lateinit var mTVShows: MutableList<TVShowBrief>
    lateinit var mTVShowsAdapter: TVShowBriefsSmallAdapter

    private var mTVShowType = 0

    private var pagesOver = false
    private var presentPage = 1
    private var loading = true
    private var previousTotal = 0
    private val visibleThreshold = 5

    lateinit var mAiringTodayTVShowsCall: Call<AiringTodayTVShowsResponse>
    lateinit var mOnTheAirTVShowsCall: Call<OnTheAirTVShowsResponse>
    lateinit var mPopularTVShowsCall: Call<PopularTVShowsResponse>
    lateinit var mTopRatedTVShowsCall: Call<TopRatedTVShowsResponse>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.movieapp.R.layout.activity_view_all_tvshows)
        val toolbar: Toolbar = findViewById<View>(com.example.movieapp.R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val receivedIntent = intent
        mTVShowType = receivedIntent.getIntExtra(Constants.VIEW_ALL_TV_SHOWS_TYPE, -1)
        if (mTVShowType == -1) finish()
        when (mTVShowType) {
            Constants.AIRING_TODAY_TV_SHOWS_TYPE -> setTitle(R.string.airing_today_tv_shows)
            Constants.ON_THE_AIR_TV_SHOWS_TYPE -> setTitle(R.string.on_the_air_tv_shows)
            Constants.POPULAR_TV_SHOWS_TYPE -> setTitle(R.string.popular_tv_shows)
            Constants.TOP_RATED_TV_SHOWS_TYPE -> setTitle(R.string.top_rated_tv_shows)
        }

//        mSmoothProgressBar = (SmoothProgressBar) findViewById(R.id.smooth_progress_bar);
        mRecyclerView = findViewById<View>(R.id.recycler_view_view_all) as RecyclerView
        mTVShows = ArrayList()
        mTVShowsAdapter = TVShowBriefsSmallAdapter(this@ViewAllTVShowsActivity)
        mRecyclerView!!.adapter = mTVShowsAdapter
        val gridLayoutManager = GridLayoutManager(this@ViewAllTVShowsActivity, 3)
        mRecyclerView!!.layoutManager = gridLayoutManager

        // hitanshu : use Paging Library
       /* mRecyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                    loadTVShows(mTVShowType)
                    loading = true
                }
            }
        })*/
        loadTVShows(mTVShowType)
    }

    override fun onStart() {
        super.onStart()
        mTVShowsAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mAiringTodayTVShowsCall != null) mAiringTodayTVShowsCall.cancel()
        if (mOnTheAirTVShowsCall != null) mOnTheAirTVShowsCall.cancel()
        if (mPopularTVShowsCall != null) mPopularTVShowsCall.cancel()
        if (mTopRatedTVShowsCall != null) mTopRatedTVShowsCall.cancel()
    }

    private fun loadTVShows(tvShowType: Int) {
        if (pagesOver) return
        /*val apiService = getClient()!!.create(ApiService::class.java)
        when (tvShowType) {
            Constants.AIRING_TODAY_TV_SHOWS_TYPE -> {
                mAiringTodayTVShowsCall = apiService.getAiringTodayTVShows(
                    resources.getString(R.string.MOVIE_DB_API_KEY),
                    presentPage
                )
                mAiringTodayTVShowsCall.enqueue(object : Callback<AiringTodayTVShowsResponse> {
                    override fun onResponse(
                        call: Call<AiringTodayTVShowsResponse>,
                        response: Response<AiringTodayTVShowsResponse>
                    ) {
                        if (!response.isSuccessful()) {
                            mAiringTodayTVShowsCall = call.clone()
                            mAiringTodayTVShowsCall.enqueue(this)
                            return
                        }
                        if (response.body() == null) return
                        if (response.body()!!.results == null) return

//                        mSmoothProgressBar.progressiveStop();
                        for (tvShowBrief in response.body()!!.results) {
                            if (tvShowBrief != null && tvShowBrief.name != null && tvShowBrief.posterPath != null) mTVShows!!.add(
                                tvShowBrief
                            )
                        }
                        mTVShowsAdapter.notifyDataSetChanged()
                        if (response.body()!!.page === response.body()!!
                                .totalPages
                        ) pagesOver = true else presentPage++
                    }

                    override fun onFailure(call: Call<AiringTodayTVShowsResponse>, t: Throwable?) {}
                })
            }
            Constants.ON_THE_AIR_TV_SHOWS_TYPE -> {
                mOnTheAirTVShowsCall = apiService.getOnTheAirTVShows(
                    resources.getString(R.string.MOVIE_DB_API_KEY),
                    presentPage
                )
                mOnTheAirTVShowsCall.enqueue(object : Callback<OnTheAirTVShowsResponse> {
                    override fun onResponse(
                        call: Call<OnTheAirTVShowsResponse>,
                        response: Response<OnTheAirTVShowsResponse>
                    ) {
                        if (!response.isSuccessful()) {
                            mOnTheAirTVShowsCall = call.clone()
                            mOnTheAirTVShowsCall.enqueue(this)
                            return
                        }
                        if (response.body() == null) return
                        if (response.body()!!.results == null) return

//                        mSmoothProgressBar.progressiveStop();
                        for (tvShowBrief in response.body()!!.results) {
                            if (tvShowBrief != null && tvShowBrief.name != null && tvShowBrief.posterPath != null) mTVShows!!.add(
                                tvShowBrief
                            )
                        }
                        mTVShowsAdapter.notifyDataSetChanged()
                        if (response.body()!!.page === response.body()!!
                                .totalPages
                        ) pagesOver = true else presentPage++
                    }

                    override fun onFailure(call: Call<OnTheAirTVShowsResponse>, t: Throwable?) {}
                })
            }
            Constants.POPULAR_TV_SHOWS_TYPE -> {
                mPopularTVShowsCall = apiService.getPopularTVShows(
                    resources.getString(R.string.MOVIE_DB_API_KEY),
                    presentPage
                )
                mPopularTVShowsCall.enqueue(object : Callback<PopularTVShowsResponse> {
                    override fun onResponse(
                        call: Call<PopularTVShowsResponse>,
                        response: Response<PopularTVShowsResponse>
                    ) {
                        if (!response.isSuccessful()) {
                            mPopularTVShowsCall = call.clone()
                            mPopularTVShowsCall.enqueue(this)
                            return
                        }
                        if (response.body() == null) return
                        if (response.body()!!.results == null) return

//                        mSmoothProgressBar.progressiveStop();
                        for (tvShowBrief in response.body()!!.results) {
                            if (tvShowBrief != null && tvShowBrief.name != null && tvShowBrief.posterPath != null) mTVShows!!.add(
                                tvShowBrief
                            )
                        }
                        mTVShowsAdapter.notifyDataSetChanged()
                        if (response.body()!!.page === response.body()!!
                                .totalPages
                        ) pagesOver = true else presentPage++
                    }

                    override fun onFailure(call: Call<PopularTVShowsResponse>, t: Throwable?) {}
                })
            }
            Constants.TOP_RATED_TV_SHOWS_TYPE -> {
                mTopRatedTVShowsCall = apiService.getTopRatedTVShows(
                    resources.getString(R.string.MOVIE_DB_API_KEY),
                    presentPage
                )
                mTopRatedTVShowsCall.enqueue(object : Callback<TopRatedTVShowsResponse> {
                    override fun onResponse(
                        call: Call<TopRatedTVShowsResponse>,
                        response: Response<TopRatedTVShowsResponse>
                    ) {
                        if (!response.isSuccessful()) {
                            mTopRatedTVShowsCall = call.clone()
                            mTopRatedTVShowsCall.enqueue(this)
                            return
                        }
                        if (response.body() == null) return
                        if (response.body()!!.results == null) return

//                        mSmoothProgressBar.progressiveStop();
                        for (tvShowBrief in response.body()!!.results) {
                            if (tvShowBrief != null && tvShowBrief.name != null && tvShowBrief.posterPath != null) mTVShows!!.add(
                                tvShowBrief
                            )
                        }
                        mTVShowsAdapter.notifyDataSetChanged()
                        if (response.body()!!.page === response.body()!!
                                .totalPages
                        ) pagesOver = true else presentPage++
                    }

                    override fun onFailure(call: Call<TopRatedTVShowsResponse>, t: Throwable?) {}
                })
            }
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() === android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}