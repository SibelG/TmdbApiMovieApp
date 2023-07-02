package com.example.movieapp.activities


import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.movieapp.R
import com.example.movieapp.fragments.FavoritesFragment
import com.example.movieapp.fragments.MoviesFragment
import com.example.movieapp.fragments.TVShowsFragment
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.NetworkHelper
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mDrawer: DrawerLayout
    private lateinit var mNavigationView: NavigationView
    @Inject
    lateinit var  networkHelper:NetworkHelper

    private val doubleBackToExitPressedOnce = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
        if (sharedPreferences.getBoolean(Constants.FIRST_TIME_LAUNCH, true)) {
            startActivity(Intent(this@MainActivity, IntroActivity::class.java))
            val sharedPreferencesEditor = sharedPreferences.edit()
            sharedPreferencesEditor.putBoolean(Constants.FIRST_TIME_LAUNCH, false)
            sharedPreferencesEditor.apply()
        }

        val toolbar: Toolbar = findViewById<View>(com.example.movieapp.R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        mDrawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
            this,
            mDrawer,
            toolbar,
            com.example.movieapp.R.string.navigation_drawer_open,
            com.example.movieapp.R.string.navigation_drawer_close
        )
        mDrawer.setDrawerListener(toggle)
        toggle.syncState()

        mNavigationView = findViewById<View>(R.id.nav_view) as NavigationView
        mNavigationView.setNavigationItemSelectedListener(this@MainActivity)

        mNavigationView.setCheckedItem(R.id.nav_movies)
        setTitle(com.example.movieapp.R.string.movies)
        setFragment(MoviesFragment())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        mDrawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        mDrawer.closeDrawer(GravityCompat.START)

        when (id) {
            R.id.nav_movies -> {
                setTitle(R.string.movies)
                setFragment(MoviesFragment())
                return true
            }
            R.id.nav_tv_shows -> {
                setTitle(R.string.tv_shows)
                setFragment(TVShowsFragment())
                return true
            }
            R.id.nav_favorites -> {
                setTitle(R.string.favorites)
                setFragment(FavoritesFragment())
                return true
            }
            R.id.nav_about -> {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
                return false
            }
        }

        return false
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val searchMenuItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchMenuItem.actionView as SearchView
        searchView.setQueryHint(resources.getString(R.string.search_movies_tv_shows_people))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!networkHelper.isNetworkConnected()) {
                    Toast.makeText(this@MainActivity, R.string.no_network, Toast.LENGTH_SHORT)
                        .show()
                    return true
                }
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                intent.putExtra(Constants.QUERY, query)
                startActivity(intent)
                searchMenuItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_activity_fragment_container, fragment)
        fragmentTransaction.commit()
    }
}