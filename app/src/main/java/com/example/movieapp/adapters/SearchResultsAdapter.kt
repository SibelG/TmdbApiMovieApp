package com.example.movieapp.adapters


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.movieapp.Network.Movies.MovieBrief
import com.example.movieapp.R
import com.example.movieapp.activities.MovieDetailActivity
import com.example.movieapp.activities.PersonDetailActivity
import com.example.movieapp.activities.TVShowDetailActivity
import com.example.movieapp.search.SearchResult
import com.example.movieapp.utils.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class SearchResultsAdapter @Inject constructor(private val mContext: Context) :
    RecyclerView.Adapter<SearchResultsAdapter.ResultViewHolder>() {

    private var mSearchResults = ArrayList<SearchResult>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_search_result, parent, false)
        )
    }
    fun submitList(searchList: List<SearchResult>) {
        this.mSearchResults = searchList as ArrayList<SearchResult>
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {

        holder.cardView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.translate)

        if (mSearchResults[position].media_type != null && mSearchResults[position].media_type
                .equals("movie") || mSearchResults[position].media_type
                .equals("tv"))

                    Glide.with(mContext.getApplicationContext())
                        .load(Constants.IMAGE_LOADING_BASE_URL_342 + mSearchResults[position].poster_path)
                        .asBitmap()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.posterImageView)

       else
            Glide.with(mContext.getApplicationContext())
                .load(Constants.IMAGE_LOADING_BASE_URL_342 + mSearchResults[position].profile_path)
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.posterImageView)

        if (mSearchResults[position].name != null && !mSearchResults[position].name.trim()
                .isEmpty()
        ) holder.nameTextView.setText(
            mSearchResults[position].name
        ) else holder.nameTextView.text = mSearchResults[position].title

        if (mSearchResults[position].media_type != null && mSearchResults[position].media_type
                .equals("movie")
        ) holder.mediaTypeTextView.setText(R.string.movie) else if (mSearchResults[position].media_type != null && mSearchResults[position].media_type
                .equals("tv")
        ) holder.mediaTypeTextView.setText(R.string.tv_show) else if (mSearchResults[position].media_type != null && mSearchResults[position].media_type
                .equals("person")
        ) holder.mediaTypeTextView.setText(R.string.person) else holder.mediaTypeTextView.text =
            ""
        if (mSearchResults[position].overview != null && !mSearchResults[position].overview
                .trim().isEmpty()
        ) holder.overviewTextView.setText(
            mSearchResults[position].overview
        ) else holder.overviewTextView.text =
            ""
        if (mSearchResults[position].release_date != null && !mSearchResults[position].release_date
                .trim().isEmpty()
        ) {
            val sdf1 = SimpleDateFormat("yyyy-MM-dd")
            val sdf2 = SimpleDateFormat("yyyy")
            try {
                val releaseDate: Date = sdf1.parse(mSearchResults[position].release_date)
                holder.yearTextView.setText(sdf2.format(releaseDate))
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        } else {
            holder.yearTextView.text = ""
        }
    }

    override fun getItemCount(): Int {
        return mSearchResults.size
    }

    inner class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cardView: CardView
        var posterImageView: ImageView
        var nameTextView: TextView
        var mediaTypeTextView: TextView
        var overviewTextView: TextView
        var yearTextView: TextView
        lateinit var container: FrameLayout

        init {
            cardView = itemView.findViewById(R.id.card_view_search)
            posterImageView = itemView.findViewById(R.id.image_view_poster_search) as ImageView
            nameTextView = itemView.findViewById(R.id.text_view_name_search)
            mediaTypeTextView = itemView.findViewById(R.id.text_view_media_type_search)
            overviewTextView = itemView.findViewById(R.id.text_view_overview_search)
            yearTextView = itemView.findViewById(R.id.text_view_year_search)
            posterImageView.getLayoutParams().width =
                ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31).toInt())
            posterImageView.getLayoutParams().height =
                ((mContext.getResources().getDisplayMetrics().widthPixels * 0.31 / 0.66).toInt())
            cardView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    if (mSearchResults[adapterPosition].media_type.equals("movie")) {
                        val intent = Intent(mContext, MovieDetailActivity::class.java)
                        intent.putExtra(Constants.MOVIE_ID, mSearchResults[adapterPosition].id)
                        mContext.startActivity(intent)
                    } else if (mSearchResults[adapterPosition].media_type.equals("tv")) {
                        val intent = Intent(mContext, TVShowDetailActivity::class.java)
                        intent.putExtra(
                            Constants.TV_SHOW_ID,
                            mSearchResults[adapterPosition].id
                        )
                        mContext.startActivity(intent)
                    } else if (mSearchResults[adapterPosition].media_type.equals("person")) {
                        val intent = Intent(mContext, PersonDetailActivity::class.java)
                        intent.putExtra(
                            Constants.PERSON_ID,
                            mSearchResults[adapterPosition].id
                        )
                        mContext.startActivity(intent)
                    }
                }
            })
        }
    }

}