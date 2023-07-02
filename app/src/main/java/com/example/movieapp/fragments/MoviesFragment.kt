package com.example.movieapp.fragments


import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.Network.Movies.*
import com.example.movieapp.R
import com.example.movieapp.activities.ViewAllMoviesActivity
import com.example.movieapp.adapters.MovieBriefsLargeAdapter
import com.example.movieapp.adapters.MovieBriefsSmallAdapter
import com.example.movieapp.databinding.FragmentMoviesBinding
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.NetworkHelper
import com.example.movieapp.utils.Status
import com.example.movieapp.view_model.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var networkHelper: NetworkHelper

    // Create a viewModel
    private val viewModel: MainViewModel by viewModels()

    lateinit var mProgressBar: ProgressBar

    lateinit var mNowShowingLayout: FrameLayout
    lateinit var mNowShowingViewAllTextView: TextView
    lateinit var mNowShowingRecyclerView: RecyclerView
    lateinit var mNowShowingMovies: List<MovieBrief>
    lateinit var mNowShowingAdapter: MovieBriefsLargeAdapter
    //private val mNowShowingAdapter by lazy{ MovieBriefsLargeAdapter(requireContext()) }
    lateinit var mPopularLayout: FrameLayout
    lateinit var mPopularViewAllTextView: TextView
    lateinit var mPopularRecyclerView: RecyclerView
    lateinit var mPopularMovies: List<MovieBrief>
    lateinit var mPopularAdapter: MovieBriefsSmallAdapter
    //private val mPopularAdapter by lazy{ MovieBriefsLargeAdapter(requireContext()) }
    lateinit var mUpcomingLayout: FrameLayout
    lateinit var mUpcomingViewAllTextView: TextView
    lateinit var mUpcomingRecyclerView: RecyclerView
    lateinit var mUpcomingMovies: List<MovieBrief>
    lateinit var mUpcomingAdapter: MovieBriefsLargeAdapter
    //private val mUpcomingAdapter by lazy{ MovieBriefsLargeAdapter(requireContext()) }
    lateinit var mTopRatedLayout: FrameLayout
    lateinit var mTopRatedViewAllTextView: TextView
    lateinit var mTopRatedRecyclerView: RecyclerView
    lateinit var mTopRatedMovies: List<MovieBrief>
    lateinit var mTopRatedAdapter: MovieBriefsSmallAdapter
    //private val mTopRatedAdapter by lazy{ MovieBriefsLargeAdapter(requireContext()) }

    lateinit var mConnectivitySnackbar: Snackbar
