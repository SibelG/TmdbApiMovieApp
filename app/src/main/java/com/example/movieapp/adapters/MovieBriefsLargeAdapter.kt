package com.example.movieapp.adapters


import android.content.Context
import android.content.Intent
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movieapp.Network.Movies.MovieBrief
import com.example.movieapp.R
import com.example.movieapp.activities.MovieDetailActivity
import com.example.movieapp.utils.Constants
import com.example.movieapp.utils.Favourite.addMovieToFav
import com.example.movieapp.utils.Favourite.isMovieFav
import javax.inject.Inject


class MovieBriefsLargeAdapter @Inject constructor(
    private val mContext: Context,

) :
    RecyclerView.Adapter<MovieBriefsLargeAdapter.MovieViewHolder>() {
    private var mMovies = ArrayList<MovieBrief>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieBriefsLargeAdapter.MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_show_large, parent, false)
        )
    }
    fun submitList(movieList: List<MovieBrief>) {
        this.mMovies = movieList as ArrayList<MovieBrief>
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        Glide.with(mContext.applicationContext)
            .load(Constants.IMAGE_LOADING_BASE_URL_780.toString() + mMovies[position].backdropPath)
            .asBitmap()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.moviePosterImageView)
        if (mMovies[position].title != null) holder.movieTitleTextView.text =
            mMovies[position].title else holder.movieTitleTextView.text =
            ""
        if (mMovies[position].voteAverage != null && mMovies[position].voteAverage!! > 0) {
            holder.movieRatingTextView.visibility = View.VISIBLE
            holder.movieRatingTextView.text =
                String.format("%.1f", mMovies[position].voteAverage) + Constants.RATING_SYMBOL
        } else {
            holder.movieRatingTextView.visibility = View.GONE
        }
        setGenres(holder, mMovies[position])
        if (isMovieFav(mContext, mMovies[position].id)) {
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

    private fun setGenres(holder: MovieBriefsLargeAdapter.MovieViewHolder, movie: MovieBrief) {
        var genreString = ""
       /* for (i in movie.genreIds!!.indices) {
            if (movie.genreIds[i] == null) continue
            if (MovieGenres.getGenreName(movie.genreIds[i]) == null) continue
            genreString += MovieGenres.getGenreName(movie.genreIds[i]).toString() + ", "
        }
        if (!genreString.isEmpty()) holder.movieGenreTextView.text =
            genreString.substring(0, genreString.length - 2) else holder.movieGenreTextView.text =
            ""*/
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieCard: CardView
        var imageLayout: RelativeLayout
        var moviePosterImageView: ImageView
        var movieTitleTextView: TextView
        var movieRatingTextView: TextView
        var movieGenreTextView: TextView
        var movieFavImageButton: ImageButton

        init {
            movieCard = itemView.findViewById<View>(R.id.card_view_show_card) as CardView
            imageLayout = itemView.findViewById<View>(R.id.image_layout_show_card) as RelativeLayout
            moviePosterImageView =
                itemView.findViewById<View>(R.id.image_view_show_card) as ImageView
            movieTitleTextView =
                itemView.findViewById<View>(R.id.text_view_title_show_card) as TextView
            movieRatingTextView =
                itemView.findViewById<View>(R.id.text_view_rating_show_card) as TextView
            movieGenreTextView =
                itemView.findViewById<View>(R.id.text_view_genre_show_card) as TextView
            movieFavImageButton =
                itemView.findViewById<View>(R.id.image_button_fav_show_card) as ImageButton
            imageLayout.layoutParams.width =
                (mContext.resources.displayMetrics.widthPixels * 0.9).toInt()
            imageLayout.layoutParams.height =
                (mContext.resources.displayMetrics.widthPixels * 0.9 / 1.77).toInt()
            movieCard.setOnClickListener {
                val intent = Intent(mContext, MovieDetailActivity::class.java)
                intent.putExtra(Constants.MOVIE_ID, mMovies[adapterPosition].id)
                mContext.startActivity(intent)
            }
            movieFavImageButton.setOnClickListener { view ->
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                addMovieToFav(
                    mContext,
                    mMovies[adapterPosition].id,
                    mMovies[adapterPosition].posterPath,
                    mMovies[adapterPosition].title
                )
                movieFavImageButton.setImageResource(R.mipmap.ic_favorite_black_18dp)
                movieFavImageButton.isEnabled = false
            }
        }
    }
}
/*
class MovieBriefsLargeAdapter(private val mContext: Context): RecyclerView.Adapter<MovieBriefsLargeAdapter.MovieBriefViewHolder>(){

    inner class MovieBriefViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<MovieBrief>(){
        override fun areItemsTheSame(oldItem: MovieBrief, newItem: MovieBrief): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieBrief, newItem: MovieBrief): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitList(list: List<MovieBrief>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieBriefViewHolder {
        return MovieBriefViewHolder(
            LayoutInflater.from(
                mContext
            ).inflate(
                R.layout.item_show_large,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MovieBriefViewHolder, position: Int) {

        val item = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(mContext.applicationContext)
                .load(Constants.IMAGE_LOADING_BASE_URL_780.toString() + item.backdropPath)
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(moviePosterImageView)
            if (item.title != null) movieTitleTextView.text =
                item.title else movieTitleTextView.text =
                ""
            if (item.voteAverage != null && item.voteAverage!! > 0) {
                movieRatingTextView.visibility = View.VISIBLE
                movieRatingTextView.text =
                    String.format("%.1f", item.voteAverage) + Constants.RATING_SYMBOL
            } else {
                movieRatingTextView.visibility = View.GONE
            }
            //setGenres(holder, mMovies[position])
            if (isMovieFav(mContext, item.id)) {
                movieFavImageButton.setImageResource(R.mipmap.ic_favorite_black_18dp)
                movieFavImageButton.isEnabled = false
            } else {
                movieFavImageButton.setImageResource(R.mipmap.ic_favorite_border_black_18dp)
                movieFavImageButton.isEnabled = true
            }
            /*tvName.text = "${item.MovieBrief_name}"
            tvSalary.text = "Salary: Rs.${item.MovieBrief_salary}"
            tvAge.text = "Age: ${item.MovieBrief_age}"*/
        }

    }
}
*/