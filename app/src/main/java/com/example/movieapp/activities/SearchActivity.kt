package com.example.movieapp.activities


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.Network.ApiService
import com.example.movieapp.R
import com.example.movieapp.adapters.SearchResultsAdapter
import com.example.movieapp.databinding.ActivityMovieDetailBinding
import com.example.movieapp.databinding.ActivitySearchBinding
import com.example.movieapp.search.SearchResponse
import com.example.movieapp.search.SearchResult
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.Status
import com.example.movieapp.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    lateinit var mQuery: String
    lateinit var binding : ActivitySearchBinding
    lateinit var viewModel: MainViewModel
    //    private SmoothProgressBar mSmoothProgressBar;
    lateinit var mSearchResultsRecyclerView: RecyclerView
    lateinit var mSearchResults: ArrayList<SearchResult>
    lateinit var mSearchResultsAdapter: SearchResultsAdapter
    lateinit var mProgressBar:ProgressBar

    lateinit var mEmptyTextView: TextView
    lateinit var mSearchResultCall: Call<SearchResponse>
    private var pagesOver = false
    private var presentPage = 1
    private var loading = true
    private var previousTotal = 0
    private val visibleThreshold = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val receivedIntent = intent
        mQuery = receivedIntent.getStringExtra(Constants.QUERY).toString()

        if (mQuery == null || mQuery.trim().isEmpty()) finish()

        setTitle(mQuery)

        mEmptyTextView = findViewById<View>(R.id.text_view_empty_search) as TextView

        mSearchResultsRecyclerView = findViewById<View>(R.id.recycler_view_search) as RecyclerView
        mSearchResults = ArrayList()
        mSearchResultsAdapter = SearchResultsAdapter(this@SearchActivity)
        mSearchResultsRecyclerView.setAdapter(mSearchResultsAdapter)
        val linearLayoutManager =
            LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
        mSearchResultsRecyclerView.setLayoutManager(linearLayoutManager)
        mProgressBar = findViewById(R.id.progressBar)

        /*mSearchResultsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition()
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false
                        previousTotal = totalItemCount
                    }
                }
                if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
                    loadSearchResults()
                    loading = true
                }
            }
        })*/

        loadSearchResults()

    }
    private fun loadSearchResults(){
        viewModel.getSearchMovies(mQuery,presentPage)
        viewModel.resSearchMovies.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    mProgressBar!!.visibility = View.GONE
                    it.data?.let { res ->
                        if (res.page == res.totalPages)
                            pagesOver = true;
                        else
                            presentPage++;

                        res?.results?.let { it1 ->

                            for (searchResult in it1) {
                                if (searchResult != null)
                                    mSearchResultsAdapter.submitList(it1)
                                    Log.d("Tagg", it1.toString())


                            }
                            if (it1.isEmpty()) mEmptyTextView.setVisibility(View.VISIBLE);


                        }
                    }
                }
                Status.LOADING -> {
                    mProgressBar!!.visibility = View.VISIBLE
                    mEmptyTextView.setVisibility(View.GONE);
                }
                Status.ERROR -> {
                    mProgressBar!!.visibility = View.GONE
                    mEmptyTextView.setVisibility(View.VISIBLE);
                    Snackbar.make(findViewById(R.id.main_activity_fragment_container), "Something went wrong",
                        Snackbar.LENGTH_SHORT).show()
                }
            }
        })
        /*val apiService: ApiService =
            ApiClient.getClient()!!.create<ApiService>(ApiService::class.java)

                mSearchResultCall = apiService.getSearchResponse(
                    resources.getString(com.example.movieapp.R.string.MOVIE_DB_API_KEY),
                    mQuery,
                    presentPage
                )
                mSearchResultCall.enqueue(object : Callback<SearchResponse> {
                    override fun onResponse(
                        call: Call<SearchResponse>,
                        response: Response<SearchResponse>
                    ) {
                        if (!response.isSuccessful()) {
                            mSearchResultCall = call.clone()
                            mSearchResultCall.enqueue(this)
                            return
                        }

                        if (response.body() == null) return
                        if (response.body()!!.results == null) return

//                        mSmoothProgressBar.progressiveStop();
                        for (searchResult in response.body()!!.results) {
                            if (searchResult != null)
                                mSearchResults.add(searchResult)
                        }
                        mSearchResultsAdapter.notifyDataSetChanged();
                        if (mSearchResults.isEmpty()) mEmptyTextView.setVisibility(View.VISIBLE);
                        if (response.body()!!.page == response.body()!!.totalPages)
                            pagesOver = true;
                        else
                            presentPage++;
                    }

                    override fun onFailure(call: Call<SearchResponse>, t: Throwable?) {}
                })*/
            }

}