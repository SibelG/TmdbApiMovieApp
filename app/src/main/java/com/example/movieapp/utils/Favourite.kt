package com.example.movieapp.utils

import DatabaseHelper
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.movieapp.Network.Movies.MovieBrief
import com.example.movieapp.Network.tvshows.TVShowBrief
import java.util.*


object Favourite {
    fun addMovieToFav(context: Context?, movieId: Int?, posterPath: String?, name: String?) {
        if (movieId == null) return
        val databaseHelper = DatabaseHelper(context)
        val database: SQLiteDatabase = databaseHelper.getWritableDatabase()
        if (!isMovieFav(context, movieId)) {
            val contentValues = ContentValues()
            contentValues.put(DatabaseHelper.MOVIE_ID, movieId)
            contentValues.put(DatabaseHelper.POSTER_PATH, posterPath)
            contentValues.put(DatabaseHelper.NAME, name)
            database.insert(DatabaseHelper.FAV_MOVIES_TABLE_NAME, null, contentValues)
        }
        database.close()
    }
    fun removeMovieFromFav(context: Context?, movieId: Int?) {
        if (movieId == null) return
        val databaseHelper = DatabaseHelper(context)
        val database: SQLiteDatabase = databaseHelper.getWritableDatabase()
        if (isMovieFav(context, movieId)) {
            database.delete(
                DatabaseHelper.FAV_MOVIES_TABLE_NAME,
                DatabaseHelper.MOVIE_ID.toString() + " = " + movieId,
                null
            )
        }
        database.close()
    }
    fun isMovieFav(context: Context?, movieId: Int?): Boolean {
        if (movieId == null) return false
        val databaseHelper = DatabaseHelper(context)
        val database = databaseHelper.readableDatabase
        val isMovieFav: Boolean
        val cursor: Cursor = database.query(
            DatabaseHelper.FAV_MOVIES_TABLE_NAME,
            null,
            DatabaseHelper.MOVIE_ID + " = " + movieId,
            null,
            null,
            null,
            null
        )
        isMovieFav = if (cursor.getCount() === 1) true else false
        cursor.close()
        database.close()
        return isMovieFav
    }

    fun getFavMovieBriefs(context: Context?): List<MovieBrief>? {
        val databaseHelper = DatabaseHelper(context)
        val database = databaseHelper.readableDatabase
        val favMovies: MutableList<MovieBrief> = ArrayList()
        val cursor: Cursor = database.query(
            DatabaseHelper.FAV_MOVIES_TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            DatabaseHelper.ID + " DESC"
        )
        if (cursor.moveToFirst()) {
            do {
                val model = MovieBrief()
                model.id= cursor.getInt(1)
                model.posterPath = cursor.getString(2)
                model.title = cursor.getString(3)

                favMovies.add(model)
            } while (cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return favMovies
    }

    fun addTVShowToFav(context: Context?, tvShowId: Int?, posterPath: String?, name: String?) {
        if (tvShowId == null) return
        val databaseHelper = DatabaseHelper(context)
        val database = databaseHelper.writableDatabase
        if (!isTVShowFav(context, tvShowId)) {
            val contentValues = ContentValues()
            contentValues.put(DatabaseHelper.TV_SHOW_ID, tvShowId)
            contentValues.put(DatabaseHelper.POSTER_PATH, posterPath)
            contentValues.put(DatabaseHelper.NAME, name)
            database.insert(DatabaseHelper.FAV_TV_SHOWS_TABLE_NAME, null, contentValues)
        }
        database.close()
    }

    fun removeTVShowFromFav(context: Context?, tvShowId: Int?) {
        if (tvShowId == null) return
        val databaseHelper = DatabaseHelper(context)
        val database = databaseHelper.writableDatabase
        if (isTVShowFav(context, tvShowId)) {
            database.delete(
                DatabaseHelper.FAV_TV_SHOWS_TABLE_NAME,
                DatabaseHelper.TV_SHOW_ID + " = " + tvShowId,
                null
            )
        }
        database.close()
    }

    fun isTVShowFav(context: Context?, tvShowId: Int?): Boolean {
        if (tvShowId == null) return false
        val databaseHelper = DatabaseHelper(context)
        val database = databaseHelper.readableDatabase
        val isTVShowFav: Boolean
        val cursor = database.query(
            DatabaseHelper.FAV_TV_SHOWS_TABLE_NAME,
            null,
            DatabaseHelper.TV_SHOW_ID + " = " + tvShowId,
            null,
            null,
            null,
            null
        )
        isTVShowFav = if (cursor.count == 1) true else false
        cursor.close()
        database.close()
        return isTVShowFav
    }

    @SuppressLint("Range")
    fun getFavTVShowBriefs(context: Context?): List<TVShowBrief>? {
        val databaseHelper = DatabaseHelper(context)
        val database = databaseHelper.readableDatabase
        val favTVShows: MutableList<TVShowBrief> = ArrayList()
        val cursor = database.query(
            DatabaseHelper.FAV_TV_SHOWS_TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            DatabaseHelper.ID + " DESC"
        )
        if (cursor.moveToFirst()) {
            do {
                val model = TVShowBrief()
                model.id = cursor.getInt(1)
                model.posterPath = cursor.getString(2)
                model.name = cursor.getString(3)

                favTVShows.add(model)
            } while (cursor.moveToNext())
        }
        cursor.close()
        database.close()
        return favTVShows
    }

}