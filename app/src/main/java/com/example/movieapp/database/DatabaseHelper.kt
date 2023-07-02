
import android.content.Context
import android.database.sqlite.SQLiteDatabase

import android.database.sqlite.SQLiteOpenHelper
import javax.inject.Inject
import javax.inject.Singleton


class DatabaseHelper @Inject constructor(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        val queryCreateMovieTable = ("CREATE TABLE " + FAV_MOVIES_TABLE_NAME + " ( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + MOVIE_ID + " INTEGER, "
                + POSTER_PATH + " TEXT, "
                + NAME + " TEXT )")
        val queryCreateTVShowTable = ("CREATE TABLE " + FAV_TV_SHOWS_TABLE_NAME + " ( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TV_SHOW_ID + " INTEGER, "
                + POSTER_PATH + " TEXT, "
                + NAME + " TEXT )")
        sqLiteDatabase.execSQL(queryCreateMovieTable)
        sqLiteDatabase.execSQL(queryCreateTVShowTable)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {}

    companion object {
        private const val DATABASE_NAME = "database.db"
        const val FAV_MOVIES_TABLE_NAME = "FavouriteMoviesTable"
        const val FAV_TV_SHOWS_TABLE_NAME = "FavouriteTVShowsTable"
        const val ID = "id"
        const val MOVIE_ID = "movie_id"
        const val TV_SHOW_ID = "tv_show_id"
        const val POSTER_PATH = "poster_path"
        const val NAME = "name"
    }
}