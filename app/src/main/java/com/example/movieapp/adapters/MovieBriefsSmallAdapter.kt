package com.example.movieapp.adapters

import android.content.Context
import android.content.Intent
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movieapp.Network.Movies.MovieBrief
import com.example.movieapp.R
import com.example.movieapp.activities.MovieDetailActivity
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.Favourite
import javax.inject.Inject


class MovieBriefsSmallAdapter @Inject constructor(private val mContext: Context) :
    RecyclerView.Adapter<MovieBriefsSmallAdapter.MovieViewHolder>() {

    private var mMovies = ArrayList<MovieBrief>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_show_small, parent, false)
        )
    }
    fun submitList(movieList: List<MovieBrief>) {
        this.mMovies = movieList as ArrayList<MovieBrief>
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(mContext.getApplicationContext())
            .load(Constants.IMAGE_LOADING_BASE_URL_342 + mMovies[position].posterPath)
            .asBitmap()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.moviePosterImageView)
        if (mMovies[position].title != null) holder.movieTitleTextView.setText(mMovies[position].title) else holder.movieTitleTextView.text =
            ""
        if (Favourite.isMovieFav(mContext, mMovies[position].id)) {
            holder.movieFavImageButton.setImageResource(R.mipmap.ic_favorite_black_18dp)
            holder.movieFavImageButton.isEnabled = false
        } else {
            holder.movieFavImageButton.setImageResource(R.mipmap.ic_favorite_border_black_18dp)
            holder.movieFavImageButton.isEnabled = true
        }
    }

    override fun getItemCount(): Int {
        return mMovies.size
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieCard: CardView
        var moviePosterImageView: ImageView
        var movieTitleTextView: TextView
        var movieFavImageButton: ImageButton

        init {
            movieCard = itemView.findViewById(R.id.card_view_show_card)
            moviePosterImageView = itemView.findViewById(R.id.image_view_show_card) as ImageView
            movieTitleTextView = itemView.findViewById(R.id.text_view_title_show_card)
            movieFavImageButton = itemView.findViewById(R.id.image_button_fav_show_card)
            moviePosterImageView.getLayoutParams().width =
                ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31).toInt())
            moviePosterImageView.getLayoutParams().height =
                ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31 / 0.66).toInt())
            movieCard.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    val intent = Intent(mContext, MovieDetailActivity::class.java)
                    intent.putExtra(Constants.MOVIE_ID, mMovies[adapterPosition].id)
                    mContext.startActivity(intent)
                }
            })
            movieFavImageButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View) {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    Favourite.addMovieToFav(
                        mContext,
                        mMovies[adapterPosition].id,
                        mMovies[adapterPosition].posterPath,
                        mMovies[adapterPosition].title
                    )
                    movieFavImageButton.setImageResource(R.mipmap.ic_favorite_black_18dp)
                    movieFavImageButton.isEnabled = false
                }
            })
        }
    }

}