//    lateinit var mGenresListCall: Call<GenresList>


    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        val view = binding.root

        mProgressBar = view.findViewById<View>(R.id.progress_bar) as ProgressBar
        mProgressBar!!.visibility = View.GONE
        mNowShowingLayout = view.findViewById<View>(R.id.layout_now_showing) as FrameLayout
        mPopularLayout = view.findViewById<View>(R.id.layout_popular) as FrameLayout
        mUpcomingLayout = view.findViewById<View>(R.id.layout_upcoming) as FrameLayout
        mTopRatedLayout = view.findViewById<View>(R.id.layout_top_rated) as FrameLayout
        mNowShowingViewAllTextView =
            view.findViewById<View>(R.id.text_view_view_all_now_showing) as TextView
        mPopularViewAllTextView =
            view.findViewById<View>(R.id.text_view_view_all_popular) as TextView
        mUpcomingViewAllTextView =
            view.findViewById<View>(R.id.text_view_view_all_upcoming) as TextView
        mTopRatedViewAllTextView =
            view.findViewById<View>(R.id.text_view_view_all_top_rated) as TextView
        mNowShowingRecyclerView =
            view.findViewById<View>(R.id.recycler_view_now_showing) as RecyclerView
        LinearSnapHelper().attachToRecyclerView(mNowShowingRecyclerView)
        mPopularRecyclerView = view.findViewById<View>(R.id.recycler_view_popular) as RecyclerView
        mUpcomingRecyclerView = view.findViewById<View>(R.id.recycler_view_upcoming) as RecyclerView
        LinearSnapHelper().attachToRecyclerView(mUpcomingRecyclerView)
        mTopRatedRecyclerView =
            view.findViewById<View>(R.id.recycler_view_top_rated) as RecyclerView

        mNowShowingAdapter = MovieBriefsLargeAdapter(requireContext())
        mPopularAdapter = MovieBriefsSmallAdapter(requireContext()
        )
        mUpcomingAdapter = MovieBriefsLargeAdapter(requireContext())
        mTopRatedAdapter = MovieBriefsSmallAdapter(requireContext())
        mNowShowingRecyclerView!!.adapter = mNowShowingAdapter
        mNowShowingRecyclerView!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mPopularRecyclerView!!.adapter = mPopularAdapter
        mPopularRecyclerView!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mUpcomingRecyclerView!!.adapter = mUpcomingAdapter
        mUpcomingRecyclerView!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mTopRatedRecyclerView!!.adapter = mTopRatedAdapter
        mTopRatedRecyclerView!!.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mNowShowingViewAllTextView!!.setOnClickListener(View.OnClickListener {

            val intent = Intent(context, ViewAllMoviesActivity::class.java)
            intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.NOW_SHOWING_MOVIES_TYPE)
            startActivity(intent)
        })
        mPopularViewAllTextView!!.setOnClickListener(View.OnClickListener {

            val intent = Intent(context, ViewAllMoviesActivity::class.java)
            intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.POPULAR_MOVIES_TYPE)
            startActivity(intent)
        })
        mUpcomingViewAllTextView!!.setOnClickListener(View.OnClickListener {

            val intent = Intent(context, ViewAllMoviesActivity::class.java)
            intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.UPCOMING_MOVIES_TYPE)
            startActivity(intent)
        })
        mTopRatedViewAllTextView!!.setOnClickListener(View.OnClickListener {

            val intent = Intent(context, ViewAllMoviesActivity::class.java)
            intent.putExtra(Constants.VIEW_ALL_MOVIES_TYPE, Constants.TOP_RATED_MOVIES_TYPE)
            startActivity(intent)
        })
        if (networkHelper.isNetworkConnected()) {
            loadFragment()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        mNowShowingAdapter.notifyDataSetChanged()
        mPopularAdapter.notifyDataSetChanged()
        mUpcomingAdapter.notifyDataSetChanged()
        mTopRatedAdapter.notifyDataSetChanged()
    }

   override fun onResume() {
        super.onResume()
        if (!networkHelper.isNetworkConnected()) {
            mConnectivitySnackbar = Snackbar.make(
                requireActivity().findViewById(R.id.main_activity_fragment_container),
                R.string.no_network,
                Snackbar.LENGTH_INDEFINITE
            )
        } else  {
            loadFragment()
        }
    }

    override fun onPause() {
        super.onPause()
        /*if (isBroadcastReceiverRegistered) {
            mConnectivitySnackbar!!.dismiss()
            isBroadcastReceiverRegistered = false
            requireActivity().unregisterReceiver(mConnectivityBroadcastReceiver)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //if (mGenresListCall != null) mGenresListCall.cancel()
        _binding = null


    }

    private  fun loadFragment() {
        //if (MovieGenres.isGenresListLoaded()) {
            loadNowShowingMovies()
            loadPopularMovies()
            loadUpcomingMovies()
            loadTopRatedMovies()
        //}
        // else {
            /*val apiService = getClient()!!.create(ApiInterface::class.java)
            mProgressBar!!.visibility = View.VISIBLE
            mGenresListCall =
                apiService.getMovieGenresList(resources.getString(R.string.MOVIE_DB_API_KEY))
            mGenresListCall.enqueue(object : Callback<GenresList> {
                override fun onResponse(call: Call<GenresList>, response: Response<GenresList>) {
                    if (!response.isSuccessful()) {
                        mGenresListCall = call.clone()
                        mGenresListCall.enqueue(this)
                        return
                    }
                    if (response.body() == null) return
                    if (response.body()!!.genres == null) return
                    //MovieGenres.loadGenresList(response.body().getGenres())
                    loadNowShowingMovies()
                    loadPopularMovies()
                    loadUpcomingMovies()
                    loadTopRatedMovies()
                }

                override fun onFailure(call: Call<GenresList>, t: Throwable?) {}
            })*/

    }

    private fun loadNowShowingMovies() {
        viewModel.res.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    mProgressBar!!.visibility = View.GONE
                    mNowShowingLayout!!.visibility = View.VISIBLE
                    it.data.let {res->
                        res?.results?.let {
                                it1 -> mNowShowingAdapter.submitList(it1)
                                Log.d("Tag", it1.toString())}

                    }
                }
                Status.LOADING -> {
                    mProgressBar!!.visibility = View.VISIBLE
                    mNowShowingLayout!!.visibility = View.GONE
                }
                Status.ERROR -> {
                    mProgressBar!!.visibility = View.GONE
                    mNowShowingLayout!!.visibility = View.VISIBLE
                    Snackbar.make(requireActivity().findViewById(R.id.main_activity_fragment_container), "Something went wrong",Snackbar.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun loadPopularMovies() {
        viewModel.resPopular.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    mProgressBar!!.visibility = View.GONE
                    mPopularLayout!!.visibility = View.VISIBLE
                    it.data.let {res->
                        res?.results?.let {
                                it1 -> mPopularAdapter.submitList(it1)
                            Log.d("Tag", it1.toString())}

                    }
                }
                Status.LOADING -> {
                    mProgressBar!!.visibility = View.VISIBLE
                    mPopularLayout!!.visibility = View.GONE
                }
                Status.ERROR -> {
                    mProgressBar!!.visibility = View.GONE
                    mPopularLayout!!.visibility = View.VISIBLE
                    Snackbar.make(requireActivity().findViewById(R.id.main_activity_fragment_container), "Something went wrong",Snackbar.LENGTH_SHORT).show()
                }
            }
        })


    }

    private fun loadUpcomingMovies() {
        viewModel.resUpcoming.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    mProgressBar!!.visibility = View.GONE
                    mUpcomingLayout!!.visibility = View.VISIBLE
                    it.data.let {res->
                        res?.results?.let {
                                it1 -> mUpcomingAdapter.submitList(it1)
                            Log.d("Tag", it1.toString())}

                    }
                }
                Status.LOADING -> {
                    mProgressBar!!.visibility = View.VISIBLE
                    mUpcomingLayout!!.visibility = View.GONE
                }
                Status.ERROR -> {
                    mProgressBar!!.visibility = View.GONE
                    mUpcomingLayout!!.visibility = View.VISIBLE
                    Snackbar.make(requireActivity().findViewById(R.id.main_activity_fragment_container), "Something went wrong",Snackbar.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun loadTopRatedMovies() {
        viewModel.resTopRated.observe(this, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    mProgressBar!!.visibility = View.GONE
                    mTopRatedLayout!!.visibility = View.VISIBLE
                    it.data.let {res->
                        res?.results?.let {
                                it1 -> mTopRatedAdapter.submitList(it1)
                            Log.d("Tag", it1.toString())}

                    }
                }
                Status.LOADING -> {
                    mProgressBar!!.visibility = View.VISIBLE
                    mTopRatedLayout!!.visibility = View.GONE
                }
                Status.ERROR -> {
                    mProgressBar!!.visibility = View.GONE
                    mTopRatedLayout!!.visibility = View.VISIBLE
                    Snackbar.make(requireActivity().findViewById(R.id.main_activity_fragment_container), "Something went wrong",Snackbar.LENGTH_SHORT).show()
                }
            }
        })

    }

}