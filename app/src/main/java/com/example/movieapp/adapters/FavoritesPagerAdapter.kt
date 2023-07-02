package com.example.movieapp.adapters



import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.movieapp.R
import com.example.movieapp.fragments.FavoriteMoviesFragment
import com.example.movieapp.fragments.FavoriteTVShowsFragment


class FavoritesPagerAdapter(fm: FragmentManager, private val mContext: Context) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FavoriteMoviesFragment()
            }
            else -> {
                FavoriteTVShowsFragment()
            }
        }
    }


    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return mContext.resources.getString(R.string.movies)
            1 -> return mContext.resources.getString(R.string.tv_shows)
        }
        return null
    }
}