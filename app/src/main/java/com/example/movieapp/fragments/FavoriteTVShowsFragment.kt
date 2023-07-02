package com.example.movieapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.Network.tvshows.TVShowBrief
import com.example.movieapp.R
import com.example.movieapp.adapters.TVShowBriefsSmallAdapter
import com.example.movieapp.utils.Favourite.getFavTVShowBriefs


class FavoriteTVShowsFragment : Fragment() {

    lateinit var mFavTVShowsRecyclerView: RecyclerView
    lateinit var mFavTVShows: MutableList<TVShowBrief>
    lateinit var mFavTVShowsAdapter: TVShowBriefsSmallAdapter

    lateinit var mEmptyLayout: LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(com.example.movieapp.R.layout.fragment_favorite_tv_shows, container, false)
        mFavTVShowsRecyclerView =
            view.findViewById<View>(com.example.movieapp.R.id.recycler_view_fav_tv_shows) as RecyclerView
        mFavTVShows = ArrayList()
        mFavTVShowsAdapter = TVShowBriefsSmallAdapter(context!!)
        mFavTVShowsRecyclerView!!.adapter = mFavTVShowsAdapter
        mFavTVShowsRecyclerView!!.layoutManager = GridLayoutManager(
            context, 3
        )
        mEmptyLayout =
            view.findViewById<View>(com.example.movieapp.R.id.layout_recycler_view_fav_tv_shows_empty) as LinearLayout
        loadFavTVShows()
        return view
    }

    override fun onStart() {
        super.onStart()
        mFavTVShowsAdapter!!.notifyDataSetChanged()
    }

    private fun loadFavTVShows() {
        val favTVShowBriefs = getFavTVShowBriefs(
            context
        )
        if (favTVShowBriefs!!.isEmpty()) {
            mEmptyLayout!!.visibility = View.VISIBLE
            return
        }
        for (tvShowBrief in favTVShowBriefs) {
            mFavTVShows!!.add(tvShowBrief)
        }
        mFavTVShowsAdapter.submitList(mFavTVShows)
    }

}