package com.example.movieapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.movieapp.R
import com.example.movieapp.adapters.FavoritesPagerAdapter
import com.example.movieapp.databinding.FragmentFavoritesBinding
import com.example.movieapp.databinding.FragmentMoviesBinding
import com.ogaclejapan.smarttablayout.SmartTabLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoritesFragment : Fragment() {
    lateinit var mSmartTabLayout: SmartTabLayout
    lateinit var mViewPager: ViewPager
    lateinit var _binding: FragmentFavoritesBinding
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root

        mSmartTabLayout = view!!.findViewById(R.id.tab_view_pager_fav) as SmartTabLayout
        mViewPager = view!!.findViewById(R.id.view_pager_fav) as ViewPager
        mViewPager.setAdapter(FavoritesPagerAdapter(childFragmentManager, context!!))
        mSmartTabLayout.setViewPager(mViewPager)

        return view

    }

}