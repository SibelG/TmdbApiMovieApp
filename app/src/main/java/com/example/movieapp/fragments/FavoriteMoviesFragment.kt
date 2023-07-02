package com.example.movieapp.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.Network.Movies.MovieBrief
import com.example.movieapp.R
import com.example.movieapp.adapters.MovieBriefsSmallAdapter
import com.example.movieapp.utils.Favourite.getFavMovieBriefs


class FavoriteMoviesFragment : Fragment() {

    lateinit var mFavMoviesRecyclerView: RecyclerView
    lateinit var mFavMovies: MutableList<MovieBrief>
    lateinit var mFavMoviesAdapter: MovieBriefsSmallAdapter

    lateinit var mEmptyLayout: LinearLayout

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_favorite_movies, container, false)
        mFavMoviesRecyclerView =
            view.findViewById<View>(R.id.recycler_view_fav_movies) as RecyclerView
        mFavMovies = ArrayList()
        mFavMoviesAdapter = MovieBriefsSmallAdapter(context!!)
        mFavMoviesRecyclerView!!.adapter = mFavMoviesAdapter
        mFavMoviesRecyclerView!!.layoutManager =
            GridLayoutManager(context, 3)
        mEmptyLayout =
            view.findViewById<View>(R.id.layout_recycler_view_fav_movies_empty) as LinearLayout
        loadFavMovies()
        return view
    }

    override fun onStart() {
        super.onStart()
        // TODO (feature or a bug? :P)
        // hitanshu : use Room with LiveData to solve this problem
        // for now when coming back to this activity after removing from fav it shows border heart.
        mFavMoviesAdapter!!.notifyDataSetChanged()
    }

    private fun loadFavMovies() {
        val favMovieBriefs = getFavMovieBriefs(context)
        if (favMovieBriefs!!.isEmpty()) {
            mEmptyLayout!!.visibility = View.VISIBLE
            return
        }
        for (movieBrief in favMovieBriefs) {
            mFavMovies!!.add(movieBrief)
        }
            mFavMoviesAdapter.submitList(mFavMovies!!)
    }
}